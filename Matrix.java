import javafx.scene.control.TextArea;

public class Matrix {

    int rowNumber;
    int columnNumber;
    double[][] elements;

    Matrix(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.elements = new double[rowNumber][columnNumber];
    }  //  end of constructor

    public void printMatrix() {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                System.out.printf("%15.2f", elements[i][j]);
            }
            System.out.println();
        }
    }  //  end of printMatrix()

    public void printMatrix(TextArea output) {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                output.appendText(String.format("%15.2f", elements[i][j]));
            }
            output.appendText("\n");
        }
    }  //  end of printMatrix()

    private static void interchangeRows(double[] row1, double[] row2) {
        double[] temp = new double[row1.length];
        for (int i = 0; i < row1.length; i++) {
            temp[i] = row1[i];
            row1[i] = row2[i];
            row2[i] = temp[i];
        }
    }  //  end of interchangeRows()

    private static void divideRowByPivot(double[] row) {

        double pivot = 0;

        //  find pivot
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                pivot = row[i];
                break;
            }
        }

        //  divide by pivot
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                row[i] /= pivot;
            }
        }

    }  //  end of divideRowByPivot()

    private static void addRows(double[] row1, double[] row2) {

        double pivot = 0;

        //  find pivot
        for (int i = 0; i < row1.length; i++) {
            if (row1[i] != 0) {
                pivot = row2[i];
                break;
            }
        }

        pivot *= -1;

        //  add rows
        for (int i = 0; i < row2.length; i++) {
            if (row1[i] != 0 || row2[i] != 0) {
                row2[i] += (pivot * row1[i]);
            }
        }

    }  //  end of addRows()

    public void reduceMatrix() {

        String operations;

        //  go through each row
        for (int i = 0; i < elements.length - 1; i++) {
            operations = "";
            double pivot = 0;
            int pivotColumn = 0;

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivot = elements[i][j];
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  interchange rows where possible
            if (pivot != 1) {
                for (int j = i + 1; j < elements.length; j++) {
                    for (int m = 0; m < pivotColumn; m++) {
                        if (elements[j][m] != 0) {
                            operations += "R" + (i + 1) + " <--> " + "R" + (j + 1) + "\n";
                            interchangeRows(elements[i], elements[j]);
                            break;
                        }
                    }  //  end of m for loop
                }  //   end of j for loop
            }  //  end of if to interchange rows

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivot = elements[i][j];
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  divide row by pivot
            if (pivot != 1) {
                operations += "R" + (i + 1) + " / " + pivot + "\n";
                divideRowByPivot(elements[i]);
            }  //  end of if to divide rows

            //  add rows
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[j][pivotColumn] != 0) {
                    operations += "R" + (j + 1) + " = R" + (j + 1) + " + " + (elements[j][pivotColumn] * -1) + "R" + (i + 1) + "\n";
                    addRows(elements[i], elements[j]);
                }
            }  //  end of for loop to add rows
            System.out.println("\n" + operations);
            printMatrix();

        }  //  end of for loop to go through rows

        //  reduce last row
        double pivot = 0;
        for (int i = 0; i < elements[elements.length - 1].length; i++) {
            if (elements[elements.length - 1][i] != 0) {
                pivot = elements[elements.length - 1][i];
                break;
            }
        }
        if (pivot != 1 && pivot != 0) {
            operations = "";
            operations += "R" + elements.length + " / " + pivot + "\n";
            divideRowByPivot(elements[elements.length - 1]);
            System.out.println("\n" + operations);
            printMatrix();
        }

    }  //  end of reduceMatrix()

    public void reduceMatrix(TextArea output) {

        String operations;

        //  go through each row
        for (int i = 0; i < elements.length - 1; i++) {
            operations = "";
            double pivot = 0;
            int pivotColumn = 0;

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivot = elements[i][j];
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  interchange rows where possible
            if (pivot != 1) {
                for (int j = i + 1; j < elements.length; j++) {
                    for (int m = 0; m < pivotColumn; m++) {
                        if (elements[j][m] != 0) {
                            operations += "R" + (i + 1) + " <--> " + "R" + (j + 1) + "\n";
                            interchangeRows(elements[i], elements[j]);
                            break;
                        }
                    }  //  end of m for loop
                }  //   end of j for loop
            }  //  end of if to interchange rows

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivot = elements[i][j];
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  divide row by pivot
            if (pivot != 1) {
                operations += "R" + (i + 1) + " / " + pivot + "\n";
                divideRowByPivot(elements[i]);
            }  //  end of if to divide rows

            //  add rows
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[j][pivotColumn] != 0) {
                    operations += "R" + (j + 1) + " = R" + (j + 1) + " + " + (elements[j][pivotColumn] * -1) + "R" + (i + 1) + "\n";
                    addRows(elements[i], elements[j]);
                }
            }  //  end of for loop to add rows
            output.appendText("\n" + operations);
            printMatrix(output);

        }  //  end of for loop to go through rows

        //  reduce last row
        double pivot = 0;
        for (int i = 0; i < elements[elements.length - 1].length; i++) {
            if (elements[elements.length - 1][i] != 0) {
                pivot = elements[elements.length - 1][i];
                break;
            }
        }
        if (pivot != 1 && pivot != 0) {
            operations = "";
            operations += "R" + elements.length + " / " + pivot + "\n";
            divideRowByPivot(elements[elements.length - 1]);
            output.appendText("\n" + operations);
            printMatrix(output);
        }

    }  //  end of reduceMatrix()

    public void reduceRowMatrix() {

        reduceMatrix();

        String operations;
        //  go through rows in reverse
        for (int i = elements.length - 1; i > 0; i--) {

            operations = "";
            int pivotColumn = 0;

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  add rows
            for (int j = i - 1; j >= 0; j--) {
                if (elements[j][pivotColumn] != 0) {
                    operations += "R" + (j + 1) + " = R" + (j + 1) + " + " + (elements[j][pivotColumn] * -1) + "R" + (i + 1) + "\n";
                    addRows(elements[i], elements[j]);
                }
            }  //  end of for loop to add rows

            System.out.println("\n" + operations);
            printMatrix();

        }  //  end of for loop to go through rows

    }  //  end of reduceRowMatrix()

    public void reduceRowMatrix(TextArea output) {

        reduceMatrix(output);

        String operations;
        //  go through rows in reverse
        for (int i = elements.length - 1; i > 0; i--) {

            operations = "";
            int pivotColumn = 0;

            //  find pivot
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != 0) {
                    pivotColumn = j;
                    break;
                }
            }  //  end of for loop to find pivot

            //  add rows
            for (int j = i - 1; j >= 0; j--) {
                if (elements[j][pivotColumn] != 0) {
                    operations += "R" + (j + 1) + " = R" + (j + 1) + " + " + (elements[j][pivotColumn] * -1) + "R" + (i + 1) + "\n";
                    addRows(elements[i], elements[j]);
                }
            }  //  end of for loop to add rows

            output.appendText("\n" + operations);
            printMatrix(output);

        }  //  end of for loop to go through rows

    }  //  end of reduceRowMatrix()

}  //  end of class
