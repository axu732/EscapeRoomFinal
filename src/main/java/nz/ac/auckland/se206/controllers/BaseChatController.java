package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/**
 * Abstract class that is inherited by all the chat controllers. Contains all the methods that are
 * shared between them
 */
public abstract class BaseChatController extends HelperMethods {
  @FXML protected TextArea chatTextArea;
  @FXML protected TextField inputText;
  @FXML protected Button buttonSend;
  @FXML protected Label labelLoading;
  @FXML protected Label labelTimer;
  @FXML protected Button buttonGoBack;
  @FXML protected Button buttonHint;
  @FXML protected Label labelHint;
  @FXML protected ImageView moriartyThinking;
  @FXML protected ImageView thinkingBubble;
  @FXML protected ImageView moriartyNormal;
  @FXML protected ImageView normalBubble;
  @FXML protected Pane moriartyOverlay;

  protected ChatCompletionRequest chatCompletionRequest;
  protected ChatCompletionRequest chatCompletionRequestHint;
  protected ChatCompletionRequest chatCompletionRequestRefuseHint;
  protected Random random;

  /**
   * Initializes the chat view, loading the GPT chat requests and binds the helper method properties
   * to labels.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initializeCommon() throws ApiProxyException {
    // Initialise the chat completion requests
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    chatCompletionRequestHint =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    chatCompletionRequestRefuseHint =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    // Binds the labels to the timer and hint display
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    labelHint.textProperty().bind(HelperMethods.hintDisplay);
  }

  // Creates a thread for running GPT ai
  protected void createThread(ChatMessage msg, ChatCompletionRequest chatrequest) {
    Task<Void> runAi =
        new Task<Void>() {
          @Override
          // Calls GPT to be run inside a task
          protected Void call() throws Exception {
            chatTextArea.clear();
            runGpt(msg, chatrequest);
            return null;
          }
        };
    // Run the thread
    Thread runAiThread = new Thread(runAi, "ChatController Thread");
    runAiThread.start();
  }

  // Creates a thread for running GPT ai at the start
  protected void createThreadInitial(ChatMessage msg, ChatCompletionRequest chatrequest) {
    Task<Void> runAi =
        new Task<Void>() {
          @Override
          // Calls GPT to be run inside a task
          protected Void call() throws Exception {
            chatTextArea.clear();
            runGpt(msg, chatrequest);
            GameState.chatMessage = chatrequest.getMessages().get(1).getContent();
            updateChat();
            return null;
          }
        };
    // Run the thread
    Thread runAiThread = new Thread(runAi, "ChatController InititalThread");
    runAiThread.start();
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  protected ChatMessage runGpt(ChatMessage msg, ChatCompletionRequest chatrequest)
      throws ApiProxyException {
    chatrequest.addMessage(msg);

    // Remove the first message in order to limit token use and chatgpt remembers its prompt.
    while (chatrequest.getMessages().size() > 5) {
      chatrequest.getMessages().remove(2);
    }
    /* in the following code, the chatCompletionRequest is executed and the result is stored in a
    ChatCompletionResult object. The first choice in the result is then stored in a Choice
    object.
    The chatCompletionRequest is then updated with the chat message from the choice object. The
    chat message from the choice object is then appended to the chat text area. The loading label
    is
    then hidden and the input text field and send button are enabled. The chat message from the
    choice object is then returned. */
    try {
      isLoading();
      ChatCompletionResult chatCompletionResult = chatrequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatrequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
      isNotLoading();
      System.out.println(chatCompletionResult.getUsageTotalTokens());
      if (GameState.isGameStarted) {
        GameState.chatMessage = result.getChatMessage().getContent();
        updateChat();
      }
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  protected void appendChatMessage(ChatMessage msg) {
    if (!(msg.getRole().equals("user"))) {
      chatTextArea.appendText("Moriarty: " + msg.getContent() + "\n\n");
    } else {
      chatTextArea.appendText("Sherlock" + ": " + msg.getContent() + "\n\n");
    }
  }

  /**
   * Enables the input text field and send hint buttons.
   *
   * @returns void
   */
  protected void enableInput() {
    inputText.setDisable(false);
    buttonSend.setDisable(false);
    buttonHint.setDisable(false);
  }

  /**
   * Disables the input text field and send and hint buttons.
   *
   * @returns void
   */
  protected void disableInput() {
    inputText.setDisable(true);
    buttonSend.setDisable(true);
    buttonHint.setDisable(true);
  }

  /**
   * what happens when chatGPT is loading a response. The chat text area is made slightly opaque,
   * the normal moriarty is made invisible, while the image or moriarty thinking is made visible,
   * the loading label is made visible, and the thinking bubble is made visible. The input text
   * field and send button are also disabled (by being called from another function).
   *
   * @returns void
   */
  protected void isLoading() {
    // Make the chat text area slightly opaque
    chatTextArea.setOpacity(0.3);
    // Make the normal moriarty invisible
    moriartyNormal.setVisible(false);
    // Make the image of moriarty thinking visible
    moriartyThinking.setVisible(true);
    // Make the loading label visible and the thinking bubble visible
    labelLoading.setVisible(true);
    thinkingBubble.setVisible(true);
    // Disable the input text field and send button
    disableInput();
  }

  /**
   * what happens when chatGPT is not loading a response. The chat text area is made less opaque,
   * the normal moriarty is made visible, while the image or moriarty thinking is made invisible,
   * the loading label is made invisible, and the thinking bubble is made invisible. The input text
   * field and send button are also enabled (by being called from another function).
   *
   * @returns void
   */
  protected void isNotLoading() {
    // Make the chat text area less opaque
    chatTextArea.setOpacity(0.8);
    // Make the normal moriarty visible
    moriartyNormal.setVisible(true);
    // Make the image of moriarty thinking invisible
    moriartyThinking.setVisible(false);
    // Make the loading label invisible and the thinking bubble invisible
    labelLoading.setVisible(false);
    thinkingBubble.setVisible(false);
    // Renable user input
    enableInput();
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.valueOf(GameState.currentRoom.toUpperCase()));
  }

  // The following methods are called when the send button is pressed, this will run for the chat
  // riddle room
  @FXML
  protected void onSendMessageRiddle(ActionEvent event) throws ApiProxyException, IOException {
    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    Task<Void> runAi =
        new Task<Void>() {
          /**
           * Sends the player's input to the GPT model and waits for a response. While waiting for a
           * response, it will call another method that shows a loading label. Once it is finished
           * getting a response, it will call another method to hide the loading label. If the
           * response starts with "Correct", then the player has solved the riddle. The input text
           * field and send button are disabled and a dialog is shown congratulating the player. The
           * player is then navigated to the diary view. The game's state also changes so that the
           * riddle is solved. Otherwise, the player's input is appended to the chat text area and
           * the input text field is cleared.
           *
           * @returns Void
           */
          @Override
          protected Void call() throws Exception {
            // Show the loading  while chatGPT is loading a response
            isLoading();
            inputText.clear();
            chatTextArea.clear();
            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);
            isNotLoading();

            if (message.toLowerCase().contains("hint")
                || message.toLowerCase().contains("help")
                || message.toLowerCase().contains("clue")) {

              runGpt(
                  new ChatMessage(
                      "user",
                      "You are Moriarty from Sherlock, say that Holmes should take a look around"
                          + " the lounge for important objects. Two short sentences max."),
                  chatCompletionRequestRefuseHint);
              return null;
            }
            // Call GPT on the latest message
            ChatMessage lastMsg = runGpt(msg, chatCompletionRequest);

            // This if statement will check if the Game Master has seen the correct answer from the
            // user. If so it will tell the player to look at the diary

            if (lastMsg.getRole().equals("assistant")
                && lastMsg.getContent().startsWith("Correct")) {
              inputText.setDisable(true);
              buttonSend.setDisable(true);
              GameState.isRiddleResolved = true;
              Platform.runLater(
                  () -> {
                    moriartyOverlay.setVisible(true);
                  });
            }
            return null;
          }
        };
    // Start the thread
    Thread runAiThread = new Thread(runAi, "Run AI ChatRiddleController");
    runAiThread.start();
  }

  /**
   * Sends a message to the GPT model, this is for the normal chat room.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  protected void onSendMessageNormal(ActionEvent event) throws ApiProxyException, IOException {

    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    Task<Void> runAi =
        new Task<Void>() {

          // Sends the player's input to the GPT model and waits for a response. While waiting for a
          // response, it will call another method that shows a loading label. Once it is finished
          // getting a response, it will call another method to hide the loading label. If the
          // response starts with "Correct", then the player has solved the riddle. The input text
          // field and send button are disabled and a dialog is shown congratulating the player. The
          // player is then navigated to the diary view. The game's state also changes so that the
          // riddle is solved. Otherwise, the player's input is appended to the chat text area and
          //  the input text field is cleared.

          @Override

          // Sends the player's input to the GPT model and waits for a response. While waiting for a
          // response, it will call another method that shows a loading label. Once it is finished
          // getting a response, it will call another method to hide the loading label.
          protected Void call() throws Exception {
            isLoading();
            inputText.clear();
            chatTextArea.clear();
            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);
            isNotLoading();

            if (message.toLowerCase().contains("code")) {

              runGpt(
                  new ChatMessage(
                      "user",
                      "You are Moriarty from Sherlock, say that you have hid the code to the door"
                          + " somewhere, but I can't tell you what it is. Two short sentences"
                          + " max."),
                  chatCompletionRequestRefuseHint);
              return null;
            }

            if (message.toLowerCase().contains("hint")
                || message.toLowerCase().contains("help")
                || message.toLowerCase().contains("clue")) {
              runGpt(
                  new ChatMessage(
                      "user",
                      "You are Moriarty from Sherlock. Act superior and tell Sherlock to use the"
                          + " hint button if they desperately need help. 2 short sentences max."),
                  chatCompletionRequestRefuseHint);
              return null;
            }

            runGpt(msg, chatCompletionRequest);
            return null;
          }
        };
    Thread runAiThread = new Thread(runAi, "Run AI");
    runAiThread.start();
  }
}
