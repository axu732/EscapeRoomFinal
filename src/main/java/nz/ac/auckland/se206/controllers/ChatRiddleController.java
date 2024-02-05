package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the chat view. */
public class ChatRiddleController extends BaseChatController {
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button buttonSend;
  @FXML private Label labelLoading;
  @FXML private Label labelTimer;
  @FXML private Button buttonGoBack;
  @FXML private Button buttonHint;
  @FXML private Label labelHint;
  @FXML private ImageView moriartyThinking;
  @FXML private ImageView thinkingBubble;
  @FXML private ImageView moriartyNormal;
  @FXML private ImageView normalBubble;
  @FXML private Label riddleLabel;

  private HashMap<Integer, String> thinkingPhrases;
  private Random random;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    initializeCommon();
    createThread(
        new ChatMessage("user", GptPromptEngineering.getRiddleWithGivenWord(GameState.riddleWord)),
        chatCompletionRequest);

    // phrases that moriarty will say when he is thinking of what to say
    // stored in a hashmap so that a random phrase can be selected
    thinkingPhrases = new HashMap<>();
    thinkingPhrases.put(0, "My dear adversary, let me tak you deeper down the" + " rabbit hole...");
    thinkingPhrases.put(
        1,
        "The game is afoot, and soon I shall drop a breadcrumb of enigmatic wisdom that will"
            + " confound even the great Sherlock Holmes...");
    thinkingPhrases.put(2, "I am concocting an enigmatic prose that will unravel the mystery...");
    thinkingPhrases.put(
        3, "Within the depths of my devious mind, a cunning remark shall sprout forth....");
    thinkingPhrases.put(
        4, "I'm contemplating the perfect reponse to leave that will confound Sherlock Holmes...");
    thinkingPhrases.put(
        5,
        "I'm pondering the ideal breadcrumb to lead Holmes further into my intricate web of"
            + " deception...");

    random = new Random(thinkingPhrases.size() - 1);
  }

  // Function that will register if enter was pressed in the text field
  @FXML
  private void onEnterPressed(KeyEvent event) {
    // Checks to see if the key was enter
    if (event.getCode() == KeyCode.ENTER) {
      try {
        onSendMessage(new ActionEvent());
      } catch (ApiProxyException | IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * what happens when the player clicks on moriarty while he is thinking. A random phrase from the
   * hashmap is selected and displayed in the loading label.
   */
  @FXML
  private void onClickMoriartyThinking() {
    labelLoading.setText(thinkingPhrases.get(random.nextInt((thinkingPhrases.size() - 1))));
  }

  /**
   * what happens when the player clicks on the hint button. Both cases call the updateHintText
   * function. in the case where there are hints, the functions calls a function from
   * GptPromptEngineering to get a hint.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  private void onSendHint() throws ApiProxyException {
    // if there are no hints remaining, update the hint text
    if (GameState.hintsRemaining.equals("0")) {
      updateHintText();
      return;
    }
    // Otherwise get a hint from GptPromptEngineering and update the hint text
    createThread(
        new ChatMessage("user", GptPromptEngineering.getHintRiddle()), chatCompletionRequestHint);

    updateHintText();
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    labelLoading.setText(thinkingPhrases.get(random.nextInt((thinkingPhrases.size() - 1))));
    onSendMessageRiddle(event);
  }
}
