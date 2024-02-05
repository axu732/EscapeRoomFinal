package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/** Manages the scenes in the application. This class is used to switch between scenes. */
public class SceneManager {
  /**
   * Enumerates the different scenes in the application. Each scene is represented by a value in
   * this and is called when the scene is loaded.
   */
  public enum AppUi {
    LOUNGE,
    CHAT,
    START,
    FAILED,
    SUCCEEDED,
    WATSON,
    CODE,
    DIARY,
    TEASET,
    KITCHEN,
    ENTRANCE,
    RECIPE,
    RIDDLEROOM,
    CHATRIDDLE,
    INSTRUCTIONS
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  public static void addAppUi(AppUi ui, Parent root) {
    sceneMap.put(ui, root);
  }

  public static Parent getUi(AppUi ui) {
    return sceneMap.get(ui);
  }
}
