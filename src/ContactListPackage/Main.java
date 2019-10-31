package ContactListPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

import static javafx.application.Platform.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainContactsMenu.fxml"));
        primaryStage.setTitle("ContactList");
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(500);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

//        exit();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
