package edu.byu.cstaheli.cs478.decision_tree.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cstaheli on 3/9/2017.
 */
public abstract class Node
{
    private double primaryColumnValue;
    private String attributeName;
    private List<Node> children;

    protected Node()
    {
        primaryColumnValue = -1;
        children = new ArrayList<>();
    }

    public abstract double getOutputClass(double[] row);

    public void setChildren(List<Node> children)
    {
        this.children = children;
    }

    public Node getChild(int index)
    {
        return children.get(index);
    }

    public double getPrimaryColumnValue()
    {
        return primaryColumnValue;
    }

    public void setPrimaryColumnValue(double primaryColumnValue)
    {
        this.primaryColumnValue = primaryColumnValue;
    }

    public Node findChildWithPrimaryColumnValue(double primaryColumnValue)
    {
        for (Node node : children)
        {
            if (Double.compare(node.getPrimaryColumnValue(), primaryColumnValue) == 0)
            {
                return node;
            }
        }
        return null;
    }

    public String getAttributeName()
    {
        return attributeName;
    }

    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }
}
