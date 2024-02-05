package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the tea set view. Loads all the items from the FXML. */
public class TeaSetController extends HelperMethods {
  @FXML private Button backButton;
  @FXML private Label labelTimer;
  @FXML private Label teaLabel;
  @FXML private Label hotWaterLabel;
  @FXML private Label milkLabel;
  @FXML private Label sugarLabel;
  @FXML private Button brewButton;
  @FXML private Button resetButton;
  @FXML private ImageView teaLeft;
  @FXML private ImageView teaRight;
  @FXML private ImageView waterLeft;
  @FXML private ImageView waterRight;
  @FXML private ImageView milkLeft;
  @FXML private ImageView milkRight;
  @FXML private ImageView sugarLeft;
  @FXML private ImageView sugarRight;
  @FXML private Pane moriartyOverlay;
  @FXML private Label labelMessage;
  @FXML private Label continueLabel;
  @FXML private Button backToKitchenButton;
  @FXML private Rectangle incorrectRecipeRectangle;

  private int teaCount = 0;
  private int hotWaterCount = 0;
  private int milkCount = 0;
  private int sugarCount = 0;
  private int teaCountMax = 10;
  private int hotWaterCountMax = 10;
  private int milkCountMax = 10;
  private int sugarCountMax = 10;

  /**
   * Initializes the tea set view. This method is called when the view is loaded. It binds the timer
   * label to the time display.
   */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
  }

  /**
   * Handles what happens when the player clicks the go back button. Navigates the user to the
   * kitchen room.
   */
  @FXML
  private void onGoBack() {
    App.setUi(AppUi.KITCHEN);
  }

  /**
   * Handles what happens when the player clicks the right tea arrow image. Calls the changeTea
   * method with a parameter of 1.
   */
  @FXML
  private void addTea() {
    changeTea(1);
  }

  /**
   * Handles what happens when the player clicks the right water arrow image. Calls the changeWater
   * method with a parameter of 1.
   */
  @FXML
  private void addWater() {
    changeWater(1);
  }

  /**
   * Handles what happens when the player clicks the right milk arrow image. Calls the changeMilk
   * method with a parameter of 1.
   */
  @FXML
  private void addMilk() {
    changeMilk(1);
  }

  /**
   * Handles what happens when the player clicks the right sugar arrow image. Calls the changeSugar
   * method with a parameter of 1.
   */
  @FXML
  private void addSugar() {
    changeSugar(1);
  }

  /**
   * Handles what happens when the player clicks the left tea arrow image. Calls the changeMilk
   * method with a parameter of -1.
   */
  @FXML
  private void subtractTea() {
    changeTea(-1);
  }

  /**
   * Handles what happens when the player clicks the left water arrow image. Calls the changeWater
   * method with a parameter of -1.
   */
  @FXML
  private void subtractWater() {
    changeWater(-1);
  }

  /**
   * Handles what happens when the player clicks the left milk arrow image. Calls the changeMilk
   * method with a parameter of -1.
   */
  @FXML
  private void subtractMilk() {
    changeMilk(-1);
  }

  /**
   * Handles what happens when the player clicks the left sugar arrow image. Calls the changeSugar
   * method with a parameter of -1.
   */
  @FXML
  private void subtractSugar() {
    changeSugar(-1);
  }

  /**
   * Changes the amount of tea leaves in the tea set. The amount of tea leaves can be increased or
   * decreased by 1 depending on the parameter passed through. The amount of tea leaves cannot be
   * less than 0 or more than 10.
   *
   * @param value the amount of tea leaves need to change by
   */
  @FXML
  private void changeTea(int value) {
    teaCount += 1 * value;
    teaLabel.setText(String.valueOf(teaCount));

    // Enables both tea arrows
    teaLeft.setDisable(false);
    teaLeft.setOpacity(1);
    teaRight.setDisable(false);
    teaRight.setOpacity(1);

    // Disables the left arrow if the tea count is 0, and disables the right arrow if the tea count
    if (teaCount <= 0) {
      teaLeft.setDisable(true);
      teaLeft.setOpacity(0.5);
    } else if (teaCount >= teaCountMax) {
      teaRight.setDisable(true);
      teaRight.setOpacity(0.5);
    }
  }

  /**
   * Changes the amount of water in the tea set. The amount of water can be increased or decreased
   * by 1 depending on the parameter passed through. The amount of water cannot be less than 0 or
   * more than 10.
   *
   * @param value the amount of water need to change by
   */
  @FXML
  private void changeWater(int value) {
    // Change the amount of water given by what arrow was pressed
    hotWaterCount += 1 * value;
    hotWaterLabel.setText(String.valueOf(hotWaterCount));

    // Enables both water arrows
    waterLeft.setDisable(false);
    waterLeft.setOpacity(1);
    waterRight.setDisable(false);
    waterRight.setOpacity(1);

    // Disables the left arrow if the water count is 0, and disables the right arrow if the water
    // count is 10
    if (hotWaterCount <= 0) {
      waterLeft.setDisable(true);
      waterLeft.setOpacity(0.5);
    } else if (hotWaterCount >= hotWaterCountMax) {
      waterRight.setDisable(true);
      waterRight.setOpacity(0.5);
    }
  }

  /**
   * Changes the amount of milk in the tea set. The amount of milk can be increased or decreased by
   * 1 depending on the parameter passed through. The amount of milk cannot be less than 0 or more
   * than 10.
   *
   * @param value the amount of milk need to change by
   */
  @FXML
  private void changeMilk(int value) {
    // Change the amount of milk given by what arrow was pressed
    milkCount += 1 * value;
    milkLabel.setText(String.valueOf(milkCount));

    // Enables both milk arrows
    milkLeft.setDisable(false);
    milkLeft.setOpacity(1);
    milkRight.setDisable(false);
    milkRight.setOpacity(1);

    // Disables the left arrow if the milk count is 0, and disables the right arrow if the milk
    // count is 10
    if (milkCount <= 0) {
      milkLeft.setDisable(true);
      milkLeft.setOpacity(0.5);
    } else if (milkCount >= milkCountMax) {
      milkRight.setDisable(true);
      milkRight.setOpacity(0.5);
    }
  }

  /**
   * Changes the amount of sugar in the tea set. The amount of sugar can be increased or decreased
   * by 1 depending on the parameter passed through. The amount of sugar cannot be less than 0 or
   * more than 10.
   *
   * @param value the amount of sugar need to change by
   */
  @FXML
  private void changeSugar(int value) {
    // Change the amount of sugar given by what arrow was pressed
    sugarCount += 1 * value;
    sugarLabel.setText(String.valueOf(sugarCount));

    // Enables both sugar arrows
    sugarLeft.setDisable(false);
    sugarLeft.setOpacity(1);
    sugarRight.setDisable(false);
    sugarRight.setOpacity(1);

    // Disables the left arrow if the sugar count is 0, and disables the right arrow if the sugar
    // count is 10
    if (sugarCount <= 0) {
      sugarLeft.setDisable(true);
      sugarLeft.setOpacity(0.5);
    } else if (sugarCount >= sugarCountMax) {
      sugarRight.setDisable(true);
      sugarRight.setOpacity(0.5);
    }
  }

  /** Resets the tea set. The amount of tea leaves, water, milk and sugar are all set to 0. */
  @FXML
  private void onReset() {
    // Reset all counters to 0
    teaCount = 0;
    hotWaterCount = 0;
    milkCount = 0;
    sugarCount = 0;

    // Reset all labels for the ingredients to 0
    teaLabel.setText("0");
    hotWaterLabel.setText("0");
    milkLabel.setText("0");
    sugarLabel.setText("0");

    // Disable all left arrows
    teaLeft.setDisable(true);
    teaLeft.setOpacity(0.5);
    waterLeft.setDisable(true);
    waterLeft.setOpacity(0.5);
    milkLeft.setDisable(true);
    milkLeft.setOpacity(0.5);
    sugarLeft.setDisable(true);
    sugarLeft.setOpacity(0.5);

    // enable all right arrows
    teaRight.setDisable(false);
    teaRight.setOpacity(1);
    waterRight.setDisable(false);
    waterRight.setOpacity(1);
    milkRight.setDisable(false);
    milkRight.setOpacity(1);
    sugarRight.setDisable(false);
    sugarRight.setOpacity(1);
  }

  /**
   * Handles what happens when the player clicks the brew button. If the player has brewed the
   * correct recipe, they will be shown a message saying the tea is correct. Otherwise, they will be
   * shown an error message and the tea set will be reset.
   */
  @FXML
  private void onBrewTea() {
    // Check if the user has brewed the correct recipe
    if (teaCount == GameState.teaRandom
        && hotWaterCount == GameState.kettleRandom
        && milkCount == GameState.milkRandom
        && sugarCount == GameState.sugarRandom) {
      // If they have, show a message saying the tea is correct and set the tea created gamestate to
      // true
      GameState.isTeaCreated = true;
      updateChat();
      labelMessage.setText("Perfect brew. Admirable skills. Tea, just the way I prefer.");
      moriartyOverlay.setVisible(true);
      continueLabel.setVisible(false);
      incorrectRecipeRectangle.setVisible(false);
      backToKitchenButton.setVisible(true);
    } else {
      // Otherwise, show that tea was wrong and reset the tea set
      labelMessage.setText("Your tea, alas, is brewed incorrectly. Try again.");
      moriartyOverlay.setVisible(true);
      continueLabel.setVisible(true);
      backToKitchenButton.setVisible(false);
      incorrectRecipeRectangle.setVisible(true);
      onReset();
    }
  }

  /**
   * Handles what happens when the player clicks anywhere on the screen with the moriarty overlay.
   * It navigates them to where they w
   */
  @FXML
  private void backToNormalRoom() {
    moriartyOverlay.setVisible(false);
  }
}
