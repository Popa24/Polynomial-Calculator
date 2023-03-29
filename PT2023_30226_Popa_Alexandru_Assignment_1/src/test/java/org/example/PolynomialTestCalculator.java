package org.example;

import org.example.model.Polynomial;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolynomialTestCalculator {
    private Polynomial p1;
    private Polynomial p2;

    @Before
    public void setUp() {
        p1 = new Polynomial();
        p1.addTerm(5.0, 2);
        p1.addTerm((double) -2, 1);
        p1.addTerm(7.0, 0);

        p2 = new Polynomial();
        p2.addTerm((double) -3, 3);
        p2.addTerm(1.0, 1);
        p2.addTerm(4.0, 0);
    }

    @Test
    public void testAdd() {
        Polynomial result = p1.add(p2);
        assertEquals(" - 3.0x^3 + 5.0x^2 - x + 11.0", result.toString());
    }

    @Test
    public void testSubtract() {
        Polynomial result = p1.subtract(p2);
        assertEquals("3.0x^3 + 5.0x^2 - 3.0x + 3.0", result.toString());
    }

    @Test
    public void testMultiply() {
        Polynomial result = p1.multiply(p2);
        assertEquals(" - 15.0x^5 + 6.0x^4 - 16.0x^3 + 18.0x^2 - x + 28.0", result.toString());
    }

    @Test
    public void testDifferentiate() {
        Polynomial result = p1.differentiate();
        assertEquals("10.0x - 2.0", result.toString());
    }

    @Test
    public void testIntegrate() {
        Polynomial result = p1.integrate();
        assertEquals("x^3 - x^2 + 7.0x", result.toString());
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        Polynomial zero = new Polynomial();
        p1.divide(zero);
    }

    @Test
    public void testDivide() {
        Polynomial divisor = new Polynomial();
        divisor.addTerm(1.0, 1);
        divisor.addTerm(1.0, 0);
        String result = p1.divide(divisor);
        assertEquals("Quotient: 5.0x - 7.0 Remainder: 14.0", result);
    }
}
