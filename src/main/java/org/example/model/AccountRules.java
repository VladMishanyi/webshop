package org.example.model;

import java.util.Objects;

public class AccountRules extends Model {
    private String name;
    private String email;
    private Currency inputCurrency;
    private Currency outputCurrency;
    private Country country;

    public AccountRules() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Currency getInputCurrency() {
        return inputCurrency;
    }

    public void setInputCurrency(Currency inputCurrency) {
        this.inputCurrency = inputCurrency;
    }

    public Currency getOutputCurrency() {
        return outputCurrency;
    }

    public void setOutputCurrency(Currency outputCurrency) {
        this.outputCurrency = outputCurrency;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRules that = (AccountRules) o;
        return name.equals(that.name)
                && email.equals(that.email)
                && inputCurrency == that.inputCurrency
                && outputCurrency == that.outputCurrency
                && country == that.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, inputCurrency, outputCurrency, country);
    }

    @Override
    public String toString() {
        return "AccountRules{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", inputCurrency=" + inputCurrency +
                ", outputCurrency=" + outputCurrency +
                ", country=" + country +
                '}';
    }

    public static class Builder {
        private String name;
        private String email;
        private Currency inputCurrency;
        private Currency outputCurrency;
        private Country country;

        public String getName() {
            return name;
        }

        public Builder addName(String name) {
            this.name = name;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public Currency getInputCurrency() {
            return inputCurrency;
        }

        public Builder addInputCurrency(Currency inputCurrency) {
            this.inputCurrency = inputCurrency;
            return this;
        }

        public Currency getOutputCurrency() {
            return outputCurrency;
        }

        public Builder addOutputCurrency(Currency outputCurrency) {
            this.outputCurrency = outputCurrency;
            return this;
        }

        public Country getCountry() {
            return country;
        }

        public Builder addCountry(Country country) {
            this.country = country;
            return this;
        }

        public AccountRules build() {
            AccountRules accountRules = new AccountRules();
            accountRules.setName(name);
            accountRules.setEmail(email);
            accountRules.setInputCurrency(inputCurrency);
            accountRules.setOutputCurrency(outputCurrency);
            accountRules.setCountry(country);
            return accountRules;
        }
    }


}
