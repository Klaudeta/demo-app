package org.vaadin.demo;

public class Address {

	private Integer id;
	
	private String country;
	
	private String state;
	
	private String name;
	
	private String address;

	
	
	public Address() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Address(Integer id, String country, String state, String name, String address) {
		super();
		this.id = id;
		this.country = country;
		this.state = state;
		this.name = name;
		this.address = address;
	}



	public boolean isNew() {
		return id == null;
	}
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
