package pl.javastart.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "C:/Users/ASUS/IdeaProjects/jjd-ind-67_rbzf_zad_13.3/src/main/resources/products.csv";
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        BigDecimal sum = new BigDecimal(0);
        BigDecimal maximum = new BigDecimal(0);
        BigDecimal minimum = new BigDecimal(1000);
        int lines = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines++;
            String[] product = line.split(";");
            BigDecimal priceInEuro = getProductPriceInEuro(product);
            sum = sum.add(priceInEuro);
            maximum = priceInEuro.max(maximum);
            minimum = priceInEuro.min(minimum);

        }
        System.out.println("suma cen wszystkich produktów w EUR " + sum);
        System.out.println("średnia wartośc produktu w EUR " + sum.divide(BigDecimal.valueOf(lines), RoundingMode.UP));
        System.out.println("najdroższy produkt w przeliczeniu na EUR " + maximum);
        System.out.println("najtańszy produkt w przeliczeniu na EUR " + minimum);
    }

    private static BigDecimal getProductPriceInEuro(String[] product) throws FileNotFoundException {
        BigDecimal priceInLocalCurrency = new BigDecimal(product[1]);
        BigDecimal exchangeRate = findExchangeRate(product[2]);
        return priceInLocalCurrency.multiply(exchangeRate);
    }

    private static BigDecimal findExchangeRate(String localCurrency) throws FileNotFoundException {
        BigDecimal exchangeRate = null;
        String filePath = "C:/Users/ASUS/IdeaProjects/jjd-ind-67_rbzf_zad_13.3/src/main/resources/currencies.csv";
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] currency = line.split(";");
            if (currency[0].equals(localCurrency)) {
                exchangeRate = new BigDecimal(currency[1]);
            }
        }
        return exchangeRate;
    }
}
