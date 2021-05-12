package com.callumdennien.jaseinvaders;

public class GamePreferences {
    private static GamePreferences instance;
    private String player_name;
    private boolean sound_effects;
    private boolean music;
    private int personal_best;
    private Difficulty difficulty;

    public GamePreferences() {
        player_name = "Anonymous";
        sound_effects = true;
        music = true;
        personal_best = 999;
        difficulty = Difficulty.EASY;
    }

    public static GamePreferences getInstance() {
        if (instance == null) {
            instance = new GamePreferences();
        }

        return instance;
    }

    public String getPlayerName() {
        return player_name;
    }

    public void setPlayerName(String player_name) {
        this.player_name = player_name;
    }

    public boolean getSoundEffects() {
        return sound_effects;
    }

    public void setSoundEffects(boolean sound_effects) {
        this.sound_effects = sound_effects;
    }

    public boolean getMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public int getPersonalBest() {
        return personal_best;
    }

    public void setPersonalBest(int personal_best) {
        this.personal_best = personal_best;
    }

    public String getDifficulty() {
        return difficulty.label;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
