package piserver;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class PiController {

	ArrayList<NetworkDevice> deviceList = new ArrayList<NetworkDevice>();
	
	@RequestMapping(value = "/find_device", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<ArrayList<NetworkDevice>> listDevices() {

		String currentIp = null;
		String output = Helper.executeGetInet("ifconfig eth0");
		String[] ip = output.split(" ");
		
		for (int i = 0; i < ip.length; i++) {
			if (ip[i].contains("addr"))
				currentIp = ip[i].substring(ip[i].lastIndexOf(":") + 1);
		}

		String subnet = Helper.getSubnet(currentIp);
		ArrayList<NetworkDevice> deviceList = Helper.executeGetDevices("nmap -sP " + subnet + "1-255", currentIp);
		
		if(!deviceList.isEmpty())
		{
			return new ResponseEntity<ArrayList<NetworkDevice>>(deviceList, HttpStatus.OK);
		}
		
		return new ResponseEntity<ArrayList<NetworkDevice>>(HttpStatus.NOT_FOUND);
	}
}
