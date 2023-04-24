package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected Image img;

    protected boolean isActive = true;
    protected boolean isBrokenBrick = false;

    //Item đã được nhặt hay chưa
    protected boolean picked_up = false;


    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getIsActve() {
        return isActive;
    }
    public boolean checkCollide(double _x, double _y, Entity entity) {
        return true;
    }
    public void setPicked_up() {
        this.picked_up = true;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public Image getImg() {
        return this.img;
    }

    public void setDisable() {
        isActive = false;
    }
    public void setActive() {
        isActive = true;
    }
}
