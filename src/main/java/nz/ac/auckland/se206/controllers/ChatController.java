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
public class ChatController extends BaseChatController {
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

    createThreadInitial(
        new ChatMessage("user", GptPromptEngineering.getChatInvitation()), chatCompletionRequest);

    // phrases that moriarty will say when he is thinking of what to say
    // stored in a hashmap so that a random phrase can be selected
    thinkingPhrases = new HashMap<>();
    thinkingPhrases.put(
        0, "Calculating the perfect phrase, my mind dances amidst schemes and manipulations...");
    thinkingPhrases.put(
        1, "I am deliberating on the precise words to engineer a brilliant scheme...");
    thinkingPhrases.put(
        2,
        "Carefully calculated words dance upon the edge of my tongue, as I ponder the precise"
            + " manipulation of emotions...");
    thinkingPhrases.put(3, "Silent scheming fills my mind as I craft the perfect verbal trap...");
    thinkingPhrases.put(
        4,
        "Calculating tactics pulse through my thoughts, as I consider the most strategic words to"
            + " ensnare my adversary...");
    thinkingPhrases.put(
        5,
        "Meticulously plotting my words, I prepare to unleash a verbal masterpiece upon the"
            + " unsuspecting world...");

    random = new Random(thinkingPhrases.size() - 1);
  }

  /**
   * Handles what happens if the enter key is pressed, feeding it to send message function.
   *
   * @param event the key event
   */
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
  protected void onClickMoriartyThinking() {
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
    // No hints left
    if (GameState.hintsRemaining.equals("0")) {
      updateHintText();
      return;
    }

    // Send the hint request
    createThread(
        new ChatMessage("user", GptPromptEngineering.getHintGeneral()), chatCompletionRequestHint);
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
    onSendMessageNormal(event);
  }
}
