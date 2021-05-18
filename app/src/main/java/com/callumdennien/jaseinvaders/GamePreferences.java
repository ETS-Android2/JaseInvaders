package com.callumdennien.jaseinvaders;

public class GamePreferences {
    private static GamePreferences instance;
    private String player_name;
    private Difficulty difficulty;
    private boolean sound_effects;
    private boolean music;
    private boolean answered_question;
    private int personal_best;
    private int current_score;
    private String current_question;
    private int current_answer;

    public GamePreferences() {
        player_name = "Anonymous";
        difficulty = Difficulty.EASY;
        sound_effects = true;
        music = true;
        answered_question = false;
        personal_best = 999;
        current_score = 0;
        current_question = "1 / 1 =";
        current_answer = 1;
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

    public String getDifficulty() {
        return difficulty.label;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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

    public boolean getAnsweredQuestion() {
        return !answered_question;
    }

    public void setAnsweredQuestion(boolean answered_question) {
        this.answered_question = answered_question;
    }

    public int getPersonalBest() {
        return personal_best;
    }

    public void setPersonalBest(int personal_best) {
        this.personal_best = personal_best;
    }

    public int getCurrentScore() {
        return current_score;
    }

    public void setCurrentScore(int current_score) {
        this.current_score = current_score;
    }

    public String getCurrentQuestion() {
        return current_question;
    }

    public void setCurrentQuestion(String current_question) {
        this.current_question = current_question;
    }

    public int getCurrentAnswer() {
        return current_answer;
    }

    public void setCurrentAnswer(int current_answer) {
        this.current_answer = current_answer;
    }
}
