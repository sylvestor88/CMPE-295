package piserver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Result;

import beans.Device;
import beans.Message;
import beans.User;

@RestController
@ComponentScan
@EnableAutoConfiguration
@RequestMapping("/pimask/")
public class PiController {
	
	ArrayList<NetworkDevice> deviceList = new ArrayList<NetworkDevice>();
	
	// finds devices on the same network as the Pi Server
	@RequestMapping(value = "find_devices", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<ArrayList<NetworkDevice>> listDevices() {

		String currentIp = Helper.executeGetInet("ifconfig eth0");
		String subnet = Helper.getSubnet(currentIp);
		ArrayList<NetworkDevice> deviceList = Helper.executeGetDevices("nmap -sP " + subnet + "1-255", currentIp);
		
		if(!deviceList.isEmpty())
		{
			return new ResponseEntity<ArrayList<NetworkDevice>>(deviceList, HttpStatus.OK);
		}
		
		return new ResponseEntity<ArrayList<NetworkDevice>>(HttpStatus.NOT_FOUND);
	}
	
	// save configured device to the database
		@RequestMapping(value="save_device", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody ResponseEntity<Message> saveDevice(@Valid @RequestBody Device dev)
		{
			dev.setData_location("/home/pi/pimask_data/" + dev.getName() + "_" + dev.getDevice_ip());
			String serverIp = Helper.executeGetInet("ifconfig eth0");
			dev.setNetwork_server(serverIp);
			dev.setTarget_dir("/data/media/motioneye_" + serverIp.replaceAll("\\.", "_") + "_" + dev.getNetwork_share_name() + "_" + dev.getNetwork_username() + "/" + dev.getName() + "_" + dev.getDevice_ip());
			ConfFileTemplate.createConfigFile(dev);
			Helper.executePushConfFile(dev.getDevice_ip());
			Helper.saveDeviceInDB(dev);
			Message msg = new Message("Device " + dev.getName() + " with IP " + dev.getDevice_ip() + " is now connected.");
			return new ResponseEntity<Message>(msg, HttpStatus.CREATED);
		}
		
	// update configured device to the database
		@RequestMapping(value="edit_device/{id:.+}", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody ResponseEntity<Message> editDevice(
				@PathVariable("id") String devId, @Valid @RequestBody Device dev)
		{
			dev.setData_location("/home/pi/pimask_data/" + dev.getName() + "_" + dev.getDevice_ip());
			String serverIp = Helper.executeGetInet("ifconfig eth0");
			dev.setNetwork_server(serverIp);
			dev.setTarget_dir("/data/media/motioneye_" + serverIp.replaceAll("\\.", "_") + "_" + dev.getNetwork_share_name() + "_" + dev.getNetwork_username() + "/" + dev.getName() + "_" + dev.getDevice_ip());
			ConfFileTemplate.createConfigFile(dev);
			Helper.executePushConfFile(dev.getDevice_ip());
			Helper.saveDeviceInDB(dev);
			Message msg = new Message("Device " + dev.getName() + " with IP " + dev.getDevice_ip() + " has been updated.");
			return new ResponseEntity<Message>(msg, HttpStatus.CREATED);
		}
		
		
	// get list of configured device from the database
	@RequestMapping(value="connected", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<List<Device>> getDevices()
	{
		List<Device> list = null;
		Result<Device> results = Helper.getConnectedDevices();
		list = results.all();
		
		if(!(list == null))
		{
			return new ResponseEntity<List<Device>>(list, HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<Device>>(HttpStatus.NOT_FOUND);	
	}
	
	// get a device details from the database
		@RequestMapping(value="findDevice/{id:.+}", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody ResponseEntity<Device> findDevice(
				@PathVariable("id") String devId)
		{
			Device dev = Helper.findDeviceInDB(devId);
			return new ResponseEntity<Device>(dev, HttpStatus.OK);
		}
		
	// delete a device
	@RequestMapping(value="deleteDevice/{id:.+}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Message> deleteDevice(
			@PathVariable("id") String devId)
	{
		Helper.deleteDeviceInDB(devId);
		Message msg = new Message("Device Deleted from the Sever");
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
	}
	
	// save user to the database
	@RequestMapping(value="save_user", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Message> saveUser(@Valid @RequestBody User user)
	{
		UUID id = UUIDs.random();
		user.setUser_id(id);
		Helper.saveUserInDB(user);
		Message msg = new Message("User record for " + user.getFirst_name() + " with mail Id " + user.getEmail() + " saved.");
		return new ResponseEntity<Message>(msg, HttpStatus.CREATED);
	}
	
	// get a user from the database
	@RequestMapping(value="users/{user_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<User> getUserById(
			@PathVariable("user_id") UUID userId)
	{
		User user = Helper.getUserById(userId);
		if(!(user == null))
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);	
	}
	
	// get list of users from the database
	@RequestMapping(value="users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<List<User>> getUsers()
	{
		List<User> list = null;
		Result<User> results = Helper.getUsers();
		list = results.all();
		
		if(!(list == null))
		{
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);	
	}
	
	//Update user in the database
	@RequestMapping(value="edit_user/{user_id}", method=RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody ResponseEntity<Message> editUser(
			@RequestBody User user, @PathVariable("user_id") UUID userId)
	{
		Helper.saveUserInDB(user);;
		Message msg = new Message("User record for " + user.getFirst_name() + " with mail Id " + user.getEmail() + " updated.");
		return new ResponseEntity<Message>(msg, HttpStatus.CREATED);
	}
	
	//Delete user from the database
		@RequestMapping(value="delete_user/{user_id}", method=RequestMethod.DELETE, produces = "application/json")
		public @ResponseBody ResponseEntity<Message> deleteUser(
				@PathVariable("user_id") UUID userId)
		{
			Helper.deleteUserInDB(userId);
			Message msg = new Message("User Deleted from the Sever");
			return new ResponseEntity<Message>(msg, HttpStatus.OK);
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(PiController.class, args);
	}
	
}
