package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.decision_tree.node.DecisionTreeNode;
import edu.byu.cstaheli.cs478.decision_tree.node.FeatureNode;
import edu.byu.cstaheli.cs478.decision_tree.node.LeafNode;
import edu.byu.cstaheli.cs478.toolkit.exception.MatrixException;
import edu.byu.cstaheli.cs478.toolkit.learner.SupervisedLearner;
import edu.byu.cstaheli.cs478.toolkit.strategy.LearningStrategy;
import edu.byu.cstaheli.cs478.toolkit.utility.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by cstaheli on 3/1/2017.
 */
public class DecisionTree extends SupervisedLearner
{
    private final static Logger LOGGER = Logger.getLogger(DecisionTree.class.getName());
    private DecisionTreeNode decisionTreeRoot;
    private double learningRate;

    @Override
    public void train(LearningStrategy strategy) throws Exception
    {
        Matrix trainingFeatures = strategy.getTrainingFeatures();
        Matrix trainingLabels = strategy.getTrainingLabels();
        Matrix trainingData = strategy.getTrainingData();
        decisionTreeRoot = populateDecisionTree(trainingData);
        prune(decisionTreeRoot, strategy.getValidationData());
    }

    private void prune(DecisionTreeNode decisionTreeRoot, Matrix validationData)
    {
        return;
    }

    public DecisionTreeNode populateDecisionTree(Matrix matrix) throws MatrixException
    {
        Map<Double, Integer> outputDistribution = getOutputDistribution(matrix);
        LOGGER.info("Output Class Distributions:");
        for (Map.Entry<Double, Integer> entry : outputDistribution.entrySet())
        {
            LOGGER.info(String.format("Class: %s, Occurrences: %s", entry.getKey(), entry.getValue()));
        }
        int totalOutputs = matrix.valueCount(matrix.cols() - 1);
        Map.Entry<Double, Integer> pureClassIfAny = getPureClassIfAny(outputDistribution);
        if (pureClassIfAny != null)
        {
            return new LeafNode(pureClassIfAny.getKey());
        }
        else if (matrix.cols() == 1)
        {
            return new LeafNode(getMostProbable(outputDistribution));
        }

        int bestFeature = getBestFeature(matrix);
        Map<Double, Integer> featureDistribution = matrix.getColumnOccurrences(bestFeature);
        DecisionTreeNode node = new FeatureNode(bestFeature);
        List<DecisionTreeNode> children = new ArrayList<>();
        for (Map.Entry<Double, Integer> entry : featureDistribution.entrySet())
        {
            Matrix filteredMatrix = matrix.getRowsWithColumnClass(bestFeature, entry.getKey());
            children.add(populateDecisionTree(filteredMatrix));
        }
        node.setChildren(children);
        return node;
    }

    private double getMostProbable(Map<Double, Integer> outputDistribution)
    {
        Double bestKey = 0.0;
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : outputDistribution.entrySet())
        {
            if (entry.getValue() > maxCount)
            {
                maxCount = entry.getValue();
                bestKey = entry.getKey();
            }
        }
        return bestKey;
    }

    private Map.Entry<Double, Integer> getPureClassIfAny(Map<Double, Integer> occurrences)
    {
        Map.Entry<Double, Integer> pureEntry = null;
        int numberOfZeroOccurrences = 0;
        for (Map.Entry<Double, Integer> entry : occurrences.entrySet())
        {
            int occurrenceCount = entry.getValue();
            if (occurrenceCount == 0)
            {
                ++numberOfZeroOccurrences;
            }
            else
            {
                pureEntry = entry;
            }
        }
        if (numberOfZeroOccurrences == 1)
        {
            return pureEntry;
        }
        return null;
    }

    public int getBestFeature(Matrix matrix) throws MatrixException
    {
        double outputInformationGain = calculateOutputInformation(matrix);
        double bestFeatureInformationGain = 0;
        int bestFeature = 0;
        for (int i = 0; i < matrix.cols() - 1; ++i)
        {
            double featureInformationGain = calculateFeatureInformation(matrix, i);

            if (featureInformationGain > bestFeatureInformationGain)
            {
                bestFeatureInformationGain = featureInformationGain;
                bestFeature = i;
            }
        }

        assert bestFeature != matrix.cols() - 1;
        return bestFeature;
    }

    public double calculateOverallInformationGain(Matrix matrix)
    {
        int lastColumn = matrix.cols() - 1;
        assert matrix.valueCount(lastColumn) > 0;
        Map<Double, Integer> allOccurrences = matrix.getColumnOccurrences(lastColumn);
        assert matrix.valueCount(lastColumn) == allOccurrences.size();
        int totalOccurrences = matrix.rows();
        double sum = 0;
        for (Map.Entry<Double, Integer> entry : allOccurrences.entrySet())
        {
            int occurrences = entry.getValue();
            sum += calculateEntropy(calculateProbability(occurrences, totalOccurrences));
        }
        return sum;
    }

    public double calculateOutputInformation(Matrix matrix)
    {
        int column = matrix.cols() - 1;
        Map<Double, Integer> allOccurrences = matrix.getColumnOccurrences(column);
        assert matrix.valueCount(column) > 0;
        assert matrix.valueCount(column) == allOccurrences.size();
        int totalOccurrences = matrix.rows();
        double information = 0;
        for (Map.Entry<Double, Integer> entry : allOccurrences.entrySet())
        {
            int occurrences = entry.getValue();
            information += calculateEntropy(calculateProbability(occurrences, totalOccurrences));
        }
        return information;
    }

    public Map<Double, Integer> getOutputDistribution(Matrix matrix)
    {
        return matrix.getColumnOccurrences(matrix.cols() - 1);
    }

    public double calculateFeatureInformation(Matrix matrix, int feature) throws MatrixException
    {
        Map<Double, Integer> columnOccurrences = matrix.getColumnOccurrences(feature);
        double information = 0;
        for (Map.Entry<Double, Integer> entry : columnOccurrences.entrySet())
        {
            Matrix relevantMatrix = matrix.getRowsWithColumnClass(feature, entry.getKey());
            information += calculateOutputInformation(relevantMatrix);
        }
        return information;
    }

    private double calculateNeededInformation(List<Double> entropies)
    {
        double sum = 0;
        for (Double entropy : entropies)
        {
            sum += entropy;
        }
        return sum;
    }

    private double calculateEntropy(double probability)
    {
        if (probability == 0)
        {
            return 0;
        }
        return -1 * probability * log2(probability);
    }

    private double log2(double value)
    {
        return Math.log(value) / Math.log(2);
    }

    private double calculateProbability(int favorableOutcomes, int totalOutcomes)
    {
        assert totalOutcomes != 0;
        assert favorableOutcomes <= totalOutcomes;
        return ((double) favorableOutcomes / (double) totalOutcomes);
    }

    @Override
    public void predict(double[] features, double[] labels) throws Exception
    {
        labels[0] = decisionTreeRoot.getOutputClass(features);
    }

    public double getLearningRate()
    {
        return learningRate;
    }

    public void setLearningRate(double learningRate)
    {
        this.learningRate = learningRate;
    }
}
