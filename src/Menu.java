import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static void displayMenu(ArrayList<ElectricityPrice> testList) {
    while (true) {
        // printing out menu-choices and awaiting input from user
        System.out.println("\nElpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("e. Avsluta\n");
        System.out.print("Skriv in ditt val: ");

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        if (input.equals("1")) {
//                System.out.println("Första valet");
            ElectricityPrice.updatePrices(testList);
        } else if (input.equals("2")) {
//                System.out.println("Andra valet");
            ElectricityPrice.presentMinMaxAverage(testList);
        } else if (input.equals("3")) {
//                System.out.println("Tredje valet");
            ElectricityPrice.showSortedPriceList(testList);
        } else if (input.equals("4")) {
//                System.out.println("Fjärde valet");
            ElectricityPrice.bestChargingTime(testList);
        } else if (input.equalsIgnoreCase("e")) {
            System.out.println("Progrannet avslutades");
            break;
        } else {
            System.out.println("Fel val, försök igen");
        }
    }
}
}
