package org.example.model;

import java.util.Comparator;
import java.util.TreeMap;

public class Polynomial {
    private final TreeMap<Integer, Double> terms;

    public Polynomial() {
        terms = new TreeMap<>(Comparator.reverseOrder());
    }

    public void addTerm(Double coefficient, int exponent) {
        if (coefficient == 0) {
            return;
        }

        if (terms.containsKey(exponent)) {
            double currentCoefficient = terms.get(exponent);
            double newCoefficient = currentCoefficient + coefficient;

            if (newCoefficient == 0) {
                terms.remove(exponent);
            } else {
                terms.put(exponent, newCoefficient);
            }
        } else {
            terms.put(exponent, coefficient);
        }
    }

    public void subtractTerm(Double coefficient, int exponent) {
        addTerm(-coefficient, exponent);
    }

    public Polynomial add(Polynomial p) {
        Polynomial result = new Polynomial();

        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            result.addTerm(coefficient, exponent);
        }

        for (int exponent : p.terms.keySet()) {
            Double coefficient = p.terms.get(exponent);
            result.addTerm(coefficient, exponent);
        }

        return result;
    }

    public Polynomial subtract(Polynomial p) {
        return getPolynomial(p);
    }

    private Polynomial getPolynomial(Polynomial p) {
        Polynomial result = new Polynomial();

        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            result.addTerm(coefficient, exponent);
        }

        for (int exponent : p.terms.keySet()) {
            Double coefficient = p.terms.get(exponent);
            result.subtractTerm(coefficient, exponent);
        }

        return result;
    }

    public Polynomial multiply(Polynomial p) {
        Polynomial result = new Polynomial();

        for (int exponent1 : terms.keySet()) {
            for (int exponent2 : p.terms.keySet()) {
                Double coefficient1 = terms.get(exponent1);
                Double coefficient2 = p.terms.get(exponent2);
                int newCoefficient = (int) (coefficient1 * coefficient2);
                int newExponent = exponent1 + exponent2;

                result.addTerm((double) newCoefficient, newExponent);
            }
        }

        return result;
    }

    public String divide(Polynomial divisor) {
        // Check if the divisor is zero
        if (divisor.terms.isEmpty() || divisor.degree() == -1) {
            throw new ArithmeticException("Divide by zero error");
        }

        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial();

        // Copy the terms of the dividend to the remainder
        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            remainder.addTerm(coefficient, exponent);
        }

        // Perform long division
        while (remainder.degree() >= divisor.degree()) {
            int quotientExponent = remainder.degree() - divisor.degree();
            Double quotientCoefficient = remainder.getCoefficient(remainder.degree()) / divisor.getCoefficient(divisor.degree());
            quotient.addTerm(quotientCoefficient, quotientExponent);

            Polynomial product = new Polynomial();
            for (int exponent : divisor.terms.keySet()) {
                Double coefficient = divisor.terms.get(exponent);
                int newExponent = exponent + quotientExponent;
                Double newCoefficient = coefficient * quotientCoefficient;
                product.addTerm(newCoefficient, newExponent);
            }

            remainder = remainder.subtract(product);
        }

        return "Quotient: "+quotient+" Remainder: "+remainder;
    }


    public Polynomial differentiate() {
        Polynomial result = new Polynomial();

        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            if (exponent > 0) {
                int newCoefficient = (int) (exponent * coefficient);
                int newExponent = exponent - 1;
                result.addTerm((double) newCoefficient, newExponent);
            }
        }

        return result;
    }

    public int degree() {
        int degree = -1;

        for (int exponent : terms.keySet()) {
            if (exponent > degree) {
                degree = exponent;
            }
        }

        return degree;
    }

    public Double getCoefficient(int exponent) {
        return terms.getOrDefault(exponent, (double) 0);
    }



    public Polynomial integrate() {
        Polynomial result = new Polynomial();
        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            int newExponent = exponent + 1;
            int newCoefficient = (int) (coefficient / newExponent);
            if (newCoefficient != 0) {
                result.addTerm((double) newCoefficient, newExponent);
            }
        }
        result.addTerm((double) 0, 0);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int exponent : terms.keySet()) {
            Double coefficient = terms.get(exponent);
            if (coefficient == 0) {
                continue;
            }
            if (coefficient < 0) {
                sb.append(" - ");
                coefficient = -coefficient;
            } else if (sb.length() > 0) {
                sb.append(" + ");
            }
            if (exponent == 0 || coefficient != 1) {
                sb.append(coefficient);
            }
            if (exponent > 0) {
                sb.append("x");
                if (exponent > 1) {
                    sb.append("^").append(exponent);
                }
            }
        }
        if (sb.length() == 0) {
            sb.append(0);
        }
        return sb.toString();
    }
    private Polynomial parsePolynomial(String input) {
        Polynomial p = new Polynomial();

        if (input.isEmpty()) {
            return p;
        }

        String[] terms = input.split("\\s*(?=[+-])\\s*");

        for (String term : terms) {
            if (term.isEmpty()) {
                continue;
            }

            int coefficient;
            int exponent;

            if (term.equals("+")) {
                coefficient = 1;
                exponent = 0;
            } else if (term.equals("-")) {
                coefficient = -1;
                exponent = 0;
            } else {
                int indexOfX = term.indexOf("x");

                if (indexOfX == -1) {
                    coefficient = Integer.parseInt(term);
                    exponent = 0;
                } else {
                    String coefficientStr = term.substring(0, indexOfX).trim();
                    coefficient = coefficientStr.isEmpty() ? 1 : Integer.parseInt(coefficientStr);

                    int indexOfPower = term.indexOf("^");

                    if (indexOfPower == -1) {
                        exponent = 1;
                    } else {
                        exponent = Integer.parseInt(term.substring(indexOfPower + 1).trim());
                    }
                }
            }

            p.addTerm((double) coefficient, exponent);
        }

        return p;
    }
}
