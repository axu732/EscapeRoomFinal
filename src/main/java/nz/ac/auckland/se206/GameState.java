package nz.ac.auckland.se206;

import java.util.Random;

/** Represents the state of the game. */
public class GameState {

  // Indicates the difficulty the user has chosen
  public static String difficulty = "Easy";

  // Indicates the number of hints the user has chosen
  public static String hintsRemaining = "Infinite";

  // Indicates the length of the game the user has chosen
  public static int gameLength = 120;

  // Indicates the current chat message from GPT
  public static String chatMessage = "";

  // Indicates where the user is currently
  public static String currentRoom = "Entrance";

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** Indicates whether the game has started or not. */
  public static boolean isGameStarted = false;

  /** Indicates whether the door has been clicked one before or not. */
  public static boolean hasDoorBeenClicked = false;

  /** Indicates whether the violin has been clicked one before or not. */
  public static boolean hasViolinBeenClicked = false;

  /** Indicates whether the diary has been clicked one before or not. */
  public static boolean hasDiaryBeenClicked = false;

  // Indicates if the tea has been created correctly.
  public static boolean isTeaCreated = false;

  // Indicates whether the kitchen has been visited.
  public static boolean hasKitchenBeenVisited = false;

  // Indicates whether the lounge has been visited
  public static boolean hasLoungeBeenVisited = false;

  // Indicates whether Moriarty has asked for tea
  public static boolean hasTeaHintBeenGiven = false;

  // Indicates whether the player has talked to Moriarty about the riddle
  public static boolean hasRiddleBeenGiven = false;

  // Generate the random numbers for the recipe
  private static Random random = new Random();

  public static int kettleRandom = random.nextInt(3) + 1;
  public static int teaRandom = random.nextInt(2) + 1;
  public static int sugarRandom = random.nextInt(2) + 1;
  public static int milkRandom = random.nextInt(2) + 1;

  // Generate the random word to guess
  private static String[] riddleWords = {"Violin", "Diary", "Pipe", "Magnifying Glass", "Painting"};
  public static String riddleWord = riddleWords[random.nextInt(5)];

  // Generate dates for the diary to use
  public static int diaryDate = random.nextInt(28) + 1;
  public static int diaryMonth = random.nextInt(12) + 1;
  public static int diaryYear = random.nextInt(9) + 1890;

  /**
   * Rerandomises the random numbers for the recipe, the random word to guess, and the dates for the
   * diary.
   */
  public static void rerandomise() {
    // Generate the random numbers for the recipe
    kettleRandom = random.nextInt(3) + 1;
    teaRandom = random.nextInt(2) + 1;
    sugarRandom = random.nextInt(2) + 1;
    milkRandom = random.nextInt(2) + 1;

    // Generate the random word to guess
    riddleWord = riddleWords[random.nextInt(5)];

    // Generate dates for the diary to use
    diaryDate = random.nextInt(28) + 1;
    diaryMonth = random.nextInt(12) + 1;
    diaryYear = random.nextInt(9) + 1890;

    // Reset the boolean values
    isRiddleResolved = false;
    isKeyFound = false;
    isGameStarted = false;
    hasDoorBeenClicked = false;
    hasViolinBeenClicked = false;
    hasDiaryBeenClicked = false;
    isTeaCreated = false;
    hasKitchenBeenVisited = false;
    hasLoungeBeenVisited = false;
    hasTeaHintBeenGiven = false;
    chatMessage = "";
  }

  /** Resets all the boolean state values to false, clearing the history. */
  public static void resetBooleanStates() {
    // Set every boolean value to false
    isRiddleResolved = false;
    isKeyFound = false;
    isGameStarted = false;
    hasDoorBeenClicked = false;
    hasViolinBeenClicked = false;
    hasDiaryBeenClicked = false;
    isTeaCreated = false;
    hasKitchenBeenVisited = false;
    hasLoungeBeenVisited = false;
    hasTeaHintBeenGiven = false;
    hasRiddleBeenGiven = false;
    chatMessage = "";
  }
}
