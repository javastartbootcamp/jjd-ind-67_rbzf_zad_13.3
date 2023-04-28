package pl.javastart.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileNameProducts = "products.csv";
        String fileNameCurrencies = "currencies.csv";
        File fileProducts = new File(fileNameProducts);
        File fileCurrencies = new File(fileNameCurrencies);

        try {
            BigDecimal sum = new BigDecimal(0);
            BigDecimal maximum = new BigDecimal(0);
            BigDecimal minimum = new BigDecimal(1000);
            List<Product> listProducts = readProductsFromFile(fileNameProducts);
            List<Currency> listCurrencies = readCurrenciesFromFile(fileNameCurrencies);
            Product productMax = null;
            Product productMin = null;

            for (Product product : listProducts) {
                BigDecimal priceInEuro = getProductPriceInEuro(product, listCurrencies);
                sum = sum.add(priceInEuro);
                if ((priceInEuro.compareTo(maximum) >= 0)) {
                    maximum = priceInEuro.max(maximum);
                    productMax = product;
                }
                if ((priceInEuro.compareTo(minimum) <= 0)) {
                    minimum = priceInEuro.min(minimum);
                    productMin = product;
                }
            }

            System.out.println("suma cen wszystkich produktów w EUR " + sum);
            System.out.println("średnia wartośc produktu w EUR " + sum.divide(BigDecimal.valueOf(listProducts.size()), RoundingMode.UP));
            if (productMax != null) {
                System.out.println("najdroższy produkt w przeliczeniu na EUR: " + productMax.getName() + " cena " + maximum);
            }
            if (productMin != null) {
                System.out.println("najtańszy produkt w przeliczeniu na EUR: " + productMin.getName() + " cena " + minimum);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Blad odczytu pliku");
        }
    }

    private static List<Currency> readCurrenciesFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        List<Currency> listCurrencies = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] currencyAsString = line.split(";");
            Currency currency = new Currency(currencyAsString[0], currencyAsString[1]);
            listCurrencies.add(currency);
        }
        return listCurrencies;
    }

    private static List<Product> readProductsFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        List<Product> listProducts = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] productAsString = line.split(";");
            Product product = new Product(productAsString[0], productAsString[1], productAsString[2]);
            listProducts.add(product);
        }
        return listProducts;
    }

    private static BigDecimal getProductPriceInEuro(Product product, List<Currency> listCurrencies) throws FileNotFoundException {
        BigDecimal priceInLocalCurrency = BigDecimal.valueOf(Double.parseDouble(product.getPrice()));
        BigDecimal exchangeRate = findExchangeRate(product.getCurrency(), listCurrencies);
        return priceInLocalCurrency.multiply(exchangeRate);
    }

    private static BigDecimal findExchangeRate(String localCurrency, List<Currency> listCurrencies) throws FileNotFoundException {
        BigDecimal exchangeRate = null;
        for (Currency currency : listCurrencies) {
            if (currency.getName().equals(localCurrency)) {
                exchangeRate = new BigDecimal(currency.getExchangeRate());
            }
        }
        return exchangeRate;
    }
}
