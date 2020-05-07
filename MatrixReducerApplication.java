import fractions.DenominatorException;
import fractions.Fraction;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MatrixReducerApplication extends Application {

    Stage window;
    Scene scene;
    BorderPane windowContent;
    ScrollPane scroller;

    HBox northContent;
    VBox[] northComponents;
    Label[] prompts;
    TextField rowNumberInput;
    TextField columnNumberInput;
    String[] options = {"Reduced Echelon Form", "Row Reduced Echelon Form"};
    ChoiceBox<String> reductionTypeSelector;

    VBox centreContent;
    Button proceedButton;
    GridPane centreComponents;
    Label[] rowNumberTitles;
    TextField[][] elementInputs;
    Button reduceButton;
    Button resetButton;

    TextArea resultDisplay;

    Alert errorAlert;

    int rowNumber;
    int columnNumber;
    String reductionType;
    Matrix matrix;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //  initialise window
        window = primaryStage;
        windowContent = new BorderPane();
        scroller = new ScrollPane(windowContent);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);

        //  north content
        northContent = new HBox(20);
        northComponents = new VBox[3];
        prompts = new Label[3];
        for (int i = 0; i < 3; i++) {
            northComponents[i] = new VBox(5);

            if (i == 0) {
                prompts[i] = new Label("Enter number of rows:");
            }
            else if (i == 1) {
                prompts[i] = new Label("Enter number of columns:");
            }
            else if (i == 2) {
                prompts[i] = new Label("Select type of reduction:");
            }

            northComponents[i].getChildren().add(prompts[i]);
            northComponents[i].setAlignment(Pos.CENTER);
        }  //  end of for
        rowNumberInput = new TextField();
        northComponents[0].getChildren().add(rowNumberInput);
        columnNumberInput = new TextField();
        northComponents[1].getChildren().add(columnNumberInput);
        reductionTypeSelector = new ChoiceBox<>();
        reductionTypeSelector.getItems().addAll(options);
        reductionTypeSelector.setValue(options[0]);
        northComponents[2].getChildren().add(reductionTypeSelector);
        northContent.getChildren().addAll(northComponents);
        northContent.setAlignment(Pos.CENTER);
        windowContent.setTop(northContent);

        //  centre content
        centreContent = new VBox(5);
        proceedButton = new Button("Proceed");
        centreComponents = new GridPane();
        centreComponents.setAlignment(Pos.CENTER);
        centreComponents.setPadding(new Insets(5, 5, 5, 5));
        centreComponents.setGridLinesVisible(true);
        reduceButton = new Button("Reduce");
        reduceButton.setVisible(false);
        resetButton = new Button("Reset");
        resetButton.setVisible(false);
        centreContent.getChildren().addAll(proceedButton, centreComponents, reduceButton, resetButton);
        centreContent.setAlignment(Pos.CENTER);
        windowContent.setCenter(centreContent);

        //  bottom content
        resultDisplay = new TextArea();
        resultDisplay.setEditable(false);
        windowContent.setBottom(resultDisplay);

        //  set scene
        scene = new Scene(scroller);
        window.setScene(scene);
        window.setTitle("Matrix Reducer");
        window.setMaximized(true);
        window.show();

        //  error
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Input Error");
        errorAlert.setHeaderText("");

        //  set style
        scene.getStylesheets().add("Style.css");

        //  set actions
        proceedButton.setOnAction(e -> {
            //  create matrix object
            try {
                rowNumber = Integer.parseInt(rowNumberInput.getText());
                columnNumber = Integer.parseInt(columnNumberInput.getText());
                if (reductionTypeSelector.getValue().equals(options[0])) {
                    reductionType = options[0];
                } else if (reductionTypeSelector.getValue().equals(options[1])) {
                    reductionType = options[1];
                }

                //  check if a valid row and column number were entered
                if (rowNumber > 0 && columnNumber > 0) {
                    matrix = new Matrix(rowNumber, columnNumber);

                    //  prepare element inputs
                    rowNumberTitles = new Label[rowNumber];
                    elementInputs = new TextField[rowNumber][columnNumber];

                    for (int i = 0; i < rowNumber; i++) {
                        rowNumberTitles[i] = new Label("Row " + (i + 1) + ":");
                        rowNumberTitles[i].setId("row-title");
                        GridPane.setConstraints(rowNumberTitles[i], 0, i);
                        centreComponents.getChildren().add(rowNumberTitles[i]);

                        for (int j = 0; j < elementInputs[i].length; j++) {
                            elementInputs[i][j] = new TextField();
                            GridPane.setConstraints(elementInputs[i][j], j + 1, i);
                            centreComponents.getChildren().add(elementInputs[i][j]);
                        }  //  end of inner for
                    }  //  end of elementInputs for loop

                    centreComponents.setVisible(true);


                    // display other buttons
                    reduceButton.setVisible(true);
                    resetButton.setVisible(true);
                    rowNumberInput.setDisable(true);
                    columnNumberInput.setDisable(true);
                    reductionTypeSelector.setDisable(true);
                    proceedButton.setDisable(true);
                }  //  end of if to check valid row and column number

                else {
                    errorAlert.setContentText("Please enter valid integers (> 0)");
                    errorAlert.showAndWait();
                }

            } catch (NumberFormatException e1) {
                errorAlert.setContentText("Please enter valid integers (> 0)");
                errorAlert.showAndWait();
            }
        });

        reduceButton.setOnAction(e -> {

            resultDisplay.setText("");

            try {

                getElements();
                resultDisplay.appendText("Original matrix:\n");
                resultDisplay.appendText(matrix.toString());

                if (reductionTypeSelector.getValue().equals(options[0])) {
                    matrix.reduceMatrix(resultDisplay);
                    resultDisplay.appendText("Reduced matrix");
                }

                else if (reductionTypeSelector.getValue().equals(options[1])) {
                    matrix.reduceRowMatrix(resultDisplay);
                    resultDisplay.appendText("Row reduced matrix");
                }

            } catch (Exception e1) {
            }

        });

        resetButton.setOnAction(e -> {

            rowNumberInput.setText("");
            columnNumberInput.setText("");
            reductionTypeSelector.setValue(options[0]);
            rowNumberInput.setDisable(false);
            columnNumberInput.setDisable(false);
            reductionTypeSelector.setDisable(false);
            proceedButton.setDisable(false);

            for (int i = 0; i < rowNumber; i++) {

                centreComponents.getChildren().remove(rowNumberTitles[i]);

                for (int j = 0; j < elementInputs[i].length; j++) {

                    centreComponents.getChildren().remove(elementInputs[i][j]);

                }  //  end of inner for

            }  //  end of constraintInputs for loop

            centreComponents.setVisible(false);
            reduceButton.setVisible(false);
            resetButton.setVisible(false);
            resultDisplay.setText("");

        });

    }  //  end of start()

    private void getElements() {

        for (int i = 0; i < elementInputs.length; i++) {

            for (int j = 0; j < elementInputs[i].length; j++) {

                if (elementInputs[i][j].getText().isEmpty()) {

                    matrix.elements[i][j] = new Fraction();

                } else {

                    try {
                        matrix.elements[i][j] = Fraction.convertToFraction(elementInputs[i][j].getText());
                    } catch (DenominatorException e1) {
                        errorAlert.setContentText("A fraction cannot have 0 as a denominator.\n" +
                                "Please enter a valid fraction");
                        errorAlert.showAndWait();
                    } catch (Exception e1) {
                        errorAlert.setContentText("Please enter fractions in any of the forms\n" +
                                "\"a\", \"b/c\" or \"a b/c\".\n" +
                                "You can also enter a whole number.\n" +
                                "Ensure that only 1 space is typed between a whole number\n" +
                                "and a fraction when entering a mixed fraction.");
                        errorAlert.showAndWait();
                    }  //  end of catch

                }  //  end of else

            }  //  end of for j

        }  //  end of for i

    }  //  end of getElements()

}  //  end of class
