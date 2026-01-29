package business.excursion;

public class Hotel implements PriceableElement{

    private String name;
    private double nightRate;
    private String beach;
    private Address address;
    private String description;
    private int grade;
    private int id;

    public Hotel() {

    }

    @Override
    public double getPrice() { 
        return this.nightRate; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNightRate() {
        return nightRate;
    }

    public void setNightRate(double nightRate) {
        this.nightRate = nightRate;
    }

    public String getBeach() {
        return beach;
    }

    public String setBeach(String beach) {
        return this.beach = beach;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



}