package edu.byu.cstaheli.cs478.decision_tree.node;

/**
 * Created by cstaheli on 3/9/2017.
 */
public class LeafNode extends Node
{
    private double outputClass;

    public LeafNode(double outputClass)
    {
        this.outputClass = outputClass;
    }

    @Override
    public double getOutputClass(double[] row)
    {
        return outputClass;
    }

    @Override
    public String toString()
    {
        return String.format("Leaf: Attr:\"%s\", Output:\"%s\"", getAttributeName(), getOutputClass());
    }

    private double getOutputClass()
    {
        return outputClass;
    }
}
