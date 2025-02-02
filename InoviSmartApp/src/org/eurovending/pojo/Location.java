package org.eurovending.pojo;

public class Location {
	public int id;
	private String locationName;
	private String locationAdress;
	private InterfaceParam interfaceParam;

	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Location(int id) {
		this.id = id;
	}




	public Location(String locationName, String locationAdress) {
		super();
		this.locationName = locationName;
		this.locationAdress = locationAdress;
	}




	public Location(int id, String locationName, String locationAdress) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
	
	}


	public Location(String locationName, String locationAdress, InterfaceParam interfaceParam) {
		super();
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.interfaceParam = interfaceParam;
	}




	public Location(int id, String locationName, String locationAdress, InterfaceParam interfaceParam) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.interfaceParam = interfaceParam;
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationAdress() {
		return locationAdress;
	}
	public void setLocationAdress(String locationAdress) {
		this.locationAdress = locationAdress;
	}




	public InterfaceParam getInterfaceParam() {
		return interfaceParam;
	}




	public void setInterfaceParam(InterfaceParam interfaceParam) {
		this.interfaceParam = interfaceParam;
	}




	
}
