package piserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Helper {
	
	public static String executeGetInet(String command) {

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) 
			{
				if(line.indexOf("inet addr") != -1)
					return line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<NetworkDevice> executeGetDevices(String command, String currentIp) {

		ArrayList<NetworkDevice> deviceList = new ArrayList<NetworkDevice>();
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				if(line.indexOf("report") != -1 & line.indexOf(currentIp) == -1)
				{
					String[] report = line.split(" ");
					if(report.length == 6)
					{
						NetworkDevice newDevice = new NetworkDevice(report[5].substring(1, report[5].length() - 1), report[4]);
						deviceList.add(newDevice);
					}
					else
					{
						NetworkDevice newDevice = new NetworkDevice(report[4], "No Hostname");
						deviceList.add(newDevice);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deviceList;
	}

	public static String getSubnet(String currentIP) {
		int firstSeparator = 0;
		int lastSeparator = currentIP.lastIndexOf(".");
		return currentIP.substring(firstSeparator, lastSeparator + 1);
	}
}
