package Tiles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PlanTile implements ITileModifier {

    public PlanTile() {

    }

    @Override
    public Rectangle modifyLayout(int xPixels, int yPixels) {
        Rectangle border = new Rectangle(xPixels, yPixels);
        border.setFill(Color.GHOSTWHITE);
        border.setStroke(Color.BLACK);
        return border;
    }

    @Override
    public Text modifyText(String display) {
        Text text = new Text(display);
        text.setFont(Font.font("Calibri", 8));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    @Override
    public void handleClick() {
    }
}