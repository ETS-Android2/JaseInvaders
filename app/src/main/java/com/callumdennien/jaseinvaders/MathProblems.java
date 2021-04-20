package com.callumdennien.jaseinvaders;

import java.util.ArrayList;
import java.util.Random;

public class MathProblems {
    Random random = new Random();

    public ArrayList<Integer> getAddition() {
        int addendsOne = random.nextInt(100);
        int addendsTwo = random.nextInt(100);
        int answer = addendsOne + addendsTwo;

        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(addendsOne);
        problem.add(addendsTwo);
        problem.add(answer);

        return problem;
    }
}
