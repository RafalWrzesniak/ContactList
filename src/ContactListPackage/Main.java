package ContactListPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    static Controller mainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContactsMenu.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();

        primaryStage.setTitle("ContactList");
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(530);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png") ));
        Scene mainScene = new Scene(root, 520, 500);

        primaryStage.setScene(mainScene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
