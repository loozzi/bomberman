package uet.oop.bomberman.common;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonImage {
  private Button btn;

  /**
   * constructor
   * @param srcImage source of image
   * @param x point x
   * @param y point y
   * @param w width
   * @param h height
   */
  public ButtonImage(String srcImage, int x, int y, int w, int h, String css, EventHandler<ActionEvent> event) {
    // Create Setting button
    Image img = new Image(srcImage);
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(w);
    imgView.setFitHeight(h);
    this.btn = new Button();
    this.btn.setGraphic(imgView);
    this.btn.setLayoutX(x);
    this.btn.setLayoutY(y);
    this.btn.setPrefSize(w, h);
    this.btn.setOnAction(event);
    this.btn.setStyle(css);
  }

  public Button getButton() {
    return this.btn;
  }
}
