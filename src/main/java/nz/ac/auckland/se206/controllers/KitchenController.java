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
 * Controller class for the kitchen view. Contains the buttons to go to the recipe, chat and tea.
 */
public class KitchenController extends HelperMethods {
  @FXML private Rectangle door;
  @FXML private Rectangle teaMaking;
  @FXML private Rectangle recipe;
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
   * Handles what happens when the player clicks the door. Navigates the user to the entrance room.
   * Also makes the message that tells the user to go to the lounge disappear.
   */
  @FXML
  private void goToEntrance() {
    GameState.currentRoom = "Entrance";
    hideSpeechBubble();
    App.setUi(AppUi.ENTRANCE);
  }

  /**
   * Handles what happens when the player clicks on the recipe image. Navigates the user to the
   * recipe scene.
   */
  @FXML
  private void goToRecipe() {
    hideSpeechBubble();
    App.setUi(AppUi.RECIPE);
  }

  /**
   * Handles what happens when the player clicks the chat button. If the user has created tea, a
   * message will appear telling the user to the lounge with moriarty sitting drinking tea.
   * Otherwise, they will be navigated to the chat scene.
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
   * Handles what happens when the player clicks the tea making image. Navigates the user to the tea
   * making scene.
   */
  @FXML
  private void goToTeaSet() {
    hideSpeechBubble();
    App.setUi(AppUi.TEASET);
  }

  private void hideSpeechBubble() {
    comeToLoungeBubble.setVisible(false);
    comeToLoungeLabel.setVisible(false);
  }
}
