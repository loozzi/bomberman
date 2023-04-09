package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.enemies.Bomb;

public class FlameItem extends Entity {
  public FlameItem(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
  }

  @Override
  public void update() {
    if (picked_up == true) {
      Bomber.setFlameItemIsActive();
      GameManagement.getItems().remove(this);
    }
  }
}
