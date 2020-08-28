import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordConf;
    @FXML
    private TextField name;
    @FXML
    private Label userCheck;


    public static void showLogin() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        Parent root = FXMLLoader.load(LoginWindowController.class.getResource("LoginWindow.fxml"));
        Scene scene = new Scene(root, 500,400);
        scene.getStylesheets().add(LoginWindowController.class.getResource("LoginWindow.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }

    public static void showCreateUser() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        Parent root = FXMLLoader.load(LoginWindowController.class.getResource("CreateUser.fxml"));
        Scene scene = new Scene(root, 600,400);
        scene.getStylesheets().add(LoginWindowController.class.getResource("LoginWindow.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }

    public void addUser() throws Exception {
        if (!(password.getText().equals(passwordConf.getText()))){
            password.setId("password-error");
            passwordConf.setId("password-error");
            userCheck.setText("Passwords Do Not Match");
            System.out.println(password.getText());
            System.out.println(passwordConf.getText());
        }

        else{
            if (Database.addUser(email.getText(), password.getText(), name.getText())){
                Stage stage = (Stage)userCheck.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MainWindow.fxml"));
                Parent root = (Parent) loader.load();
                MainWindowController controller = loader.getController();
                controller.loadUser(email.getText());
                Scene scene = new Scene(root, 1300,600);
                Stage window = new Stage();
                scene.getStylesheets().add(LoginWindowController.class.getResource("MainWindow.css").toExternalForm());
                window.setScene(scene);
                window.show();

            }
            else {
                userCheck.setText("User already exists");
            }
        }
    }


}

