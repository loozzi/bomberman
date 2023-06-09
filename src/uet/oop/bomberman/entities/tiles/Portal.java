package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.entities.Entity;

public class Portal extends Entity {
  public Portal(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
    isActive = false;
  }

  public void setBrokenBrick() {
    isBrokenBrick = true;
  }

  @Override
  public void update() {
    isActive = GameManagement.getEntities().size() == 1 && isBrokenBrick;
  }
}
