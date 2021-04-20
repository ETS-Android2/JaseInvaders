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

    public ArrayList<Integer> getSubtraction() {
        int minuend = random.nextInt(difficultyLevel);
        int subtrahend = random.nextInt(minuend - 1);
        int answerDifference = minuend - subtrahend;

        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(minuend);
        problem.add(subtrahend);
        problem.add(answerDifference);

        return problem;
    }

    public ArrayList<Integer> getMultiplication() {
        int multiplicand = random.nextInt(difficultyLevel / 2);
        int multiplier = random.nextInt(multiplicand / 3);
        int answerProduct = multiplicand * multiplier;

        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(multiplicand);
        problem.add(multiplier);
        problem.add(answerProduct);

        return problem;
    }
}
