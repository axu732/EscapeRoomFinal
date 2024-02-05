package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the succeeded view. Loads the buttons from FXML. */
public class SucceededController extends HelperMethods {
  @FXML private Button buttonClose;
  @FXML private Button mainMenuButton;

  /**
   * Closes the application when the user clicks the close button.
   *
   * @param event mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClose(ActionEvent event) throws ApiProxyException, IOException {
    closeGame();
  }

  /**
   * Restarts the game and sends the user to the main menu scene when the user clicks the retry
   * button.
   *
   * @param event mouse event
   */
  @FXML
  private void onGoToMainMenu(ActionEvent event) {
    restartGame();
  }
}
