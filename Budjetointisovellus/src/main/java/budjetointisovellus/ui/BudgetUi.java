package budjetointisovellus.ui;

import budjetointisovellus.dao.SQLBudgetDao;
import budjetointisovellus.dao.SQLCategoryDao;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.dao.SQLUserDao;
import budjetointisovellus.domain.Budget;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class BudgetUi extends Application {
    private BudgetService budgetService;
    private SQLUserDao userDao;
    private SQLBudgetDao budgetDao;
    private SQLCategoryDao  categoryDao;
    
    private Scene loginScene;
    private Scene createUserScene;
    private Scene budgetScene;
    private Scene newBudgetScene;
    
    private Label topLabel = new Label();
    private VBox budgetLines;
    
    @Override
    public void init() throws FileNotFoundException, IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String dbAddr = properties.getProperty("db");
        
        userDao = new SQLUserDao(dbAddr);
        budgetDao = new SQLBudgetDao(dbAddr);
        categoryDao = new SQLCategoryDao(dbAddr);
        budgetService = new BudgetService(userDao, budgetDao, categoryDao);
    }
    
    public void redrawBudgetLines() {
        System.out.println("Täällä renderöidään budjetti");
        budgetLines.getChildren().clear();
        
        if (budgetService.getUsersBudget() != null) {
            budgetLines.getChildren().add(new Label("Olet jo luonut yhden budjetin"));
            Budget budgetCategories = budgetService.getUsersBudget();
        }        
    }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException, FileNotFoundException, SQLException {
        primaryStage.setTitle("Budjetointisovellus");
        
        
        //budget scene
        
        VBox budgetPane = new VBox();
        HBox logoutAndInfo = new HBox();
        
        Label userInfo = new Label();
        Button logoutBtn = new Button("Kirjaudu ulos");
        
        logoutBtn.setOnAction(e -> {
            budgetService.logout();
            primaryStage.setScene(loginScene);
            topLabel.setText("Kirjaudu sisään");
        });
        
        logoutAndInfo.getChildren().addAll(userInfo, logoutBtn);
        
        budgetLines = new VBox();
        redrawBudgetLines();
        
        Label budgetLabel = new Label("Sinun budjettisi");
        Button newBudget = new Button("Aloita budjetointi");
        newBudget.setOnAction(e -> {
            primaryStage.setScene(newBudgetScene);
        }); 
        
        budgetPane.getChildren().addAll(logoutAndInfo, budgetLabel, budgetLines, newBudget);
        budgetScene = new Scene(budgetPane, 500, 300);
        
        
        //create new budget scene
        
        VBox newBudgetPane = new VBox();

        TextField budgetName = new TextField();
        TextField budgetMoneyToUse = new TextField();
        
        budgetName.getStyleClass().add("textbox-normal");
        budgetName.setPrefColumnCount(20);
        budgetName.setPromptText("Budjetin nimi");

        budgetMoneyToUse.getStyleClass().add("textbox-normal");
        budgetMoneyToUse.setPrefColumnCount(20);
        budgetMoneyToUse.setPromptText("Rahaa käytettävissä");
        
        HBox btns = new HBox();
        Button createNewBudget = new Button("Luo budjetti");
        createNewBudget.setOnAction(e -> {
            String bName = budgetName.getText();
            double moneyToUse = Double.parseDouble(budgetMoneyToUse.getText());
            try {
                budgetService.createNewBudget(bName, moneyToUse, budgetService.getLoggedUser().getName());
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            primaryStage.setScene(budgetScene);
            topLabel.setText("");
            budgetName.setText("");
            budgetMoneyToUse.setText("");
            try {
                budgetService.findUsersBudget();
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            redrawBudgetLines();
        });
        
        Button canc = new Button("Peruuta");
        canc.setOnAction(e -> {
            primaryStage.setScene(budgetScene);
        });
        
        btns.getChildren().addAll(createNewBudget, canc);
        
        newBudgetPane.getChildren().addAll(topLabel, budgetName, 
                budgetMoneyToUse, btns);
        
        
        newBudgetScene = new Scene(newBudgetPane,  500, 300);
        
        
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
        
        HBox createAndCancelBtns = new HBox();
        
        Button createNewUser = new Button("Rekisteröidy");
        createNewUser.setOnAction(e -> {
            String name = newName.getText();
            String username = newUsername.getText();
            String password = newPassword.getText();
            try {
                budgetService.createUser(name, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            primaryStage.setScene(loginScene);
            topLabel.setText("Kirjaudu sisään");
            newName.setText("");
            newUsername.setText("");
            newPassword.setText("");
        });
        
        Button cancel = new Button("Peruuta");
        cancel.setOnAction(e -> {
            primaryStage.setScene(loginScene);
        });
        
        createAndCancelBtns.getChildren().addAll(createNewUser, cancel);
        
        registerPane.getChildren().addAll(registerLabel, newName, newUsername, 
                newPassword, createAndCancelBtns);
        
        createUserScene = new Scene(registerPane, 500, 300);
        
 
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
                if (budgetService.login(username, password)) {
                    primaryStage.setScene(budgetScene);
                    usernameTxt.setText("");
                    passwordTxt.setText("");
                    userInfo.setText("Tervetuloa "+budgetService.getLoggedUser().getName());
                    if (budgetService.findUsersBudget()) {
                        System.out.println("Käyttäjälle löytyi budjetti");
                    }
                    redrawBudgetLines();
                } else {
                    topLabel.setText("Anna käyttäjätunnus ja salasana tai luo uusi käyttäjä ");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button registerButton = new Button("Luo käyttäjätunnus");
        registerButton.setOnAction(e -> {
            primaryStage.setScene(createUserScene);
        });
        
        buttonPane.getChildren().addAll(loginButton, registerButton);

        loginPane.getChildren().addAll(topLabel, usernameTxt, passwordTxt, buttonPane);
        
        loginScene = new Scene(loginPane, 500, 300);
        
        primaryStage.setScene(loginScene);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
