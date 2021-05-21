package com.callumdennien.jaseinvaders;

public enum Difficulty {
    EASY("EASY MODE", 10),
    MEDIUM("MEDIUM MODE", 20),
    HARD("HARD MODE", 30);

    public final String difficultyText;
    public final int difficultyValue;

    Difficulty(String difficultyText, int difficultyValue) {
        this.difficultyText = difficultyText;
        this.difficultyValue = difficultyValue;
    }
}
