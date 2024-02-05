package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the instructions view. Contains the button to go to the start menu */
public class InstructionsController {

  /**
   * Navigates back to the main menu scene (strat screen) when the user clicks the go back button.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickGoBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.START);
  }
}
