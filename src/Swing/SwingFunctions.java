package Swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SwingFunctions  implements SearchInterface {

    // Function could be modified in the future to add additional Functionality
    private int findLastIndex(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi){

        int l1 = findFirstIndex(data,indexBegin,indexEnd,Math.nextDown(thresholdHi),Double.MAX_VALUE,1,forwardSearch);
        int l2 = findFirstIndex(data,indexBegin,indexEnd,Double.MIN_VALUE, Math.nextUp(thresholdLo),1,forwardSearch);

        if(l1==-1) l1 = indexEnd;
        if(l2==-1) l2 = indexEnd;

        return Math.min(l1,l2)-1;
    }

    private int findFirstIndex(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength, int searchMode){

        int firstIndex= -1,count=0;
        boolean findFirst = true;

        int iterations = Math.abs(indexBegin-indexEnd);
        int i =indexBegin;

        while(iterations>=0 && i >=0 && i<=Math.max(indexBegin,indexEnd)){

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
                findFirst=true;
                firstIndex=-1;
            }

            if(searchMode==forwardSearch) // Forward Search
                i++;
            else if (searchMode==backwardSearch) // Backward Search
                i--;
            iterations--;

        }
        return -1;

    }
    @Override
    public int searchContinuityAboveValue(List<Double> data, int indexBegin, int indexEnd, double threshold, int winLength) {

        // Not possible then return -1
        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return -1;


        // If not found then return -1
        return findFirstIndex(data,indexBegin,indexEnd,threshold,Double.MAX_VALUE,winLength,forwardSearch);
    }

    @Override
    public int backSearchContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength) {

        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return -1;
        return  findFirstIndex(data,indexBegin,indexEnd,thresholdLo,thresholdHi,winLength,backwardSearch);
    }

    @Override
    public int searchContinuityAboveValueTwoSignals(List<Double> data1, List<Double> data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength) {
        if(!sanityCheck(data1,indexBegin,indexEnd,winLength) && !sanityCheck(data1,indexBegin,indexEnd,winLength))
            return -1;

        int first =-1,second =-2 , addIndex = 0;
        do{


            first = findFirstIndex(data1,indexBegin,indexEnd,threshold1,Double.MAX_VALUE,winLength,forwardSearch);
            second = findFirstIndex(data2,indexBegin,indexEnd,threshold2,Double.MAX_VALUE,winLength,forwardSearch);

            addIndex= Math.max(first,second);
            indexBegin+= addIndex;

        }while(first!=second && indexBegin<indexEnd );
        if(first==second)
            return first;
        else
            return -1;
    }

    @Override
    public List<List<Integer>> searchMultiContinuityWithinRange(List<Double> data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength) {

        if(!sanityCheck(data,indexBegin,indexEnd,winLength))
            return new ArrayList<>();

        int firstIndex;

        List<List<Integer>> startEndIndices = new ArrayList<>();

        while ( indexBegin <= indexEnd){
            firstIndex = findFirstIndex(data,indexBegin,indexEnd,thresholdLo,thresholdHi,winLength,forwardSearch);
            indexBegin = firstIndex+1;


            if(firstIndex>-1){

                int lastIndex = findLastIndex(data, indexBegin, indexEnd, thresholdLo, thresholdHi);

                startEndIndices.add(Arrays.asList(firstIndex,lastIndex));
                indexBegin =lastIndex+1;
            }
            else break;

        }

        return  startEndIndices;
    }

    // Assumption no garbage value
    private  boolean sanityCheck(List<Double> data, int indexBegin, int indexEnd,  int winLength){
        return  winLength<= Math.abs(indexEnd-indexBegin+1) && indexBegin>=0 && indexBegin<data.size() && indexEnd>=0 && indexEnd<data.size();
    }
}
