package launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    private Stage window;
    private Scene loginScene, booksScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ComponentFactory.getInstance(false, primaryStage);
        //LoginComponentFactory.getInstance(false, primaryStage);
        AdminComponentFactory.getInstance(false, primaryStage);
    }
}
