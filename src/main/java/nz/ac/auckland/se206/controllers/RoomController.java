package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the room view. */
public class RoomController extends HelperMethods {

  @FXML private Rectangle rectangleDoor;
  @FXML private Rectangle rectangleViolin;
  @FXML private Rectangle rectangleDiary;
  @FXML private Label labelTimer;
  @FXML private Rectangle rectangleBackground;
  @FXML private Label labelViolinText;
  @FXML private Label labelMessage;
  @FXML private Button buttonGoBack;
  @FXML private Label labelTimerBlack;
  @FXML private ImageView moriarty;
  @FXML private Label hintLabel;
  @FXML private Pane moriartyOverlay;
  @FXML private TextArea hintTextArea;
  @FXML private Label labelThought;
  @FXML private Pane selfOverlay;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    labelTimerBlack.textProperty().bind(HelperMethods.timeDisplay);
    hintLabel.textProperty().bind(HelperMethods.hintDisplay);
    hintTextArea.textProperty().bind(HelperMethods.chatDisplay);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Handles what happens when the player clicks the door. Navigates the user to the entrance room.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  private void onClickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");
    GameState.currentRoom = "Entrance";
    App.setUi(AppUi.ENTRANCE);
  }

  /**
   * /** Handles what happens when the player clicks on the diary. If the user has not answered the
   * riddle, they will be shown a message telling them that the diary is locked. Otherwise, they
   * will be navigated to the diary scene.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickDiary(MouseEvent event) throws IOException {
    System.out.println("diary clicked");
    GameState.hasDiaryBeenClicked = true;
    // If the user has not answered the riddle, they will be shown a message telling them that the
    // diary is locked and that moriarty has the key
    selfOverlay.setVisible(true);
    labelThought.setText(
        "It seems Moriarty has the key...\nPerhaps I'll brew some tea as a means to obtain the"
            + " key.");
  }

  /**
   * What happens when a user clicks on an item (is called by other functions). Makes an overlay
   * with moriarty speaking appear, and sets the text to the message parameter.
   *
   * @param message the message to be displayed
   */
  private void showMessage(String message) {
    moriarty.setVisible(false);
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
        "Ah, you've encountered a violin - a wondrous tool offering hidden possibilities. Allow its"
            + " melodies to shape your destiny.");
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
        "A pipe, you say? An ally in contemplation, channeling thoughts and unraveling secrets. Let"
            + " its wisps guide you.");
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
        "Ah, a magnifying glass! A tool of observation, unlocking hidden details. Its lens holds"
            + " the power to reveal truths. Embrace it.");
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
        "Ah, a painting! Within its strokes and colors lies a web of secrets waiting to unravel"
            + " mysteries. Embrace its potential.");
  }

  /**
   * Handles the going back to the normal room event. Makes the overlay invisible and moriarty
   * visible.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the room view
   */
  @FXML
  private void backToNormalRoom(MouseEvent event) throws IOException {
    moriartyOverlay.setVisible(false);
    selfOverlay.setVisible(false);
    moriarty.setVisible(true);
  }

  /**
   * Handles what happens when the player clicks the chat button. If the user has created tea, they
   * will be navigated to the lounge withmoriarty sitting drinking tea. Otherwise, they will be
   * navigated to the chat scene.
   */
  @FXML
  private void goToChat() {
    App.setUi(AppUi.CHAT);
  }
}
