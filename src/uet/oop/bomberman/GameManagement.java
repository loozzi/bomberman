package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.common.Utils;
import uet.oop.bomberman.controller.InputManager;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.items.*;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.GameScene;
import uet.oop.bomberman.gui.GameScreen;
import uet.oop.bomberman.gui.InitApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManagement {
  static Stage primaryStage = BombermanGame.primaryState;
  static Scene scene;
  static Group root;
  static Canvas canvas;
  static GraphicsContext gc;

  private static AnimationTimer timer;
  private static long currentGameTime = 0;
  private static long startNanoTime;
  static int score = 0;

  // all object
  private static List<Entity> entities = new ArrayList<>();
  private static List<Entity> stillObjects = new ArrayList<>();
  private static List<Entity> bombs = new ArrayList<>();
  private static List<Entity> items = new ArrayList<>();
  private static List<Entity> itemsActivated = new ArrayList<>();


  // all status in a game
  static boolean isPaused;
  static boolean isFreezy;
  static boolean isBlind;

  //Số bom trên sân.
  private static int BombsInField = 0;
  //Level và tọa độ game.
  private static int level;
  private static int row;
  private static int col;

  public static int getBombsInField() {
    return BombsInField;
  }

  public static void ChangeBombsInField(int number) {
    BombsInField += number;
  }
  public static void init(String mapSrc) {
    root = new Group();
    scene = new Scene(root);
    canvas = new Canvas();
    gc = canvas.getGraphicsContext2D();
    gc.scale(Utils.SCALE_MAP, Utils.SCALE_MAP);
    GameScene.setGraphicsContext(gc);
    InputManager.keyboardHandle(scene);

    loadMap(mapSrc);

    root.getChildren().addAll(canvas);
    start();
  }

  public static Scene getScene() {
    return scene;
  }

  public static void addLayer(Node layer) {
    root.getChildren().addAll(layer);
  }

  public static void removeLayer(Node layer) {
    root.getChildren().remove(layer);
  }

  public static Bomber getBomber() {
    for (Entity e : entities) {
      if (e instanceof Bomber) {
        return (Bomber) e;
      }
    }
    return null;
  }

  public static List<Entity> getEntities() {
    return entities;
  }

  public static List<Entity> getStillObjects() {
    return stillObjects;
  }

  public static List<Entity> getBombs() {
    return bombs;
  }

  public static List<Entity> getItems() {
    return items;
  }
  public static List<Entity> getItemsActivated() {
    return itemsActivated;
  }

  public static void addBombs(Entity bomb) {
    bombs.add(bomb);
  }

  public static void removeBombs(Entity bomb) {
    bombs.remove(bomb);
  }

  public static void addItemsActivated(Entity item) {
    itemsActivated.add(item);
  }

  public static void removeItemsActivated(Entity item) {
    itemsActivated.remove(item);
  }

  public static void resetItemsActivated() {
    itemsActivated.clear();
  }
  public static void removeEnemy(Entity enemy) {
    entities.remove(enemy);
  }
  public static int getLevel() {
    return level;
  }

  public static int getRow() {
    return row;
  }

  public static int getCol() {
    return col;
  }

  public static void loadMap(String mapSrc) {
    String map = "res/levels/" + mapSrc;
    File file = new File(map);
    try {
      Scanner sc = new Scanner(file);
      int l = sc.nextInt();
      level = l;
      int r = sc.nextInt();
      row = r;
      int c = sc.nextInt();
      col = c;
      String s = sc.nextLine();
      canvas.setWidth(Sprite.SCALED_SIZE * c * Utils.SCALE_MAP);
      canvas.setHeight(Sprite.SCALED_SIZE * (r + 2) * Utils.SCALE_MAP);
      for (int i = 2; i < r + 2; i++) {
        s = sc.nextLine();
        for (int j = 0; j < c; j++) {
          if (s.charAt(j) != '#') {
            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
          }
          switch (s.charAt(j)) {
            case '#':
              stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
              break;
            case '*':
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              break;
            case 'x':
              items.add(new Portal(j, i, Sprite.portal.getFxImage()));
              break;
            case 'p':
              entities.add(new Bomber(j, i, Sprite.player_down.getFxImage()));
              break;
            case '1':
              entities.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
              break;
            case '2':
              entities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
              break;
            case 'b':
              items.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
              break;
            case 'f':
              items.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
              break;
            case 's':
              items.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
              break;
            case 'd':
              items.add(new DetonatorItem(j, i, Sprite.powerup_detonator.getFxImage()));
              break;
            case 'w':
              items.add(new WallpassItem(j, i, Sprite.powerup_wallpass.getFxImage()));
              break;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  public static void start() {
    currentGameTime = 0;
    startNanoTime = 0;
    SFX.playMusic(SFX.bombermanMusic_media);

    runningGame();
  }

  public static void runningGame() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        if ((l - startNanoTime) / (1000000000 / 60) > currentGameTime) {
          if (isPaused) {
            startNanoTime += (1000000000 / 60);
          } else {
            ++currentGameTime;
          }
        }

        if (!isPaused) {
          if (InputManager.isPauseGame()) {
            addLayer(GameScreen.gameMenu);
            pause();
          }
          gc.clearRect(0, 0, Utils.CANVAS_WIDTH * Utils.SCALE_MAP, Utils.CANVAS_HEIGHT * Utils.SCALE_MAP);
          update();
          render();
        }
      }
    };
    timer.start();
  }

  private static void render() {
    GameScene.drawEntity(stillObjects);
    GameScene.drawEntity(items);
    GameScene.drawEntity(bombs);
    GameScene.drawEntity(entities);
  }

  private static void update() {
    try {
      for (Entity entity : entities) {
        entity.update();
      }
      for (Entity entity : bombs) {
        entity.update();
      }
      for (Entity entity : stillObjects) {
        entity.update();
      }
      //test
      for (Entity entity : items) {
        entity.update();
      }
    } catch (Exception ignored) {}
  }

  public static void pause() {
    isPaused = true;
    timer.stop();
  }

  public static void freeze() {

  }

  public static void resume() {
    isPaused = false;
    InputManager.resumeGame();
    timer.start();
  }

  public static void reset() {

  }

  public static void exit() {
    timer.stop();
    entities.clear();
    stillObjects.clear();
    bombs.clear();

    InitApp.init();
    primaryStage.setScene(InitApp.getScene());
  }
}
