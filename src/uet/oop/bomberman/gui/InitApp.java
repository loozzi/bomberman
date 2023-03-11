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
import uet.oop.bomberman.common.Utils;

import java.awt.*;

public class InitApp {
  static Stage primaryStage = BombermanGame.primaryState;
  static Scene scene;

  static Group root;
  static GraphicsContext gc;
  static Canvas canvas;

  public static final int CANVAS_WIDTH = Utils.CANVAS_WIDTH;
  public static final int CANVAS_HEIGHT = Utils.CANVAS_HEIGHT;

  public static Scene getScene() {
    return scene;
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
    Image imgStartBtn = new Image(Utils.SRC_START_BTN);
    ImageView imgStartBtnView = new ImageView(imgStartBtn);
    imgStartBtnView.setFitHeight(80);
    imgStartBtnView.setFitWidth(240);
    Button startBtn = new Button();
    startBtn.setGraphic(imgStartBtnView);
    startBtn.setLayoutX(520);
    startBtn.setLayoutY(400);
    startBtn.setPrefSize(80, 80);
    startBtn.setOnAction(e -> {
      System.out.println("Start game");
    });
    startBtn.setOnMouseEntered(new EventHandler< MouseEvent >(){
      public void handle(MouseEvent event) {
        startBtn.setStyle("-fx-cursor: hand;");
      }
    });

    // Create Exit button
    Image imgExitBtn = new Image(Utils.SRC_QUIT_BTN);
    ImageView imgExitBtnView = new ImageView(imgExitBtn);
    imgExitBtnView.setFitHeight(80);
    imgExitBtnView.setFitWidth(360);
    Button exitBtn = new Button();
    exitBtn.setGraphic(imgExitBtnView);
    exitBtn.setLayoutX(460);
    exitBtn.setLayoutY(520);
    exitBtn.setPrefSize(80, 80);
    exitBtn.setOnAction(e -> {
      primaryStage.close();
    });
    exitBtn.setOnMouseEntered(new EventHandler< MouseEvent >(){
      public void handle(MouseEvent event) {
        exitBtn.setStyle("-fx-cursor: hand;");
      }
    });

    root.getChildren().setAll(canvas, startBtn, exitBtn, startScreenLogoView);

    primaryStage.setScene(scene);
  }
}
