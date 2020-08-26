import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class Controller {

    public HBox cards;
    public void addTask(ActionEvent actionEvent) {

    }

    public void addProject(ActionEvent actionEvent) {
        VBox newProject = new VBox();
        newProject.setId("project");
        TextField projectName = new TextField();

        projectName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    if (projectName.getText().trim().isEmpty()){
                        cards.getChildren().remove(newProject);
                    }
                }
            }
        });
        projectName.setId("projectName");

        projectName.setPromptText("Project Name");
        newProject.getChildren().add(projectName);
        cards.getChildren().add(newProject);
    }
}
