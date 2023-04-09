package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Item extends Entity {
    protected boolean pickedup = false;

    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void pick() {
        this.pickedup = true;
    }

    public boolean isPickedup() {
        return pickedup;
    }
}
