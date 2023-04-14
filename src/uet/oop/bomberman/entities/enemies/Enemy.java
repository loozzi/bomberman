package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.entities.Entity;

public abstract class Enemy extends Entity {
    protected int type;
    protected Direction direction = Direction.UP;
    protected double speed = 0.5;

    protected int timeToRandom = 0;
    protected final int MAX_TIME_ANIMATION = 6000;
    protected int timeAnimation = 0;
    protected int timeRunAnimation = 40;

    protected boolean isKilled = false;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void killed();

    @Override
    public void update() {

    }
}
