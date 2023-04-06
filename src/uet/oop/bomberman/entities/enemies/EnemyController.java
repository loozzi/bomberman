package uet.oop.bomberman.entities.enemies;

import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Wall;

import java.util.ArrayList;
import java.util.List;

public class EnemyController {
  private static int size_block = 32;
  /****************************************
   * check move ability                   *
   ****************************************/
  /**
   * check moving ability.
   *
   * @param _x pointX calculate
   * @param _y pointY calculate
   * @return true | false
   */
  public static boolean checkMoveAbility(double _x, double _y, Direction direction) {
    List<Entity> merge = new ArrayList<Entity>();
    merge.addAll(GameManagement.getStillObjects());
    merge.addAll(GameManagement.getBombs());
    switch (direction) {
      case DOWN:
        _y = Math.floor(_y / 32) * 32;
        for (Entity entity : merge) {
          if (_y + size_block == entity.getY()) {
            if ((_x >= entity.getX() && _x < entity.getX() + size_block)
                    || (_x + size_block > entity.getX() && _x + size_block < entity.getX() + size_block)
            ) {
              if ((entity instanceof Brick || entity instanceof Wall || entity instanceof BombItem))
                return false;
            }
          }
        }
        break;
      case UP:
        _y = Math.ceil(_y / 32) * 32;
        _x = Math.round(_x / 32) * 32;
        for (Entity entity : merge) {
          if (_y == entity.getY() + 32) {
            if ((_x >= entity.getX() && _x < entity.getX() + size_block)
                    || (_x + size_block > entity.getX() && _x + size_block < entity.getX() + size_block)
            ) {
              if ((entity instanceof Brick || entity instanceof Wall || entity instanceof BombItem)) {
                return false;
              }
            }
          }
        }
        break;
      case LEFT:
        _x = Math.floor(_x / 32) * 32;
        for (Entity entity : merge) {
          if (_x == entity.getX()) {
            if (_y >= entity.getY() && _y < entity.getY() + size_block) {
              if ((entity instanceof Brick || entity instanceof Wall || entity instanceof BombItem)
                      || (_y + size_block > entity.getY() && _y + size_block < entity.getY() + size_block)
              ) {
                return false;
              }
            }
          }
        }
        break;
      case RIGHT:
        _x = Math.floor(_x / 32) * 32;
        for (Entity entity : merge) {
          if (_x + size_block == entity.getX()) {
            if (_y >= entity.getY() && _y < entity.getY() + size_block) {
              if ((entity instanceof Brick || entity instanceof Wall || entity instanceof BombItem)
                      || (_y + size_block > entity.getY() && _y + size_block < entity.getY() + size_block)
              ) {
                return false;
              }
            }
          }
        }
        break;
    }
    return true;
  }
}