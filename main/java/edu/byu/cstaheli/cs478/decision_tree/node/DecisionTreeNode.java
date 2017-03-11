package edu.byu.cstaheli.cs478.decision_tree.node;

import java.util.List;

/**
 * Created by cstaheli on 3/9/2017.
 */
public abstract class DecisionTreeNode
{
    private List<DecisionTreeNode> children;

    public abstract double getOutputClass(double[] row);

    public void setChildren(List<DecisionTreeNode> children)
    {
        this.children = children;
    }

    public DecisionTreeNode getChild(int index)
    {
        return children.get(index);
    }
}
