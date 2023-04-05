package uet.oop.bomberman.common;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;

public class SFX {
    private static int volumeSFX = 50;
    private static int volumeMusic = 50;
    private static MediaPlayer sfxPlayer;
    private static MediaPlayer musicPlayer;

    // init file path variable
    public static final Media audience_media = new Media(Paths.get("res/audio/Audience.mp3").toUri().toString());
    public static final Media bombExplodes_media = new Media(Paths.get("res/audio/Bomb Explodes.mp3").toUri().toString());
    public static final Media bombermanDies_media = new Media(Paths.get("res/audio/Bomberman Dies.mp3").toUri().toString());
    public static final Media bombermanMusic_media = new Media(Paths.get("res/audio/Bomberman_BGM_1.mp3").toUri().toString());
    public static final Media combo_media = new Media(Paths.get("res/audio/Combo.mp3").toUri().toString());
    public static final Media ending_media = new Media(Paths.get("res/audio/Ending - Building Crumbling 2.mp3").toUri().toString());
    public static final Media enemyDies_media = new Media(Paths.get("res/audio/Enemy Dies.mp3").toUri().toString());
    public static final Media gameOver_media = new Media(Paths.get("res/audio/Game_over.mp3").toUri().toString());
    public static final Media itemGet_media = new Media(Paths.get("res/audio/Item Get.mp3").toUri().toString());
    public static final Media mookClaw_media = new Media(Paths.get("res/audio/Mook Claw.mp3").toUri().toString());
    public static final Media mookDoorOpenClose_media = new Media(Paths.get("res/audio/Mook Door Open Close.mp3").toUri().toString());
    public static final Media pause_media = new Media(Paths.get("res/audio/Pause.mp3").toUri().toString());
    public static final Media placeBomb_media = new Media(Paths.get("res/audio/Place Bomb.mp3").toUri().toString());
    public static final Media punchBomb_media = new Media(Paths.get("res/audio/Punch Bomb.mp3").toUri().toString());
    public static final Media skullItem_media = new Media(Paths.get("res/audio/Skull Item.mp3").toUri().toString());
    public static final Media spidererWalking_media = new Media(Paths.get("res/audio/Spiderer Walking.mp3").toUri().toString());
    public static final Media stageClear_media = new Media(Paths.get("res/audio/Stage Clear.mp3").toUri().toString());
    public static final Media stageIntro_media = new Media(Paths.get("res/audio/Stage Intro.mp3").toUri().toString());
    public static final Media timeUp_media = new Media(Paths.get("res/audio/Time Up (Full).mp3").toUri().toString());
    public static final Media titleScreenCursor_media = new Media(Paths.get("res/audio/Title Screen Cursor.mp3").toUri().toString());
    public static final Media titleScreenSelect_media = new Media(Paths.get("res/audio/Title Screen Select.mp3").toUri().toString());
    public static final Media uiteruVDamage_media = new Media(Paths.get("res/audio/Uiteru V Damage.mp3").toUri().toString());
    public static final Media uiteruVItemToss_media = new Media(Paths.get("res/audio/Uiteru V Item Toss.mp3").toUri().toString());
    public static final Media walking1_media = new Media(Paths.get("res/audio/Walking 1.mp3").toUri().toString());
    public static final Media walking2_media = new Media(Paths.get("res/audio/Walking 2.mp3").toUri().toString());

    // init media player from file path
//  public static final MediaPlayer Audience = new MediaPlayer(audience_media);
//  public static final MediaPlayer BombExplodes = new MediaPlayer(bombExplodes_media);
//  public static final MediaPlayer BombermanDies = new MediaPlayer(bombermanDies_media);
//  public static final MediaPlayer BombermanMusic = new MediaPlayer(bombermanMusic_media);
//  public static final MediaPlayer Combo = new MediaPlayer(combo_media);
//  public static final MediaPlayer Ending = new MediaPlayer(ending_media);
//  public static final MediaPlayer EnemyDies = new MediaPlayer(enemyDies_media);
//  public static final MediaPlayer GameOver = new MediaPlayer(gameOver_media);
//  public static final MediaPlayer ItemGet = new MediaPlayer(itemGet_media);
//  public static final MediaPlayer MookClaw = new MediaPlayer(mookClaw_media);
//  public static final MediaPlayer MookDoorOpenClose = new MediaPlayer(mookDoorOpenClose_media);
//  public static final MediaPlayer Pause = new MediaPlayer(pause_media);
//  public static final MediaPlayer PlaceBomb = new MediaPlayer(placeBomb_media);
//  public static final MediaPlayer PunchBomb = new MediaPlayer(punchBomb_media);
//  public static final MediaPlayer SkullItem = new MediaPlayer(skullItem_media);
//  public static final MediaPlayer SpidererWalking = new MediaPlayer(spidererWalking_media);
//  public static final MediaPlayer StageClear = new MediaPlayer(stageClear_media);
//  public static final MediaPlayer StageIntro = new MediaPlayer(stageIntro_media);
//  public static final MediaPlayer TimeUp = new MediaPlayer(timeUp_media);
//  public static final MediaPlayer TitleScreenCursor = new MediaPlayer(titleScreenCursor_media);
//  public static final MediaPlayer TitleScreenSelect = new MediaPlayer(titleScreenSelect_media);
//  public static final MediaPlayer UiteruVDamage = new MediaPlayer(uiteruVDamage_media);
//  public static final MediaPlayer UiteruVItemToss = new MediaPlayer(uiteruVItemToss_media);
//  public static final MediaPlayer Walking1 = new MediaPlayer(walking1_media);
//  public static final MediaPlayer Walking2 = new MediaPlayer(walking2_media);

    public static int getVolumeMusic() {
        return volumeMusic;
    }

    public static int getVolumeSFX() {
        return volumeSFX;
    }

    public static void setVolumeSFX(int value) {
        volumeSFX = value;
        try {
            sfxPlayer.setVolume((double) volumeSFX / 100);
        } catch (Exception ignored) {
            // todo
        }
    }

    public static void setVolumeMusic(int value) {
        volumeMusic = value;
        try {
            musicPlayer.setVolume((double) volumeMusic / 100);
        } catch (Exception ignored) {
            // todo
        }
    }

    public static void playSFX(Media sfxMedia) {
        try {
            sfxPlayer.dispose();
        } catch (Exception ignore) {

        }
        sfxPlayer = new MediaPlayer(sfxMedia);
        sfxPlayer.play();
        sfxPlayer.setVolume((double) volumeSFX / 100);
    }

    public static void playMusic(Media musicMedia) {
        musicPlayer = new MediaPlayer(musicMedia);
        musicPlayer.play();
        musicPlayer.setVolume((double) volumeMusic / 100);
    }

    public static void pauseSFX() {
        sfxPlayer.pause();
    }

    public static void pauseMusic() {
        musicPlayer.pause();
    }
}
