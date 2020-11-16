package Controllers;

import Day.Day;
import Task.TrivialTask;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class AddTaskDialogController {
    public static final Logger log = LoggerFactory.getLogger(AddTaskDialogController.class);

    Day day = GetBetterCalendarController.getInstance().getSelectedDay();
    @FXML
    TextField taskName;
    @FXML
    TextArea details;
    @FXML
    DatePicker deadlineDate;

    public TrivialTask createTask(){
        String name = taskName.getText().trim();
        String description = details.getText().trim();
        LocalDate deadline = deadlineDate.getValue();
        return new TrivialTask(day, name,description);
    }
}
