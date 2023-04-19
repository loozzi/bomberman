package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.Direction;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.graphics.Sprite;
import java.util.Random;

public class Doll extends Enemy {
    private double speed  = 0.3;

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        System.out.println(type);
    }

    @Override
    public void update() {
        if (isKilled) {
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            super.img = Sprite.doll_dead.getFxImage();
            if (timeAnimation >= 160) {
                GameManagement.removeEnemy(this);
                GameManagement.increaseScore("Doll");
            } else if (timeAnimation > 120) {
                super.img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, timeAnimation, timeRunAnimation).getFxImage();
            }
        } else {
            Random_move();
        }
    }

    private void Random_move() {
        this.timeToRandom += 1;
        Random rand = new Random();
        if (this.timeToRandom == 6400) {
            this.timeToRandom = 0;
        }
        Direction randDirection = Direction.values()[rand.nextInt(Direction.values().length)];
        if (EnemyController.checkMoveAbility(this, randDirection)) {
            if ((randDirection == Direction.RIGHT || randDirection == Direction.LEFT)
                    && (direction == Direction.UP || direction == Direction.DOWN)
                    && (timeToRandom % 64 == 0)
            ) {
                this.timeToRandom = 0;
                direction = randDirection;
            } else if ((direction == Direction.RIGHT || direction == Direction.LEFT)
                    && (randDirection == Direction.UP || randDirection == Direction.DOWN)
                    && (timeToRandom % 64 == 0)
            ) {
                this.timeToRandom = 0;
                direction = randDirection;
            }
        }

        if (EnemyController.checkMoveAbility(this, direction)) {
            move(speed, direction);
            if (direction == Direction.UP || direction == Direction.DOWN) {
                super.x = Math.round(super.x / 32) * 32;
            } else {
                super.y = Math.round(super.y / 32) * 32;
            }
        } else {
            direction = randDirection;
        }
    }

    private void move(double value, Direction direction) {
        timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
        switch (direction) {
            case UP:
                super.y -= value;
                super.img = (Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, timeAnimation, timeRunAnimation)).getFxImage();
                break;
            case DOWN:
                super.y += value;
                super.img = (Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, timeAnimation, timeRunAnimation)).getFxImage();
                break;
            case LEFT:
                super.x -= value;
                super.img = (Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, timeAnimation, timeRunAnimation)).getFxImage();
                break;
            case RIGHT:
                super.x += value;
                super.img = (Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, timeAnimation, timeRunAnimation)).getFxImage();
                break;
        }
    }


    public void killed() {
        timeAnimation = 0;
        isKilled = true;
        SFX.playSFX(SFX.enemyDies_media);
    }
}
