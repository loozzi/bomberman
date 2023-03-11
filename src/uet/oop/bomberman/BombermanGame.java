package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.gui.InitApp;

public class BombermanGame extends Application {
  public static Stage primaryState;

  public static void main(String[] args) {
    Application.launch(BombermanGame.class);
  }

  @Override
  public void start(Stage stage) {
    primaryState = stage;

    InitApp.init();

//    primaryState.setResizable(false);
    primaryState.setTitle("Game Bomberman - UET OOP Project");
    primaryState.getIcons().setAll(new Image("/textures/icon.png"));
    primaryState.show();
  }
}
