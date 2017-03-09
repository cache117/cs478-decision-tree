package edu.byu.cstaheli.cs478.decision_tree;

import edu.byu.cstaheli.cs478.toolkit.MLSystemManager;
import org.junit.jupiter.api.Test;

/**
 * Created by cstaheli on 3/1/2017.
 */
class DecisionTreeTest
{
    @Test
    void testAnalyzeInputRow() throws Exception
    {
        String[] args;
        MLSystemManager manager = new MLSystemManager();
        String datasetsLocation = "src/test/resources/datasets/decision_tree/";
        args = ("-L decisiontree -A " + datasetsLocation + "voting.arff -E training -V").split(" ");
        manager.run(args);
    }

}