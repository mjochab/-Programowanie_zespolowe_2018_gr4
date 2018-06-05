package protolab;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProtoLab extends Application {

    private static Stage pStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Aplikacja ProtoLab");
        stage.show();

        pStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getpStage() {
        return pStage;
    }

}
