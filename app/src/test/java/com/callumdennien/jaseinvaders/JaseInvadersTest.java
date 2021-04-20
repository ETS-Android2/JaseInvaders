package com.callumdennien.jaseinvaders;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JaseInvadersTest {
    MathProblems mathProblems = new MathProblems();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

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

    // Multiplication
    // Division
}