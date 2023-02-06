package org.example.model;

import java.util.Objects;

public class CurrencyRules extends Model {
    private Currency currency;
    private double rate;

    public CurrencyRules() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRules that = (CurrencyRules) o;
        return Double.compare(that.rate, rate) == 0 && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, rate);
    }

    @Override
    public String toString() {
        return "CurrencyRules{" +
                "currency=" + currency +
                ", rate=" + rate +
                '}';
    }

    public static class Builder {
        private Currency currency;
        private double rate;

        public Currency getCurrency() {
            return currency;
        }

        public Builder addCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public double getRate() {
            return rate;
        }

        public Builder addRate(double rate) {
            this.rate = rate;
            return this;
        }

        public CurrencyRules build() {
            CurrencyRules currencyRules = new CurrencyRules();
            currencyRules.setCurrency(currency);
            currencyRules.setRate(rate);
            return currencyRules;
        }
    }
}
