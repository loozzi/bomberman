package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
  private boolean isExploded = false;
  private final int MAX_TIME_ANIMATION = 6000;
  private int timeAnimation = 0;
  private int timeRunAnimation = 40;
  public Brick(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }

  @Override
  public void update() {
    if (isExploded) {
      exploded();
    }
  }

  void exploded() {
    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
    super.img = (Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, timeAnimation, timeRunAnimation)).getFxImage();
    if (timeAnimation > timeRunAnimation) {
      GameManagement.getStillObjects().remove(this);
    }
  }

  public void setExploded() {
    isExploded = true;
  }
}
