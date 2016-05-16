package com.company.util;

import java.math.BigInteger;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class Factorial {

    private static BigInteger calcFactorialPart(long start, long n) {
        Long i;

        if (n <= 16) {
            BigInteger r = new BigInteger(((Long) start).toString());
            for (i = start + 1; i < start + n; i++) {
                r = r.multiply(new BigInteger(i.toString()));
            }

            return r;
        }

        i = n / 2;
        BigInteger firstOperand = calcFactorialPart(start, i);
        BigInteger secondOperand = calcFactorialPart(start + i, n - i);

        return firstOperand.multiply(secondOperand);
    }

    public static BigInteger factorial(long n) {
        return calcFactorialPart(1, n);
    }
}
