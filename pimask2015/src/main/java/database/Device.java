package database;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "pimaskapp", name = "connected_devices")
public class Device {
	
	@PartitionKey
	private UUID device_id;
	
	private String device_name;
	private String device_ip;
	private boolean notification;
	
	public UUID getDevice_id() {
		return device_id;
	}
	public void setDevice_id(UUID device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_ip() {
		return device_ip;
	}
	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}
	public boolean isNotification() {
		return notification;
	}
	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	
	
}
