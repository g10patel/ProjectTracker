import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.Collections;


public class MainWindowController {
    @FXML
    private HBox cards;
    @FXML
    private ScrollPane scrollbar;


    public void addTask(ActionEvent actionEvent) {

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
                    String original = projectLabel[0].getText();
                    newProject.getChildren().remove(projectLabel[0]);
                    newProject.getChildren().add(projectName);
                    projectName.toBack();
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
        view.setFitHeight(50);
        view.setPreserveRatio(true);

        Button addTaskButton = new Button();
        addTaskButton.setId("addTaskButton");
        addTaskButton.setGraphic(view);

        projectName.setId("projectName");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.05), newProject);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        projectName.setPromptText("Project Name");
        newProject.getChildren().addAll(projectName,move, addTaskButton);
        cards.getChildren().add(newProject);
        fadeTransition.play();
        projectName.requestFocus();

    }
    public void newUser() throws IOException {
        LoginWindowController.showCreateUser();
    }

    public void login() throws IOException {
        LoginWindowController.showLogin();
    }

    public static void loadUser(String email) {

    }


}
