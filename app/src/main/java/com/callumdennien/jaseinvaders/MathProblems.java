package com.callumdennien.jaseinvaders;

import java.util.ArrayList;
import java.util.Random;

public class MathProblems {
    private int difficultyLevel = 10;
    private final Random random = new Random();

    public ArrayList<Integer> getAddition() {
        int addendsOne = random.nextInt(difficultyLevel);
        int addendsTwo = random.nextInt(difficultyLevel);
        int answer = addendsOne + addendsTwo;

        return createProblem(addendsOne, addendsTwo, answer);
    }

    public ArrayList<Integer> getSubtraction() {
        int minuend = random.nextInt(difficultyLevel) + 1;
        int subtrahend = random.nextInt(minuend);
        int answerDifference = minuend - subtrahend;

        return createProblem(minuend, subtrahend, answerDifference);
    }

    public ArrayList<Integer> getMultiplication() {
        int multiplicand = random.nextInt(difficultyLevel) + 1;
        int multiplier = random.nextInt(multiplicand);
        int answerProduct = multiplicand * multiplier;

        return createProblem(multiplicand, multiplier, answerProduct);
    }

    public ArrayList<Integer> getDivision() {
        int dividend = random.nextInt(difficultyLevel) + 1;
        int divisor = random.nextInt(dividend) + 1;
        int answerQuotient = dividend / divisor;

        return createProblem(dividend, divisor, answerQuotient);
    }

    private ArrayList<Integer> createProblem(int addendsOne, int addendsTwo, int answer) {
        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(addendsOne);
        problem.add(addendsTwo);
        problem.add(answer);

        return problem;
    }

    public int getDifficulty() {
        return difficultyLevel;
    }

    public int setDifficulty(int newDifficulty) {
        difficultyLevel = newDifficulty;

        return difficultyLevel;
    }
}
