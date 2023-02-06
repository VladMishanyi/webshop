package org.example.model;

import java.util.Objects;

public class VatRules extends Model {
    private String area;
    private double rate;
    private Country country;
    private Type type;

    public VatRules() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VatRules vatRules = (VatRules) o;
        return Double.compare(vatRules.rate, rate) == 0
                && area.equals(vatRules.area)
                && country == vatRules.country
                && type == vatRules.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, rate, country, type);
    }

    @Override
    public String toString() {
        return "VatRules{" +
                "area='" + area + '\'' +
                ", rate=" + rate +
                ", country=" + country +
                ", type=" + type +
                '}';
    }
}
