package Controllers;

import CalendarControl.GetBetterCalendar;
import Day.Day;
import Task.Task;
import Tiles.CalendarTile;
import Tiles.PlanTile;
import Tiles.Tile;
import Tiles.TimeTile;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class GetBetterCalendarController {


    //    //GENERAL:
    private static GetBetterCalendarController instance;
    private ArrayList<InvalidationListener> listeners = new ArrayList<>();
    private int currentMonthNum;
    private int currentYearNum;
    private int currentDayNum;
    private Day selectedDay = GetBetterCalendar.getDays().get(GetBetterCalendar.getDayIndex(LocalDate.now()));

    //CALENDAR

    @FXML
    private Label monthName;
    @FXML
    private Label yearNumber;
    @FXML
    private Pane daysTilePane;


    //DAY DETAILS
    @FXML
    private TabPane detailsTabPane;



    //Day Plan
    @FXML
    private Label showDay;
    @FXML
    private ComboBox<Task> taskSelectionCombo;
    @FXML
    private Pane TimePane;
    @FXML
    private Pane PlanningPane;

    ProgressBar completion = new ProgressBar();



    @FXML
    private TreeTableView<Task> subtasksTreeTable;
    @FXML
    private TreeTableColumn<Task, String> subtaskNameColumn;
    @FXML
    private TreeTableColumn<Task, String> subtaskDeadlineColumn;
    @FXML
    private TreeTableColumn<Task, ProgressBar> subtaskProgressColumn;


    @FXML
    private TableView<Task> TVTasksTable;
    @FXML
    private TableColumn<Task, String> TVTaskName;
    @FXML
    private TableColumn<Task, String> TVTaskDeadline;
    @FXML
    private TableColumn<Task, Integer> TVNumberOfSubtasks;

    @FXML
    private Button AddTaskButton;
    @FXML
    private Button EditTaskButton;
    @FXML
    private Button DeleteTaskButton;



    //GENERAL METHODS:
    public GetBetterCalendarController(){
        instance = this;
    }

    public static GetBetterCalendarController getInstance(){
        return instance;
    }

    public Day getSelectedDay() {
        return selectedDay;
    }

    public int getCurrentMonthNum() {
        return currentMonthNum;
    }

    public int getCurrentYearNum() {
        return currentYearNum;
    }

    public Pane getTimePane() {
        return TimePane;
    }
    public Label getShowDay(){
        return showDay;
    }

    public Pane getPlanningPane() {
        return PlanningPane;
    }

    public TabPane getDetailsTabPane() {
        return detailsTabPane;
    }

    public void setSelectedDay(Day selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void initialize() {
        detailsTabPane.setDisable(true);
        currentMonthNum = selectedDay.getDate().getMonthValue();
        currentYearNum = selectedDay.getDate().getYear();
        currentDayNum = selectedDay.getDate().getDayOfMonth();
        configureCalendarPane();
    }
    //CALENDAR AREA METHODS:
    //=====================

    private void configureCalendarPane() {

        monthName.setText(LocalDate.of(currentYearNum, currentMonthNum, currentDayNum).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        yearNumber.setText(String.valueOf(currentYearNum));

        int firstDayOfMonth = LocalDate.of(currentYearNum, currentMonthNum, currentDayNum - (currentDayNum - 1)).getDayOfWeek().getValue();
        int numberOfDaysInCurrentMonth = LocalDate.of(currentYearNum, currentMonthNum, currentDayNum).getMonth().maxLength();

        int NUM_OF_TILES = 42;
        int size = 90;

        for (int i = 1; i <= NUM_OF_TILES; i++) {

            String idString = "CalendarTile" + i;
            Tile calendarTile;
            int dayNumber = (i - (firstDayOfMonth - 1));
            String display = dayNumber+"";
            if ((i >= firstDayOfMonth) && (i < (numberOfDaysInCurrentMonth + firstDayOfMonth))) {

                calendarTile = new Tile(daysTilePane, idString, display, size, size, new CalendarTile(dayNumber));

            } else {
                calendarTile = new Tile(daysTilePane, idString, display, size, size, new CalendarTile(1));
                calendarTile.setDisable(true);
                calendarTile.setVisible(false);
            }

        }
    }

    public void handleMonthBack() {
        daysTilePane.getChildren().clear();
        if (currentMonthNum == 1) {
            currentMonthNum = 12;
            currentYearNum--;
        } else {
            currentMonthNum--;
        }
        detailsTabPane.setDisable(true);
        configureCalendarPane();
        System.out.println("Currently chosen month is: " + LocalDate.of(currentYearNum, currentMonthNum, currentDayNum).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
    }

    public void handleMonthForward() {
        daysTilePane.getChildren().clear();
        if (currentMonthNum == 12) {
            currentMonthNum = 1;
            currentYearNum++;
        } else {
            currentMonthNum++;
        }
        detailsTabPane.setDisable(true);
        configureCalendarPane();
        System.out.println("Currently chosen month is: " + LocalDate.of(currentYearNum, currentMonthNum, currentDayNum).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
    }

    //DAY DETAILS METHODS:
    //===================

    private void noTaskSelected(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missing information!");
        alert.setHeaderText("No task was chosen. Chosen action requires a selected task to be performed.");
        alert.setContentText("Please select one of the tasks from the list and proceed.");
        alert.showAndWait();
    }


    // A. DAY PLAN METHODS:
    public void configureTimeTiles() {
        final int TILES_NUMBER = 18;
        int hour1 = 6;
        int hour2 = 7;
        StringBuilder text = new StringBuilder();

        for(int i =1; i<=TILES_NUMBER; i++){
            text.append(hour1);
            text.append(":00");
            text.append("-");
            text.append(hour2);
            text.append(":");
            text.append(":00");

            Tile timeTile = new Tile(TimePane, i+"", text.toString(), 100, (int)(TimePane.getHeight()/TILES_NUMBER), new TimeTile());

            hour1++;
            hour2++;

            text.delete(0, text.length());
        }

    }
    public void configurePlanTiles() {
        final int TILES_NUMBER = 72;
        for(int i =1; i<=TILES_NUMBER; i++) {
            Tile planTile = new Tile(PlanningPane, i+"","Plan", 200, 9, new PlanTile());
        }

    }



    // B. TODAYS TASK METHODS:


    public void configureTasksTable() {
        TVTasksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TVTaskName.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
        TVTaskDeadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));
        TVNumberOfSubtasks.setCellValueFactory(new PropertyValueFactory<Task, Integer>("subtaskQuantity"));

        TVTasksTable.setItems(selectedDay.getTodayTasks());
    }

    public void handleTaskSelection(MouseEvent mouseEvent) {
        Task cTask = TVTasksTable.getSelectionModel().getSelectedItem();
        System.out.println("Currently selected task: " + cTask.getTaskName());
        //  subtasksTreeTable.setDisable(false);

        AddTaskButton.setDisable(false);
        EditTaskButton.setDisable(false);
        DeleteTaskButton.setDisable(false);

    }



    public void configureSubtasksTable() {
        TreeItem<Task> root = new TreeItem<>();

        subtaskNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String> taskStringCellDataFeatures) {
                return new SimpleStringProperty(taskStringCellDataFeatures.getValue().getValue().getTaskName());
            }


        });

//        subtaskDeadlineColumn.setCellValueFactory(taskStringCellDataFeatures -> {
//            if (taskStringCellDataFeatures == null) {
//                return null;
//            } else {
//                return new SimpleStringProperty(taskStringCellDataFeatures.getValue().getValue().getDeadline().toString());
//            }
//        });
//
//        for(Task task : selectedDay.getTodayTasks()) {
//           TreeItem<Task> listableTask = new TreeItem<>(task);
//
//            for (Task subtask : task.getSubtasks()) {
//                TreeItem<Task> listableSubtask = new TreeItem<>(subtask);
//                listableTask.getChildren().add(listableSubtask);
//            }
//            root.getChildren().add(listableTask);
//        }
//        subtasksTreeTable.setRoot(root);



    }

    public void handleAddTaskClick() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Addition of task to the day: " + selectedDay.getDate());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddTaskDialog.fxml"));


        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddTaskDialogController newTaskToAdd = fxmlLoader.getController();
            Task toAdd = newTaskToAdd.createTask();
            selectedDay.addTask(toAdd);

        }


    }
    public void handleAddSubtaskClick(ActionEvent event) {
//        Task task = TVTasksTable.getSelectionModel().getSelectedItem();
//        if (task == null) {
//            noTaskSelected();
//        } else {
//            Dialog<ButtonType> dialog = new Dialog<>();
//            dialog.setTitle("Addition of subtask to task: " + task.getTaskName());
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("AddTaskDialog.fxml"));
//
//            try {
//                dialog.getDialogPane().setContent(fxmlLoader.load());
//            } catch (IOException e) {
//                System.out.println("Could not load the dialog");
//                e.printStackTrace();
//                return;
//            }
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//
//            Optional<ButtonType> result = dialog.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                GetBetter.Kalendarz.Controllers.AddTaskDialogController newTaskToAdd = fxmlLoader.getController();
//                Task toAdd = newTaskToAdd.createTask();
//                task.addSubtask(toAdd);
//            }
//        }
    }

    public void handleEditTaskClick() {
//        Task task = TVTasksTable.getSelectionModel().getSelectedItem();
//        if(task == null){
//            noTaskSelected();
//            return;
//        } else {
//            Controllers.EditTaskDialogController.setSelectedTask(task);
//
//            Dialog<ButtonType> dialog = new Dialog<>();
//            dialog.setTitle("Editing task: " + task.getTaskName());
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("EditTaskDialog.fxml"));
//
//            try {
//                dialog.getDialogPane().setContent(fxmlLoader.load());
//            } catch (IOException e) {
//                System.out.println("Could not load the dialog");
//                e.printStackTrace();
//                return;
//            }
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//
//            Controllers.EditTaskDialogController ETDialogController = fxmlLoader.getController();
//
//            Optional<ButtonType> result = dialog.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.APPLY) {
//               ETDialogController.handleApplyChangesButton();
//
//            }
//        }
    }

    public void handleDeleteTaskClick(ActionEvent event) {
//
//        Task task = TVTasksTable.getSelectionModel().getSelectedItem();
//        deleteTask(task);
    }

    public void deleteTask(Task t) {
//        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
//        a.setTitle("Task deletion");
//        a.setHeaderText("You intend to delete task: " + t.getTaskName());
//        a.setContentText("Are you sure you want to proceed? This operation is irreversible and you put this task in for a good reason");
//        Optional<ButtonType> result = a.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            selectedDay.removeTask(t);
//            //visibleTasks.removeVisibleTask(t);
//        }
    }






}


