package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class BombEffect extends Entity {
    private final int MAX_TIME_ANIMATION = 6000;
    private int timeAnimation = 0;
    private int timeRunAnimation = 40;
    private Sprite ef1;
    private Sprite ef2;
    private Sprite ef3;

    public BombEffect(int xUnit, int yUnit, Sprite ef1, Sprite ef2, Sprite ef3) {
        super(xUnit, yUnit, ef1.getFxImage());
        this.ef1 = ef1;
        this.ef2 = ef2;
        this.ef3 = ef3;
        handleExplode();
    }

    /**
     *
     */
    @Override
    public void update() {
        timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
        super.img = (Sprite.movingSprite(this.ef1, this.ef2, this.ef3, timeAnimation, timeRunAnimation)).getFxImage();
        if (timeAnimation > 40) {
            GameManagement.removeBombs(this);
        }
    }

    public void handleExplode() {
        Entity entity = checkExplode();
        if (entity instanceof Wall) {
            hide();
        } else if (entity instanceof Brick) {
            ((Brick) entity).setExploded();
            hide();
        }
    }

    public Entity checkExplode() {
        for (Entity entity : GameManagement.getStillObjects()) {
            if ((entity.getX() == super.getX()
                    && (int) Math.round(entity.getY() / 32) == (int) Math.round(super.getY() / 32))) {
                if (entity instanceof Wall || entity instanceof Brick) {
                    return entity;
                }
            }
        }

        return null;
    }

    public void hide() {
        timeAnimation = 1000;
    }
}