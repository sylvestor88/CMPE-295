package piserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import beans.Device;
import beans.User;
import database.CassandraDB;

public class Helper {
	
//==================get Ip of the device ===============================================
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
//======================retrieve the list of connected devices ===================================
	
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
				p.destroy();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deviceList;
	}
//========================get subnet mask of the network ========================
	public static String getSubnet(String currentIP) {
		int firstSeparator = 0;
		int lastSeparator = currentIP.lastIndexOf(".");
		return currentIP.substring(firstSeparator, lastSeparator + 1);
	}
//=========================Save the discovered devices to Database===============	
	public static void saveDeviceInDB(Device dev)
	{
		CassandraDB db = new CassandraDB();
		Session sess = db.connect();
		Mapper<Device> DeviceMapper = new MappingManager(sess).mapper(Device.class);
		DeviceMapper.save(dev);
		sess.close();
	}
//========================Save users into the database ============================	
	public static void saveUserInDB(User user)
	{
		CassandraDB db = new CassandraDB();
		Session sess = db.connect();
		Mapper<User> UserMapper = new MappingManager(sess).mapper(User.class);
		UserMapper.save(user);
		sess.close();
	}
//=============================Discover connected devices ===========================	
	public static Result<Device> getConnectedDevices()
	{
		CassandraDB db = new CassandraDB();
		Session sess = db.connect();
		Mapper<Device> DeviceMapper = new MappingManager(sess).mapper(Device.class);
		ResultSet results = sess.execute("SELECT * FROM connected_devices;");
		Result<Device> devices = DeviceMapper.map(results);
		return devices;
	}
//============================Retreive list of Users =================================	
	public static Result<User> getUsers()
	{
		CassandraDB db = new CassandraDB();
		Session sess = db.connect();
		Mapper<User> UserMapper = new MappingManager(sess).mapper(User.class);
		ResultSet results = sess.execute("SELECT * FROM users;");
		Result<User> users = UserMapper.map(results);
		return users;
	}
//============================pushing the configuration into the device==================
	public static void executePushConfFile(String host,String password){
		String command = "sshpass -p '"+password+"' scp -r /home/user/desktop/thread-1.conf admin@"+
							host+":/data/etc" ;
		executeCommand(command);
		restart(host,password);
		
	}
//======================Restart the camera=========================
	public static void restart(String host,String password){
		String command = "sshpass -p "+password+" ssh user@"+host+" reboot";
		executeCommand(command);
		
	}
//=======================command executer ===============================
	static void executeCommand(String COMMAND){

		String[] SHELL_COMMAND = {"/bin/sh","-c",COMMAND};
			String line = "";
			Process p;
			try {
				p = Runtime.getRuntime().exec(SHELL_COMMAND);
				p.waitFor();
				System.out.print(loadStream(p.getInputStream()));
	            System.err.print(loadStream(p.getErrorStream()));
	        
					p.destroy();
				}
			catch(IOException | InterruptedException ioe) {
	            ioe.printStackTrace();
	        }
	}
	static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while( (ptr = in.read()) != -1 ) {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }
	
	
		
}
