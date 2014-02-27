package org.aksw.palmetto.calculations;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.palmetto.subsets.OneOne;
import org.aksw.palmetto.subsets.SubsetCreator;
import org.aksw.palmetto.subsets.SubsetDefinition;
import org.aksw.palmetto.subsets.SubsetProbabilities;
import org.aksw.palmetto.sum.ArithmeticMean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RatioCoherenceCalculationTest {

    private static final double DOUBLE_PRECISION_DELTA = 0.00000001;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays
                .asList(new Object[][] {
                        /*
                         * word1 1 1 1
                         * 
                         * word2 0 1 1
                         * 
                         * word3 0 1 1
                         * 
                         * C_d,oneone= 1/3 * ((P(w_1,w_2)/(P(w_1)*P(w_2))) +
                         * (P(w_1,w_3)/(P(w_1)*P(w_3))) +
                         * (P(w_2,w_3)/(P(w_2)*P(w_3)))) = 1/3 * (2/3 /
                         * (1 * 2/3)) + (2/3 / (1 * 2/3)) + (2/3 / (2/3 *
                         * 2/3))) = 1/3 * (1 + 1 + 3/2) = 7/6
                         */
                        {
                                new OneOne(),
                                3,
                                new double[] { 0, 1.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0 },
                                7.0/6.0 },

                        /*
                         * word1 0 1 1
                         * 
                         * word2 1 0 1
                         * 
                         * word3 1 1 0
                         * 
                         * C_d,oneone= 1/3 * ((P(w_1,w_2)/(P(w_1)*P(w_2))) +
                         * (P(w_1,w_3)/(P(w_1)*P(w_3))) +
                         * (P(w_2,w_3)/(P(w_2)*P(w_3)))) = 1/3 * ((1/3 /
                         * (2/3 * 2/3)) + (1/3 / (2/3 * 2/3)) + (1/3 / (2/3 *
                         * 2/3))) = 3/4
                         */{
                                new OneOne(),
                                3,
                                new double[] { 0, 2.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0, 0 },
                                3.0 / 4.0 },
                        /*
                         * word1 0 0 0 1
                         * 
                         * word2 0 1 0 1
                         * 
                         * word3 0 0 1 1
                         * 
                         * C_d,oneone= 1/3 * ((P(w_1,w_2)/(P(w_1)*P(w_2))) +
                         * (P(w_1,w_3)/(P(w_1)*P(w_3))) +
                         * (P(w_2,w_3)/(P(w_2)*P(w_3)))) = 1/3 * ((1/4 /
                         * (1/4 * 1/2)) + (1/4 / (1/4 * 1/2)) + (1/4 / (1/2 *
                         * 1/2))) = 1/3 * (2 + 2 + 1) = 5/3
                         */
                        { new OneOne(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 },
                                5.0 / 3.0 } });
    }

    private SubsetCreator subsetCreator;
    private int wordsetSize;
    private double probabilities[];
    private double expectedCoherence;

    public RatioCoherenceCalculationTest(SubsetCreator subsetCreator, int wordsetSize, double[] probabilities,
            double expectedCoherence) {
        this.probabilities = probabilities;
        this.wordsetSize = wordsetSize;
        this.subsetCreator = subsetCreator;
        this.expectedCoherence = expectedCoherence;
    }

    @Test
    public void test() {
        RatioCoherenceCalculation calculation = new RatioCoherenceCalculation();
        SubsetDefinition subsets = subsetCreator.getSubsetDefinition(wordsetSize);
        Assert.assertEquals(expectedCoherence, (new ArithmeticMean()).summarize(calculation
                .calculateCoherenceValues(new SubsetProbabilities(subsets.segments,
                subsets.conditions, probabilities))), DOUBLE_PRECISION_DELTA);
    }
}
