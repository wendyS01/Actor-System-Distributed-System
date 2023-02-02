package service.core;

/**
 * Class to store the quotations returned by the quotation services
 * Quotation Java Bean Class
 * @author Rem
 *
 */
public class Quotation {
    public Quotation(String company, String reference, double price) {
        this.company = company;
        this.reference = reference;
        this.price = price;

    }

    private String company;
    private String reference;
    private double price;

    public Quotation() {}

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

