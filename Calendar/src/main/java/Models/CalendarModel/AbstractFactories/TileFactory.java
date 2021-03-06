package Models.CalendarModel.AbstractFactories;

import Models.CalendarModel.CalendarModel;
import Models.CalendarModel.Tiles.CalendarTile;
import Models.CalendarModel.Tiles.PlanTile;
import Models.CalendarModel.Tiles.Tile;
import Models.CalendarModel.Tiles.TimeTile;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class TileFactory {


    public static void createSetOfTiles(String typeOfTile, Pane rootPane, int numberOfTiles) throws IllegalArgumentException {

        //========Creation of tiles for days of Month========
        if (typeOfTile.equals(CalendarTile.class.getSimpleName())) {
            final int observedYearNum = CalendarModel.getInstance().getCurrentYearNum();
            final int observedMonthNum = CalendarModel.getInstance().getCurrentMonthNum();


            int firstDayOfMonth = LocalDate.of(observedYearNum, observedMonthNum, 1).getDayOfWeek().getValue();
            int numberOfDaysInCurrentMonth = LocalDate.of(observedYearNum, observedMonthNum, 1).getMonth().maxLength();
            if (observedMonthNum == 2) {
                numberOfDaysInCurrentMonth = ((observedYearNum % 4 == 0 && observedYearNum % 100 != 0) || (observedYearNum % 400 == 0)) ? 29 : 28;
            }


            for (int i = 1; i <= numberOfTiles; i++) {
                int dayNumber = (i - (firstDayOfMonth - 1));
                String display = dayNumber + "";

                if ((i >= firstDayOfMonth) && (i < (numberOfDaysInCurrentMonth + firstDayOfMonth))) {

                    Tile calendarTile = new Tile(rootPane, i, display, 90, 90, new CalendarTile(dayNumber));

                } else {
                    Tile calendarTile = new Tile(rootPane, i, display, 90, 90, new CalendarTile(1));
                    calendarTile.setDisable(true);
                    calendarTile.setVisible(false);
                }
            }

        }

        //========Creation of tiles for hours in current day========
        else if (typeOfTile.equals(TimeTile.class.getSimpleName())) {
            int hour1 = 6;
            int hour2 = 7;
            StringBuilder text = new StringBuilder();

            for (int i = 1; i <= numberOfTiles; i++) {
                text.append(hour1);
                text.append(":00");
                text.append("-");
                text.append(hour2);
                text.append(":");
                text.append(":00");

                Tile timeTile = new Tile(rootPane, i, text.toString(), 100, (int) (rootPane.getHeight() / numberOfTiles), new TimeTile());
                hour1++;
                hour2++;

                text.delete(0, text.length());
            }
        }

        //========Creation of tiles for tasks to be assigned========
        else if (typeOfTile.equals(PlanTile.class.getSimpleName())) {
            for (int i = 1; i <= numberOfTiles; i++) {
                Tile planTile = new Tile(rootPane, i, "Plan", 200, 9, new PlanTile());
            }

        } else {
            throw new IllegalArgumentException("Wrong type of tile selected: " + typeOfTile);
        }
    }
}
