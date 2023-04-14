package uet.oop.bomberman.entities.enemies;

import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.entities.BombEffect;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

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
     * @param _entity Balloon or Oneal
     * @return true | false
     */
    public static boolean checkMoveAbility(Enemy _entity, Direction direction) {
        double _x = _entity.getX();
        double _y = _entity.getY();
        List<Entity> merge = new ArrayList<Entity>();
        merge.addAll(GameManagement.getStillObjects());
        merge.addAll(GameManagement.getBombs());

        for (Entity entity : merge) {
            switch (direction) {
                case DOWN:
                    _y = Math.floor(_y / 32) * 32;
                    if (_y + size_block == entity.getY()) {
                        if ((_x >= entity.getX() && _x < entity.getX() + size_block)
                                || (_x + size_block > entity.getX() && _x + size_block < entity.getX() + size_block)
                        ) {
                            if (entity instanceof Brick || /*entity instanceof Wall ||*/ entity instanceof BombItem)
                                return false;
                            if (entity instanceof Wall) {
                                if ((entity.getX() / Sprite.SCALED_SIZE == 0 || entity.getY() / Sprite.SCALED_SIZE == 1
                                        || entity.getX() / Sprite.SCALED_SIZE == GameManagement.getCol() - 1
                                        || entity.getY() / Sprite.SCALED_SIZE == GameManagement.getRow())) {
                                    return false;
                                } else if (_entity instanceof Doll) {
                                    return true;
                                }
                                return false;
                            }
                        }
                    }

                    break;
                case UP:
                    //_y = Math.ceil(_y / 32) * 32;
                    _x = Math.round(_x / 32) * 32;
                    if (_y == entity.getY() + 32) {
                        if ((_x >= entity.getX() && _x < entity.getX() + size_block)
                                || (_x + size_block > entity.getX() && _x + size_block < entity.getX() + size_block)
                        ) {
                            if (entity instanceof Brick || /*entity instanceof Wall ||*/ entity instanceof BombItem)
                                return false;
                            if (entity instanceof Wall) {
                                if ((entity.getX() / Sprite.SCALED_SIZE == 0 || entity.getY() / Sprite.SCALED_SIZE == 1
                                        || entity.getX() / Sprite.SCALED_SIZE == GameManagement.getCol() - 1
                                        || entity.getY() / Sprite.SCALED_SIZE == GameManagement.getRow())) {
                                    return false;
                                } else if (_entity instanceof Doll) {
                                    return true;
                                }
                                return false;
                            }
                        }
                    }

                    break;
                case LEFT:
                    _x = Math.floor(_x / 32) * 32;
                    if (_x == entity.getX()) {
                        if (_y >= entity.getY() && _y < entity.getY() + size_block) {
                            if ((entity instanceof Brick /*|| entity instanceof Wall*/ || entity instanceof Bomb)
                                    || (_y + size_block > entity.getY() && _y + size_block < entity.getY() + size_block)
                            ) {
                                return false;
                            }
                            if (entity instanceof Wall) {
                                if ((entity.getX() / Sprite.SCALED_SIZE == 0 || entity.getY() / Sprite.SCALED_SIZE == 1
                                        || entity.getX() / Sprite.SCALED_SIZE == GameManagement.getCol() - 1
                                        || entity.getY() / Sprite.SCALED_SIZE == GameManagement.getRow())) {
                                    return false;
                                } else if (_entity instanceof Doll) {
                                    return true;
                                }
                                return false;
                            }
                        }
                    }

                    break;
                case RIGHT:
                    _x = Math.floor(_x / 32) * 32;
                    if (_x + size_block == entity.getX()) {
                        if (_y >= entity.getY() && _y < entity.getY() + size_block) {
                            if ((entity instanceof Brick /*|| entity instanceof Wall*/ || entity instanceof Bomb)
                                    || (_y + size_block > entity.getY() && _y + size_block < entity.getY() + size_block)
                            ) {
                                return false;
                            }
                            if (entity instanceof Wall) {
                                if ((entity.getX() / Sprite.SCALED_SIZE == 0 || entity.getY() / Sprite.SCALED_SIZE == 1
                                        || entity.getX() / Sprite.SCALED_SIZE == GameManagement.getCol() - 1
                                        || entity.getY() / Sprite.SCALED_SIZE == GameManagement.getRow())) {
                                    return false;
                                } else if (_entity instanceof Doll) {
                                    return true;
                                }
                                return false;
                            }
                        }
                    }

                    break;
            }

            if ((int) Math.round(entity.getX()/32) == (int) Math.round(_x/32)
                    && (int) Math.round(entity.getY()/32) == (int) Math.round(_y/32)
                    && entity instanceof BombEffect
            ) {
                try {
                    _entity.killed();
                } catch (Exception ignored) {}
            }
        }
        return true;
    }
}