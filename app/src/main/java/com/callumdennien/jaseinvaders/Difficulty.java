package com.callumdennien.jaseinvaders;

public enum Difficulty {
    EASY("EASY MODE"),
    MEDIUM("MEDIUM MODE"),
    HARD("HARD MODE");

    public final String difficultyText;

    Difficulty(String difficultyText) {
        this.difficultyText = difficultyText;
    }
}
