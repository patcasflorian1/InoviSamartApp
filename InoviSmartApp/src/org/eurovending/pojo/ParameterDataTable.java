package org.eurovending.pojo;


public class ParameterDataTable {
	private int id;
	private int idLocation;
	private String monthly;
	private String year;
	private double temperature;
	private double humidity;
	private double light;
	private double pollutionCo;
	private double pollutionSound;
	private String date;
	private String time;
	public ParameterDataTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	



	public ParameterDataTable(int id, int idLocation, String monthly, String year, double temperature, double humidity,
			double light, double pollutionCo, double pollutionSound, String date, String time) {
		
		this.id = id;
		this.idLocation = idLocation;
		this.monthly = monthly;
		this.year = year;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionSound = pollutionSound;
		this.date = date;
		this.time = time;
	}

	public ParameterDataTable( int idLocation, String monthly, String year, double temperature, double humidity,
			double light, double pollutionCo, double pollutionSound, String date, String time) {
	
	
		this.idLocation = idLocation;
		this.monthly = monthly;
		this.year = year;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionSound = pollutionSound;
		this.date = date;
		this.time = time;
	}



	public ParameterDataTable(int id, double temperature, double humidity, double light, double pollutionCo,
			double pollutionSound, String date, String time) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionSound = pollutionSound;
		this.date = date;
		this.time = time;
	}





	public ParameterDataTable(int idLocation, String monthly, String year, double temperature, double humidity,
			double light, double pollutionCo, double pollutionSound) {
		super();
		this.idLocation = idLocation;
		this.monthly = monthly;
		this.year = year;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.pollutionCo = pollutionCo;
		this.pollutionSound = pollutionSound;
	}





	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdLocation() {
		return idLocation;
	}



	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}



	public String getMonthly() {
		return monthly;
	}



	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
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

	
	


	public double getPollutionSound() {
		return pollutionSound;
	}





	public void setPollutionSound(double pollutionSound) {
		this.pollutionSound = pollutionSound;
	}





	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
