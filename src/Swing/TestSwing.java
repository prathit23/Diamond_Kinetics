package Swing;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSwing {

    static SwingFunctions swingFunctions;
    static private List<List<Double>> swingData;

    @BeforeClass
    public static void setupClass() {
        Swing swing = new Swing();
        swingData = swing.getSwingData();
        swingFunctions = new SwingFunctions();
    }

    @Test
    public void test1_searchContinuityAboveValue() {
        assertEquals(24, swingFunctions.searchContinuityAboveValue(swingData.get(1),0,1000,0.3,10));
    }

    @Test
    public void test2_backSearchContinuityWithinRange() {
        assertEquals(72, swingFunctions.backSearchContinuityWithinRange(swingData.get(1),100,10,1.0,1.9,10));
    }

    @Test
    public void test3_searchContinuityAboveValueTwoSignals() {
        assertEquals(24, swingFunctions.searchContinuityAboveValueTwoSignals(swingData.get(1), swingData.get(2),0,1000,0.3,0.4,10));
    }

    @Test
    public void test4_searchMultiContinuityWithinRange() {
        List<List<Integer>> startEndIndices = new ArrayList<>();
        startEndIndices.add(Arrays.asList(24,36));
        startEndIndices.add(Arrays.asList(73,286));
        startEndIndices.add(Arrays.asList(337,475));
        startEndIndices.add(Arrays.asList(477,621));
        startEndIndices.add(Arrays.asList(647,732));
        assertEquals(startEndIndices, swingFunctions.searchMultiContinuityWithinRange(swingData.get(1),0,1000,0.3,1.0,10));
    }



}
