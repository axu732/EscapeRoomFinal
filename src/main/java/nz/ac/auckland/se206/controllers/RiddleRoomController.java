package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the riddle room view. Loads all the items from the FXML */
public class RiddleRoomController extends HelperMethods {
  @FXML private Label labelTimer;

  @FXML private Rectangle rectangleViolin;
  @FXML private Rectangle diary;
  @FXML private Rectangle rectangleMagnifyingGlass;
  @FXML private Rectangle rectanglePainting;
  @FXML private Rectangle rectanglePipe;

  @FXML private ImageView speechBubble;
  @FXML private Label speechBubbleLabel;
  @FXML private Label hintLabel;

  @FXML private Rectangle door;

  @FXML private Rectangle moriarty;
  @FXML private Pane moriartyOverlay;
  @FXML private Label labelMessage;

  @FXML private TextArea hintTextArea;
  @FXML private Label labelThought;
  @FXML private Pane selfOverlay;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    hintLabel.textProperty().bind(HelperMethods.hintDisplay);
    hintTextArea.textProperty().bind(HelperMethods.chatDisplay);

    speechBubble.setVisible(true);
    speechBubbleLabel.setVisible(true);
  }

  /**
   * Handles what happens when the player clicks on the diary. If the user has not answered the
   * riddle, they will be shown a message telling them that the diary is locked. Otherwise, they
   * will be navigated to the diary scene.
   */
  @FXML
  private void onClickDiary() {
    // If the user has not answered the riddle, show a message telling them that the diary is locked
    GameState.hasDiaryBeenClicked = true;
    if (GameState.isRiddleResolved == false) {
      selfOverlay.setVisible(true);
      labelThought.setText(
          "Moriarty still has the key!\nIt seems I must talk to Moriarty to get it.");
    } else {
      // Otherwise, navigate to the diary scene
      App.setUi(AppUi.DIARY);
    }
  }

  /**
   * Handles what happens when the player clicks the door. Navigates the user to the entrance room.
   */
  @FXML
  private void onClickDoor() {
    GameState.currentRoom = "Entrance";
    App.setUi(AppUi.ENTRANCE);
  }

  /**
   * Handles what happens when the player clicks on moariarty. If the user has not answered the
   * riddle, the speech bubble image and speech bubble text will be made invisible. Regardless of if
   * they have solved the riddle, the user will be navigated to the chat scene.
   */
  @FXML
  private void enterRiddle() {
    // If the user has not answered the riddle, make the speech bubble invisible and navigate to the
    // chat riddle scene
    if (GameState.isRiddleResolved == false) {
      GameState.hasRiddleBeenGiven = true;
      speechBubble.setVisible(false);
      speechBubbleLabel.setVisible(false);
      App.setUi(AppUi.CHATRIDDLE);
    } else {
      // Otherwise, navigate to the normal chat scene
      App.setUi(AppUi.CHAT);
    }
  }

  /**
   * What happens when a user clicks on an item (is called by other functions). Makes an overlay
   * with moriarty speaking appear, and sets the text to the message parameter.
   *
   * @param message the message to be displayed
   */
  private void showMessage(String message) {
    moriartyOverlay.setVisible(true);
    labelMessage.setText(message);
  }

  /**
   * Handles the click event on the violin. Calls the showMessage method with a message telling the
   * user what the violin could potentially be used for.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickViolin(MouseEvent event) throws IOException {
    System.out.println("violin clicked");
    GameState.hasViolinBeenClicked = true;
    showMessage(
        "Ah, a violin! Its melodies may hold the key, harmonizing with riddles to unlock the"
            + " secrets of the perplexing. Explore its potential.");
  }

  /**
   * Handles the click event on the pipe. Calls the showMessage method with a message telling the
   * user what the pipe could potentially be used for.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickPipe(MouseEvent event) throws IOException {
    System.out.println("pipe clicked");
    showMessage(
        "Ah, a pipe! Ignite the embers of your mind, let its wisps guide you through riddles,"
            + " unraveling mysteries one puff at a time.");
  }

  /**
   * Handles the click event on the magnifying glass. Calls the showMessage method with a message
   * telling the user what the magnifying glass could potentially be used for.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickMagnifyingGlass(MouseEvent event) throws IOException {
    System.out.println("magnifying glass clicked");
    showMessage(
        "Ah, a magnifying glass! Peer through its lens, unlock the hidden, for within its focus"
            + " lies the answer to riddles.");
  }

  /**
   * Handles the click event on the painting. Calls the showMessage method with a message telling
   * the user what the painting could potentially be used for.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickPainting(MouseEvent event) throws IOException {
    System.out.println("painting clicked");
    showMessage(
        "Ah, a painting. Delve deep into its enigmatic brushstrokes, for it holds hints to solve"
            + " the riddles of intrigue.");
  }

  /**
   * Handles the going back to the normal room event. Makes the overlay invisible and moriarty
   * visible.
   *
   * @param event the mouse event
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void backToNormalRoom(MouseEvent event) throws IOException {
    moriartyOverlay.setVisible(false);
    selfOverlay.setVisible(false);
    moriarty.setVisible(true);
  }
}
