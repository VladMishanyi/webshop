package org.example.model;

public enum Currency {
    DKK(1),
    NOK(2),
    SEK(3),
    GBP(4),
    EUR(5);

    private int index;
    Currency(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
