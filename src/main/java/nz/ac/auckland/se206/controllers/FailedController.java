package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.HelperMethods;

/** Controller class for the failed view. Loads the buttons from FXML. */
public class FailedController extends HelperMethods {
  @FXML private Button buttonClose;
  @FXML private Button retryButton;

  /** Closes the application when the user clicks the close button. */
  @FXML
  private void onClose() {
    closeGame();
  }

  /**
   * Restarts the game and sends the user to the main menu scene when the user clicks the retry
   * button.
   */
  @FXML
  private void onGoToMainMenu(ActionEvent event) {
    restartGame();
  }
}
