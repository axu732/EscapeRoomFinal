package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  public static void setUi(AppUi newUi) {
    scene.setRoot(SceneManager.getUi(newUi));
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    SceneManager.addAppUi(AppUi.CHATRIDDLE, loadFxml("chatRiddle"));
    SceneManager.addAppUi(AppUi.CHAT, loadFxml("chat"));
    SceneManager.addAppUi(AppUi.CODE, loadFxml("code"));
    SceneManager.addAppUi(AppUi.FAILED, loadFxml("failed"));
    SceneManager.addAppUi(AppUi.LOUNGE, loadFxml("room"));
    SceneManager.addAppUi(AppUi.START, loadFxml("start"));
    SceneManager.addAppUi(AppUi.SUCCEEDED, loadFxml("succeeded"));
    SceneManager.addAppUi(AppUi.DIARY, loadFxml("diary"));
    SceneManager.addAppUi(AppUi.KITCHEN, loadFxml("kitchen"));
    SceneManager.addAppUi(AppUi.TEASET, loadFxml("teaMaking"));
    SceneManager.addAppUi(AppUi.ENTRANCE, loadFxml("entrance"));
    SceneManager.addAppUi(AppUi.RECIPE, loadFxml("recipe"));
    SceneManager.addAppUi(AppUi.RIDDLEROOM, loadFxml("riddleRoom"));
    SceneManager.addAppUi(AppUi.INSTRUCTIONS, loadFxml("instructions"));
    scene = new Scene(SceneManager.getUi(AppUi.START), 1018, 720);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            Platform.exit();
            System.exit(0);
          }
        });
  }
}
