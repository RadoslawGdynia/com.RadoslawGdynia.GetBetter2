package Models.CalendarModel.Tasks;

import Models.CalendarModel.Days.Day;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subtask extends Task {
    private final WorkTask assignedTo;
    public static final Logger log = LoggerFactory.getLogger(Subtask.class);

    public Subtask(Day day, String name, String details, WorkTask assignedTo) {

        this.setAssignedToDay(null);
        this.setName(name);
        this.setDetails(details);
        this.assignedTo = assignedTo;
        this.setPointValue(0);
        assignedTo.addSubtask(this);
    }
}
