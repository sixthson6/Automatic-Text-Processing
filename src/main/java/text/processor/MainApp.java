package text.processor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import text.processor.ui.MainController;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        Scene scene = new Scene(controller.getRoot(), 800, 600);

        primaryStage.setTitle("Text Processor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}