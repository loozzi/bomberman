package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloon extends Entity {
  private int type;
  private Direction direction = Direction.UP;
  private double speed = 0.5;

  private int timeToRandom = 0;
  private final int MAX_TIME_ANIMATION = 6000;
  private int timeAnimation = 0;
  private int timeRunAnimation = 40;

  public Balloon(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
    /**
     * Balloon type:
     * 	- 1: left right
     * 	- 2: up down
     * 	- 3: around
     * 	- 4: around rotate
     * 	- 5: random
     * 	- 6: follow player
     */
    this.type = (new Random()).nextInt(1) + 5;
    System.out.println(type);
//    this.type = 1;
  }

  @Override
  public void update() {
    switch (this.type) {
      case 1:
        type_1();
        break;
      case 2:
        type_2();
        break;
      case 3:
        type_3();
        break;
      case 4:
        type_4();
        break;
      case 5:
        type_5();
        break;
    }
  }

  private void type_1() {
    if (direction != Direction.RIGHT) {
      direction = Direction.LEFT;
    }
    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), direction)) {
      move(speed, direction);
    } else {
      if (direction == Direction.LEFT) {
        direction = Direction.RIGHT;
      } else {
        direction = Direction.LEFT;
      }
    }
  }

  private void type_2() {
    if (direction != Direction.UP) {
      direction = Direction.DOWN;
    }
    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), direction)) {
      move(speed, direction);
    } else {
      if (direction == Direction.UP) {
        direction = Direction.DOWN;
      } else {
        direction = Direction.UP;
      }
    }
  }

  private void type_3() {
    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), direction)) {
      move(speed, direction);
      if (direction == Direction.UP || direction == Direction.DOWN) {
        super.x = Math.round(super.x / 32) * 32;
      } else {
        super.y = Math.round(super.y / 32) * 32;
      }
    } else {
      if (direction == Direction.UP) {
        direction = Direction.RIGHT;
      } else if (direction == Direction.RIGHT) {
        direction = Direction.DOWN;
      } else if (direction == Direction.DOWN) {
        direction = Direction.LEFT;
      } else {
        direction = Direction.UP;
      }
    }
  }

  private void type_4() {
    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), direction)) {
      move(speed, direction);
      if (direction == Direction.UP || direction == Direction.DOWN) {
        super.x = Math.round(super.x / 32) * 32;
      } else {
        super.y = Math.round(super.y / 32) * 32;
      }
    } else {
      if (direction == Direction.UP) {
        direction = Direction.LEFT;
      } else if (direction == Direction.LEFT) {
        direction = Direction.DOWN;
      } else if (direction == Direction.DOWN) {
        direction = Direction.RIGHT;
      } else {
        direction = Direction.UP;
      }
    }
  }

  private void type_5() {
    this.timeToRandom += 1;
    Random rand = new Random();
    if (this.timeToRandom == 6400) {
      this.timeToRandom = 0;
    }
    Direction randDirection = Direction.values()[rand.nextInt(Direction.values().length)];
    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), randDirection)) {
      if ((randDirection == Direction.RIGHT || randDirection == Direction.LEFT)
              && (direction == Direction.UP || direction == Direction.DOWN)
              && (timeToRandom % 64 == 0)
      ) {
        this.timeToRandom = 0;
        direction = randDirection;
      } else if ((direction == Direction.RIGHT || direction == Direction.LEFT)
              && (randDirection == Direction.UP || randDirection == Direction.DOWN)
              && (timeToRandom % 64 == 0)
      ) {
        this.timeToRandom = 0;
        direction = randDirection;
      }
    }

    if (EnemyController.checkMoveAbility(super.getX(), super.getY(), direction)) {
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

  private void move(double value, Direction direction) {
    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
    switch (direction) {
      case UP:
        super.y -= value;
        super.img = (Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case DOWN:
        super.y += value;
        super.img = (Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case LEFT:
        super.x -= value;
        super.img = (Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
      case RIGHT:
        super.x += value;
        super.img = (Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, timeAnimation, timeRunAnimation)).getFxImage();
        break;
    }
  }

  public void killed() {

  }
}
