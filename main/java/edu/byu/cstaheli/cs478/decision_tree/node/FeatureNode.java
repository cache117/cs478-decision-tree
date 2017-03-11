package edu.byu.cstaheli.cs478.decision_tree.node;

import edu.byu.cstaheli.cs478.decision_tree.util.Utility;

/**
 * Created by cstaheli on 3/9/2017.
 */
public class FeatureNode extends DecisionTreeNode
{
    private int splitOn;

    public FeatureNode(int column)
    {
        splitOn = column;
    }

    @Override
    public double getOutputClass(double[] row)
    {
        double[] transformedRow = Utility.removeColumnFromRow(splitOn, row);
        return getChild(splitOn).getOutputClass(transformedRow);
    }
}
