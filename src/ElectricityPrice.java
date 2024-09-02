import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class ElectricityPrice {
    // Making hourInterval final, no further changes is needed for that string
    private final String hourInterval;
    private int price;

    // Constructor
    public ElectricityPrice(String hourInterval, int price) {
        this.hourInterval = hourInterval;
        this.price = price;
    }

    String getHourInterval() {
        return hourInterval;
    }

    int getPrice() {
        return price;
    }

    // when we want to change prices (with method updatePrices) we use this method
    void setPrice(int price) {
        this.price = price;
    }

    // Create a method who adds some initial price-values
    static ArrayList<ElectricityPrice> getInitialValues() {
        // Creating an ArrayList inside this method. Now we can create an array with values directly,
        // instead of creating a blank array and THEN add values
        ArrayList<ElectricityPrice> electricityPrices = new ArrayList<>();

        electricityPrices.add(new ElectricityPrice("00-01", 270));
        electricityPrices.add(new ElectricityPrice("01-02", 103));
        electricityPrices.add(new ElectricityPrice("02-03", 22));
        electricityPrices.add(new ElectricityPrice("03-04", 71));
        electricityPrices.add(new ElectricityPrice("04-05", 67));
        electricityPrices.add(new ElectricityPrice("05-06", 114));
        electricityPrices.add(new ElectricityPrice("06-07", 2502));
        electricityPrices.add(new ElectricityPrice("07-08", 1891));
        electricityPrices.add(new ElectricityPrice("08-09", 1383));
        electricityPrices.add(new ElectricityPrice("09-10", 901));
        electricityPrices.add(new ElectricityPrice("10-11", 804));
        electricityPrices.add(new ElectricityPrice("11-12", 2606));
        electricityPrices.add(new ElectricityPrice("12-13", 5176));
        electricityPrices.add(new ElectricityPrice("13-14", 5234));
        electricityPrices.add(new ElectricityPrice("14-15", 3492));
        electricityPrices.add(new ElectricityPrice("15-16", 4235));
        electricityPrices.add(new ElectricityPrice("16-17", 6730));
        electricityPrices.add(new ElectricityPrice("17-18", 7021));
        electricityPrices.add(new ElectricityPrice("18-19", 6293));
        electricityPrices.add(new ElectricityPrice("19-20", 4291));
        electricityPrices.add(new ElectricityPrice("20-21", 3497));
        electricityPrices.add(new ElectricityPrice("21-22", 1985));
        electricityPrices.add(new ElectricityPrice("22-23", 1249));
        electricityPrices.add(new ElectricityPrice("23-00", 693));

        return electricityPrices;
    }

    // Method that change order of list with cheapest price first
    static void showSortedPriceList(ArrayList<ElectricityPrice> chosenArray) {
        chosenArray.sort(Comparator.comparingInt(ElectricityPrice::getPrice));

        System.out.println("\nLista över billigast pris samt vid vilket timmesintervall dessa priser är:\n");
        for (ElectricityPrice electricityPrice : chosenArray) {
            System.out.println("Tidsintervall: " + electricityPrice.getHourInterval() + "h, pris: " + electricityPrice.getPrice() + " öre/kWh");
        }
        sortPricesByTimeInterval(chosenArray);
        backtoMenu();
    }

    // method that present min, max and average prices
    static void presentMinMaxAverage(ArrayList<ElectricityPrice> chosenArray) {
        System.out.println("\nPresentation av min, max och snittpris:");
        // get cheapest price
        ElectricityPrice minPrice = chosenArray.stream().min(Comparator.comparingInt(ElectricityPrice::getPrice)).get();
        System.out.println("Minsta pris: " + minPrice.price + " öre/kWh, infaller mellan: " + minPrice.hourInterval + "h");

        // get most expensive price
        ElectricityPrice maxPrice = chosenArray.stream().max(Comparator.comparingInt(ElectricityPrice::getPrice)).get();
        System.out.println("Dyrast pris: " + maxPrice.price + " öre/kWh, infaller mellan: " + maxPrice.hourInterval + "h");

        // get average price
        int total = 0;
        for (ElectricityPrice electricityPrice : chosenArray) {
            total += electricityPrice.getPrice();
        }
        int averagePrice = total / chosenArray.size();
        System.out.println("Snitt pris: " + averagePrice + " öre/kWh");
        backtoMenu();
    }

    // method to update prices
    static void updatePrices(ArrayList<ElectricityPrice> chosenArray) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nAnge nytt pris för varje tidsintervall:");
        System.out.println("(Ange e för att avbryta)");

        for (ElectricityPrice price : chosenArray) {
            System.out.println("Nytt pris för " + price.hourInterval + ":");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("e")) {
                System.out.println("Inmatning avslutades");
                break;
            }
            try {
                int newPrice = Integer.parseInt(input);
                price.setPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde, ange heltal");
            }
        }
    }

    // This method makes us press Enter before going back to menu
    private static void backtoMenu() {
        System.out.println("\nÅtergå till huvudmeny med Enter");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /*
    method that sort our pricelist with chronological order.
    When we use option 3 (sortera) our list changes order (cheapest price first etc),
    so when we go to option 1 (Inmatning) after option 3, our list is rearanged.
    This method handles that problem and makes the list in chronological order again.
    */
    static void sortPricesByTimeInterval(ArrayList<ElectricityPrice> chosenArray) {
        chosenArray.sort(Comparator.comparing(ElectricityPrice::getHourInterval));
    }

    // method with sliding window technique that calculate cheapest 4 hours to charge car
    static void bestChargingTime(ArrayList<ElectricityPrice> chosenArray) {
        int minSum = Integer.MAX_VALUE;
//        int minSum = 1234;
        int bestStartIndex = 0;

        for (int i = 0; i <= chosenArray.size() - 4; i++) {
            int currentSum = 0;

            for (int j = i; j < i + 4; j++) {
                currentSum += chosenArray.get(j).getPrice();
            }
            if (currentSum < minSum) {
                minSum = currentSum;
                bestStartIndex = i;
            }
        }

        double averagePrice = (double) minSum / 4;

        System.out.println("Bästa laddningstid för 4 timmar börjar vid: " + chosenArray.get(bestStartIndex).getHourInterval());
        System.out.println("Medelpris för dessa 4 timmar: " + String.format("%.2f", averagePrice) + " öre/kWh");

        backtoMenu();
    }
}