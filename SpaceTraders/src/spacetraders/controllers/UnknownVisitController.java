package spacetraders.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import spacetraders.classes.Person;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnknownVisitController implements Initializable {
    private @FXML Label fuelCost, currFuel, distance;
    private @FXML Button visit;
    final Pane[] root = {null};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Person person = new Person();
        Double xDiff = (double) (person.getNextRegion().getCoords()[0] - person.getCurrRegion().getCoords()[0]);
        Double yDiff = (double) (person.getNextRegion().getCoords()[1] - person.getCurrRegion().getCoords()[1]);
        Double dist = (Math.pow(Math.pow(xDiff, 2.0) + Math.pow(yDiff, 2.0), 0.5));
        int fuelCostCalc = (int) (
                35 * dist - 10 * person.getPilotPoints()); //Fuel Cost formula
        if (fuelCostCalc > 0) {
            person.setFuelCost(fuelCostCalc);
        } else {
            person.setFuelCost(0);
        }
        fuelCost.setText("" + person.getFuelCost());
        currFuel.setText("" + person.getCurrFuel());
        distance.setText("" + dist);
    }

    public void visitUnknownRegion(javafx.event.ActionEvent actionEvent) {
        Person person = new Person();
        if (person.getFuelCost() < person.getCurrFuel()) {
            person.setCurrFuel(person.getCurrFuel() - person.getFuelCost());
            person.setCurrRegion(person.getNextRegion());
            person.addVisited(person.getCurrRegion());
            person.getCurrButton().setStyle("-fx-background-color: #00ff00");
            person.getCurrButton().setShape(new Circle(20.0));
            person.getNextButton().setStyle("-fx-background-color: #ffc300");
            person.getNextButton().setShape(new Rectangle(20.0, 20.0));
            person.setCurrButton(person.getCurrButton());
            try {
                root[0] = FXMLLoader.load(getClass().getResource(
                        "..//screens//RegionPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene regionPage = new Scene(root[0], 720, 480);
            GameController gameController = new GameController();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            gameController.changeStage(regionPage);
            stage.close();
        } else {
            try {
                root[0] = FXMLLoader.load(getClass().getResource(
                        "..//screens//notEnoughFuelError.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene fuelErrorPage = new Scene(root[0], 600, 400);
            GameController gameController = new GameController();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            gameController.changeStage(fuelErrorPage);
            stage.close();
        }
    }
}