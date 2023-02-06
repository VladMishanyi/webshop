package org.example.model;

public enum Type {
    ALL(1),
    ONLINE(2),
    BOOK(3);

    private int index;
    Type(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
