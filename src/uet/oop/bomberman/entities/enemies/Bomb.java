package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameManagement;
import uet.oop.bomberman.common.SFX;
import uet.oop.bomberman.entities.BombEffect;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private final int MAX_TIME_ANIMATION = 6000;
    private int timeAnimation = 0;
    private int timeRunAnimation = 40;
    private boolean[] effects = {false, false, false, false};
    private boolean bombItemIsActive = false;
    private boolean isExploded = false;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
        super.img = (Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeAnimation, timeRunAnimation)).getFxImage();
        if (timeAnimation == 200) {
            SFX.playSFX(SFX.bombExplodes_media);
        }
        if (timeAnimation > 240) {
            super.img = (Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeAnimation, timeRunAnimation)).getFxImage();
            exploded();
        }
    }

    public void exploded() {
        if (!isExploded) {
            GameManagement.removeBombs(this);
            BombEffect bombEffect = new BombEffect(
                    (int) Math.round(this.getX()/32),
                    (int) Math.round(this.getY()/32),
                    Sprite.bomb_exploded,
                    Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2
            );
            GameManagement.addBombs(bombEffect);

            isExploded = true;
        }
        // 0-tren 1-phai 2-duoi 3-trai
        if (!effects[0]) {
            BombEffect bombEffect = new BombEffect(
                    (int) Math.round(this.getX()/32),
                    (int) Math.round(this.getY()/32) - 1,
                    Sprite.explosion_vertical_top_last,
                    Sprite.explosion_vertical_top_last1,
                    Sprite.explosion_vertical_top_last2
            );
            if (bombEffect.checkExplode() == null && bombItemIsActive) {
                GameManagement.addBombs(new BombEffect(
                        (int) Math.round(this.getX()/32),
                        (int) Math.round(this.getY()/32) - 2,
                        Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2
                ));
                bombEffect.changeAnimation(
                        Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2
                );
            }
            GameManagement.addBombs(bombEffect);
            effects[0] = true;
        }
        if (!effects[1]) {
            BombEffect bombEffect = new BombEffect(
                    (int) Math.round(this.getX()/32) + 1,
                    (int) Math.round(this.getY()/32),
                    Sprite.explosion_horizontal_right_last,
                    Sprite.explosion_horizontal_right_last1,
                    Sprite.explosion_horizontal_right_last2
            );
            if (bombEffect.checkExplode() == null && bombItemIsActive) {
                GameManagement.addBombs(new BombEffect(
                        (int) Math.round(this.getX()/32) + 2,
                        (int) Math.round(this.getY()/32),
                        Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2
                ));
                bombEffect.changeAnimation(
                        Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2
                );
            }
            GameManagement.addBombs(bombEffect);
            effects[1] = true;
        }
        if (!effects[2]) {
            BombEffect bombEffect = new BombEffect(
                    (int) Math.round(this.getX()/32),
                    (int) Math.round(this.getY()/32) + 1,
                    Sprite.explosion_vertical_down_last,
                    Sprite.explosion_vertical_down_last1,
                    Sprite.explosion_vertical_down_last2
            );
            if (bombEffect.checkExplode() == null && bombItemIsActive) {
                GameManagement.addBombs(new BombEffect(
                        (int) Math.round(this.getX()/32),
                        (int) Math.round(this.getY()/32) + 2,
                        Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1,
                        Sprite.explosion_vertical_down_last2
                ));
                bombEffect.changeAnimation(
                        Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2
                );
            }
            GameManagement.addBombs(bombEffect);
            effects[2] = true;
        }
        if (!effects[3]) {
            BombEffect bombEffect = new BombEffect(
                    (int) Math.round(this.getX()/32) - 1,
                    (int) Math.round(this.getY()/32),
                    Sprite.explosion_horizontal_left_last,
                    Sprite.explosion_horizontal_left_last1,
                    Sprite.explosion_horizontal_left_last2
            );
            if (bombEffect.checkExplode() == null && bombItemIsActive) {
                GameManagement.addBombs(new BombEffect(
                        (int) Math.round(this.getX()/32) - 2,
                        (int) Math.round(this.getY()/32),
                        Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2
                ));
                bombEffect.changeAnimation(
                        Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2
                );
            }
            GameManagement.addBombs(bombEffect);
            effects[3] = true;
        }
    }
}
