import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Collections;


public class MainWindowController {
    @FXML
    private HBox cards;
    @FXML
    private Button newUserButton;
    @FXML
    private Button loginButton;

    public void addTask(VBox project) {
        final Label[] taskLabel = new Label[1];

        if(project.getChildren().get(0).getClass() == Label.class) {
            VBox vbox = getTaskTemplate();
            TextField taskName = (TextField)vbox.getChildren().get(0);
            taskName.setId("task-label");
            VBox.setMargin(vbox, new Insets(3, 2, 3, 2));
            vbox.setId("task");
            int index = project.getChildren().size() - 1;



            HBox buttons = (HBox) project.getChildren().get(1);
            project.getChildren().remove(1);
            project.getChildren().add(1, buttons);
            project.getChildren().add(index, vbox);
            vbox.getChildren().get(0).requestFocus();

            vbox.getChildren().get(0).focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean old, Boolean newValue) {
                    if(!newValue)
                    {
                        if(project.getChildren().get(0).getClass() == TextField.class) {
                            TextField textField = (TextField) vbox.getChildren().get(0);
                            if (textField.getText().trim().isEmpty()) {
                                project.getChildren().remove(index);
                            }
                        }
                    }
                }
            });

            vbox.getChildren().get(0).setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode() == KeyCode.ENTER ){
                        if (((TextField)(vbox.getChildren().get(0))).getText().trim().isEmpty()){
                            project.getChildren().remove(index);

                        }
                        else{
                            taskLabel[0] = new Label(((TextField)(vbox.getChildren().get(0))).getText());
                            vbox.getChildren().remove(vbox.getChildren().get(0));
                            vbox.getChildren().add(taskLabel[0]);
                            taskLabel[0].setId("taskName");
                            taskLabel[0].toBack();

                        }

                    }
                    if(taskLabel[0] != null) {
                        taskLabel[0].setOnMouseClicked(e -> {
                            String original = taskLabel[0].getText();
                            vbox.getChildren().remove(taskLabel[0]);
                            vbox.getChildren().add(taskName);
                            taskName.toBack();


                            return;
                        });
                    }

                }
            });


        }


    }

    public void addProject(ActionEvent actionEvent) {
        VBox newProject = new VBox();
        newProject.setAlignment(Pos.TOP_CENTER);
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
                    if (newProject.getChildren().size() > 2) {
                        String original = projectLabel[0].getText();
                        newProject.getChildren().remove(projectLabel[0]);
                        newProject.getChildren().add(projectName);
                        projectName.toBack();
                    }
                    return;
                });
            }
        });


        Button left = new Button("left");
        Button right = new Button("right");
        HBox move = new HBox();
        Region buttonSpacer = new Region();
        HBox.setHgrow(buttonSpacer, Priority.ALWAYS);


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
        HBox.setMargin(right, new Insets(0,5,0,0));
        HBox.setMargin(left, new Insets(0,0,0,5));
        buttonSpacer.setMinWidth(50);
        buttonSpacer.setMinHeight(Region.USE_COMPUTED_SIZE);

        move.getChildren().addAll(left, buttonSpacer, right);


        Image img = new Image("img/add.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);

        Button addTaskButton = new Button();
        addTaskButton.setId("addTaskButton");
        addTaskButton.setGraphic(view);
        addTaskButton.setOnAction(e -> {
            addTask(newProject);
        });

        projectName.setId("projectName");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        projectName.setPromptText("Project Name");
        newProject.getChildren().addAll(projectName,move, addTaskButton);
        cards.getChildren().add(newProject);
        fadeTransition.play();
        projectName.requestFocus();

        projectName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean old, Boolean newValue) {
                if(!newValue)
                {

                    if (projectName.getText().trim().isEmpty()) {
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
                        fadeTransition.setFromValue(1.0);
                        fadeTransition.setToValue(0.0);
                        cards.getChildren().remove(newProject);
                    }
                }
            }
        });
    }


    public void newUser() throws IOException {

        LoginWindowController.showCreateUser((Stage)newUserButton.getScene().getWindow());
    }

    public void login() throws IOException {

        LoginWindowController.showLogin();

    }

    public void loadUser(String email) {
        Database.getUserData(email);
        setupUser(email);
    }

    private void setupUser(String email) {

        HBox hbox = (HBox)newUserButton.getParent();
        hbox.getChildren().remove(newUserButton);
        hbox.getChildren().add(2, new Label(email));
    }

    private  VBox getTaskTemplate(){
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


}
