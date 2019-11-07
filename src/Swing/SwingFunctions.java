package Swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SwingFunctions  implements SearchInterface {

    @Override
    public int searchContinuityAboveValue(List<Double> data, int indexBegin, int indexEnd, double threshold, int winLength) {

        // Not possible then return -1
        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return -1;

        int firstIndex= -1,count=0;
        boolean findFirst = true;

        for(int i =indexBegin;i<=indexEnd;i++){

            if(data.get(i)>threshold){
                count++;
                if(findFirst){
                    firstIndex = i;
                    findFirst= false;
                }
                if(count>=winLength)
                    return firstIndex;
            }
            else{
                count=0;
                findFirst=true;
                firstIndex=-1;
            }
        }
        // If not found then return -1
        return firstIndex;
    }

    @Override
    public int backSearchContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength) {

        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return -1;

        int firstIndex= -1,count=0;
        boolean findFirst = true;
        for(int i =indexBegin;i>=indexEnd;i--){

            if(data.get(i)>thresholdLo && data.get(i)<thresholdHi){
                count++;
                if(findFirst){
                    firstIndex = i;
                    findFirst= false;
                }
                if(count>=winLength)
                    return firstIndex;
            }
            else{
                count=0;
                firstIndex=-1;
                findFirst=true;
            }
        }
        return firstIndex;
    }

    @Override
    public int searchContinuityAboveValueTwoSignals(List<Double> data1, List<Double> data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength) {
        if(!sanityCheck(data1,indexBegin,indexEnd,winLength) && !sanityCheck(data1,indexBegin,indexEnd,winLength))
            return -1;

        int firstIndex= -1,count=0;
        boolean findFirst = true;
        for(int i =0;i<=indexEnd;i++){

            if(data1.get(i)>threshold1 && data2.get(i)>threshold2){
                count++;
                if(findFirst){
                    firstIndex = i;
                    findFirst= false;
                }
                if(count>=winLength)
                    return firstIndex;
            }
            else{
                count=0;
                firstIndex=-1;
                findFirst=true;
            }
        }
        return firstIndex;
    }

    @Override
    public List<List<Integer>> searchMultiContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength) {

        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return new ArrayList<>();

        int firstIndex= -1,count=0;
        boolean findFirst = true;

        List<List<Integer>> startEndIndices = new ArrayList<>();
        for(int i =0;i<=indexEnd;i++){

            if(data.get(i)>thresholdLo && data.get(i)<thresholdHi){
                count++;
                if(findFirst){
                    firstIndex = i;
                    findFirst= false;
                }

            }
            else{
                if(count>=winLength){
                    startEndIndices.add(Arrays.asList(firstIndex,i-1));
                }
                count=0;
                firstIndex=-1;
                findFirst=true;
            }
        }
        return  startEndIndices;
    }

    // Assumption no garbage value
    private  boolean sanityCheck(List<Double> data, int indexBegin, int indexEnd,  int winLength){
        return  winLength<= Math.abs(indexEnd-indexBegin+1) && indexBegin>=0 && indexBegin<data.size() && indexEnd>=0 && indexEnd<data.size();
    }
}
