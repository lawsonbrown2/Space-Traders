package spacetraders.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import spacetraders.classes.Person;
import spacetraders.classes.Good;
import spacetraders.classes.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketplaceController implements Initializable {

    private @FXML Button fuelGoodConfirm;
    private @FXML Button cargoGoodConfirm;
    private @FXML Button healthGoodConfirm;
    private @FXML Button fighterGoodConfirm;
    private @FXML Button miscGoodConfirm;
    private @FXML Label itemName;
    private @FXML Label numInv;
    private @FXML Label buyPrice;
    private @FXML Label sellPrice;
    private @FXML Label descItem;
    private @FXML Label currentCredits;
    private @FXML Label inventorySpaceLabel;
    private @FXML Label afterPurchaseLabel;
    private @FXML Label itemInventoryLabel;
    private @FXML Label characterStats;
    private @FXML Label repairCost;
    private @FXML Label refuelCost;
    private @FXML Label shipHealth;
    private @FXML Label currentFuel;


    private Person person = new Person();
    private Good good = null;
    private int buyPriceInt = 0;
    private int sellPriceInt = 0;
    private boolean hullTaken = false;
    private int size = 0;
    private MapController mapController = new MapController();
    private Region selectedRegion = mapController.getSelectedRegion();

    public void repairShip(ActionEvent actionEvent) {
        int cost = 100 * (person.getShip().getMaxHealth()
                - person.getShip().getCurrHealth()) - (5 * person.getEngineeringPoints());
        if (cost < 0) {
            cost = 0;
        }
        if (person.getCredits() >= cost) {
            person.setCredits(person.getCredits() - cost);
            person.getShip().setCurrHealth(person.getShip().getMaxHealth());
            repairCost.setText("0");
            shipHealth.setText(person.getShip().getCurrHealth() + "");
        } else {
            afterPurchaseLabel.setText("Cannot repair. You do not have enough money.");
        }
    }

    public void refuelShip(ActionEvent actionEvent) {
        int cost = person.getShip().getFuelCapacity() - person.getCurrFuel();
        if (person.getCredits() >= cost) {
            person.setCredits(person.getCredits() - cost);
            person.getShip().refuel(person.getShip().getFuelCapacity());
            refuelCost.setText("0");
            currentFuel.setText(person.getCurrFuel() + "");
        } else {
            afterPurchaseLabel.setText("Cannot refuel. You do not have enough money.");
        }
    }

    public void buyItem(ActionEvent actionEvent) {
        if (person.getCredits() >= buyPriceInt) {
            if (size < person.getShip().getCargoCapacity()) {
                if (good.getModStat() == "cargoCapacity") {
                    if (hullTaken) {
                        afterPurchaseLabel.setText("Your ship already has a Hull");
                        return;
                    } else {
                        hullTaken = true;
                    }
                } else {
                    size++;
                    person.getShip().getItemInventory().setSize(person.getShip().
                            getItemInventory().getSize() + 1);
                }
                int oldCredit = person.getCredits();
                int newCredit = oldCredit - buyPriceInt;
                person.setCredits(newCredit);
                if (good == Good.WINITEM) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource(
                                "..//screens//GameOver.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene gameOver = new Scene(root, 720, 480);
                    GameController gameController = new GameController();
                    gameController.changeStage(gameOver);
                }
                person.getShip().addItem(good);
                afterPurchaseLabel.setText("Purchase Successful");
                update();
            } else {
                afterPurchaseLabel.setText("Not enough space");
            }
        } else {
            afterPurchaseLabel.setText("Not enough credits");
        }
    }

    public void sellItem(ActionEvent actionEvent) {
        if (person.getShip().getItemInventory().getNumberOfGood(good) > 0) {
            if (good.getModStat() == "cargo capacity") {
                if (person.getShip().getCargoCapacity() - size >= good.getModFactor()) {
                    hullTaken = false;
                } else {
                    afterPurchaseLabel.setText("This Hull is Currently Holding Items");
                    return;
                }
            } else {
                size--;
                person.getShip().getItemInventory().setSize(person.getShip().
                        getItemInventory().getSize() - 1);
            }
            int oldCredit = person.getCredits();
            int newCredit = oldCredit + sellPriceInt;
            person.setCredits(newCredit);
            person.getShip().removeItem(good);
            afterPurchaseLabel.setText("Sale Successful");
            update();
        } else {
            afterPurchaseLabel.setText("No item to sell");
        }
    }

    public void showItem(ActionEvent actionEvent) {
        if (actionEvent.getSource() == fuelGoodConfirm) {           // button is for fuel
            good = person.getCurrRegion().getTechLevel().getFuel();
        }
        if (actionEvent.getSource() == cargoGoodConfirm) {
            good = person.getCurrRegion().getTechLevel().getCapacity();
        }
        if (actionEvent.getSource() == healthGoodConfirm) {
            good = person.getCurrRegion().getTechLevel().getDefense();
        }
        if (actionEvent.getSource() == fighterGoodConfirm) {
            good = person.getCurrRegion().getTechLevel().getWeapon();
        }
        if (actionEvent.getSource() == miscGoodConfirm) {
            if (person.getCurrRegion() == selectedRegion) {
                good = Good.WINITEM;
            } else {
                good = person.getCurrRegion().getTechLevel().getUpgrades();
            }
        }
        buyPriceInt = good.getBasePrice() - ((5 * person.getMerchantPoints()) / 10);
        sellPriceInt = good.getBasePrice() + (1 + (person.getMerchantPoints() / 10));
        itemName.setText(good.getName());
        descItem.setText("Increases " + good.getModStat() + " by " + good.getModFactor());
        buyPrice.setText(String.valueOf(buyPriceInt));
        sellPrice.setText(String.valueOf(sellPriceInt));
        numInv.setText(String.valueOf(person.getShip().getItemInventory().
                getNumberOfGood(good)));
        currentCredits.setText("" + person.getCredits());
        inventorySpaceLabel.setText("" + person.getShip().getCargoCapacity());
        itemInventoryLabel.setText("Item Inventory: " + person.getShip().getItemInventory()
                .toString());
        afterPurchaseLabel.setText("");
    }

    public void update() {
        person.getShip().setCargoCapacity(person.getShip().getCargoCapacity() - size);
        size = 0;
        numInv.setText(String.valueOf(person.getShip().getItemInventory().
                getNumberOfGood(good)));
        currentCredits.setText("" + person.getCredits());
        inventorySpaceLabel.setText("" + person.getShip().getCargoCapacity());
        itemInventoryLabel.setText("Item Inventory: " + person.getShip().getItemInventory()
                .toString());
        characterStats.setText("Engineering: " + person.getEngineeringPoints() + " Fighter: "
                + person.getFighterPoints() + " Merchant: " + person.getMerchantPoints()
                + " Pilot: " + person.getPilotPoints());
        buyPriceInt = good.getBasePrice() - ((5 * person.getMerchantPoints()) / 10);
        sellPriceInt = good.getBasePrice() + (1 + (person.getMerchantPoints() / 10));
        buyPrice.setText(String.valueOf(buyPriceInt));
        sellPrice.setText(String.valueOf(sellPriceInt));
        if (good == person.getCurrRegion().getTechLevel().getFuel()) {
            person.getShip().refuel(good.getModFactor() + person.getShip().getFuelCapacity());
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shipHealth.setText(person.getShip().getCurrHealth() + "");
        currentFuel.setText(person.getCurrFuel() + "");
        refuelCost.setText((person.getShip().getFuelCapacity() - person.getCurrFuel()) + "");
        int cost = 100 * (person.getShip().getMaxHealth()
                - person.getShip().getCurrHealth()) - (5 * person.getEngineeringPoints());
        if (cost < 0) {
            cost = 0;
        }
        repairCost.setText(cost + "");
    }
}
