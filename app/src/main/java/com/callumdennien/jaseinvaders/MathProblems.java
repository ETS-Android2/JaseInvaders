package com.callumdennien.jaseinvaders;

import java.util.ArrayList;
import java.util.Random;

public class MathProblems {
    final int difficultyLevel = 100;
    Random random = new Random();

    public ArrayList<Integer> getAddition() {
        int addendsOne = random.nextInt(difficultyLevel);
        int addendsTwo = random.nextInt(difficultyLevel);
        int answer = addendsOne + addendsTwo;

        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(addendsOne);
        problem.add(addendsTwo);
        problem.add(answer);

        return problem;
    }
}
