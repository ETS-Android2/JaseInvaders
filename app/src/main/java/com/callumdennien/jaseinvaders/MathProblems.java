package com.callumdennien.jaseinvaders;

import java.util.ArrayList;
import java.util.Random;

public class MathProblems {
    private int difficultyValue = Difficulty.EASY.difficultyValue;
    private final Random random = new Random();

    public ArrayList<Integer> getAddition() {
        // generate two random numbers and their sum, return as array list.
        int addendsOne = random.nextInt(difficultyValue);
        int addendsTwo = random.nextInt(difficultyValue);
        int answerSum = addendsOne + addendsTwo;

        return createProblem(addendsOne, addendsTwo, answerSum);
    }

    public ArrayList<Integer> getSubtraction() {
        // generate two random numbers and their difference, return as array list.
        int minuend = random.nextInt(difficultyValue) + 1;
        int subtrahend = random.nextInt(minuend);
        int answerDifference = minuend - subtrahend;

        return createProblem(minuend, subtrahend, answerDifference);
    }

    public ArrayList<Integer> getMultiplication() {
        // generate two random numbers and their product, return as array list.
        int multiplicand = random.nextInt(difficultyValue) + 1;
        int multiplier = random.nextInt(multiplicand);
        int answerProduct = multiplicand * multiplier;

        return createProblem(multiplicand, multiplier, answerProduct);
    }

    public ArrayList<Integer> getDivision() {
        // generate two random numbers and their quotient, return as array list.
        int dividend = random.nextInt(difficultyValue) + 1;
        int divisor = random.nextInt(dividend) + 1;
        int answerQuotient = dividend / divisor;

        return createProblem(dividend, divisor, answerQuotient);
    }

    private ArrayList<Integer> createProblem(int firstValue, int secondValue, int answer) {
        // create array list with problem values and answer.
        ArrayList<Integer> problem = new ArrayList<>();
        problem.add(firstValue);
        problem.add(secondValue);
        problem.add(answer);

        return problem;
    }

    public int setDifficulty(int newDifficulty) {
        // update math problem difficulty level.
        difficultyValue = newDifficulty;

        return difficultyValue;
    }
}
