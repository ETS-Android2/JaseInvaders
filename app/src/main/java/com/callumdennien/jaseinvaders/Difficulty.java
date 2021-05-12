package com.callumdennien.jaseinvaders;

public enum Difficulty {
    EASY("EASY MODE"),
    MEDIUM("MEDIUM MODE"),
    HARD("HARD MODE");

    public final String label;

    private Difficulty(String label) {
        this.label = label;
    }
}
