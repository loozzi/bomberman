package uet.oop.bomberman.entities.bomber;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.controller.InputManager;
import uet.oop.bomberman.entities.BombEffect;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.items.*;
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
    private int curTime = 0;
    private int timeRunAnimation = 40;
    //Biến số bom có thể đặt.
    private static int Bombs = 1;
    //Biến đã nhặt được item Flame chưa.
    private static boolean FlameItemIsActive = false;
    private static boolean DetonatorItemIsActive = false;
    private static boolean WallpassItemIsActive = false;
    private static boolean FlamepassItemIsActive = false;
    private boolean isKilled = false;

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        curTime = (curTime > MAX_TIME_ANIMATION) ? 0 : curTime + 1;
        if (isKilled) {
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            if (timeAnimation >= timeRunAnimation) {
                GameManagement.dies();
                this.setX(32);
                this.setY(64);
                super.img = Sprite.player_down.getFxImage();
                isKilled = false;
            } else {
                super.img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, timeAnimation, timeRunAnimation).getFxImage();
            }
        } else {
            moveHanlde();
            for (Entity bomb : GameManagement.getBombs()) {
                if (bomb instanceof BombEffect) {
                    if ((int) Math.round(super.getX() / 32) == (int) Math.round(bomb.getX() / 32)
                            && (int) Math.round(super.getY() / 32) == (int) Math.round(bomb.getY() / 32)) {
                        killed();
                    }
                }
            }
            checkMoveAbility(this.x, this.y+1, Direction.UP);
            checkMoveAbility(this.x, this.y-1, Direction.DOWN);
            checkMoveAbility(this.x-1, this.y, Direction.LEFT);
            checkMoveAbility(this.x+1, this.y, Direction.RIGHT);
        }
    }

    public static void setSpeed(double _speed) {
        speed = _speed;
    }

    /**
     * increasing speed.
     */
    public static void increaseSpeed() {
        speed += 0.3;
    }

    public static void setFlameItemIsActive() {
        FlameItemIsActive = true;
    }

    public static void BombItemIsActive() {
        Bombs++;
    }

    public static int getBombs() {
        return Bombs;
    }

    public static void reset() {
        Bombs = 1;
        FlameItemIsActive = false;
        DetonatorItemIsActive = false;
        WallpassItemIsActive = false;
        FlamepassItemIsActive = false;
        speed = 0.7;
    }

    public static void DetonatorItemIsActive() {
        DetonatorItemIsActive = true;
    }

    public static void WallpassItemIsActive() {
        WallpassItemIsActive = true;
    }

    public static void FlamepassItemIsActive() {
        FlamepassItemIsActive = true;
    }

    public static boolean getDetonatorItemIsActive() {
        return DetonatorItemIsActive;
    }

    public static boolean getWallpassItemIsActive() {
        return WallpassItemIsActive;
    }

    public static boolean getFlamepassItemIsActive() {
        return FlamepassItemIsActive;
    }

    /**
     * Check for duplicate bomb placement,
     *
     * @return - true/false
     */
    public boolean CheckLocationSetBomb() {
        boolean result = true;
        for (Entity entity : GameManagement.getBombs()) {
            if ((int) (Math.round(entity.getX()) / 32) == (int) (Math.round(super.getX() / 32))
                    && (int) (Math.round(entity.getY()) / 32) == (int) (Math.round(super.getY() / 32))) {
                result = false;
            }
        }
        for (Entity entity : GameManagement.getStillObjects()) {
            if (entity instanceof Brick || entity instanceof Wall) {
                if ((int) (Math.round(entity.getX()) / 32) == (int) (Math.round(super.getX() / 32))
                        && (int) (Math.round(entity.getY()) / 32) == (int) (Math.round(super.getY() / 32))) {
                    result = false;
                }
            }
        }
        for (Entity entity : GameManagement.getEntities()) {
            if (entity instanceof Enemy) {
                if ((int) (Math.round(entity.getX()) / 32) == (int) (Math.round(super.getX() / 32))
                        && (int) (Math.round(entity.getY()) / 32) == (int) (Math.round(super.getY() / 32))) {
                    result = false;
                }
            }
        }
        return result;
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
                    if (entity.getIsActve() && (_y + 30 > entity.getY() && _y <= entity.getY())
                            && ((_x < entity.getX() + 30 && _x >= entity.getX())
                            || (_x + 24 > entity.getX() && _x <= entity.getX()))
                    ) {
                        if (handleCollideObject(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case UP:
                for (Entity entity : merge) {
                    if (entity.getIsActve() && (_y < entity.getY() + 30 && _y > entity.getY())
                            && ((_x < entity.getX() + 30 && _x >= entity.getX())
                            || (_x + 24 > entity.getX() && _x <= entity.getX()))
                    ) {
                        if (handleCollideObject(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case LEFT:
                for (Entity entity : merge) {
                    if (entity.getIsActve() && (_x < entity.getX() + 30 && _x > entity.getX())
                            && ((_y < entity.getY() + 30 && _y >= entity.getY())
                            || (_y + 30 > entity.getY() && _y <= entity.getY()))
                    ) {
                        if (handleCollideObject(entity, _x, _y)) {
                            return false;
                        }
                    }
                }
                break;
            case RIGHT:
                for (Entity entity : merge) {
                    if (entity.getIsActve() && (_x + 24 > entity.getX() && _x < entity.getX())
                            && ((_y < entity.getY() + 30 && _y >= entity.getY())
                            || (_y + 30 > entity.getY() && _y <= entity.getY()))
                    ) {
                        if (handleCollideObject(entity, _x, _y)) {
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
    public boolean handleCollideObject(Entity entity, double _x, double _y) {
        if (entity instanceof Wall || entity instanceof Brick) {
            if (entity.getX() / Sprite.SCALED_SIZE != 0 && entity.getY() / Sprite.SCALED_SIZE != 1
                    && entity.getX() / Sprite.SCALED_SIZE != GameManagement.getCol() - 1
                    && entity.getY() / Sprite.SCALED_SIZE != GameManagement.getRow()) {
                return !WallpassItemIsActive;
            }
            return true;
        } else if (entity instanceof Bomb) {
            return Math.round(_x / 32) != Math.round(entity.getX() / 32)
                    || Math.round(_y / 32) != Math.round(entity.getY() / 32);
        } else if (entity instanceof Grass) {
            return false;
        } else if (entity instanceof BombEffect) {
            killed();
            return true;
        } else if (entity instanceof Balloon) {
            System.out.println("Collide Balloon");
            killed();
            return false;
        } else if (entity instanceof Oneal) {
            System.out.println("Collide Oneal");
            killed();
            return false;
        } else if (entity instanceof Doll) {
            System.out.println("Collide Doll");
            killed();
            return false;
        } else if (entity instanceof Kondoria) {
            System.out.println("Collide Kondoria");
            killed();
        } else if (entity instanceof Portal) {
            if (entity.getIsActve()) {
                System.out.println("Collide Portal");
                entity.setPicked_up();
                GameManagement.handleVictory();
            }
            return false;
        } else if (entity instanceof BombItem) {
            System.out.println("Collide BombItem");
            entity.setPicked_up();
            return false;
        } else if (entity instanceof DetonatorItem) {
            System.out.println("Collide DetonatorItem");
            entity.setPicked_up();
            return false;
        } else if (entity instanceof WallpassItem) {
            System.out.println("Collide WallpassItem");
            entity.setPicked_up();
            return false;
        } else if (entity instanceof FlamepassItem) {
            System.out.println("Collide FlamepassItem");
            entity.setPicked_up();
            return false;
        } else if (entity instanceof FlameItem) {
            System.out.println("Collide FlameItem");
            entity.setPicked_up();
            return false;
        } else if (entity instanceof SpeedItem) {
            System.out.println("Collide SpeedItem");
            entity.setPicked_up();
            return false;
        }
        return true;
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
        if (InputManager.isSetBomb() && CheckLocationSetBomb() && GameManagement.getBombsInField() < Bombs) {
            GameManagement.addBombs(new Bomb((int) (Math.round(super.getX() / 32)), (int) (Math.round(super.getY() / 32)), Sprite.bomb.getFxImage(), FlameItemIsActive));
        }
    }

    public void killed() {
        if (!isKilled && curTime >= timeRunAnimation * 4) {
            SFX.playSFX(SFX.getHit_media);
            isKilled = true;
            timeAnimation = 0;
            curTime = 0;
        }
    }

}
