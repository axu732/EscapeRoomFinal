package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * Controller class for the code view. Loads the buttons, text field, pane and labels from the FXML.
 */
public class CodeController extends HelperMethods {
  @FXML private Button buttonGoBack;
  @FXML private TextField fieldAnswer;
  @FXML private Button buttonBack;
  @FXML private Button buttonSubmit;
  @FXML private Button button1;
  @FXML private Button button2;
  @FXML private Button button3;
  @FXML private Button button4;
  @FXML private Button button5;
  @FXML private Button button6;
  @FXML private Button button7;
  @FXML private Button button8;
  @FXML private Button button9;
  @FXML private Button button0;
  @FXML private Button diaryButton;
  @FXML private Label labelTimer;
  @FXML private Label labelThought;
  @FXML private Pane selfOverlay;

  private ArrayList<String> answerInputs = new ArrayList<String>();

  /**
   * Initializes the room view, it is called when the room loads. Binds the time display to the time
   * label.
   */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickBack(ActionEvent event) throws ApiProxyException, IOException {
    GameState.currentRoom = "Entrance";
    App.setUi(AppUi.ENTRANCE);
  }

  /**
   * Navigates to the diary view if the riddle is resolved. Otherwise, the user is notified that the
   * diary is locked.
   */
  @FXML
  private void onGoToDiary() {
    if (GameState.isRiddleResolved == false) {
      selfOverlay.setVisible(true);
      labelThought.setText("The diary is locked!\nI need to find the key to it!");
    } else {
      App.setUi(AppUi.DIARY);
    }
  }

  /**
   * Clears the last input. If there is no input, nothing happens. Otherwise, the last input is
   * removed from the answer input. To remove the last input, the textfield is cleared then all the
   * inputs are appended again to the textfield.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickBackSpace(ActionEvent event) throws ApiProxyException, IOException {
    if (!answerInputs.isEmpty()) {
      answerInputs.remove(answerInputs.size() - 1);
      fieldAnswer.clear();
      for (String input : answerInputs) {
        fieldAnswer.appendText(input);
      }
    }
  }

  /**
   * Adds the number to the answer input. If the limit of 8 digits is reached, the user is notified
   * that the limit is reached. Otherwise, the number is added to the answer input. To add the
   * number, the textfield is cleared then all the numbers are appended again to the textfield.
   *
   * @param num the number to add
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  private void onClickNumber(String num) {
    // Add user input to the arraylist answerinputs
    answerInputs.add(num);

    // Clear the current text and then re add each number
    fieldAnswer.clear();
    for (String input : answerInputs) {
      fieldAnswer.appendText(input);
    }
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick1(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("1");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick2(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("2");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick3(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("3");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick4(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("4");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick5(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("5");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick6(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("6");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick7(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("7");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick8(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("8");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick9(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("9");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClick0(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("0");
  }

  /**
   * Checks if the answer is correct. If it is, the user is navigated to the succeeded screen.
   * Otherwise, the user is notified that the answer is incorrect.
   *
   * @param event the mouse event
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickSubmit(ActionEvent event) throws ApiProxyException, IOException {
    // Initialise the answer through Gamestate
    String answer = GameState.diaryDate + "" + GameState.diaryMonth + "" + GameState.diaryYear;
    // Check if the answer is correct
    if (fieldAnswer.getText().equals(answer)) {
      showSucceededScene();
    } else {
      selfOverlay.setVisible(true);
      labelThought.setText("The door is still locked!\nI need to find the code fast!");
    }
    System.out.println("Submit button clicked");
  }

  /**
   * Navigates back to a succeeded screen.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  private void showSucceededScene() {
    GameState.currentRoom = "Success";
    App.setUi(AppUi.SUCCEEDED);
    GameState.isRiddleResolved = false;
    GameState.isTeaCreated = false;
    GameState.isGameStarted = false;
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
    selfOverlay.setVisible(false);
  }
}
