package com.callumdennien.jaseinvaders;

import androidx.annotation.NonNull;

public class Timer {
    private int seconds;

    Timer() {seconds = 0;}

    void tick() {
        ++seconds;
    }

    @NonNull
    @Override
    public String toString() {
        return seconds + " Seconds";
    }

    public void add(int i) {
        seconds += i;
    }
}
