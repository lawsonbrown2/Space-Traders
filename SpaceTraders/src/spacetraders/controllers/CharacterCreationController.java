package spacetraders.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import spacetraders.classes.Person;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCreationController implements Initializable {
    private @FXML Button engineerPlus;
    private @FXML Button engineerMinus;
    private @FXML Button pilotPlus;
    private @FXML Button pilotMinus;
    private @FXML Button fighterPlus;
    private @FXML Button fighterMinus;
    private @FXML Button merchantPlus;
    private @FXML Button merchantMinus;
    private @FXML TextField nameField;
    private @FXML TextField engineeringField;
    private @FXML TextField fighterField;
    private @FXML TextField pilotField;
    private @FXML TextField merchantField;
    private @FXML Label nameBox;
    private @FXML Label remainMessage;

    private Person person = new Person();
    private Pane root = null;
    private GameController gameController = new GameController();
    private Boolean nameCheck = false;
    public void setEasy() {
        person.setDifficulty("Easy");
        System.out.println(person.getDifficulty());
        updateTextFields();
    }
    public void setMedium() {
        person.setDifficulty("Medium");
        System.out.println(person.getDifficulty());
        updateTextFields();
    }
    public void setHard() {
        person.setDifficulty("Hard");
        updateTextFields();
    }
    public void configDone(javafx.event.ActionEvent actionEvent) {
        if (nameCheck && person.getSkillPoints() == 0) {
            try {
                root = FXMLLoader.load(getClass().getResource("..//screens//characterPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene characterPage = new Scene(root, 720, 480);
            GameController gameController = new GameController();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            gameController.changeStage(characterPage);
        }
    }

    public void submitName(ActionEvent actionEvent) {
        if (nameField.getText() != null && !nameField.getText().isEmpty()) {
            nameBox.setText("Your name is: " + nameField.getText());
            nameCheck = true;
            person.setName(nameField.getText());
        } else {
            nameBox.setText("Enter a name.");
            nameCheck = false;
        }
    }
    public void incrementUp(ActionEvent actionEvent) {
        if (person.getSkillPoints() > 0) {
            if (actionEvent.getSource() == engineerPlus) {
                person.setEngineeringPoints(person.getEngineeringPoints() + 1);
            } else if (actionEvent.getSource() == merchantPlus) {
                person.setMerchantPoints(person.getMerchantPoints() + 1);
            } else if (actionEvent.getSource() == pilotPlus) {
                person.setPilotPoints(person.getPilotPoints() + 1);
            } else if (actionEvent.getSource() == fighterPlus) {
                person.setFighterPoints(person.getFighterPoints() + 1);
            }
            person.setSkillPoints(person.getSkillPoints() - 1);
        }
        updateTextFields();
    }
    public void incrementDown(ActionEvent actionEvent) {
        if (actionEvent.getSource() == engineerMinus) {
            person.setEngineeringPoints(person.getEngineeringPoints() - 1);
        } else if (actionEvent.getSource() == merchantMinus) {
            person.setMerchantPoints(person.getMerchantPoints() - 1);
        } else if (actionEvent.getSource() == pilotMinus) {
            person.setPilotPoints(person.getPilotPoints() - 1);
        } else if (actionEvent.getSource() == fighterMinus) {
            person.setFighterPoints(person.getFighterPoints() - 1);
        }
        person.setSkillPoints(person.getSkillPoints() + 1);
        updateTextFields();
    }
    public void updateTextFields() {
        engineeringField.setText(String.valueOf(person.getEngineeringPoints()));
        pilotField.setText(String.valueOf(person.getPilotPoints()));
        fighterField.setText(String.valueOf(person.getFighterPoints()));
        merchantField.setText(String.valueOf(person.getMerchantPoints()));
        remainMessage.setText("Skill points remaining: " + person.getSkillPoints());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
