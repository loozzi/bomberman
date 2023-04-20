package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.AStar;
import uet.oop.bomberman.common.BFS;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Oneal extends Enemy {
  private Stack<Direction> stMove = new Stack<>();
  public Oneal(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }


  private double speed = 0.5;
  @Override
  public void update() {
    if (isKilled) {
      timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
      super.img = Sprite.oneal_dead.getFxImage();
      if (timeAnimation >= 160) {
        GameManagement.removeEnemy(this);
      } else if (timeAnimation > 120) {
        super.img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, timeAnimation, timeRunAnimation).getFxImage();
      }
    } else {
      handleMove();
    }
  }

  private void handleMove() {
    this.timeToRandom += 1;
    Random rand = new Random();
    if (this.timeToRandom == 6400) {
      this.timeToRandom = 0;
    }
    Direction randDirection = Direction.values()[rand.nextInt(Direction.values().length)];

    if (timeToRandom % 64 == 0) {
      direction = getDirection();
    }

    if (EnemyController.checkMoveAbility(this, direction)) {
      move(speed, direction);
      if (direction == Direction.UP || direction == Direction.DOWN) {
        super.x = Math.round(super.x / 32) * 32;
      } else {
        super.y = Math.round(super.y / 32) * 32;
      }

    } else {
      direction = randDirection;
    }
  }

  private Direction getDirection() {
    int[][] map2D = new int[GameManagement.getRow()][GameManagement.getCol()];
    Entity bomber = GameManagement.getBomber();
    List<Entity> stillObjects = GameManagement.getStillObjects();
    List<Entity> bombs = GameManagement.getBombs();
    int x;
    int y;

    for (int i = 0; i < GameManagement.getRow(); i++) {
      Arrays.fill(map2D[i], 0);
    }

    for (Entity entity : stillObjects) {
      x = (int) Math.round(entity.getX() / 32);
      y = (int) Math.round(entity.getY() / 32);
      if (entity instanceof Wall || entity instanceof Brick) {
        map2D[y - 1][x] = 1;
      }
    }

    for (Entity entity : bombs) {
      x = (int) Math.round(entity.getX() / 32);
      y = (int) Math.round(entity.getY() / 32);
      map2D[y - 1][x] = 1;
    }

    Stack<Direction> res = BFS.shortestPath(
            map2D,
            (int) Math.round(this.getY() / 32 - 1),
            (int) Math.round(this.getX() / 32),
            (int) Math.round(bomber.getY() / 32 - 1),
            (int) Math.round(bomber.getX() / 32)
    );

    if (stMove.isEmpty() || stMove.size() > res.size()) {
      stMove = res;
    }

    if (stMove.isEmpty()) {
      return Direction.values()[(new Random()).nextInt(Direction.values().length)];
    } else {
      return stMove.pop();
    }
  }

  private void move(double value, Direction direction) {
    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
    switch (direction) {
      case UP:
        super.y -= value;
        super.img = (Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case DOWN:
        super.y += value;
        super.img = (Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case LEFT:
        super.x -= value;
        super.img = (Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case RIGHT:
        super.x += value;
        super.img = (Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
    }
  }

  @Override
  public void killed() {
    if(!isKilled) {
      timeAnimation = 0;
      isKilled = true;
      SFX.playSFX(SFX.enemyDies_media);
      GameManagement.increaseScore("Oneal");
    }
  }
}
