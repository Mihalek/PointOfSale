package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;


// This class is our controller

public class Controller {

    private DBConnection dbConnection;

    @FXML
    private Label lcd;

    @FXML
    private TextField inputdevice;

    @FXML
    private Label printer;

    @FXML
    private Button enter;

    List<Pair> products;

    public Controller() {
        dbConnection = new DBConnection();
        lcd = new Label();
        printer = new Label();
        products = new ArrayList<Pair>();

    }

    @FXML
    void initialize() throws Exception {
        dbConnection.DeleteTable();
        dbConnection.CreateTable();


    }

    public void End(){
        double sum=0.0;
        String sSum;
        for(Pair i: products) {
            sum+= (Double)i.getValue();
        }
        sum = Math.round(sum * 100.0) / 100.0;
        sSum = new StringBuilder().append(sum).toString();
        String startText= "name | prize\n";
        printer.setText(startText);
        for(Pair i: products){
            printer.setText(printer.getText()+i.getKey()+" | " + i.getValue() +"\n");
        }

        printer.setText(printer.getText()+"\ntotal sum: " +sSum);
        lcd.setText("total sum: "+sSum);

    }

    @FXML
    public void onClick(ActionEvent actionEvent) throws Exception {
        String name;
        double prize;
        if(!inputdevice.getText().equals("exit")) {
            if(!inputdevice.getText().isEmpty()) {
                if (dbConnection.CodeISExists(inputdevice.getText())) {
                    name= dbConnection.SearchName(inputdevice.getText());
                    prize =dbConnection.SearchPrize(inputdevice.getText());
                    lcd.setText("name: " + name  +
                            "           prize: " + prize );
                    products.add(new Pair(name,prize));
                    inputdevice.clear();
                } else {
                    lcd.setText("Product not found");
                    inputdevice.clear();
                }
            }else{
                lcd.setText("Invalid bar-code");
                inputdevice.clear();
            }
        }

        else{
            End();
            inputdevice.clear();
        }
    }

    public void clear(ActionEvent actionEvent) {
        products.clear();
        lcd.setText("");
        printer.setText("");
        inputdevice.clear();
    }

    @FXML
    public void exitApplication(ActionEvent event) throws Exception {
        dbConnection.DeleteTable();
        Platform.exit();

    }

}
