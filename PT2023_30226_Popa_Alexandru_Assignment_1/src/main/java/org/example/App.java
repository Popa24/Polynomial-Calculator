package org.example;

import org.example.model.Polynomial;
import org.example.view.PolynomialCalculator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();
        p1.addTerm(1.0, 3);
        p1.addTerm(-2.0, 2);
        p1.addTerm(6.0, 1);
        p1.addTerm(-5.0, 0);
        System.out.println("p1(x) = " + p1);

        Polynomial p2 = new Polynomial();
        p2.addTerm(1.0, 2);
        p2.addTerm(0.0, 1);
        p2.addTerm(-1.0, 0);
        System.out.println("p2(x) = " + p2);


        Polynomial sum = p1.add(p2);
        System.out.println("Sum: " + sum.toString());

        Polynomial difference = p1.subtract(p2);
        System.out.println("Difference: " + difference.toString());

        Polynomial product = p1.multiply(p2);
        System.out.println("Product: " + product.toString());

        String division = p1.divide (p2);
        System.out.println("Division: "+ division);

        Polynomial derivative = p1.differentiate();
        System.out.println("Derivative of p1(x): " + derivative.toString());

        Polynomial derivative1 = p2.differentiate();
        System.out.println("Derivative of p2(x): " + derivative1.toString());

        Polynomial integral = p2.integrate();
        System.out.println("Integral of p2(x): " + integral.toString());

        Polynomial integral1 = p1.integrate();
        System.out.println("Integral of p1(x): " + integral1.toString());
        PolynomialCalculator calculator = new PolynomialCalculator();
    }
}
