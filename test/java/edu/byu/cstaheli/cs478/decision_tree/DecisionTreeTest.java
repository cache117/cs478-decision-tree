package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.toolkit.MLSystemManager;
import edu.byu.cstaheli.cs478.toolkit.utility.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by cstaheli on 3/1/2017.
 */
class DecisionTreeTest
{
    private static String datasetsLocation = "src/test/resources/datasets/decision_tree/";

    private static void assertNumberBetween(double number, double lowerBound, double upperBound)
    {
        assertTrue(Double.compare(number, lowerBound) != -1 && Double.compare(number, upperBound) != 1, String.format("Actual: %s. Expected Bounds[%s, %s].", number, lowerBound, upperBound));
        assertTrue(number >= lowerBound && number <= upperBound, String.format("Actual: %s. Expected Bounds[%s, %s].", number, lowerBound, upperBound));
    }

    private static void assertNumbersEqualWithEpsilon(double expected, double actual, double epsilon)
    {
        assertNumberBetween(actual, expected - epsilon, expected + epsilon);
    }

    @Test
    void getBestFeature() throws Exception
    {
        Matrix matrix = new Matrix(datasetsLocation + "pizza.arff");
        DecisionTree tree = new DecisionTree();
        int bestFeature = tree.getBestFeature(matrix);
        assertEquals(0, bestFeature);
        Matrix meatMatrix = matrix.getRowsWithColumnClass(0, 0.0);
        bestFeature = tree.getBestFeature(meatMatrix);
        assertEquals(1, bestFeature);
    }

    @Test
    void calculateOutputInformation() throws Exception
    {
        Matrix matrix = new Matrix(datasetsLocation + "pizza.arff");
        DecisionTree tree = new DecisionTree();
        double outputInformation = tree.calculateOutputInformation(matrix);
        assertNumberBetween(outputInformation, 1.530, 1.531);
    }

    @Test
    void calculateFeatureInformation() throws Exception
    {
        Matrix matrix = new Matrix(datasetsLocation + "pizza.arff");
        DecisionTree tree = new DecisionTree();
        double featureInformation = tree.calculateFeatureInformation(matrix, 0);
        assertNumbersEqualWithEpsilon(.983, featureInformation, .001);
        featureInformation = tree.calculateFeatureInformation(matrix, 1);
        assertNumbersEqualWithEpsilon(1.417, featureInformation, .001);
        featureInformation = tree.calculateFeatureInformation(matrix, 2);
        assertNumbersEqualWithEpsilon(1.29, featureInformation, .01);
        //Split on meat
        Matrix meatMatrix = matrix.getRowsWithColumnClass(0, 0.0);
        featureInformation = tree.calculateFeatureInformation(meatMatrix, 0);
        assertNumbersEqualWithEpsilon(.5, featureInformation, .1);
        featureInformation = tree.calculateFeatureInformation(meatMatrix, 1);
        assertNumbersEqualWithEpsilon(0, featureInformation, .1);
    }

    @Test
    void testRunManager() throws Exception
    {
        String[] args;
        MLSystemManager manager = new MLSystemManager();
        args = ("-L decisiontree -A " + datasetsLocation + "pizza.arff -E training -V").split(" ");
        manager.run(args);
    }


}