package uet.oop.bomberman.gui;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Utils;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.items.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScene {
  private static final int maxItem = 7;
  static GridPane menubar;
  static Canvas leftSide;
  static Canvas heartSide;
  static GraphicsContext gc;
  static GraphicsContext gcLeftSide;
  static GraphicsContext gcHeartSide;
  static Label timer;
  static Label score;
  static Label remainingBombs;
  private static List<Image> listItem;
  private static int currentTime;
  private static int currentHeart;

  public static void setGraphicsContext(GraphicsContext _gc) {
    gc = _gc;
  }

  public static void drawEntity(List<Entity> entities) {
    for (Entity entity: entities) {
      if (entity.getIsActve()) {
        entity.render(gc);
      }
    }
  }

  public static void drawMenubar() {
    currentTime = 60;
    currentHeart = 0;
    menubar = new GridPane();
    leftSide = new Canvas(32 * maxItem * Utils.SCALE_MAP, 32 * Utils.SCALE_MAP);
    heartSide = new Canvas(32 * 4 * Utils.SCALE_MAP, 32 * Utils.SCALE_MAP);
    gcLeftSide = leftSide.getGraphicsContext2D();
    gcHeartSide = heartSide.getGraphicsContext2D();
    listItem = new ArrayList<>();
    timer = new Label();
    score = new Label();
    remainingBombs = new Label();


//    listItem.add(new Image(Utils.SRC_BOMBPASS_DISABLED));
    listItem.add(new Image(Utils.SRC_BOMBS_DISABLED, 32, 32, false, false));
    listItem.add(new Image(Utils.SRC_DETONATOR_DISABLED, 32, 32, false, false));
    listItem.add(new Image(Utils.SRC_FLAMES_DISABLED, 32, 32, false, false));
    listItem.add(new Image(Utils.SRC_FLAMEPASS_DISABLED, 32, 32, false, false));
    listItem.add(new Image(Utils.SRC_SPEED_DISABLED, 32, 32, false, false));
    listItem.add(new Image(Utils.SRC_WALLPASS_DISABLED, 32, 32, false, false));


    timer.setStyle("-fx-font-size: 24; -fx-font-family: Minecraft");
    score.setStyle("-fx-font-size: 24; -fx-font-family: Minecraft");
    remainingBombs.setStyle("-fx-font-size: 24; -fx-font-family: Minecraft");

    ColumnConstraints col1 = new ColumnConstraints();
    ColumnConstraints col2 = new ColumnConstraints();
    ColumnConstraints col3 = new ColumnConstraints();
    ColumnConstraints col4 = new ColumnConstraints();
    ColumnConstraints col5 = new ColumnConstraints();
    ColumnConstraints col6 = new ColumnConstraints();

    RowConstraints row = new RowConstraints();
    col1.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 10.0));
    col2.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 6.0));
    col3.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 6.0));
    col4.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 6.0));
    col5.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 6.0));
    col6.setPrefWidth(Math.round(Utils.CANVAS_WIDTH / 7.0));

    Button btnPause = new Button("Pause");
    btnPause.setStyle("-fx-font-family: Minecraft");
    btnPause.setPrefWidth(100);
    btnPause.setOnAction(e -> {
      GameManagement.pause();
    });
    HBox btnWrapper = new HBox(btnPause);
    HBox.setMargin(btnPause, new Insets(8, 10, 10, 80));

    menubar.getRowConstraints().add(row);
    menubar.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

    menubar.add(heartSide, 0, 0);
    menubar.add(leftSide, 1, 0);
    menubar.add(timer, 2, 0);
    menubar.add(score, 3, 0);
    menubar.add(remainingBombs, 4, 0);
    menubar.add(btnWrapper, 5, 0);


    GameManagement.getRoot().getChildren().add(menubar);
  }

  public static void updateMenubar(List<Entity> itemActives, int time, int newScore, int newHeart, int newRemainingBombs) {
    gcLeftSide.clearRect(0, 0, 32 * maxItem, 32 * Utils.SCALE_MAP);
    int bombsItemCount = 0;
    for (Entity entity : itemActives) {
      int index;
      if (entity instanceof BombItem) {
        index = 0;
        ++bombsItemCount;
      } else if (entity instanceof DetonatorItem) {
        index = 1;
      } else if (entity instanceof FlameItem) {
        index = 2;
      } else if (entity instanceof FlamepassItem) {
        index = 3;
      } else if (entity instanceof SpeedItem) {
        index = 4;
      } else {
        index = 5;
      }
      listItem.set(index, entity.getImg());
    }

    if (bombsItemCount == 2) {
      listItem.set(0, new Image(Utils.SRC_BOMBS_TWICE, 32, 32, false, false));
    }

    for (int i = 0; i < 6; i++) {
      gcLeftSide.drawImage(listItem.get(i), i * 32, 4);
    }


    if (currentTime + time != 60)
    {
      timer.setText(String.format("Time left: %d", currentTime));
      --currentTime;
    }

    score.setText(String.format("Score: %d", newScore));
    remainingBombs.setText(String.format("Bombs: %d", newRemainingBombs));

    if (newHeart != currentHeart) {
      gcHeartSide.clearRect(0, 0, 32 * 4, 32 * Utils.SCALE_MAP);
      for (int i = 0; i < newHeart  ; i++) {
        gcHeartSide.drawImage(new Image(Utils.SRC_HEART, 32, 32, false, false), i * 32 + 4, 4);
      }
      currentHeart = newHeart;
    }
  }
}
