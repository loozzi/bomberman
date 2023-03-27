package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
  private final int MAX_TIME_ANIMATION = 6000;
  private int timeAnimation = 0;
  private int timeRunAnimation = 60;
  public Bomb(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }

  @Override
  public void update() {
    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
    super.img = (Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeAnimation, timeRunAnimation)).getFxImage();
    if (timeAnimation > 300) {
      super.img = (Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeAnimation, timeRunAnimation)).getFxImage();
    }
    if (timeAnimation > 500) {
      GameManagement.removeBombs(this);
    }
  }

}
