import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Collections;


public class MainWindowController {
    @FXML
    private HBox cards;
    @FXML
    private Button newUserButton;

    public void addTask(VBox project) {
        final Label[] taskLabel = new Label[1];


        if (project.getChildren().get(0).getClass() == Label.class) {
            VBox vbox = getTaskTemplate();

            TextField taskName = (TextField) vbox.getChildren().get(0);
            taskName.setId("task-label");
            VBox.setMargin(vbox, new Insets(3, 2, 3, 2));
            vbox.setId("task");
            int index = project.getChildren().size() - 1;


            HBox buttons = (HBox) project.getChildren().get(1);
            project.getChildren().remove(1);
            project.getChildren().add(1, buttons);
            project.getChildren().add(index, vbox);

            setFocusListener(project, taskLabel, vbox, taskName, index);
            setOnKeyPressedEvent(project, taskLabel, vbox, taskName, index);
        }

    }

    private void setOnKeyPressedEvent(VBox project, Label[] taskLabel, VBox vbox, TextField taskName, int index) {
        taskName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                taskValidation(vbox, project, index, taskLabel, taskName);
            }
        });
    }

    private void setFocusListener(VBox project, Label[] taskLabel, VBox vbox, TextField taskName, int index) {
        taskName.focusedProperty().addListener((arg0, old, newValue) -> {
            if (!newValue) {
                if (vbox.getChildren().get(0).getClass() == TextField.class) {
                    taskValidation(vbox, project, index, taskLabel, taskName);
                }
            }

        });
    }

    private void setListener(TextField taskName) {

    }

    private int getNodeIndex(Node node) {
        Pane parent = (Pane)node.getParent();
        ObservableList<Node> children = parent.getChildren();
        int count = 0;
        for (Node i : children)
        {
            if (i == node) {
                return count;
            }
            count++;
        }
        return -1;

    }



    private void taskValidation(VBox vbox, VBox project, int index, Label[] taskLabel, TextField taskName) {
        TextField textField = (TextField) vbox.getChildren().get(0);
        if (textField.getText().trim().isEmpty()) {
            if (project.getChildren().size() > 3)
                project.getChildren().remove(index);

        } else {

            taskLabel[0] = textFieldToLabel(vbox, "taskLabel");
            taskLabel[0].setOnMouseClicked(e -> {
                vbox.getChildren().remove(taskLabel[0]);
                vbox.getChildren().add(taskName);
                taskName.toBack();
            });
        }
    }

    public void addProject() {
        VBox newProject = new VBox();
        newProject.setAlignment(Pos.TOP_CENTER);
        newProject.setId("project");
        TextField projectName = new TextField();
        final Label[] projectLabel = new Label[1];
        projectLabel[0] = new Label("");
        projectName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (projectName.getText().trim().isEmpty()) {
                    cards.getChildren().remove(newProject);
                } else {
                    projectLabel[0] = textFieldToLabel(newProject, "projectName");

                }
            }
            projectLabel[0].setOnMouseClicked(e -> {
                if (newProject.getChildren().size() > 2) {
                    newProject.getChildren().remove(projectLabel[0]);
                    newProject.getChildren().add(projectName);
                    projectName.toBack();
                }

            });
        });


        Button left = new Button();
        setButtonGraphic(left, new Image("img/left-arrow.png"), 20);
        Button right = new Button();
        setButtonGraphic(right, new Image("img/right-arrow.png"), 20);
        HBox move = new HBox();
        Region buttonSpacer = new Region();
        HBox.setHgrow(buttonSpacer, Priority.ALWAYS);


        moveProjectLeft(newProject, left);

        moveProjectRight(newProject, right);
        HBox.setMargin(right, new Insets(0, 5, 0, 0));
        HBox.setMargin(left, new Insets(0, 0, 0, 5));
        buttonSpacer.setMinWidth(50);
        buttonSpacer.setMinHeight(Region.USE_COMPUTED_SIZE);

        move.getChildren().addAll(left, buttonSpacer, right);


        Image img = new Image("img/add.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);

        Button addTaskButton = new Button();
        addTaskButton.setId("button");
        addTaskButton.setGraphic(view);
        addTaskButton.setOnAction(e -> addTask(newProject));

        projectName.setId("projectName");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        projectName.setPromptText("Project Name");
        newProject.getChildren().addAll(projectName, move, addTaskButton);
        cards.getChildren().add(newProject);
        fadeTransition.play();
        projectName.requestFocus();

        projectName.focusedProperty().addListener((arg0, old, newValue) -> {
            if (!newValue) {

                if (projectName.getText().trim().isEmpty()) {
                    FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(.05), newProject);
                    fadeTransition1.setFromValue(1.0);
                    fadeTransition1.setToValue(0.0);
                    cards.getChildren().remove(newProject);
                }
            }
        });
    }

    private void setButtonGraphic(Button button, Image image, int height) {
        ImageView view = new ImageView(image);
        view.setFitHeight(height);
        view.setPreserveRatio(true);
        button.setGraphic(view);
        button.setId("button");
    }

    private void moveProjectLeft(VBox newProject, Button left) {
        left.setOnAction(e ->
        {
            ObservableList<Node> arr = FXCollections.observableArrayList(cards.getChildren());
            if (arr.indexOf(newProject) != 0) {
                Collections.swap(arr, arr.indexOf(newProject), arr.indexOf(newProject) - 1);
                cards.getChildren().clear();
                cards.getChildren().addAll(arr);

            }
        });
    }

    private void moveProjectRight(VBox newProject, Button right) {
        right.setOnAction(e -> {
            ObservableList<Node> arr = FXCollections.observableArrayList(cards.getChildren());
            if (arr.indexOf(newProject) != arr.size() - 1) {
                Collections.swap(arr, arr.indexOf(newProject), arr.indexOf(newProject) + 1);
                cards.getChildren().clear();
                cards.getChildren().addAll(arr);
            }
        });
    }


    public void newUser() throws IOException {

        LoginWindowController.showCreateUser();
    }

    public void login() throws IOException {

        LoginWindowController.showLogin();

    }

    public void loadUser(String email) {
        Database.getUserData(email);
        setupUser(email);
    }

    private void setupUser(String email) {

        HBox hbox = (HBox) newUserButton.getParent();
        hbox.getChildren().remove(newUserButton);
        hbox.getChildren().add(2, new Label(email));
    }

    private VBox getTaskTemplate() {
        VBox vbox = new VBox();
        TextField task = new TextField();
        task.setId("task-label");

        Image img = new Image("img/text.png");
        ImageView view = new ImageView(img);

        view.setFitHeight(10);
        view.setPreserveRatio(true);

        vbox.getChildren().addAll(task, view);

        return vbox;
    }

    private Label textFieldToLabel(Pane vbox, String id){
        Label taskLabel = new Label(((TextField) (vbox.getChildren().get(0))).getText());
        taskLabel.setWrapText(true);
        taskLabel.setMaxWidth(vbox.getWidth() - 10);
        vbox.getChildren().remove(vbox.getChildren().get(0));
        vbox.getChildren().add(taskLabel);
        taskLabel.setId(id);
        taskLabel.toBack();
        return taskLabel;
    }
}
