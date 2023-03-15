package uet.oop.bomberman.gui;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class GameScene {
  static GraphicsContext gc;

  public static void setGraphicsContext(GraphicsContext _gc) {
    gc = _gc;
  }

  public static void drawEntity(List<Entity> entities) {
    for (Entity entity: entities) {
      entity.render(gc);
    }
  }

}
