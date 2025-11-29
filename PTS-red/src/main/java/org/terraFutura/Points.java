package org.terraFutura;

public class Points {
    private int value;

    public Points(int value) {
        if (value < 0) {
           throw new IllegalArgumentException("The value is less then 0");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void addPoints(int value){
        if (value < 0) throw new IllegalArgumentException("The value is less then 0");
        this.value+=value;
    }
}
