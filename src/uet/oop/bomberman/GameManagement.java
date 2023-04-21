package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.common.Utils;
import uet.oop.bomberman.controller.InputManager;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.*;
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
import java.io.FileInputStream;
import java.io.InputStream;
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
  static int heart = 3;
  static int initScore;
  static int initHeart;
  static int remainingBombs = 50;
  static String mapCurrent;
  static String mapNext;

  // all object
  private static List<Entity> entities = new ArrayList<>();
  private static List<Entity> stillObjects = new ArrayList<>();
  private static List<Entity> bombs = new ArrayList<>();
  private static List<Entity> items = new ArrayList<>();
  private static List<Entity> itemsActivated = new ArrayList<>();
  private static List<Entity> initItemsActivated;


  // all status in a game
  static boolean isPaused;

  //Level và tọa độ game.
  private static int level;
  private static int row;
  private static int col;

  //|============================================|
  //|            GETTER AND SETTER               |
  //|============================================|
  public static int getBombsInField() {
    int count = 0;
    for (Entity entity : bombs) {
      if (entity instanceof Bomb) {
        count ++;
      }
    }
    return count;
  }

  public static Group getRoot() {
    return root;
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
    if (bomb instanceof Bomb) {
      if (remainingBombs == 0) {
        dies();
      } else {
        --remainingBombs;
        bombs.add(bomb);
      }
    } else {
      bombs.add(bomb);
    }
  }

  public static void removeBombs(Entity bomb) {
    bombs.remove(bomb);
  }

  public static void addItemsActivated(Entity item) {
    itemsActivated.add(item);
    stillObjects.add(new Grass((int) (item.getX() / 32), (int) (item.getY() / 32), Sprite.grass.getFxImage()));
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
  public static void addEnemy(Entity enemy) {
    entities.add(enemy);
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

  public static int getScore() {
    return score;
  }

  public static void increaseScore(String type) {
    switch (type) {
      case "Balloon":
        score += 100;
        break;
      case "Oneal":
        score += 150;
        break;
      case "Brick":
        score += 10;
        break;
      case "Doll":
        score += 200;
        break;
      case "Kondoria":
        score += 50;
        break;
    }
    System.out.println(type);
  }

  public static void dies() {
    heart--;
  }


  //|=======================================================|
  //|               GAME MANAGEMENT                         |
  //|=======================================================|
  public static void init(String mapSrc, int _initHeart, int _initScore, List<Entity> _initItemsActivated) {
    // init data
    initHeart = _initHeart;
    initScore = _initScore;
    initItemsActivated = new ArrayList<>();
    initItemsActivated.addAll(_initItemsActivated);

    mapCurrent = mapSrc;
    root = new Group();
    scene = new Scene(root);
    canvas = new Canvas();
    gc = canvas.getGraphicsContext2D();
    gc.scale(Utils.SCALE_MAP, Utils.SCALE_MAP);
    GameScene.setGraphicsContext(gc);
    InputManager.keyboardHandle(scene);

    loadMap();

    root.getChildren().clear();
    root.getChildren().addAll(canvas);

    start();
  }

  public static void loadMap() {
    String map = "res/levels/" + mapCurrent;
    File file = new File(map);
    try {
      String s;
      Scanner sc = new Scanner(file);
      level = sc.nextInt();
      row = sc.nextInt();
      col = sc.nextInt();
      s = sc.nextLine();
      canvas.setWidth(Sprite.SCALED_SIZE * col * Utils.SCALE_MAP);
      canvas.setHeight(Sprite.SCALED_SIZE * (row + 1) * Utils.SCALE_MAP);

      for (int i = 1; i < row + 1; i++) {
        s = sc.nextLine();
        for (int j = 0; j < col; j++) {
          Entity grass = new Grass(j, i, Sprite.grass.getFxImage());
          if (s.charAt(j) != '#') {
            stillObjects.add(grass);
          }
          switch (s.charAt(j)) {
            case '#':
              stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
              break;
            case '*':
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              break;
            case 'x':
              stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              break;
            case 'p':
              Entity bomber = new Bomber(j, i, Sprite.player_down.getFxImage());
              Bomber.reset();

              for (Entity item : initItemsActivated) {
                if (item instanceof BombItem) {
                  Bomber.FlamepassItemIsActive();
                } else if (item instanceof DetonatorItem) {
                  Bomber.DetonatorItemIsActive();
                } else if (item instanceof WallpassItem) {
                  Bomber.WallpassItemIsActive();
                } else if (item instanceof SpeedItem) {
                  Bomber.increaseSpeed();
                }
              }

              entities.add(bomber);
              break;
            case '1':
              entities.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
              break;
            case '2':
              entities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
              break;
            case '3':
              entities.add(new Doll(j, i, Sprite.doll_right1.getFxImage()));
              break;
            case '4':
              entities.add(new Kondoria(j, i, Sprite.kondoria_right1.getFxImage()));
              break;
            case 'b':
              Entity bombItem = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
              bombItem.setDisable();
              items.add(bombItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
            case 'f':
              Entity flameItem = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
              flameItem.setDisable();
              items.add(flameItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
            case 's':
              Entity speedItem = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
              speedItem.setDisable();
              items.add(speedItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
            case 'd':
              Entity detonatorItem = new DetonatorItem(j, i, Sprite.powerup_detonator.getFxImage());
              detonatorItem.setDisable();
              items.add(detonatorItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
            case 'w':
              Entity wallPassItem = new WallpassItem(j, i, Sprite.powerup_wallpass.getFxImage());
              wallPassItem.setDisable();
              items.add(wallPassItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
            case 'l':
              Entity flamePassItem = new FlamepassItem(j, i, Sprite.powerup_flamepass.getFxImage());
              flamePassItem.setDisable();
              items.add(flamePassItem);
              stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
              stillObjects.remove(grass);
              break;
          }
        }
      }
      mapNext = sc.nextLine();
      System.out.println(mapNext);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void start() {


    currentGameTime = 0;
    startNanoTime = 0;
    SFX.playMusic(SFX.bombermanMusic_media);
    GameScene.drawMenubar();

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
        if (currentGameTime < 300) {
          gc.setFill(Color.BLACK);
          gc.setFont(new Font(72));
          gc.fillText("Stage " + getLevel(), canvas.getWidth() / 2 - 250, canvas.getHeight() / 2 - 50);
        } else {
          if (!isPaused) {
            if (InputManager.isPauseGame()) {
              pause();
            }
            gc.clearRect(0, 0, Utils.CANVAS_WIDTH * Utils.SCALE_MAP, Utils.CANVAS_HEIGHT * Utils.SCALE_MAP);
            update();
            render();
            GameScene.updateMenubar(itemsActivated,(int) Math.round((currentGameTime - 300)/130.0), score, heart, remainingBombs);
          }
        }

      }
    };
    timer.start();
  }

  private static void render() {
    GameScene.drawEntity(items);
    GameScene.drawEntity(stillObjects);
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


  //|============================================|
  //|            GAME CONTROLLER                 |
  //|============================================|

  public static void handleVictory() {
    addLayer(GameScreen.victory);
    isPaused = true;
    timer.stop();
  }

  public static void handleGameOver() {

  }

  public static void pause() {
    addLayer(GameScreen.gameMenu);
    isPaused = true;
    timer.stop();
  }

  public static void resume() {
    isPaused = false;
    InputManager.resumeGame();
    timer.start();
  }


  public static void handleReset() {
    reset();
    removeLayer(GameScreen.gameMenu);
    GameManagement.init(mapCurrent, initHeart, initScore, initItemsActivated);
    primaryStage.setScene(GameManagement.getScene());

    try {
      resume();
    } catch (Exception ignore) { }
  }

  public static void reset() {
    entities.clear();
    stillObjects.clear();
    bombs.clear();
    items.clear();
    itemsActivated.clear();
    itemsActivated.addAll(initItemsActivated);
    score = initScore;
    heart = initHeart;
    remainingBombs = 50;
    Bomber.reset();
    timer = null;
  }

  public static void handleNextLevel() {
    entities.clear();
    stillObjects.clear();
    bombs.clear();
    items.clear();
    remainingBombs = 50;
    timer = null;

    removeLayer(GameScreen.victory);
    GameManagement.init(mapNext, heart, score, itemsActivated);
    primaryStage.setScene(GameManagement.getScene());

    try {
      resume();
    } catch (Exception ignore) { }
  }

  public static void exit() {
    reset();
    primaryStage.setScene(InitApp.getScene());
    SFX.pauseMusic();
    try {
      resume();
    } catch (Exception ignore) { }
  }
}
