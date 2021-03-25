package budjetointisovellus.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class BudjetUi extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budjetointisovellus");
        
        BorderPane loginPane = new BorderPane();
        
        TextField username = new TextField();
        username.getStyleClass().add("textbox-normal");
        username.setPrefColumnCount(20);
        username.setPromptText("Username");
        
        PasswordField password = new PasswordField();
        password.getStyleClass().add("textbox-normal");
        password.setPrefColumnCount(20);
        password.setPromptText("Password");
        
        loginPane.setTop(new Label("Kirjaudu sisään"));
        loginPane.setCenter(username);
        loginPane.setBottom(password);
        
        Scene loginWiew = new Scene(loginPane);
        
        primaryStage.setScene(loginWiew);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
