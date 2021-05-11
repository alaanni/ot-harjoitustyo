package budjetointisovellus.ui;

import budjetointisovellus.dao.SQLBudgetDao;
import budjetointisovellus.dao.SQLCategoryDao;
import budjetointisovellus.dao.SQLCostDao;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.dao.SQLUserDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Category;
import budjetointisovellus.domain.Cost;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Käyttöliittymä
 */

public class BudgetUi extends Application {
    private BudgetService budgetService;
    private SQLUserDao userDao;
    private SQLBudgetDao budgetDao;
    private SQLCategoryDao  categoryDao;
    private SQLCostDao costDao;
    
    private Scene loginScene;
    private Scene createUserScene;
    private Scene budgetScene;
    private Scene newBudgetScene;
    
    private Label topLabel = new Label();
    private VBox budgetLines;
    private Label totalLeftLabel = new Label();
    
    @Override
    public void init() throws FileNotFoundException, IOException, SQLException {
        Properties properties = new Properties();
        File file = new File("config.properties");
        if (!file.exists()) {
           try (final OutputStream out = new FileOutputStream("config.properties")) {
                properties.store(out, "db=budgetapp.db\n" + "testdb=testdb.db");
              } catch (IOException e) {
                throw new RuntimeException("Error writing the properties file", e);
              }
        }
        
        properties.load(new FileInputStream("config.properties"));
        
        String dbAddr = properties.getProperty("db");
        
        userDao = new SQLUserDao(dbAddr);
        budgetDao = new SQLBudgetDao(dbAddr);
        categoryDao = new SQLCategoryDao(dbAddr);
        costDao = new SQLCostDao(dbAddr);
        budgetService = new BudgetService(userDao, budgetDao, categoryDao, costDao);
    }
    
    /**
    * luo rivin budjettiin
     * @param cost
     * @throws java.sql.SQLException
    */
    
    public Node createBudgetLine(Cost cost) throws SQLException {
        HBox bLine = new HBox();
        TextField amount = new TextField();
        amount.setText(String.valueOf(cost.getAmount()));
        Label title = new Label(cost.getName());
        Button editButton = new Button("Tallenna muutokset");
        editButton.setOnAction(e -> {
            Double tempAmount = cost.getAmount();
            if (!amount.getText().isEmpty()) {
                try { cost.setAmount(Double.parseDouble(amount.getText()));
                } catch (NumberFormatException ex) {
                    cost.setAmount(-1.0);
                }
            } else {
                cost.setAmount(0.0);
            }
            try {
                if (budgetService.editCost(cost)) {
                    redrawBudgetLines();
                } 
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            });
        Button deleteButton = new Button("Poista kulu");
        deleteButton.setOnAction(e -> {
            try {
                if (budgetService.removeCost(cost)) {
                    redrawBudgetLines();
                } 
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            });
        bLine.getChildren().addAll(title, amount, editButton, deleteButton);
        bLine.setSpacing(10);
        
        return bLine;
    }
    
    /**
    * uudelleenrenderöi budjettinäkymän
     * @throws java.sql.SQLException
    */
    
    public void redrawBudgetLines() throws SQLException {
        budgetLines.getChildren().clear();

        if (budgetService.getUsersBudget() != null) {
            Budget bud = budgetService.getUsersBudget();
            HBox budgetInfo = new HBox();
            budgetInfo.setSpacing(10);
            Label budgetName = new Label(bud.getName().toUpperCase());
            TextField moneyToUse = new TextField();
            moneyToUse.setPrefColumnCount(7);
            moneyToUse.setText(String.valueOf(bud.getMoneyToUse()));
            Label euroChar = new Label("€");
            Button editMoneyToUse = new Button("Tallenna muutokset");
            editMoneyToUse.setOnAction(e -> {
            Double mToUse;
            if (!moneyToUse.getText().isEmpty()) {
                try { mToUse = Double.parseDouble(moneyToUse.getText());
                } catch (NumberFormatException ex) {
                    mToUse = -1.0;
                }
            } else {
                mToUse = 0.0;
            }
            try {
                if (budgetService.editBudgetsMoneyToUse(mToUse)) {
                    budgetService.findUsersBudget();
                    moneyToUse.setText(String.valueOf(budgetService.getUsersBudget().getMoneyToUse()));
                    totalLeftLabel.setText(String.valueOf(budgetService.getUsersBudget().getMoneyToUse()-budgetService.getTotalSum()));
                } 
                
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            });
            
            budgetInfo.getChildren().addAll(budgetName, moneyToUse, euroChar, editMoneyToUse);
            budgetLines.getChildren().add(budgetInfo);
            List<Category> budgetCategories = budgetService.findBudgetCategories();
            List<Cost> categoryCosts;
            for (Category c : budgetCategories) {
                budgetLines.getChildren().add(new Label(c.getName().toUpperCase()));
                categoryCosts = budgetService.findCategorysCosts(c);
                for (Cost cost : categoryCosts) {
                    budgetLines.getChildren().add(createBudgetLine(cost));
                }
            }
            
            Label addCostLabel = new Label("Lisää uusi kulu: ");
            HBox addCostFields = new HBox();
            Label newCostLabel = new Label("Kulun nimi: ");
            TextField newCostField = new TextField();
            TextField newAmountField = new TextField();
            Label euroLabel = new Label(" Kustannus (€): ");
            Label categLabel = new Label(" Kategoria: ");
            TextField categoryField = new TextField();
            Button addCostBtn = new Button("Tallenna");
            addCostBtn.setOnAction(e -> {
            String cName = newCostField.getText();
            Double cAmount = 0.0;
            if (!newAmountField.getText().isEmpty()) {
                try { cAmount = Double.parseDouble(newAmountField.getText());
                } catch (NumberFormatException ex) {
                    cAmount = -1.0;
                }
            }
            
            String cCategory = categoryField.getText();
            try {
                if (budgetService.createNewCost(cName, cAmount, cCategory.trim().toLowerCase())) {
                    redrawBudgetLines();
                    newCostField.setText("");
                    newAmountField.setText("");
                    categoryField.setText("");
                } else {
                    addCostLabel.setText("Täytä kaikki kentät ja ilmoita kustannus positiivisena lukuna!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            });
            
            HBox total = new HBox();
            HBox moneyLeft = new HBox();
            
            Label totalLabel = new Label("Suunnitellut kulut yhteensä (€): ");
            Label totalAmount = new Label(Double.toString(budgetService.getTotalSum()));
            total.getChildren().addAll(totalLabel, totalAmount);
            
            Label moneyLeftLabel = new Label("Rahaa käytettävissä (€): ");
            totalLeftLabel.setText(String.valueOf(bud.getMoneyToUse()-budgetService.getTotalSum()));
            moneyLeft.getChildren().addAll(moneyLeftLabel, totalLeftLabel);
            
            addCostFields.getChildren().addAll(newCostLabel, newCostField, 
                    euroLabel, newAmountField, categLabel, categoryField, 
                    addCostBtn);
            budgetLines.getChildren().addAll(addCostLabel, addCostFields, total, moneyLeft);
            budgetLines.setSpacing(10);
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
         
        Label budgetLabel = new Label("Sinun budjettisi: ");
        
        Button newBudget = new Button("Aloita budjetointi");
        newBudget.setOnAction(e -> {
            primaryStage.setScene(newBudgetScene);
        }); 
        
        budgetPane.getChildren().addAll(logoutAndInfo, budgetLabel, budgetLines, newBudget);
        budgetPane.setSpacing(10);
        budgetScene = new Scene(budgetPane, 800, 500);
    
        
        //create new budget scene
        
        VBox newBudgetPane = new VBox();

        TextField budgetName = new TextField();
        TextField budgetMoneyToUse = new TextField();
        
        Label bn = new Label("Budjetin nimi: ");

        Label mtu = new Label("Rahaa käytettävissä:      (voi jättää myös tyhjäksi ja lisätä myöhemmin) ");
        
        HBox btns = new HBox();
        Button createNewBudget = new Button("Luo budjetti");
        createNewBudget.setOnAction(e -> {
            double moneyToUse;
            String bName = budgetName.getText();
            if (!budgetMoneyToUse.getText().isEmpty()) {
                try { moneyToUse = Double.parseDouble(budgetMoneyToUse.getText());
                } catch (NumberFormatException ex) {
                    moneyToUse = -1.0;
                }
            } else {
                moneyToUse = 0.0;
            }
            try {
                if (budgetService.createNewBudget(bName, moneyToUse)) {
                    budgetPane.getChildren().clear();
                    budgetPane.getChildren().addAll(logoutAndInfo, budgetLabel, budgetLines);
                    primaryStage.setScene(budgetScene);
                    topLabel.setText("");
                    budgetName.setText("");
                    budgetMoneyToUse.setText("");
                    bn.setText("Budjetin nimi: ");
                    try {
                        budgetService.findUsersBudget();
                    } catch (SQLException ex) {
                        Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    redrawBudgetLines();
                } else {
                    bn.setText("Anna nimi ja käytettävissä oleva rahamäärä positiivisena lukuna.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button canc = new Button("Peruuta");
        canc.setOnAction(e -> {
            primaryStage.setScene(budgetScene);
        });
        
        btns.getChildren().addAll(createNewBudget, canc);
        
        newBudgetPane.getChildren().addAll(topLabel, bn, budgetName, mtu,
                budgetMoneyToUse, btns);
        newBudgetPane.setSpacing(10);
        
        newBudgetScene = new Scene(newBudgetPane,  800, 500);
        
        
        //create new user scene
        
        Label registerLabel = new Label("Luo uusi käyttäjä");
        
        TextField newName = new TextField();
        Label newUn = new Label("Nimi: ");
        TextField newUsername = new TextField();
        Label newUsern = new Label("Käyttäjätunnus: ");
        PasswordField newPassword = new PasswordField();
        Label newPw = new Label("Salasana: ");
        
        VBox registerPane = new VBox();
        
        HBox createAndCancelBtns = new HBox();
        
        Button createNewUser = new Button("Rekisteröidy");
        createNewUser.setOnAction(e -> {
            String name = newName.getText();
            String username = newUsername.getText();
            String password = newPassword.getText();
            try {
                if (budgetService.createUser(name, username, password)) {
                    primaryStage.setScene(loginScene);
                    topLabel.setText("Kirjaudu sisään, " + name);
                    newName.setText("");
                    newUsername.setText("");
                    newPassword.setText("");
                    registerLabel.setText("Luo uusi käyttäjä");
                } 
                else if (!newName.getText().isEmpty() && !newUsername.getText().isEmpty() &&
                        !newPassword.getText().isEmpty()) {
                    registerLabel.setText("Käyttäjänimi on jo varattu.");         
                } else {
                    registerLabel.setText("Täytä kaikki kentät!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BudgetUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        Button cancel = new Button("Peruuta");
        cancel.setOnAction(e -> {
            primaryStage.setScene(loginScene);
            registerLabel.setText("Luo uusi käyttäjä");
        });
        
        createAndCancelBtns.getChildren().addAll(createNewUser, cancel);
        
        registerPane.getChildren().addAll(registerLabel, newUn, newName, newUsern,newUsername, 
                newPw, newPassword, createAndCancelBtns);
        registerPane.setSpacing(10);
        
        createUserScene = new Scene(registerPane, 800, 500);
        
 
        //login scene
        
        topLabel.setText("Kirjaudu sisään");
        
        VBox loginPane = new VBox();
        
        TextField usernameTxt = new TextField();
        Label usern = new Label("Käyttäjätunnus: ");
        PasswordField passwordTxt = new PasswordField();
        Label pwf = new Label("Salasana: ");
        
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
                    userInfo.setText("Tervetuloa "+budgetService.getLoggedUser().getName()+"! ");
                    if (budgetService.findUsersBudget()) {
                        budgetPane.getChildren().clear();
                        budgetPane.getChildren().addAll(logoutAndInfo, budgetLabel, budgetLines);
                    } else {
                        budgetPane.getChildren().clear();
                        budgetPane.getChildren().addAll(logoutAndInfo, budgetLabel, budgetLines, newBudget);
                    }
                    redrawBudgetLines();
                } else {
                    topLabel.setText("Anna oikea käyttäjätunnus ja salasana tai luo uusi käyttäjä ");
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
        buttonPane.setSpacing(10);

        loginPane.getChildren().addAll(topLabel, usern, usernameTxt, pwf, passwordTxt, buttonPane);
        loginPane.setSpacing(10);
        
        loginScene = new Scene(loginPane, 800, 500);
        
        primaryStage.setScene(loginScene);
        
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
