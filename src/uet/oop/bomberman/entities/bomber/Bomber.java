package uet.oop.bomberman.entities.bomber;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.controller.InputManager;
import uet.oop.bomberman.entities.BombEffect;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.entities.enemies.Bomb;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private static double speed = 0.7;
    private final int MAX_TIME_ANIMATION = 6000;
    private int timeAnimation = 0;
    private int timeRunAnimation = 40;

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public static void setSpeed(double _speed) {
        speed = _speed;
    }

    /**
     * increasing speed.
     */
    public static void increaseSpeed() {
        speed += 0.1;
    }

    @Override
    public void update() {
        moveHanlde();
        for (Entity bomb : GameManagement.getBombs()) {
            if (bomb instanceof BombEffect) {
                if (    ((int) Math.round(super.getX() / 32) == (int) Math.round(bomb.getX() / 32)
                        && (int) Math.round(super.getY() / 32) == (int) Math.round(bomb.getY() / 32))) {
                    killed();
                }
            }
        }
    }

    /**
     * get moving value.
     *
     * @param n        pointX or pointY
     * @param negative true | false (up/down or left/right)
     * @return pointX or pointY
     */
    public double getMove(double n, boolean negative) {
        return negative ? n - speed : n + speed;
    }

    /**
     * check moving ability.
     *
     * @param _x        pointX calculate
     * @param _y        pointY calculate
     * @param direction 1-down 2-up 3-left 4-right
     * @return true | false
     */
    public boolean checkMoveAbility(double _x, double _y, Direction direction) {
        List<Entity> merge = new ArrayList<Entity>();
        merge.addAll(GameManagement.getStillObjects());
        merge.addAll(GameManagement.getEntities());
        merge.addAll(GameManagement.getItems());
        merge.addAll(GameManagement.getBombs());
        switch (direction) {
            case DOWN:
                for (Entity entity : merge) {
                    if ((_y + 30 > entity.getY() && _y <= entity.getY())
                            && ((_x < entity.getX() + 30 && _x >= entity.getX())
                            || (_x + 24 > entity.getX() && _x <= entity.getX()))
                    ) {
                        if (!handleCollide(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case UP:
                for (Entity entity : merge) {
                    if ((_y < entity.getY() + 30 && _y > entity.getY())
                            && ((_x < entity.getX() + 30 && _x >= entity.getX())
                            || (_x + 24 > entity.getX() && _x <= entity.getX()))
                    ) {
                        if (!handleCollide(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case LEFT:
                for (Entity entity : merge) {
                    if ((_x < entity.getX() + 30 && _x > entity.getX())
                            && ((_y < entity.getY() + 30 && _y >= entity.getY())
                            || (_y + 30 > entity.getY() && _y <= entity.getY()))
                    ) {
                        if (!handleCollide(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case RIGHT:
                for (Entity entity : merge) {
                    if ((_x + 24 > entity.getX() && _x < entity.getX())
                            && ((_y < entity.getY() + 30 && _y >= entity.getY())
                            || (_y + 30 > entity.getY() && _y <= entity.getY()))
                    ) {
                        if (!handleCollide(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    /**
     * handle when bomber collide another object.
     *
     * @param entity Entity object
     * @return true | false - (true if collide brick or wall)
     */
    public static boolean handleCollide(Entity entity, double _x, double _y) {
        if (entity instanceof Wall || entity instanceof Brick) {
            return false;
        } else if (entity instanceof Bomb) {
            return Math.round(_x / 32) == Math.round(entity.getX() / 32)
                    && Math.round(_y / 32) == Math.round(entity.getY() / 32);
        } else if (entity instanceof FlameItem) {
            System.out.println("Collide FlameItem");
            return true;
        } else if (entity instanceof SpeedItem) {
            System.out.println("Collide SpeedItem");
            increaseSpeed();
            return true;
        } else if (entity instanceof Balloon) {
            System.out.println("Collide Balloon");
            killed();
            return false;
        } else if (entity instanceof Oneal) {
            System.out.println("Collide Oneal");
            return true;
        } else if (entity instanceof Portal) {
            System.out.println("Collide Portal");
            return true;
        } else if (entity instanceof Grass) {
            return true;
        } else if (entity instanceof BombEffect) {
            killed();
            return false;
        }
        return false;
    }

    /**
     * moving.
     *
     * @param _x        new pointX
     * @param _y        new pointY
     * @param direction 1-down 2-up 3-left 4-right
     */
    public void move(double _x, double _y, Direction direction) {
        timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
        if (this.checkMoveAbility(_x, _y, direction)) {
            x = _x;
            y = _y;
//      if (timeAnimation % 20 == 0) {
//        try {
//          SFX.playSFX(SFX.walking1_media);
//        } catch (Exception ignore) {
//
//        }
//      }
            switch (direction) {
                case DOWN:
                    img = (Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, timeAnimation, timeRunAnimation)).getFxImage();
                    break;
                case UP:
                    img = (Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, timeAnimation, timeRunAnimation)).getFxImage();
                    break;
                case LEFT:
                    img = (Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, timeAnimation, timeRunAnimation)).getFxImage();
                    break;
                case RIGHT:
                    img = (Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, timeAnimation, timeRunAnimation)).getFxImage();
                    break;
            }
        }
    }

    /**
     * handle moving.
     */
    public void moveHanlde() {
        // down - 1; up - 2; left - 3; right - 4;
        if (InputManager.isDown()) {
            this.move(x, this.getMove(y, false), Direction.DOWN);
        }
        if (InputManager.isUp()) {
            this.move(x, this.getMove(y, true), Direction.UP);
        }
        if (InputManager.isLeft()) {
            this.move(this.getMove(x, true), y, Direction.LEFT);
        }
        if (InputManager.isRight()) {
            this.move(this.getMove(x, false), y, Direction.RIGHT);
        }
        if (InputManager.isSetBomb() && GameManagement.getBombs().size() == 0) {
            GameManagement.addBombs(new Bomb((int) (Math.round(super.getX() / 32)), (int) (Math.round(super.getY() / 32)), Sprite.bomb.getFxImage()));
        }
    }

    public static void killed() {
        System.out.println("killed");
    }
}
