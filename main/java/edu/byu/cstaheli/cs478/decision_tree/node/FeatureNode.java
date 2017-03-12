package edu.byu.cstaheli.cs478.decision_tree.node;

import edu.byu.cstaheli.cs478.decision_tree.util.Utility;

import java.util.Random;

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
        if (wantedNode != null)
        {
            return wantedNode.getOutputClass(transformedRow);
        }
        else
        {
            Random random = new Random();
            int child = random.nextInt(getNumberOfChildren());
            return getChild(child).getOutputClass(transformedRow);
        }
    }

    public int getSplitColumn()
    {
        return splitOn;
    }

    @Override
    public String toString()
    {
        return String.format("Feature: Attr:\"%s(%s)\", SplitAttr:\"%s\"", getAttributeName(), getPrimaryColumnValue(), getSplitAttributeName());
    }

    public String getSplitAttributeName()
    {
        return splitAttributeName;
    }
}
