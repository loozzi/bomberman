package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Entity {
  public SpeedItem(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }

  @Override
  public void update() {
    if (picked_up) {
      Bomber.increaseSpeed();
      SFX.playSFX(SFX.itemGet_media);
      GameManagement.addItemsActivated(this);
      GameManagement.getItems().remove(this);
    }
  }
}
