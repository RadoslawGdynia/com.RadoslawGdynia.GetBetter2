package Day;

import CalendarControl.GetBetterCalendar;
import Task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;

public class Day {
    private final LocalDate date;
    private ObservableList<Task> todayTasks;
    private int dayID;
    private static int GlobalID= 1;
    private int taskNumber;

    public Day(LocalDate date) {
        this.date = date;
        this.dayID=GlobalID;
        GlobalID++;
        todayTasks = FXCollections.observableArrayList();
        try{
            GetBetterCalendar.getInstance().addDay(this);
        } catch (IOException e) {
            System.out.println("Operation of addition of day " + this.getDate() + " to calendar have failed");
        }
    }
    public Day(int id, LocalDate date, int taskNumber) {
        this.date = date;
        this.dayID=id;
        this.taskNumber = taskNumber;
        GlobalID=id+1;
        todayTasks = FXCollections.observableArrayList();
        try{
            GetBetterCalendar.getInstance().addDay(this);
        } catch (IOException e) {
            System.out.println("Operation of addition of day " + this.getDate() + " to calendar have failed");
        }

    }


    public LocalDate getDate() {
        return date;
    }

    public int getDayID() {
        return dayID;
    }


    public ObservableList<Task> getTodayTasks() {
        return todayTasks;
    }
    public void addTask(Task newTask) {
        if(findTask(newTask)) {
            System.out.println("Task " + newTask.getTaskName() + " is already in this day. Operation rejected.");
        } else
            todayTasks.add(newTask);
            System.out.println("Task " + newTask.getTaskName() + " was added to: " + this.getDate());

        }
    public boolean removeTask(Task taskToDelete) {
        if(todayTasks.isEmpty()){
            System.out.println("Task list for the day " + this.getDate() + " is empty, deletion impossible. Rejected.");
            return false;
        } else {
            if (findTask(taskToDelete)) {
                todayTasks.remove(taskToDelete);
                System.out.println("Task " + taskToDelete.getTaskName() + " was deleted");
                return true;
            } else {
                System.out.println("Task " + taskToDelete.getTaskName() + " is not on the task list. Rejected.");
                return false;
            }
        }
    }
    private boolean findTask(Task szukane) {
        for(Task task : todayTasks) {
            if (szukane.equals(task)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append(this.getDate());
            sb.append(" number of tasks: ");
            sb.append(this.getTodayTasks().size());

            return sb.toString();
    }
}
