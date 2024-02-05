package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * Controller class for the recipe view. Contains the button to go to the kitchen room and the
 * labels for the recipe.
 */
public class RecipeController extends HelperMethods {
  @FXML private Label kettleLabel;
  @FXML private Label teaLabel;
  @FXML private Label sugarLabel;
  @FXML private Label milkLabel;
  @FXML private Label timerLabel;
  @FXML private Button returnButton;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    timerLabel.textProperty().bind(HelperMethods.timeDisplay);
    randomiseRecipe();
  }

  /**
   * Handles what happens when the player clicks the kitchen door. Navigates the user to the kitchen
   * room.
   */
  @FXML
  private void onGoToKitchen() {
    App.setUi(AppUi.KITCHEN);
  }

  /** randomises the quantities of the ingredients in the recipe. */
  @FXML
  private void randomiseRecipe() {
    kettleLabel.setText(String.valueOf(GameState.kettleRandom));
    teaLabel.setText(String.valueOf(GameState.teaRandom));
    sugarLabel.setText(String.valueOf(GameState.sugarRandom));
    milkLabel.setText(String.valueOf(GameState.milkRandom));
  }
}
