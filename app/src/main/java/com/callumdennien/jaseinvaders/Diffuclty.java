package com.callumdennien.jaseinvaders;

public enum Diffuclty {
    EASY("EASY MODE"),
    MEDIUM("MEDIUM MODE"),
    HARD("HARD MODE");

    public final String label;

    private Diffuclty(String label) {
        this.label = label;
    }
}
