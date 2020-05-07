import fractions.*;
import javafx.scene.control.TextArea;

public class Matrix {

    Fraction[][] elements;

    private final Fraction ZERO = new Fraction();
    private final Fraction ONE = new Fraction(1);

    public Matrix(int rowNumber, int columnNumber) {

        this.elements = new Fraction[rowNumber][columnNumber];

    }  //  end of constructor

    public void reduceRowMatrix(TextArea output) {

        reduceMatrix(output);

        String operations;

        //  go through rows in reverse
        for (int i = elements.length - 1; i > 0; i--) {

            operations = "";
            int pivotColumn = getPivotColumn(elements[i]);

            //  add rows
            for (int j = i - 1; j >= 0; j--) {

                if (!elements[j][pivotColumn].equals(ZERO)) {

                    try {
                        operations += "R" + (j + 1) + " = R" + (j + 1) + " + "
                                    + Fraction.multiplyFractions(elements[j][pivotColumn], new Fraction(-1))
                                    + "R" + (i + 1) + "\n";
                    } catch (DenominatorException e) {
                        e.printStackTrace();
                    }

                    addRows(elements[i], elements[j]);

                }  //  end of if

            }  //  end of for loop to add rows

            output.appendText("\n" + operations);
            output.appendText(this.toString());

        }  //  end of for loop to go through rows

    }  //  end of reduceRowMatrix()

    public void reduceMatrix(TextArea output) {

        String operations;

        //  go through each row
        for (int i = 0; i < elements.length - 1; i++) {

            operations = "";

            Fraction pivot = getPivot(elements[i]);
            int pivotColumn = getPivotColumn(elements[i]);

            //  interchange rows where possible
            if (!pivot.equals(ONE)) {

                for (int j = i + 1; j < elements.length; j++) {

                    if (elements[j][pivotColumn].equals(ONE) ||
                        (getPivotColumn(elements[j]) < pivotColumn &&
                        !getPivot(elements[j]).equals(ZERO))) {

                        operations += "R" + (i + 1) + " <--> " + "R" + (j + 1) + "\n";
                        interchangeRows(elements[i], elements[j]);
                        output.appendText("\n" + operations);
                        output.appendText(this.toString());
                        break;

                    }  //  end of if

                }  //   end of j for loop

            }  //  end of if to interchange rows

            pivot = getPivot(elements[i]);
            pivotColumn = getPivotColumn(elements[i]);

            operations = "";
            //  divide row by pivot
            if (!pivot.equals(ONE)) {

                operations += "R" + (i + 1) + " / " + pivot + "\n";
                divideRowByPivot(elements[i]);
                output.appendText("\n" + operations);
                output.appendText(this.toString());

            }  //  end of if to divide rows

            operations = "";
            //  add rows
            for (int j = i + 1; j < elements.length; j++) {

                if (!elements[j][pivotColumn].equals(ZERO)) {

                    try {
                        operations += "R" + (j + 1) + " = R" + (j + 1) + " + " +
                                    Fraction.multiplyFractions(elements[j][pivotColumn], new Fraction(-1))
                                    + "R" + (i + 1) + "\n";
                    } catch (DenominatorException e) {
                        e.printStackTrace();
                    }

                    addRows(elements[i], elements[j]);

                }  //  end of if

            }  //  end of for loop to add rows

            output.appendText("\n" + operations);
            output.appendText(this.toString());

        }  //  end of for loop to go through rows

        //  reduce last row
        Fraction[] lastRow = elements[elements.length - 1];
        Fraction pivot = getPivot(lastRow);

        if (!pivot.equals(ONE) && !pivot.equals(ZERO)) {

            operations = "";
            operations += "R" + elements.length + " / " + pivot + "\n";
            divideRowByPivot(lastRow);
            output.appendText("\n" + operations);
            output.appendText(this.toString());

        }  //  end of if

    }  //  end of reduceMatrix()


    private void interchangeRows(Fraction[] row1, Fraction[] row2) {

        Fraction[] temporaryRow = new Fraction[row1.length];

        for (int i = 0; i < row1.length; i++) {

            temporaryRow[i] = row1[i];
            row1[i] = row2[i];
            row2[i] = temporaryRow[i];

        }  //  end of for

    }  //  end of interchangeRows()

    private void divideRowByPivot(Fraction[] row) {

        final Fraction ZERO = new Fraction();
        Fraction pivot = getPivot(row);

        for (int i = 0; i < row.length; i++) {

            if (Fraction.compareFractions(row[i], ZERO) != 0) {

                try {
                    row[i] = Fraction.divideFractions(row[i], pivot);
                } catch (DenominatorException e) {
                    e.printStackTrace();
                }

            }  //  end of if

        }  //  end of for

    }  //  end of divideRowByPivot()

    private void addRows(Fraction[] currentRow, Fraction[] otherRow) {

        Fraction pivot = otherRow[getPivotColumn(currentRow)];

        pivot = Fraction.negateFraction(pivot);

        for (int i = 0; i < otherRow.length; i++) {

            if (!currentRow[i].equals(ZERO) || !otherRow[i].equals(ZERO)) {

                try {
                    otherRow[i] = Fraction.addFractions(otherRow[i], Fraction.multiplyFractions(pivot, currentRow[i]));
                } catch (DenominatorException e) {
                    e.printStackTrace();
                }

            }  //  end of if

        }  //  end of for

    }  //  end of addRows()

    private Fraction getPivot(Fraction[] row) {

        Fraction pivot = ZERO;

        //  find pivot
        for (Fraction currentElement: row) {

            if (!currentElement.equals(ZERO)) {

                pivot = currentElement;
                break;

            }  //  end of if

        }  //  end of for to find pivot

        return pivot;

    }  //  end of getPivot()

    private int getPivotColumn(Fraction[] row) {

        int pivotColumn = -1;

        //  find pivot
        for (int j = 0; j < row.length; j++) {

            if (!row[j].equals(ZERO)) {
                pivotColumn = j;
                break;
            }

        }  //  end of for loop to find pivot

        return pivotColumn;

    }

    public String toString() {

        String matrix = "";

        for (int i = 0; i < elements.length; i++) {

            for (int j = 0; j < elements[i].length; j++) {
                matrix += String.format("%-20s", elements[i][j]);
            }

            matrix += "\n";

        }

        return matrix;

    }  //  end of toString()

}  //  end of class
