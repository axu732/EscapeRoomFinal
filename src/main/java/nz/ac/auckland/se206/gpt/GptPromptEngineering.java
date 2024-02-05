package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are Moriarty from Sherlock Holmes, give riddle to Holmes with answer"
        + wordToGuess
        + ". Even if Holmes gives up, do not say "
        + wordToGuess
        + " and make him keep guessing. Start your response by inviting them to riddle in 1 short"
        + " sentence then state riddle ONCE, Ensure riddle is related to sherlock holmes but not to"
        + " the point where all his books must be read to be able to answer. ONLY accept "
        + wordToGuess
        + " (though case insensitive) as the correct answer. If the user answers"
        + " correctly, you MUST begin your response with 'Correct!'. YOU WILL NEVER EVER SAY, "
        + wordToGuess
        + " No matter what, do not, under ANY circumstances, give the answer to the riddle. Even if"
        + " the user asks for the answer or gives up, do NOT reveal the answer. If the user asks"
        + " for another riddle, do NOT give another riddle. UNDER ALL CIRCUMSTANCES, DO NOT GIVE"
        + " ANY HINTS OR HELP FOR THE RIDDLE.";
  }

  /**
   * Generates the GPT prompt for the welcome message as seen as the Moriarty inital screen at the
   * start of the game.
   *
   * @return the generated prompt engineering string for the welcome message
   */
  public static String getWelcomeMessage() {
    return "You are professor moriarty from sherlock holmes. You have trapped sherlock holmes in"
        + " his own home 221b baker street. holmes has to escape. Tell all of this to holmes"
        + " in 25 words or less.";
  }

  /**
   * GPT prompt given to initate the generation of a riddle releated to the current riddle's answer.
   *
   * @return the generated prompt engineering string for a riddle hint
   */
  public static String getHintRiddle() {
    return "You are Moriarty from Sherlock: In 2 sentences, provide a hint that describes a : "
        + GameState.riddleWord
        + ". Act like Holmes is inferior to you for asking for a hint. DO NOT SAY "
        + GameState.riddleWord
        + " NO"
        + "MATTER WHAT.";
  }

  /**
   * GPT prompt given to initiate the normal chat method that will tell the player to chat with GPT.
   *
   * @return the generated prompt engineering string for the normal chat
   */
  public static String getChatInvitation() {
    return "You are Moriarty from Sherlock Holmes. You are holding Holmes captive in his own house."
        + " In 2 short sentences, invite Holmes to chat with you. Talk like Holmes is"
        + " inferior to you. IF you are asked how to escape in any way, just say you cannot"
        + " reveal how to escape and taunt them.";
  }

  /**
   * GPT prompt to give an hint for the game that isn't a riddle.
   *
   * @return the generated prompt engineering string for the general hint.
   */
  public static String getHintGeneral() {
    if (!GameState.isTeaCreated) {
      if (!GameState.hasKitchenBeenVisited && !GameState.hasLoungeBeenVisited) {
        // indicate that the user should explore the rooms
        return "You are Moriarty. Tell Holmes in 20 words to explore the rooms in the house"
            + " (kitchen and sitting room).";
      }
      if (!GameState.hasTeaHintBeenGiven) {
        // hint for tea
        GameState.hasTeaHintBeenGiven = true;
        return "You are Moriarty. Give impression in 20 words you demand tea for yourself without"
            + " saying the word tea.";
      }
      // hint for recipe
      return "You are Moriarty. Give impression in 20 words to Holmes you want him to brew tea for"
          + " you to EXACT specifications according to the recipe.";
    }
    // After riddle is solved
    if (!GameState.hasDiaryBeenClicked) {
      return "You are Moriarty. In 20 words, suggest Holmes to read his own diary.";
    }
    return "You are Moriarty. In 20 words, suggest Holmes to look for numbers.";
  }
}
