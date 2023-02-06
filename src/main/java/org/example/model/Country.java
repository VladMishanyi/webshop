package org.example.model;

public enum Country {
    DK(1),
    NO(2),

    SE(3),
    GB(4),
    DE(5);

    private int index;
    Country(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
