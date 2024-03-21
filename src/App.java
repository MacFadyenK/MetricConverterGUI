
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class App extends Application{
    //constant values for critical conversions
    final double miToKm = 1.6094;
    final double inToCm = 2.54;
    final double miToIn = 63360;
    final double kgToLb = 2.204623;
    final double galToL = 3.785412;

    //checks if string is a double number
    private boolean isValidDouble(String text) {
        if (text == null || text.isEmpty()) {
            return true; // Allow empty input
        }

        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //main title
        primaryStage.setTitle("Metric Converter");

        //all units in use
        String[] units = {"km", "mi", "cm", "in", "kg", "lb", "L", "gal"};

        //description of app
        Label desc = new Label("Welcome to the Metric Converter now with GUI!\n"+
        "The Metric Converter will convert a measurement value\nfrom one metric unit to another metric unit.");

        //label for first unit
        Label units1 = new Label("Unit being converted from:");

        //Drop down for the unit converting from
        @SuppressWarnings({ "rawtypes", "unchecked" })
        ComboBox unitCurrent = new ComboBox(FXCollections.observableArrayList(units));

        //label for second unit
        Label units2 = new Label("Unit to be converted to:");

        //Dropdown for unit converting to
        @SuppressWarnings({ "rawtypes", "unchecked" })
        ComboBox unitNew = new ComboBox(FXCollections.observableArrayList(units));

        //label for the numerical value textfield
        Label valueLabel = new Label("Numerical value to be converted:");

        //Textfield for entering the value to be converted
        TextField valueField = new TextField();
        valueField.setPromptText("Enter measurement value");

        //only allows double values to be input into text field
        valueField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidDouble(newValue)) {
                valueField.setText(oldValue);
            }
        });

        //button to be clicked when ready to calculate answer
        Button calcButton = new Button("Calculate");

        //Label for answer
        Label answer = new Label();

        //exit button
        Button exitButton = new Button("Exit");

        //exits application when exit button pressed
        exitButton.setOnAction(e -> {
            Platform.exit();
        });

        //action when the calculate button is clicked
        calcButton.setOnAction(new EventHandler<ActionEvent>(){
            //does all conversions adapted from original Metric Converter
            @Override
            public void handle(ActionEvent e){
                String unitStart = "";
                String unitEnd = "";
                double value = 0;
                double result = 0;
                int type = 0;
                
                boolean valid = false;

                //Unit to be converted from

                //gets value of second unit from combo box
                //if no value has been selected yet the combo box is null and the conversion is invalid
                if(unitCurrent.getValue() != null){
                    unitStart = unitCurrent.getValue().toString();
                }else{
                    answer.setText("This unit conversion is not valid.");
                    return;
                }

                switch(unitStart){
                    //all distance measurements implemented
                    case "km":
                    case "mi":
                    case "cm":
                    case "in":
                        //type 0 for distance
                        type = 0;
                        break;

                    //all weight measurements
                    case "kg":
                    case "lb":
                        //type 1 for weight
                        type = 1;
                        break;

                    //all volume measurements
                    case "L":
                    case "gal":
                        //type 2 for volume
                        type = 2;
                        break;
                }

                //Unit to be converted into
                valid = false;

                //gets value of second unit from combo box
                //if no value has been selected yet the combo box is null and the conversion is invalid
                if(unitNew.getValue() != null){
                    unitEnd = unitNew.getValue().toString();
                }else{
                    answer.setText("This unit conversion is not valid.");
                    return;
                }

                //checks for valid second units according to what the first unit is
                switch(type){
                    //when the first unit is a distance
                    case 0:
                        switch(unitEnd){
                            //valid cases for the second input
                            case "km":
                            case "mi":
                            case "cm":
                            case "in":
                                valid = true;
                                break;
                        }
                        break;

                    //when the first unit is a weight
                    case 1:
                        switch(unitEnd){
                            //valid second inputs
                            case "kg":
                            case "lb":
                                valid = true;
                                break;
                        }
                        break;

                    //when the first input is a volume
                    case 2:
                        switch(unitEnd){
                            //valid second inputs
                            case "L":
                            case "gal":
                                valid = true;
                                break;
                        }
                        break;
                }
                //if the first and second unit inputted are the same
                if(unitStart.equals(unitEnd)){
                    answer.setText("There is no conversion needed.\nThe two units are the same.");
                    valid = false;
                    return;
                }
                //the unit inputted is not valid
                else if(!valid){
                    answer.setText("This unit conversion is not valid.");
                    return;
                }

                //The numerical value for converting

                //checks if there is text inputted into the text field and takes that value as a double
                //when there is no value, a warning is issued.
                if(!valueField.getText().isEmpty()){
                    value = Double.parseDouble(valueField.getText());
                }else{
                    answer.setText("No value is input to be converted.");
                    return;
                }

                //switch statement to end all switch statements
                //converts from the first unit inputted into the second unit
                switch(unitStart){
                    //when first unit is kilometers
                    case "km":
                        switch(unitEnd){
                            //from km -> mi
                            case "mi":
                                result = value/miToKm;
                                answer.setText(String.format("%.3f mi",result));
                                break;
                            //from km -> cm
                            case "cm":
                                result = value*1000*100;
                                answer.setText(String.format("%.3f cm",result));
                                break;
                            //from km -> in
                            case "in":
                                result = value*1000*100/inToCm;
                                answer.setText(String.format("%.3f in",result));
                                break;
                        }
                        break;

                    //when first unit is miles
                    case "mi":
                        switch(unitEnd){
                            //from mi -> km
                            case "km":
                                result = value*miToKm;
                                answer.setText(String.format("%.3f km",result));
                                break;
                            //from mi -> cm
                            case "cm":
                                result = value*miToIn*inToCm;
                                answer.setText(String.format("%.3f cm",result));
                                break;
                            //from mi -> in
                            case "in":
                                result = value*miToIn;
                                answer.setText(String.format("%.3f in",result));
                                break;
                        }
                        break;

                    //when first unit is kilograms
                    case "kg":
                        switch(unitEnd){
                            //from kg -> lb
                            case "lb":
                                result = value*kgToLb;
                                answer.setText(String.format("%.3f lb",result));
                                break;
                        }
                        break;

                    //when first unit is pounds
                    case "lb":
                        switch(unitEnd){
                            //from lb -> kg
                            case "kg":
                            result = value/kgToLb;
                            answer.setText(String.format("%.3f kg",result));
                            break;
                        }
                        break;

                    //when first unit is liters
                    case "L":
                        switch(unitEnd){
                            //from L -> gal
                            case "gal":
                                result = value/galToL;
                                answer.setText(String.format("%.3f gal",result));
                                break;
                        }
                        break;

                    //when first unit is gallons
                    case "gal":
                        switch(unitEnd){
                            //from gal -> L
                            case "L":
                                result = value*galToL;
                                answer.setText(String.format("%.3f L",result));
                                break;
                        }
                        break;

                    //when first unit is cm
                    case "cm":
                        switch(unitEnd){
                            //from cm -> km
                            case "km":
                                result = value/1000/100;
                                answer.setText(String.format("%.3f km",result));
                                break;
                            //from cm -> mi
                            case "mi":
                                result = value/1000/100/miToKm;
                                answer.setText(String.format("%.3f mi",result));
                                break;
                            //from cm -> in
                            case "in":
                                result = value/inToCm;
                                answer.setText(String.format("%.3f in",result));
                                break;
                        }
                        break;

                    //when first unit is inches
                    case "in":
                        switch(unitEnd){
                            //from in -> km
                            case "km":
                                result = value*inToCm/100/1000;
                                answer.setText(String.format("%.3f km",result));
                                break;
                            //from in -> mi
                            case "mi":
                                result = value/miToIn;
                                answer.setText(String.format("%.3f mi",result));
                                break;
                            //from in -> cm
                            case "cm":
                                result = value*inToCm;
                                answer.setText(String.format("%.3f cm",result));
                                break;
                        }
                        break;
                }
            }
        });
        

        //flow pane orders all elements in a top down order
        FlowPane p = new FlowPane(Orientation.VERTICAL, 20.0, 20.0);

        //adding all elements to flow pane
        p.getChildren().add(desc);
        p.getChildren().add(units1);
        p.getChildren().add(unitCurrent);
        p.getChildren().add(units2);
        p.getChildren().add(unitNew);
        p.getChildren().add(valueLabel);
        p.getChildren().add(valueField);
        p.getChildren().add(calcButton);
        p.getChildren().add(answer);
        p.getChildren().add(exitButton);

        Scene scene = new Scene(p, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
