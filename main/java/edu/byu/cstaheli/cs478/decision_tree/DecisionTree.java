package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.toolkit.SupervisedLearner;

/**
 * Created by cstaheli on 3/1/2017.
 */
public class DecisionTree extends SupervisedLearner
{
    @Override
    public void predict(double[] features, double[] labels) throws Exception
    {

    }

    @Override
    protected void initializeWeights(int features, int outputs)
    {

    }

    @Override
    protected void analyzeInputRow(double[] row, double expectedOutput)
    {

    }

    @Override
    protected boolean isThresholdValidationAccuracyMet(double validationAccuracy, double bestAccuracy)
    {
        return false;
    }

    @Override
    public void writeAccuraciesAndFinalWeights(double trainAccuracy, double testAccuracy)
    {

    }
}
