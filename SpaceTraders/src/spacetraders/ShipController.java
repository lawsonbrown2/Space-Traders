package spacetraders;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import spacetraders.classes.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class ShipController implements Initializable {
    @FXML Label name;
    @FXML Label health;
    @FXML Label cargoCapacity;
    @FXML Label fuelCapacity;
    @FXML Label itemInventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Person person = new Person();
        name.setText(person.getShip().getName());
        health.setText("" + person.getShip().getHealth());
        cargoCapacity.setText("" + person.getShip().getCargoCapacity());
        fuelCapacity.setText("" + person.getShip().getFuelCapacity());
        itemInventory.setText("" + person.getShip().getItemInventory());
    }
}
