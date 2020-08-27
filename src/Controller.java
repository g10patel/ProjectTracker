import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.Collections;


public class Controller {

    public HBox cards;
    public void addTask(ActionEvent actionEvent) {

    }

    public void addProject(ActionEvent actionEvent) {
        VBox newProject = new VBox();
        final Button[] left = {null};
        newProject.setId("project");
        TextField projectName = new TextField();
        final Label[] projectLabel = new Label[1];
        projectLabel[0]= new Label("");
        projectName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER ){
                    if (projectName.getText().trim().isEmpty()){
                        cards.getChildren().remove(newProject);
                    }
                    else{
                        projectLabel[0] = new Label(projectName.getText());
                        newProject.getChildren().remove(projectName);
                        newProject.getChildren().add(projectLabel[0]);
                        projectLabel[0].setId("projectName");
                        projectLabel[0].toBack();


                    }
                }
                projectLabel[0].setOnMouseClicked(e -> {
                    String original = projectLabel[0].getText();
                    newProject.getChildren().remove(projectLabel[0]);
                    newProject.getChildren().add(projectName);
                    projectName.toBack();
                    return;
                });

            }


        });

        left[0] = new Button("left");
        Button right = new Button("right");
        HBox move = new HBox();

        left[0].setOnAction(e ->
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

        move.getChildren().addAll(left[0],right);


        projectName.setId("projectName");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        projectName.setPromptText("Project Name");
        newProject.getChildren().addAll(projectName,move);
        cards.getChildren().add(newProject);
        fadeTransition.play();
        projectName.requestFocus();
    }

    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return true;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }
}
