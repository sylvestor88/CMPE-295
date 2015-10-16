package piserver;

public class NetworkDevice {
	
	private String ip;
	private String hostname;
	
	public NetworkDevice(String ip, String hostname)
	{
		this.ip = ip;
		this.hostname = hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}	
}
