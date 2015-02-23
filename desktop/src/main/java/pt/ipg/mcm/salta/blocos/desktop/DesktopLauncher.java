package pt.ipg.mcm.salta.blocos.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neet.blockbunny.main.Game;
import com.neet.blockbunny.main.InstanceProvider;

public class DesktopLauncher {
  public static void main(String[] arg) {
    InstanceProvider.set(new InstanceProvider());
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    new LwjglApplication(new Game(), config);
  }
}
