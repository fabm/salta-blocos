package pt.ipg.mcm.jumper.blocks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neet.blockbunny.main.Game;

public class DesktopLauncher {
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    new LwjglApplication(new Game(), config);
  }
}
