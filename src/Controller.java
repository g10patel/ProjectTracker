import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Controller {

    public HBox cards;
    public void addTask(ActionEvent actionEvent) {

    }

    public void addProject(ActionEvent actionEvent) {
        VBox newProject = new VBox();
        newProject.setId("project");
        TextField projectName = new TextField();
        final Label[] projectLabel = new Label[1];
        projectName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    if (projectName.getText().trim().isEmpty()){
                        cards.getChildren().remove(newProject);
                    }
                    else{
                        projectLabel[0] = new Label(projectName.getText());
                        newProject.getChildren().remove(projectName);
                        newProject.getChildren().add(projectLabel[0]);
                        projectLabel[0].setId("projectName");
                        Button left = new Button("left");
                        Button right = new Button("right");
                        HBox move = new HBox();
                        left.setOnAction(e ->
                        {
                            ObservableList<Node> arr = FXCollections.observableArrayList(cards.getChildren());
                            if(arr.indexOf(newProject) != 0) {
                                Collections.swap(arr, arr.indexOf(newProject), arr.indexOf(newProject) - 1);
                                cards.getChildren().clear();
                                cards.getChildren().addAll(arr);

                            }
                        });

                        right.setOnAction(e -> {
                            ObservableList<Node> arr = FXCollections.observableArrayList(cards.getChildren());
                            if(arr.indexOf(newProject) != arr.size()-1) {
                                Collections.swap(arr, arr.indexOf(newProject), arr.indexOf(newProject) + 1);
                                cards.getChildren().clear();
                                cards.getChildren().addAll(arr);
                            }
                        });

                        move.getChildren().addAll(left,right);
                        newProject.getChildren().add(move);

                    }
                }
            }
        });

        projectName.setId("projectName");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        projectName.setPromptText("Project Name");
        newProject.getChildren().add(projectName);
        cards.getChildren().add(newProject);
        fadeTransition.play();
    }
}
