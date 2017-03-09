package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.toolkit.utility.Matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cstaheli on 3/5/2017.
 */
public class OccurrenceCount
{
    private Map<Integer, Integer> occurrences;

    public OccurrenceCount(Matrix matrix)
    {
        this(matrix.cols());
        for (int row = 0; row < matrix.rows(); ++row)
        {
            for (int col = 0; col < matrix.cols(); ++col)
            {

            }
        }
    }

    public OccurrenceCount()
    {

    }

    public OccurrenceCount(int instances)
    {
        occurrences = new HashMap<>(instances);
        for (int i = 0; i < occurrences.size(); ++i)
        {
            occurrences.put(i, 0);
        }
    }

    public void addOccurance(int key)
    {
        int newValue = occurrences.get(key);
        ++newValue;
        occurrences.put(key, newValue);
    }
}
