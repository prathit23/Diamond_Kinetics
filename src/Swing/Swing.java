package Swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Swing {

    private static final String fileName = "latestSwing.csv";
    public static void main(String[] args) {

        Swing swing = new Swing();
        List<List<Double>> swingData = swing.readFile(fileName);
        if(swingData.size()!=0)
            swing.userInterface(swingData);


    }
    private List<List<Double>>readFile(String fileName){

        /** Since we need to send entire columns to our function we create a List of List where outerList.get(0) will return the 1st column
         and outerList.get(0).get(0) will return the first element of first column. This way if we want to pass ax column we can directly
         pass outerList.get(1) to our function **/

        List<List<Double>> data = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(fileName))){
            String [] line = sc.nextLine().split(",");
            int noOfColumns = line.length;

            // Initializing the ArrayLists
            for(int i=0; i<noOfColumns;i++){
                data.add(new ArrayList<>());
                data.get(i).add(Double.parseDouble(line[i]));
            }
            while (sc.hasNextLine()){
                line = sc.nextLine().split(",");
                for(int i=0; i<noOfColumns;i++)
                    data.get(i).add(Double.parseDouble(line[i]));

            }
        }

        catch (FileNotFoundException e){
            System.out.println("Incorrect File Name");
        }
        return data;
    }
    private void userInterface(List<List<Double>> swingData){

        Scanner sc = new Scanner(System.in);
        SwingFunctions swingFunctions = new SwingFunctions();
        int choice =-1;

        int end = 0;
        do {
            try {


                System.out.println("Choose Function to Run: \n\n1. searchContinuityAboveValue \n2. backSearchContinuityWithinRange (indexBegin>indexEnd) " +
                        "\n3. searchContinuityAboveValueTwoSignals" + "\n4. searchMultiContinuityWithinRange \n5. Exit");
                choice = sc.nextInt();

                if(choice<1 || choice>5) throw new InvalidDataRangeException();

                if (choice == 5) break;


                System.out.println("Enter indexBegin");
                int indexBegin = sc.nextInt();
                System.out.println("Enter indexEnd");
                int indexEnd = sc.nextInt();
                System.out.println("Enter winLength");
                int winLength = sc.nextInt();

                System.out.println("Choose Column \n1. ax\n2. ay\n3. az \n4.wx \n5.wy \n6.wz");
                int column1 = sc.nextInt();
                int result = -1;
                switch (choice) {

                    case 1:
                        System.out.println("Enter threshold");
                        double threshold = sc.nextDouble();
                        result = swingFunctions.searchContinuityAboveValue(swingData.get(column1), indexBegin, indexEnd, threshold, winLength);
                        break;

                    case 2:
                        System.out.println("Enter low threshold");
                        double thresholdLo = sc.nextDouble();
                        System.out.println("Enter High threshold");
                        double thresholdHi = sc.nextDouble();
                        result = swingFunctions.backSearchContinuityWithinRange(swingData.get(column1), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
                        break;

                    case 3:
                        System.out.println("Choose 2nd Column \n1. ax\n2. ay\n3. az \n4.wx \n5.wy \n6.wz");
                        int column2 = sc.nextInt();
                        System.out.println("Enter 1st threshold");
                        double threshold1 = sc.nextDouble();
                        System.out.println("Enter 2nd threshold");
                        double threshold2 = sc.nextDouble();
                        result = swingFunctions.searchContinuityAboveValueTwoSignals(swingData.get(column1), swingData.get(column2), indexBegin, indexEnd, threshold1, threshold2, winLength);
                        break;

                    case 4:
                        System.out.println("Enter low threshold");
                        double thresholdLo4 = sc.nextDouble();
                        System.out.println("Enter High threshold");
                        double thresholdHi4 = sc.nextDouble();
                        List<List<Integer>> output = swingFunctions.searchMultiContinuityWithinRange(swingData.get(column1), indexBegin, indexEnd, thresholdLo4, thresholdHi4, winLength);

                        if (output.size() > 0) {
                            for (List list : output)
                                System.out.println("Start Index: " + list.get(0) + " End Index " + list.get(1));

                            result = 0;
                        }
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Wrong input");
                        break;
                }

                if (result == -1)
                    System.out.println("Sorry values you entered could not be found.\n");
                else
                    System.out.println("Index = " + result);

                System.out.println();
                System.out.println("Select 1 to continue and 2 to exit");
                end = sc.nextInt();
            }

            catch (InvalidDataRangeException e){

            }
            catch (InputMismatchException e){
                System.out.println("Incorrect Input, Exiting");
                break;
            }

        }while (end==1);
        System.out.println("Thank you for Choosing Diamond Kinetics");
    }



    class InvalidDataRangeException extends Exception{
        InvalidDataRangeException(){
            System.out.println("Invalid Entry, Exiting\n");
        }
    }
}
