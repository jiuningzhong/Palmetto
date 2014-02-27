package org.aksw.palmetto.sum;

public class GeometricMean implements Summarization {

    @Override
    public double summarize(double[] values) {
        double prod = 1;
        for (int i = 0; i < values.length; ++i) {
            if (values[i] <= 0) {
                // the geometric mean is not defined for negative numbers
                return 0;
            }
            prod *= values[i];
        }
        return Math.pow(prod, 1.0 / values.length);
    }

    @Override
    public String getName() {
        return "sigma_g";
    }

    @Override
    public String toString() {
        return getName();
    }
}
