package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.toolkit.learner.SupervisedLearner;
import edu.byu.cstaheli.cs478.toolkit.strategy.LearningStrategy;
import edu.byu.cstaheli.cs478.toolkit.utility.Matrix;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cstaheli on 3/1/2017.
 */
public class DecisionTree extends SupervisedLearner
{
    @Override
    public void train(LearningStrategy strategy) throws Exception
    {
        Matrix trainingFeatures = strategy.getTrainingFeatures();
        Matrix trainingLabels = strategy.getTrainingLabels();
        int totalOutputs = strategy.getTrainingLabels().valueCount(0);
        double gain = calculateGain(trainingLabels);
        double blank = 0;
    }

    public double calculateGain(Matrix oneColumn)
    {
        assert oneColumn.cols() == 1;
        assert oneColumn.valueCount(0) > 0;
        TreeMap<Double, Integer> allOccurrences = oneColumn.getColumnOccurrences(0);
        assert oneColumn.valueCount(0) == allOccurrences.size();
        int totalOccurrences = oneColumn.rows();
        double sum = 0;
        for (Map.Entry<Double, Integer> entry : allOccurrences.entrySet())
        {
            int occurrences = entry.getValue();
            sum += calculateEntropy(calculateProbability(occurrences, totalOccurrences));
        }
        return sum;
    }

    private double calculateOverallInformationGain()
    {
        return 0;
    }

    private double calculateEntropy(double probability)
    {
        return -1 * probability * (Math.log(probability) / Math.log(2));
    }

    private double calculateProbability(int favorableOutcomes, int totalOutcomes)
    {
        return ((double) favorableOutcomes / (double) totalOutcomes);
    }

    @Override
    public void predict(double[] features, double[] labels) throws Exception
    {

    }
}
