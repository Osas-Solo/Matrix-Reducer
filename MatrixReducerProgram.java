import java.util.Scanner;

public class MatrixReducerProgram {

    public static void main (String[] args) {

        int rowNumber = 0;
        int columnNumber = 0;
        boolean foundException;
        int option = 0;

        //  input option
        foundException = true;
        while (foundException) {
            try {
                option = inputInteger("reduction type:\n" +
                        "1. Reduced Echelon Form\n" +
                        "2. Reduced Row Echelon Form");
                if (option == 1 || option == 2) {
                    foundException = false;
                }
            } catch (Exception e) {
                printErrorMessage("integer");
            }
        }

        //  input row number
        foundException = true;
        while (foundException) {
            try {
                rowNumber = inputInteger("number of rows:");
                if (rowNumber > 1) {
                    foundException = false;
                }
            } catch (Exception e) {
                printErrorMessage("integer");
            }
        }

        //  input column number
        foundException = true;
        while (foundException) {
            try {
                columnNumber = inputInteger("number of columns:");
                if (columnNumber > 1) {
                    foundException = false;
                }
            } catch (Exception e) {
                printErrorMessage("integer");
            }
        }

        //  create matrix
        Matrix matrix = new Matrix(rowNumber, columnNumber);

        //  initialise elements
        for (int i = 0; i < matrix.elements.length; i++) {
            for (int j = 0; j < matrix.elements[i].length; j++) {
                matrix.elements[i][j] = 0;
            }
        }

        //  input elements
        for (int i = 0; i < matrix.elements.length; i++) {

            System.out.println("\nEnter elements for row " + (i + 1) + ": ");

            for (int j = 0; j < matrix.elements[i].length; j++) {

                //  input coefficient
                foundException = true;
                while (foundException) {
                    try {
                        matrix.elements[i][j] = inputDouble("element for column " + (j + 1) + ": ");
                        foundException = false;
                    } catch (Exception e) {
                        printErrorMessage("number");
                    }
                }  //  end of while

            }  //  end of inner for

        }  //  end of outer for

        //  display results
        System.out.println("\nOriginal matrix:");
        matrix.printMatrix();

        if (option == 1) { //  reduced matrix
            matrix.reduceMatrix();
            System.out.println("Reduced matrix");
        }

        else if (option == 2) {  //  row reduced matrix
            matrix.reduceRowMatrix();
            System.out.println("Row reduced matrix");
        }

    }  //  end of main



    public static int inputInteger(String message) {
        System.out.println("Enter " + message);
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        return x;
    }  //  end of inputValue()

    public static double inputDouble(String message) {
        System.out.println("Enter " + message);
        Scanner scan = new Scanner(System.in);
        double x = scan.nextInt();
        return x;
    }  //  end of inputValue()


    public static void printErrorMessage(String inputType) {
        System.out.println("Please enter a valid " + inputType);
    }  //  end of printErrorMessage()

}  //  end of class
