package com.callumdennien.jaseinvaders;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JaseInvadersTest {
    MathProblems mathProblems = new MathProblems();

    @Test
    public void generate_addition() {
        ArrayList<Integer> problem = mathProblems.getAddition();
        int addendsOne = problem.get(0);
        int addendsTwo = problem.get(1);
        int answerSum = problem.get(2);

        assertEquals(answerSum, (addendsOne + addendsTwo));
    }

    @Test
    public void generate_subtraction() {
        ArrayList<Integer> problem = mathProblems.getSubtraction();
        int minuend = problem.get(0);
        int subtrahend = problem.get(1);
        int answerDifference = problem.get(2);

        assertEquals(answerDifference, (minuend - subtrahend));
    }

    @Test
    public void generate_multiplication() {
        ArrayList<Integer> problem = mathProblems.getMultiplication();
        int multiplicand = problem.get(0);
        int multiplier = problem.get(1);
        int answerProduct = problem.get(2);

        assertEquals(answerProduct, (multiplicand * multiplier));
    }

    @Test
    public void generate_division() {
        ArrayList<Integer> problem = mathProblems.getDivision();

        int dividend = problem.get(0);
        int divisor = problem.get(1);
        int answerQuotient = problem.get(2);

        assertEquals(answerQuotient, (dividend / divisor));
    }

    @Test
    public void get_difficulty() {
        int difficulty = mathProblems.getDifficulty();

        assertEquals(difficulty, 10);
    }

    @Test
    public void set_difficulty() {
        int newDifficulty = 20;
        int difficulty = mathProblems.setDifficulty(newDifficulty);

        assertEquals(difficulty, newDifficulty);
    }

    @Test
    public void reset_timer() {
        Timer timer = new Timer();
        timer.add(1);
        timer.reset();

        assertEquals("0 Seconds", timer.toString());
    }
}