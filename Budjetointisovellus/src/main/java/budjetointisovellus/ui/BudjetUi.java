package budjetointisovellus.ui;

import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.dao.SQLUserDao;
import budjetointisovellus.domain.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class BudjetUi extends Application {
    private BudgetService budgetService;
    private SQLUserDao userDao;
    
    private Scene loginScene;
    private Scene createUserScene;
    private Scene budgetScene;
    
    private Label topLabel = new Label();
    
    @Override
    public void init() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Budjetointisovellus");
        
        //budget scene
        
        
        //create new user scene
        
        Label registerLabel = new Label("Luo uusi käyttäjä");
        
        TextField newName = new TextField();
        TextField newUsername = new TextField();
        PasswordField newPassword = new PasswordField();
        
        VBox registerPane = new VBox();
        
        newName.getStyleClass().add("textbox-normal");
        newName.setPrefColumnCount(20);
        newName.setPromptText("Nimi");

        newUsername.getStyleClass().add("textbox-normal");
        newUsername.setPrefColumnCount(20);
        newUsername.setPromptText("Käyttäjätunnus");
        
        newPassword.getStyleClass().add("textbox-normal");
        newPassword.setPrefColumnCount(20);
        newPassword.setPromptText("Salasana");
        
        Button createNewUser = new Button("Rekisteröidy");
        
        registerPane.getChildren().addAll(registerLabel, newName, newUsername, 
                newPassword, createNewUser);
        
        createUserScene = new Scene(registerPane, 300, 300);
 
        //login scene
        topLabel.setText("Kirjaudu sisään");
        
        VBox loginPane = new VBox();
        
        TextField usernameTxt = new TextField();
        
        usernameTxt.getStyleClass().add("textbox-normal");
        usernameTxt.setPrefColumnCount(20);
        usernameTxt.setPromptText("Käyttäjätunnus");
        
        PasswordField passwordTxt = new PasswordField();
        passwordTxt.getStyleClass().add("textbox-normal");
        passwordTxt.setPrefColumnCount(20);
        passwordTxt.setPromptText("Salasana");
        
        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        Button loginButton = new Button("Kirjaudu");
        loginButton.setOnAction(e -> {
            String username = usernameTxt.getText();
            String password = passwordTxt.getText();
            
            try {
                if (budgetService.login(username)) {
                    topLabel.setText("Tervetuloa "+username+"!");
                    primaryStage.setScene(budgetScene);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BudjetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button registerButton = new Button("Luo käyttäjätunnus");
        registerButton.setOnAction(e -> {
            primaryStage.setScene(createUserScene);
        });
        
        buttonPane.getChildren().addAll(loginButton, registerButton);

        loginPane.getChildren().addAll(topLabel, usernameTxt, passwordTxt, buttonPane);
        
        loginScene = new Scene(loginPane, 300, 300);
        
        primaryStage.setScene(loginScene);
        
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
