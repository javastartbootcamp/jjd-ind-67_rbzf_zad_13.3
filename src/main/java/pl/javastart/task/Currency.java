package pl.javastart.task;

public class Currency {
    private String name;
    private String exchangeRate;

    public Currency(String name, String exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
