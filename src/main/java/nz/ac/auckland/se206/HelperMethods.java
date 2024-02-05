package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * A class that contains helper methods that are used in multiple classes. It contains methods for
 * taunting, resetting, hints and the timer
 */
public abstract class HelperMethods {
  protected static StringProperty timeDisplay = new SimpleStringProperty("120");
  protected static StringProperty watsonDisplay = new SimpleStringProperty("");
  protected static StringProperty hintDisplay = new SimpleStringProperty("Hints: 5");
  protected static StringProperty chatDisplay = new SimpleStringProperty("");
  protected TextToSpeech textToSpeech = new TextToSpeech();
  protected ChatCompletionRequest chatCompletionRequest;

  /**
   * Count down from 120 seconds (2 minutes). Only start when the user enters the room. Every second
   * that it counts down from, it will update a timer label by calling another method. If the user
   * does not escape the room in time, the failed scene will be shown by calling another method. If
   * the user has not solved the riddle yet and there is only 1 minute left, a dialog box will be
   * shown to remind the user to solve the riddle.
   *
   * @returns void
   */
  protected void countDown() {
    int countdownSeconds = GameState.gameLength;

    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(

        // Creates a new timer task that counts down from 120 seconds (2 minutes). Only start when
        // the user enters the room. Every second that it counts down from, it will update a timer
        // label by calling another method. If the user does not escape the room in time, the failed
        // scene will be shown by calling another method. If the user has not solved the riddle yet
        // and there is only 1 minute left, a dialog box will be shown to remind the user to solve
        // the riddle.

        new TimerTask() {
          protected int secondsRemaining = countdownSeconds;

          @Override
          public void run() {
            if (secondsRemaining > -1) {
              if (GameState.currentRoom == "Success") {
                timer.cancel();
                try {
                  if (!TextToSpeech.getMute()) {
                    winPlayer();
                  }
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
              if (secondsRemaining % 60 == 0
                  && GameState.isGameStarted
                  && GameState.isRiddleResolved == false
                  && !TextToSpeech.getMute()) {
                if (GameState.gameLength == 120
                    && secondsRemaining != 120
                    && secondsRemaining != 0) {
                  Platform.runLater(
                      () -> {
                        try {
                          tauntPlayer();
                        } catch (ApiProxyException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                      });
                }
                if (GameState.gameLength == 240
                    && secondsRemaining != 240
                    && secondsRemaining != 0) {
                  Platform.runLater(
                      () -> {
                        try {
                          tauntPlayer();
                        } catch (ApiProxyException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                      });
                }
                if (GameState.gameLength == 360
                    && secondsRemaining != 360
                    && secondsRemaining != 0) {
                  Platform.runLater(
                      () -> {
                        try {
                          tauntPlayer();
                        } catch (ApiProxyException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                      });
                }
              }
              int seconds = secondsRemaining;
              Platform.runLater(
                  () -> {
                    updateTimerText(seconds);
                  });
              secondsRemaining--;

            } else {
              showFailedScene();
              timer.cancel();
              try {
                if (!TextToSpeech.getMute()) {
                  losePlayer();
                }
              } catch (ApiProxyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          }
        },
        0,
        1000);
  }

  protected void showFailedScene() {
    App.setUi(AppUi.FAILED);
  }

  protected void updateChat() {
    if (GameState.isTeaCreated && !GameState.hasRiddleBeenGiven) {
      chatDisplay.set(GameState.chatMessage = "");
    }
    chatDisplay.set(GameState.chatMessage);
  }

  /**
   * Updates the timer label with the given time. Calculates the amount of minutes and seconds and
   * displays that.
   *
   * @param time the time to display
   */
  protected void updateTimerText(int time) {

    // Initialisation of variables
    String timeToDisplay;
    int minutes = time / 60;
    int seconds = time % 60;

    // If the seconds is less than 10, we will add a 0 in front of the seconds
    if (seconds < 10) {
      timeToDisplay = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
      // Otherwise, we will just display the minutes and seconds
    } else {
      timeToDisplay = String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }
    // Update the timer label
    timeDisplay.set(timeToDisplay);
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  protected void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Updates the hint text. If the user has infinite hints, the hint text will be updated to "Hints:
   * Infinite". If the user has no hints remaining, a dialog box will be shown to inform the user.
   * If the user has hints remaining, the hint text will be updated to "Hints: x" where x is the
   * number of hints remaining.
   */
  protected void updateHintText() {
    // If the user has infinite hints, we will keep it the same
    if (GameState.hintsRemaining == "Infinite") {
      hintDisplay.set("Hints: " + GameState.hintsRemaining);
      return;
    }
    // If there are no more hints, we will show a dialog to user that they have no more hints
    // remaining
    if (GameState.hintsRemaining.equals("0") && GameState.isGameStarted) {
      showDialog(
          "No hints remaining", "You have no hints remaining", "You have no hints remaining");
      return;
    }
    // This is for when hard mode starts, just to make sure the dialog doesnt show up at the start
    if (GameState.hintsRemaining.equals("0")) {
      hintDisplay.set("Hints: " + GameState.hintsRemaining);
      return;
    }

    // If there are hints remaining, we will decrement the counter in gamestate and update the hint
    // labels
    int hintsRemaining = Integer.parseInt(GameState.hintsRemaining);
    hintsRemaining--;
    GameState.hintsRemaining = String.valueOf(hintsRemaining);
    hintDisplay.set("Hints: " + GameState.hintsRemaining);
  }

  /**
   * Gives message for the GPT model to run. It will give back a chatmessage response from the
   * prompt.
   *
   * @param msg the chat message to process
   * @param chatCompletionRequest the chat completion request of the chat message
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg, ChatCompletionRequest chatCompletionRequest)
      throws ApiProxyException {
    // Add the latest user message to the request
    chatCompletionRequest.addMessage(msg);
    try {
      // Run the GPT model and then get the result
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      System.out.println(chatCompletionResult.getUsageTotalTokens());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates a thread that runs the GPT model with a given chat message. The thread will also run
   * the text to speech with the response chat message. the message will taunt the player.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  protected void tauntPlayer() throws ApiProxyException {
    Task<Void> tauntTask =

        // Creates a new task that calls the method to run the GPT model with a given chat message.
        // The thread will also run the text to speech with the response chat message.

        new Task<>() {
          @Override
          protected Void call() throws ApiProxyException {
            // Set the parameters for the GPT model to run
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            // Create a taunt prompt for GPT to respond to
            ChatMessage tauntChatMessage =
                new ChatMessage(
                    "user",
                    "You are Moriarty from Sherlock, give a short taunt to Holmes in regards of not"
                        + " being able to escape his house. Two sentences max.");
            // Taunt the user by running the GPT model with the taunt prompt
            ChatMessage tauntText = runGpt(tauntChatMessage, chatCompletionRequest);
            textToSpeech.speak(tauntText.getContent());
            return null;
          }
        };

    // Start the task on a background thread
    Thread thread = new Thread(tauntTask);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Creates a thread that runs the GPT model with a given chat message. The thread will also run
   * the text to speech with the response chat message. the message tells the user that they've
   * failed.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  protected void losePlayer() throws ApiProxyException {
    Task<Void> tauntTask =

        // Creates a new task that calls the method to run the GPT model with a given chat message.
        // The thread will also run the text to speech with the response chat message.

        new Task<>() {
          @Override
          protected Void call() throws ApiProxyException {
            // Set the parameters for the GPT model to run
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            // Create a prompt for GPT to respond to about how the user lost
            ChatMessage tauntChatMessage =
                new ChatMessage(
                    "user",
                    "You are Moriarty from Sherlock, laugh at Holmes and say they have lost."
                        + " Two sentences max.");
            // Run GPT model with the prompt
            ChatMessage tauntText = runGpt(tauntChatMessage, chatCompletionRequest);
            textToSpeech.speak(tauntText.getContent());
            return null;
          }
        };

    // Start the task on a background thread
    Thread thread = new Thread(tauntTask);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Creates a thread that runs the GPT model with a given chat message. The thread will also run
   * the text to speech with the response chat message. the message tells the user that they've won.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  protected void winPlayer() throws ApiProxyException {
    Task<Void> tauntTask =
        new Task<>() {
          @Override
          // Creates a new task that calls the method to run the GPT model with a given chat
          // message. The thread will also run the text to speech with the response chat message.

          protected Void call() throws ApiProxyException {
            // Set the parameters for the GPT model to run
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            // Create a prompt for GPT to respond to about how the user won
            ChatMessage tauntChatMessage =
                new ChatMessage(
                    "user",
                    "You are Moriarty from Sherlock, say you can't believe that Holmes beat you."
                        + " Two Sentences Max.");
            // Run GPT model with the prompt
            ChatMessage tauntText = runGpt(tauntChatMessage, chatCompletionRequest);
            textToSpeech.speak(tauntText.getContent());
            return null;
          }
        };

    // Start the task on a background thread
    Thread thread = new Thread(tauntTask);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * What happens when the user restarts the game. The game will be restarted and the UI will be
   * changed to the start scene. The random numbers for the recipe, the random word to guess, and
   * the dates for the diary will be rerandomised. It creates a new task to achieve this.
   */
  protected void restartGame() {
    Task<Void> task =
        new Task<Void>() {
          @Override

          // Creates a new task that restarts the game and changes the UI to the start scene. The
          // random numbers for the recipe, the random word to guess, and the dates for the diary
          //  will be rerandomised.

          protected Void call() throws Exception {
            SceneManager.addAppUi(
                AppUi.START, new FXMLLoader(App.class.getResource("/fxml/start.fxml")).load());
            App.setUi(AppUi.START);
            GameState.rerandomise();
            GameState.resetBooleanStates();

            // Checks if the game is muted, if it is, it will unmute it
            if (TextToSpeech.getMute()) {
              TextToSpeech.setMute();
            }

            SceneManager.addAppUi(
                AppUi.CHATRIDDLE,
                new FXMLLoader(App.class.getResource("/fxml/chatriddle.fxml")).load());
            SceneManager.addAppUi(
                AppUi.CODE, new FXMLLoader(App.class.getResource("/fxml/code.fxml")).load());
            SceneManager.addAppUi(
                AppUi.TEASET, new FXMLLoader(App.class.getResource("/fxml/teaMaking.fxml")).load());
            SceneManager.addAppUi(
                AppUi.RECIPE, new FXMLLoader(App.class.getResource("/fxml/recipe.fxml")).load());
            SceneManager.addAppUi(
                AppUi.CHAT, new FXMLLoader(App.class.getResource("/fxml/chat.fxml")).load());
            SceneManager.addAppUi(
                AppUi.DIARY, new FXMLLoader(App.class.getResource("/fxml/diary.fxml")).load());
            SceneManager.addAppUi(
                AppUi.RIDDLEROOM,
                new FXMLLoader(App.class.getResource("/fxml/riddleroom.fxml")).load());

            GameState.currentRoom = "Entrance";
            return null;
          }
        };

    // Start the task on a background thread
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  /** What happens when the user closes the game. The game will be closed. */
  protected void closeGame() {
    Platform.exit();
    System.exit(0);
  }
}
