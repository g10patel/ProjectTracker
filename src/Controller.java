import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {

    public HBox cards;
    public void addTask(ActionEvent actionEvent) {

    }

    public void addProject(ActionEvent actionEvent) {
        VBox newProject = new VBox(new Label("Test"));
        newProject.setId("project");
        cards.getChildren().add(newProject);
    }
}
