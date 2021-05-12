package com.callumdennien.jaseinvaders;

public class GamePreferences {
    private static GamePreferences instance;
    private boolean sound_effects;

    public GamePreferences() {
        sound_effects = true;
    }

    public  GamePreferences getInstance(){
        if (instance == null) {
            instance = new GamePreferences();
        }

        return instance;
    }


}
