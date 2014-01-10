/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package be.ac.ulg.montefiore.run.distributions;

import java.util.Arrays;


/*
 * This class gives some simple matrix and column vector manipulation methods.
 * 
 * A matrix is simply implemented using a two-dimensional array of
 * doubles.  A (column) vector is implemented using an array of doubles.
 */
class SimpleMatrix {
    /* Matrix creation functions */

    // Creates a r x c matrix filled with zeros
    static double[][] matrix(int r, int c) {
        if (r <= 0 || c <= 0) {
            throw new IllegalArgumentException();
        }

        return new double[r][c];
    }

    static double[][] matrix(int s) {
        return matrix(s, s);
    }

    static double[][] matrix(int s, double v) {
        return matrix(s, s, v);
    }

    static double[][] matrix(int r, int c, double v) {
        double[][] m = matrix(r, c);

        while (r > 0) {
            Arrays.fill(m[--r], v);
        }

        return m;
    }

    static double[][] matrix(double[][] m) {
        double[][] mc = matrix(nbRows(m), nbColumns(m));

        for (int r = 0; r < nbRows(m); r++) {
            System.arraycopy(m[r], 0, mc[r], 0, nbColumns(m));
        }

        return mc;
    }

    // Converts a (column) vector to a (column) matrix */
    static double[][] matrix(double[] v) {
        double[][] m = matrix(dimension(v), 1);

        for (int r = 0; r < dimension(v); r++) {
            m[r][0] = v[r];
        }

        return m;
    }

    /* Vector creation functions */
    // Creates a new vector of dimension 'dimension' filled with zeros 
    static double[] vector(int dimension) {
        return new double[dimension];
    }

    static double[] vector(double[] v) {
        double[] vc = (double[]) v.clone();

        return vc;
    }

    static double[][] matrixIdentity(int s) {
        double[][] m = matrix(s, s);

        for (int i = 0; i < s; i++) {
            m[i][i] = 1.;
        }

        return m;
    }

    static int nbRows(double[][] m) {
        return m.length;
    }

    static int nbColumns(double[][] m) {
        return m[0].length;
    }

    static int dimension(double[] v) {
        return v.length;
    }

    static boolean isSquare(double[][] m) {
        return (nbRows(m) == nbColumns(m));
    }

    static double[][] transpose(double[][] m) {
        double[][] t = matrix(nbColumns(m), nbRows(m));

        for (int r = 0; r < nbRows(m); r++) {
            for (int c = 0; c < nbColumns(m); c++) {
                t[c][r] = m[r][c];
            }
        }

        return t;
    }

    static double[][] plus(double[][] m1, double[][] m2) {
        if ((nbRows(m1) != nbRows(m2)) || (nbColumns(m1) != nbColumns(m2))) {
            throw new IllegalArgumentException("Incompatible sizes");
        }

        double[][] s = matrix(m1);

        for (int r = 0; r < nbRows(s); r++) {
            for (int c = 0; c < nbColumns(s); c++) {
                s[r][c] += m2[r][c];
            }
        }

        return s;
    }

    static double[] plus(double[] v1, double[] v2) {
        if (dimension(v1) != dimension(v2)) {
            throw new IllegalArgumentException("Incompatible dimensions");
        }

        double[] s = vector(v1);

        for (int i = 0; i < dimension(v1); i++) {
            s[i] += v2[i];
        }

        return s;
    }

    static double[] minus(double[] v1, double[] v2) {
        if (dimension(v1) != dimension(v2)) {
            throw new IllegalArgumentException("Incompatible dimensions");
        }

        double[] s = vector(v1);

        for (int i = 0; i < dimension(v1); i++) {
            s[i] -= v2[i];
        }

        return s;
    }

    static double[][] times(double[][] m1, double[][] m2) {
        if (nbRows(m2) != nbColumns(m1)) {
            throw new IllegalArgumentException("Incompatible sizes");
        }

        double[][] p = matrix(nbRows(m1), nbRows(m2));

        for (int r = 0; r < nbRows(m1); r++) {
            for (int c = 0; c < nbColumns(m2); c++) {
                for (int i = 0; i < nbColumns(m1); i++) {
                    p[r][c] += m1[r][i] * m2[i][c];
                }
            }
        }

        return p;
    }

    static double[] times(double[][] m, double[] v) {
        if (dimension(v) != nbColumns(m)) {
            throw new IllegalArgumentException("Incompatible sizes");
        }

        double[] p = vector(nbRows(m));

        for (int r = 0; r < nbRows(m); r++) {
            for (int i = 0; i < nbColumns(m); i++) {
                p[r] += m[r][i] * v[i];
            }
        }

        return p;
    }

    static double[][] decomposeCholesky(double[][] m) {
        if (!isSquare(m)) {
            throw new IllegalArgumentException("Matrix is not square");
        }

        double[][] l = matrix(nbRows(m), nbColumns(m));

        for (int j = 0; j < nbRows(m); j++) {
            double[] lj = l[j];
            double d = 0.;

            for (int k = 0; k < j; k++) {
                double[] lk = l[k];
                double s = 0.;

                for (int i = 0; i < k; i++) {
                    s += lk[i] * lj[i];
                }

                lj[k] = s = (m[j][k] - s) / l[k][k];
                d += s * s;
            }

            if ((d = m[j][j] - d) <= 0.) {
                throw new IllegalArgumentException("Matrix is not positive "
                        + "defined");
            }

            l[j][j] = Math.sqrt(d);
            for (int k = j + 1; k < nbRows(m); k++) {
                l[j][k] = 0.;
            }
        }

        return l;
    }

    /*
     * Computes the determinant of a matrix given its cholesky matrix
     * decomposition.
     */
    static double determinantCholesky(double[][] l) {
        if (!isSquare(l)) {
            throw new IllegalArgumentException("Matrix is not square");
        }

        double d = 1.;
        for (int i = 0; i < nbRows(l); i++) {
            d *= l[i][i];
        }

        return d * d;
    }

    /* Computes the inverse of a matrix given its cholesky matrix
     decomposition. */
    static double[][] inverseCholesky(double[][] l) {
        if (!isSquare(l)) {
            throw new IllegalArgumentException("Matrix is not square");
        }

        double[][] li = lowerTriangularInverse(l);
        double[][] ic = matrix(nbRows(l));

        for (int r = 0; r < nbRows(l); r++) {
            for (int c = 0; c < nbRows(l); c++) {
                for (int i = 0; i < nbRows(l); i++) {
                    ic[r][c] += li[i][r] * li[i][c];
                }
            }
        }

        return ic;
    }

    static double[][] lowerTriangularInverse(double[][] m) {
        if (!isSquare(m)) {
            throw new IllegalArgumentException("Matrix is not square");
        }

        double[][] lti = matrix(nbRows(m));

        for (int j = 0; j < nbRows(m); j++) {
            if (m[j][j] == 0.) {
                throw new IllegalArgumentException("Matrix is not full rank");
            }

            lti[j][j] = 1. / m[j][j];

            for (int i = j + 1; i < nbRows(m); i++) {
                double sum = 0.;

                for (int k = j; k < i; k++) {
                    sum -= m[i][k] * lti[k][j];
                }

                lti[i][j] = sum / m[i][i];
            }
        }

        return lti;

    }

    static String toString(double[][] m) {
        String s = "[";

        for (int r = 0; r < nbRows(m); r++) {
            s += "[";

            for (int c = 0; c < nbColumns(m); c++) {
                s += " " + m[r][c];
            }

            s += " ]\n";
        }

        return s + " ]\n";
    }

    static String toString(double[] v) {
        String s = "[";

        for (int r = 0; r < dimension(v); r++) {
            s += " " + v[r];
        }

        return s + " ]\n";
    }
}
