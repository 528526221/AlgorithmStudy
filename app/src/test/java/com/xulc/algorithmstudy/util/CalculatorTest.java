package com.xulc.algorithmstudy.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Date：2018/9/12
 * Desc：
 * Created by xulc.
 */
public class CalculatorTest {
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();

    }

    @After
    public void tearDown() throws Exception {

    }

    private Calculator mCalculator;


    @Test
    public void testSum() throws Exception {
        assertEquals("1+5=6",6d, mCalculator.sum(1d, 5d), 0);
    }

    @Test
    public void testSubstract() throws Exception {
        assertEquals("5-4=1",2d, mCalculator.substract(5d, 4d), 0);
    }

    @Test
    public void testDivide() throws Exception {
        assertEquals("20/5=4",4d, mCalculator.divide(20d, 5d), 0);
    }

    @Test
    public void testMultiply() throws Exception {
        assertEquals("2*5=10",0, mCalculator.multiply(2d, 5d), 0);
    }

}