package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller for the start scene. Loads the buttons and labels from the FXML. */
public class StartController extends HelperMethods {

  @FXML private Button buttonStart;
  @FXML private ImageView leftArrowDifficulty;
  @FXML private ImageView rightArrowDifficulty;
  @FXML private ImageView leftArrowTime;
  @FXML private ImageView rightArrowTime;
  @FXML private Label labelDifficulty;
  @FXML private Label labelTime;
  @FXML private Button buttonMute;

  private HashMap<Integer, String> difficulties;
  private HashMap<Integer, String> times;
  private int timeIndex;
  private int difficultyIndex;
  private int difficultyMax;
  private int timeMax;

  /** Initializes the start view with a hashmap and all variables related to difficulty. */
  @FXML
  private void initialize() {
    difficulties = new HashMap<>();
    times = new HashMap<>();

    // Puts all possible label texts for the difficulty in a hashmapo. The key is the index of the
    // label text in the hashmap. The value is the label text, which is the current difficulty
    // selected.

    difficulties.put(0, "Difficulty");
    difficulties.put(1, "Easy");
    difficulties.put(2, "Medium");
    difficulties.put(3, "Hard");

    // Puts all possible label texts for the times in a hashmap. The key is the index of the label
    // text in the hashmap. The value is the label text, which is the current difficulty selected.

    times.put(0, "Time");
    times.put(1, "2 Minutes");
    times.put(2, "4 Minutes");
    times.put(3, "6 Minutes");

    // Sets the initial label texts for the difficulty and time. The initial label text is the first
    // label text in the hashmap.

    timeIndex = 0;
    difficultyIndex = 0;
    timeMax = 3;
    difficultyMax = 3;
    labelDifficulty.setText(difficulties.get(0));
    labelTime.setText(times.get(0));
  }

  /**
   * Handle the Start button click event. This method is called when the user clicks the start
   * button. It sets the game length and difficulty, and navigates the user to the watson scene.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onStart(ActionEvent event) throws ApiProxyException, IOException {
    // Set the game length and difficulty
    GameState.gameLength = getGameLength();
    GameState.difficulty = labelDifficulty.getText();
    System.out.println(GameState.gameLength);
    System.out.println(GameState.difficulty);

    // Set the number of hints remaining
    if (GameState.difficulty.equals("Easy")) {
      GameState.hintsRemaining = "Infinite";
    } else if (GameState.difficulty.equals("Medium")) {
      GameState.hintsRemaining = "6";
    } else if (GameState.difficulty.equals("Hard")) {
      GameState.hintsRemaining = "0";
    }
    // Update the hint texts
    updateHintText();

    // Navigate to the watson scene
    SceneManager.addAppUi(
        AppUi.WATSON, new FXMLLoader(App.class.getResource("/fxml/watson.fxml")).load());
    App.setUi(AppUi.WATSON);
  }

  /**
   * Handle the Instructions button click event. This method is called when the user clicks the
   * instructions button. It navigates the user to the instructions scene.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickInstructions(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.INSTRUCTIONS);
  }

  /**
   * calculates the length of the game in seconds from the current text of the time label.
   *
   * @return int the game length in seconds
   */
  private int getGameLength() {
    int length = Integer.parseInt(String.valueOf((labelTime.getText()).charAt(0)));
    return length * 60;
  }

  /**
   * Handle the left arrow click event for the difficulty. This method is called when the user
   * clicks the left arrow for the difficulty. It decrements the difficulty index and sets the
   * difficulty label text to the corresponding label text in the hashmap. It also checks if the
   * difficulty index is at the minimum value and disables the left arrow if it is.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onLeftDifficulty(MouseEvent event) throws ApiProxyException, IOException {
    System.out.println("decrement difficulty");

    // Decrease difficulty and set the label text to the corresponding label text in the hashmap
    difficultyIndex--;
    labelDifficulty.setText(difficulties.get(difficultyIndex));

    // Enable right arrow
    rightArrowDifficulty.setDisable(false);
    rightArrowDifficulty.setOpacity(1);

    // If at minimum value, disable left arrow
    if (difficultyIndex == 0) {
      leftArrowDifficulty.setOpacity(0.5);
      leftArrowDifficulty.setDisable(true);
    }
    // Check if both choices of start time and difficulty have been made
    checkToDisableStart();
    checkToEnableStart();
  }

  /**
   * Handle the right arrow click event for the difficulty. This method is called when the user
   * clicks the right arrow for the difficulty. It increments the difficulty index and sets the
   * difficulty label text to the corresponding label text in the hashmap. It also checks if the
   * difficulty index is at the maximum value and disables the right arrow and makes it more opaque
   * if it is.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onRightDifficulty(MouseEvent event) throws ApiProxyException, IOException {
    System.out.println("increment difficulty");
    difficultyIndex++;
    labelDifficulty.setText(difficulties.get(difficultyIndex));
    // Enable left arrow
    leftArrowDifficulty.setDisable(false);
    leftArrowDifficulty.setOpacity(1);
    // If at maximum value, disable right arrow
    if (difficultyIndex == difficultyMax) {
      rightArrowDifficulty.setOpacity(0.5);
      rightArrowDifficulty.setDisable(true);
    }
    checkToDisableStart();
    checkToEnableStart();
  }

  /**
   * Handle the left arrow click event for the time. This method is called when the user clicks the
   * left arrow for the time. It decrements the time index and sets the time label text to the
   * corresponding label text in the hashmap. It also checks if the time index is at the minimum
   * value and disables the left arrow if it is.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onLeftTime(MouseEvent event) throws ApiProxyException, IOException {
    System.out.println("decrement time");
    // Decrease time the user has to complete the game
    timeIndex--;
    labelTime.setText(times.get(timeIndex));

    // Enable right arrow
    rightArrowTime.setDisable(false);
    rightArrowTime.setOpacity(1);

    // Disable left arrow if at minimum value
    if (timeIndex == 0) {
      leftArrowTime.setOpacity(0.5);
      leftArrowTime.setDisable(true);
    }

    // Check if both choices of start time and difficulty have been made
    checkToDisableStart();
    checkToEnableStart();
  }

  /**
   * Handle the right arrow click event for the time. This method is called when the user clicks the
   * right arrow for the time. It increments the time index and sets the time label text to the
   * corresponding label text in the hashmap. It also checks if the time index is at the maximum
   * value and disables the right arrow if it is.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onRghtTime(MouseEvent event) throws ApiProxyException, IOException {
    System.out.println("increment time");

    // Increase time the user has to complete the game and set the label text to the corresponding
    // label text in the hashmap
    timeIndex++;
    labelTime.setText(times.get(timeIndex));

    // Enable left arrow
    leftArrowTime.setDisable(false);
    leftArrowTime.setOpacity(1);

    // Disable right arrow if at maximum value
    if (timeIndex == timeMax) {
      rightArrowTime.setOpacity(0.5);
      rightArrowTime.setDisable(true);
    }

    // Check if both choices of start time and difficulty have been made
    checkToDisableStart();
    checkToEnableStart();
  }

  /** Checks if both choices have been made. If they haven't, it disables the start button. */
  private void checkToEnableStart() {
    if ((timeIndex != 0) && (difficultyIndex != 0)) {
      buttonStart.setDisable(false);
    }
  }

  /** Checks if both choices have been made. If they have, it enables the start button. */
  private void checkToDisableStart() {
    if ((timeIndex == 0) || (difficultyIndex == 0)) {
      buttonStart.setDisable(true);
    }
  }

  // Mutes the text to speech from the main menu
  @FXML
  private void onMuteTextToSpeech() {
    if (TextToSpeech.getMute()) {
      buttonMute.setText("Mute Game");
    } else {
      buttonMute.setText("Unmute Game");
    }
    TextToSpeech.setMute();
  }
}
