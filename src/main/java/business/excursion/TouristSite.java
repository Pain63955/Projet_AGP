package business.excursion;

public abstract class TouristSite implements PriceableElement{
	
	private String name;
    private String description;
    private Address address;
    private double price;
    private String transport;
    
    public TouristSite() {
    	
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
	public void setPrice(double price) {
		this.price = price;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

}
