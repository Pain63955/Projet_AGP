package business.excursion;

public class Address {
	
	private int addressId;
	private double latitude;
	private double longitude;
	private  String street;
    private  String town;
    private  String postCode;
    
	public Address(int addressId, double latitude, double longitude, String street, String town, String postCode) {
		super();
		this.addressId = addressId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.town = town;
		this.postCode = postCode;
	}

	public Address() {
		
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
    
}

