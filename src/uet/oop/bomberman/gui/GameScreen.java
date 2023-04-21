package uet.oop.bomberman.gui;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.ButtonImage;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.common.Utils;

public class GameScreen {
  public static StackPane victory;
  public static StackPane gameOver;
  public static StackPane gameMenu;
  public static StackPane gameSetting;

  public static void init() {
    initVictory();
    initGameOver();
    initGameMenu();
    initGameSetting();
  }

  public static void initVictory() {
    victory = new StackPane();
    victory.setPrefSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);

    Group body = new Group();

    Canvas overlay = new Canvas(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);
    GraphicsContext gc = overlay.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);

    ImageView img = new ImageView(new Image(Utils.SRC_GAME_VICTORY));
    img.setLayoutX(217);
    img.setLayoutY(50);

    Button btnChooseLevel = (new ButtonImage(Utils.SRC_TEXT_NEXT_LEVEL, 236, 250, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.handleNextLevel();
    })).getButton();

    Button btnMainMenu = (new ButtonImage(Utils.SRC_TEXT_MAIN_MENU, 236, 300, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.exit();
    })).getButton();

    body.getChildren().addAll(img, btnMainMenu, btnChooseLevel);

    victory.getChildren().addAll(overlay, body);
    overlay.setOpacity(0.6);
    overlay.setOnMouseClicked(e -> {

    });
  }

  public static void initGameOver() {
    gameOver = new StackPane();
    gameOver.setPrefSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);

    Group body = new Group();

    Canvas overlay = new Canvas(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);
    GraphicsContext gc = overlay.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);

    ImageView img = new ImageView(new Image(Utils.SRC_GAME_OVER));
    img.setLayoutX(217);
    img.setLayoutY(50);

    Button btnPlayAgain = (new ButtonImage(Utils.SRC_TEXT_PLAY_AGAIN, 236, 250, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.handleReset();
    })).getButton();

//    Button btnChooseLevel = (new ButtonImage(Utils.SRC_TEXT_CHOOSE_LEVEL, 236, 250, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
//
//    })).getButton();

    Button btnMainMenu = (new ButtonImage(Utils.SRC_TEXT_MAIN_MENU, 236, 300, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.exit();
    })).getButton();

    body.getChildren().addAll(img, btnMainMenu, btnPlayAgain);

    gameOver.getChildren().addAll(overlay, body);
    overlay.setOpacity(0.6);
    overlay.setOnMouseClicked(e -> {

    });
  }

  public static void initGameMenu() {
    gameMenu = new StackPane();
    gameMenu.setPrefSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);

    Group body = new Group();

    Canvas overlay = new Canvas(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);
    GraphicsContext gc = overlay.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);

    ImageView img = new ImageView(new Image(Utils.SRC_GAME_PAUSE));
    img.setLayoutX(217);
    img.setLayoutY(50);

    Button btnResume = (new ButtonImage(Utils.SRC_TEXT_RESUME, 236, 150, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.removeLayer(gameMenu);
      GameManagement.resume();
    })).getButton();

    Button btnRestart = (new ButtonImage(Utils.SRC_TEXT_RESTART, 236, 200, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.handleReset();
    })).getButton();

    Button btnSetting = (new ButtonImage(Utils.SRC_TEXT_SETTING, 236, 250, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> GameManagement.addLayer(GameScreen.gameSetting))).getButton();

    Button btnMainMenu = (new ButtonImage(Utils.SRC_TEXT_MAIN_MENU, 236, 300, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      GameManagement.exit();
    })).getButton();

    body.getChildren().addAll(img, btnMainMenu, btnSetting, btnRestart, btnResume);

    gameMenu.getChildren().addAll(overlay, body);
    overlay.setOpacity(0.6);
    overlay.setOnMouseClicked(e -> {
      try {
        GameManagement.removeLayer(gameMenu);
        GameManagement.resume();
      } catch (Exception ignore) {

      }
    });
  }

  public static void initGameSetting() {
    gameSetting = new StackPane();
    gameSetting.setPrefSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);

    Group body = new Group();

    Canvas overlay = new Canvas(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT - 100);
    GraphicsContext gc = overlay.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);

    ImageView img = new ImageView(new Image(Utils.SRC_GAME_SETTING));
    img.setLayoutX(217);
    img.setLayoutY(50);

    Slider musicSlider = new Slider();
    musicSlider.setMin(0);
    musicSlider.setMax(100);
    musicSlider.setLayoutX(272);
    musicSlider.setLayoutY(170);
    musicSlider.setValue(SFX.getVolumeMusic());
    musicSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
      int nv = newValue.intValue();
      SFX.setVolumeMusic(nv);
    });


    Slider sfxSlider = new Slider();
    sfxSlider.setMin(0);
    sfxSlider.setMax(100);
    sfxSlider.setLayoutX(272);
    sfxSlider.setLayoutY(250);
    sfxSlider.setValue(SFX.getVolumeSFX());
    sfxSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
      int nv = newValue.intValue();
      SFX.setVolumeSFX(nv);
    });

    Button btnMainMenu = (new ButtonImage(Utils.SRC_TEXT_MAIN_MENU, 236, 300, 190, 40, Utils.CSS_BUTTON_IMAGE, e -> {
      try {
        InitApp.removeLayer(gameSetting);
        GameManagement.removeLayer(gameSetting);
      } catch (Exception ignore) {

      }
    })).getButton();

    body.getChildren().addAll(img, musicSlider, sfxSlider, btnMainMenu);
    gameSetting.getChildren().addAll(overlay, body);
    overlay.setOpacity(0.6);
    overlay.setOnMouseClicked(e -> {
      try {
        InitApp.removeLayer(gameSetting);
        GameManagement.removeLayer(gameSetting);
      } catch (Exception ignore) {

      }
    });
  }
}