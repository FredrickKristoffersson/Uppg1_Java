import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create ArrayList with initial values
        ArrayList<ElectricityPrice> priceList = ElectricityPrice.getInitialValues();

        Menu.displayMenu(priceList);
    }
}
