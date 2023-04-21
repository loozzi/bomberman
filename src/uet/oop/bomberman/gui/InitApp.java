package uet.oop.bomberman.gui;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.ButtonImage;
import uet.oop.bomberman.common.Utils;

import java.awt.*;
import java.util.ArrayList;

public class InitApp {
  static Stage primaryStage = BombermanGame.primaryState;
  private static Scene scene;

  private static Group root;
  private static GraphicsContext gc;
  private static Canvas canvas;

  public static final int CANVAS_WIDTH = Utils.CANVAS_WIDTH;
  public static final int CANVAS_HEIGHT = Utils.CANVAS_HEIGHT;

  public static Scene getScene() {
    return scene;
  }

  public static void removeLayer(Node layer) {
    root.getChildren().remove(layer);
  }

  public static void init() {
    root = new Group();
    scene = new Scene(root);
    canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

    // Logo in screen
    Image startScreenLogo = new Image(Utils.SRC_START_SCREEN_LOGO);
    ImageView startScreenLogoView = new ImageView(startScreenLogo);
    startScreenLogoView.setFitWidth(600);
    startScreenLogoView.setFitHeight(320);
    startScreenLogoView.setLayoutX(340);
    startScreenLogoView.setLayoutY(40);

    // Create Start button
    Button startBtn = (new ButtonImage(Utils.SRC_START_BTN, 460, 400, 240, 80, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.init("Level1.txt", 3, 0, new ArrayList<>());
      primaryStage.setScene(GameManagement.getScene());
    })).getButton();

    // Create Setting button
    Button settingBtn = (new ButtonImage(Utils.SRC_SETTING_BTN, 740, 400, 80, 80, Utils.CSS_BUTTON_IMAGE, e -> {
//      System.out.println("Setting event");
      root.getChildren().add(GameScreen.gameSetting);
    })).getButton();


    // Create Exit button
    Button exitBtn = (new ButtonImage(Utils.SRC_QUIT_BTN, 460, 520, 360, 80, Utils.CSS_BUTTON_IMAGE, e -> {
      primaryStage.close();
    })).getButton();

    root.getChildren().setAll(
            canvas,
            startBtn,
            settingBtn,
            exitBtn,
            startScreenLogoView
    );

    primaryStage.setScene(scene);
  }
}