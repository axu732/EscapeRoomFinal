package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller for the diary scene. Loads the buttons and labels from the FXML */
public class DiaryController extends HelperMethods {
  @FXML private Button buttonGoBack;
  @FXML private Label labelTimer;
  @FXML private Label labelDate;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    appendText();
  }

  /** Adds the randomised date to the diary. */
  private void appendText() {
    // setting the text for the label on the left page of the diary
    labelDate.setText(
        // the diary date is randomly generated and stored in GameState
        GameState.diaryDate + "/" + GameState.diaryMonth + "/" + GameState.diaryYear);
  }

  /**
   * Handles the click event on the go back button.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the room view
   */
  @FXML
  private void onClickBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.valueOf(GameState.currentRoom.toUpperCase()));
  }
}
