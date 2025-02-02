package org.eurovending.pojo;

public class InterfaceParam {
	
	public int idInterface;
	private String macAddress;	
	private double temperature;
	private double humidity;
	private double light;
	private double pollutionCo;
	private double pollutionGases;
	private int signalLevel;
	private String isLogin;
	private String lastOnlineDate;
	private String lastOnlineTime;
	public InterfaceParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InterfaceParam(int idInterface, String macAddress, String lastOnlineDate) {
		super();
		this.idInterface = idInterface;
		this.macAddress = macAddress;
		this.lastOnlineDate = lastOnlineDate;
	}



	public InterfaceParam(String macAddress, double temperature, double humidity, double light, double pollutionCo,
			double pollutionGases, int signalLevel, String isLogin, String lastOnlineDate) {
		super();
		this.macAddress = macAddress;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionGases = pollutionGases;
		this.signalLevel = signalLevel;
		this.isLogin = isLogin;
		this.lastOnlineDate = lastOnlineDate;
	}

	public InterfaceParam(int idInterface, String macAddress, double temperature, double humidity, double light,
			double pollutionCo, double pollutionGases, int signalLevel, String isLogin, String lastOnlineDate) {
		super();
		this.idInterface = idInterface;
		this.macAddress = macAddress;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionGases = pollutionGases;
		this.signalLevel = signalLevel;
		this.isLogin = isLogin;
		this.lastOnlineDate = lastOnlineDate;
	}

	
	public InterfaceParam(int idInterface, String macAddress, double temperature, double humidity, double light,
			double pollutionCo, double pollutionGases, int signalLevel, String isLogin, String lastOnlineDate,
			String lastOnlineTime) {
		super();
		this.idInterface = idInterface;
		this.macAddress = macAddress;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionGases = pollutionGases;
		this.signalLevel = signalLevel;
		this.isLogin = isLogin;
		this.lastOnlineDate = lastOnlineDate;
		this.lastOnlineTime = lastOnlineTime;
	}

	public int getIdInterface() {
		return idInterface;
	}
	public void setIdInterface(int idInterface) {
		this.idInterface = idInterface;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getLastOnlineDate() {
		return lastOnlineDate;
	}
	public void setLastOnlineDate(String lastOnlineDate) {
		this.lastOnlineDate = lastOnlineDate;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getLight() {
		return light;
	}
	public void setLight(double light) {
		this.light = light;
	}
	public double getPollutionCo() {
		return pollutionCo;
	}
	public void setPollutionCo(double pollutionCo) {
		this.pollutionCo = pollutionCo;
	}
	public double getPollutionGases() {
		return pollutionGases;
	}
	public void setPollutionGases(double pollutionGases) {
		this.pollutionGases = pollutionGases;
	}
	public int getSignalLevel() {
		return signalLevel;
	}
	public void setSignalLevel(int signalLevel) {
		this.signalLevel = signalLevel;
	}
	public String getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(String lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}
	
	
	
}
