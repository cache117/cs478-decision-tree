package edu.byu.cstaheli.cs478.decision_tree.node;

import edu.byu.cstaheli.cs478.decision_tree.util.Utility;

/**
 * Created by cstaheli on 3/9/2017.
 */
public class FeatureNode extends Node
{
    private int splitOn;
    private String splitAttributeName;

    public FeatureNode(int column, String splitAttributeName)
    {
        splitOn = column;
        this.splitAttributeName = splitAttributeName;
    }

    @Override
    public double getOutputClass(double[] row)
    {
        Node wantedNode = this.findChildWithPrimaryColumnValue(row[splitOn]);
        double[] transformedRow = Utility.removeColumnFromRow(splitOn, row);
        assert wantedNode != null;
        return wantedNode.getOutputClass(transformedRow);
    }

    public int getSplitColumn()
    {
        return splitOn;
    }
}
