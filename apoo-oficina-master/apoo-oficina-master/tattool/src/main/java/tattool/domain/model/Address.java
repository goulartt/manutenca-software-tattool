package tattool.domain.model;

public class Address {
	private Integer id;
	
	private String street;
	private String neighborhood;
	private String number;
	private Integer zipCode;
	private String city;
	private String state;
	
	public Address() {
		super();
		this.id = 0;
		this.street = "";
		this.neighborhood = "";
		this.number = "";
		this.zipCode = 0;
		this.city = "";
		this.state = "";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
