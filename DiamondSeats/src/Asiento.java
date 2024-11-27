// Class que define los asientos que se usan en el estadio
public class Asiento {
    private String section;
    private int row;
    private int seatNumber;
    private double price;
    // Constructor
    public Asiento(String section, int row, int seatNumber, double price) {
        this.section = section;
        this.row = row;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }
}

