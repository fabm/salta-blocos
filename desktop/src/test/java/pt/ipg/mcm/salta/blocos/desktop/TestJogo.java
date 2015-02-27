package pt.ipg.mcm.salta.blocos.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.neet.blockbunny.handlers.B2DVars;
import com.neet.blockbunny.main.Game;
import com.neet.blockbunny.main.InstanceProvider;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestJogo {
  @Test
  public void testVars() {
    Assert.assertEquals(2, B2DVars.BIT_PLAYER);
    Assert.assertEquals(4, B2DVars.BIT_RED_BLOCK);
    Assert.assertEquals(8, B2DVars.BIT_GREEN_BLOCK);
    Assert.assertEquals(16, B2DVars.BIT_BLUE_BLOCK);
    Assert.assertEquals(32, B2DVars.BIT_APPLE);
  }

  @Test
  public void testGame() throws InterruptedException {
    InstanceProvider.set(new InstanceProviderMock());

    Gdx.gl = Mockito.mock(GL20.class);
    Gdx.gl20 = Mockito.mock(GL20.class);
    Gdx.files = Mockito.mock(Files.class);
    Gdx.graphics = Mockito.mock(Graphics.class);

    Mockito.when(Gdx.graphics.getHeight()).thenReturn(200);
    Mockito.when(Gdx.graphics.getWidth()).thenReturn(320);
    Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(1.0f);

    //Mockito.when(Gdx.files.internal("res/images/montanha.png")).thenReturn(Mockito.mock(FileHandle.class));
    Mockito.when(Gdx.gl.glGenTexture()).thenReturn(0);

    final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    config.renderInterval = 1;
    HeadlessApplicationTest headlessApplication = new HeadlessApplicationTest(new Game());

    final Thread aacordar = Thread.currentThread();

    headlessApplication.postRunnable(new Runnable() {
      @Override
      public void run() {
        synchronized (aacordar) {
          aacordar.notify();
        }
      }
    });

    synchronized (aacordar) {
      aacordar.wait();
    }

  }


}
