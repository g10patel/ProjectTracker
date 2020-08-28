import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Task {
    String projectName;
    int order;
    String task;
    String description;

    public Task(String projectName,int order, String task, String description){
        this.projectName = projectName;
        this.order = order;
        this.task = task;
        this.description = description;
    }




}
