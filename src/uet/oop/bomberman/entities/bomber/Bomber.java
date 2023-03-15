package uet.oop.bomberman.entities.bomber;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.controller.InputManager;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
  private static double speed = 0.7;
  public Bomber(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }

  public static void setSpeed(double _speed) {
    speed = _speed;
  }

  public double getMove(double n, boolean negative) {
    return negative ? n - speed : n + speed;
  }

  public boolean checkMoveAbility(double _x, double _y) {
    boolean flag = true;
//    for (Entity entity: GameManagement.getStillObjects()) {
//    }
    return flag;
  }

  public void move(double _x, double _y) {
    if (this.checkMoveAbility(_x, _y)) {
      x = _x;
      y = _y;
    }
  }

  @Override
  public void update() {
    if (InputManager.isDown()) {
      this.move(x, this.getMove(y, false));
    }
    if (InputManager.isUp()) {
      this.move(x, this.getMove(y, true));
    }
    if (InputManager.isLeft()) {
      this.move(this.getMove(x, true), y);
    }
    if (InputManager.isRight()) {
      this.move(this.getMove(x, false), y);
    }
  }
}
