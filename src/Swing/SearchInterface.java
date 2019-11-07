package Swing;

import java.util.ArrayList;
import java.util.List;


public interface SearchInterface {
    // Used Lists because it's more flexible & modifiable and is as fast an array if the size of list is not changing
    //Assumption that indexBegin & indexEnd is inclusive
    int searchContinuityAboveValue(List<Double> data, int indexBegin, int indexEnd, double threshold, int winLength);
    int backSearchContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength);
    int searchContinuityAboveValueTwoSignals(List<Double> data1, List<Double> data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength);
    List<List<Integer>> searchMultiContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength);
}
