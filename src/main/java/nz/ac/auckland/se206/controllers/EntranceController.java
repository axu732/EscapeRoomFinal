package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * Controller class for the entrance view. Contains the buttons to go to the kitchen, lounge, chat
 * and other FXML elements.
 */
public class EntranceController extends HelperMethods {
  @FXML private Rectangle kitchenDoor;
  @FXML private Rectangle loungeDoor;
  @FXML private Rectangle exitDoor;
  @FXML private Label labelTimer;
  @FXML private ImageView moriarty;
  @FXML private Label hintLabel;
  @FXML private ImageView comeToLoungeBubble;
  @FXML private Label comeToLoungeLabel;
  @FXML private TextArea hintTextArea;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    hintLabel.textProperty().bind(HelperMethods.hintDisplay);
    hintTextArea.textProperty().bind(HelperMethods.chatDisplay);
  }

  /**
   * Handles what happens when the player clicks the kitchen door. Navigates the user to the kitchen
   * room.
   */
  @FXML
  private void goToKitchen() {
    GameState.hasKitchenBeenVisited = true;
    hideSpeechBubble();
    GameState.currentRoom = "Kitchen";
    App.setUi(AppUi.KITCHEN);
  }

  /**
   * Handles what happens when the player clicks the lounge door. If the user hasn't created tea,
   * they will be navigated to the normal lounge scene, otherwise, they will be navigated to the
   * lounge room with moriarty sitting drinking tea.
   */
  @FXML
  private void goToLounge() {
    GameState.hasLoungeBeenVisited = true;
    hideSpeechBubble();
    // Check if the tea has been created, if not, navigate to the normal lounge scene
    if (GameState.isTeaCreated == false) {
      GameState.currentRoom = "Lounge";
      App.setUi(AppUi.LOUNGE);
      return;
    }

    // Otherwise we will go to the riddle room with moriarty
    GameState.currentRoom = "RiddleRoom";
    App.setUi(AppUi.RIDDLEROOM);
  }

  /**
   * Handles what happens when the player clicks the chat button. If the user has created tea, they
   * will be navigated to the lounge withmoriarty sitting drinking tea. Otherwise, they will be
   * navigated to the chat scene.
   */
  @FXML
  private void goToChat() {
    if (GameState.isTeaCreated == true) {
      comeToLoungeBubble.setVisible(true);
      comeToLoungeLabel.setVisible(true);
      return;
    }
    App.setUi(AppUi.CHAT);
  }

  /**
   * Handles what happens when the player clicks the exit door. Navigates the user to the code scene
   * where the user has to input a code to exit.
   */
  @FXML
  private void goToExit() {
    hideSpeechBubble();
    GameState.currentRoom = "Code";
    App.setUi(AppUi.CODE);
  }

  private void hideSpeechBubble() {
    comeToLoungeBubble.setVisible(false);
    comeToLoungeLabel.setVisible(false);
  }
}
