package pt.ipg.mcm.salta.blocos.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pt.ipg.mcm.salta.blocos.handlers.BBInput;
import pt.ipg.mcm.salta.blocos.handlers.BBInputProcessor;
import pt.ipg.mcm.salta.blocos.handlers.DynamicCam;
import pt.ipg.mcm.salta.blocos.handlers.Content;
import pt.ipg.mcm.salta.blocos.handlers.GameStateManager;

public class Game implements ApplicationListener {

  public static final String TITLE = "Salta Blocos";
  public static final int V_WIDTH = 320;
  public static final int V_HEIGHT = 240;
  public static final float STEP = 1 / 60f;
  public static final String MUSICA = "musica";

  private SpriteBatch sb;
  private ShapeRenderer sr;
  private DynamicCam dynCam;
  private OrthographicCamera staticCam;

  private GameStateManager gsm;

  public static Content res;

  public void create() {
    Gdx.app.setLogLevel(Application.LOG_DEBUG);

    Gdx.input.setInputProcessor(new BBInputProcessor());

    res = new Content();
    res.loadTexture("res/images/montanha.png");
    res.loadTexture("res/images/menu.png");
    res.loadTexture("res/images/title.png");
    res.loadTexture("res/images/quadro.png");
    res.loadTexture("res/images/runningGrant.png");
    res.loadTexture("res/images/crystal.png");

    res.loadSound("res/sfx/jump.mp3");
    res.loadSound("res/sfx/bite.mp3");
    res.loadSound("res/sfx/victory.mp3");
    res.loadSound("res/sfx/die.mp3");
    res.loadSound("res/sfx/victory.mp3");
    res.loadSound("res/sfx/change.mp3");
    res.loadSound("res/sfx/select.mp3");

    res.loadMusic("res/music/musica.mp3");
    res.getMusic(MUSICA).setLooping(true);
    res.getMusic(MUSICA).setVolume(0.5f);
    res.getMusic(MUSICA).play();

    dynCam = new DynamicCam();
    dynCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
    staticCam = new OrthographicCamera();
    staticCam.setToOrtho(false, V_WIDTH, V_HEIGHT);

    sb = InstanceProvider.get().getSpriteBatchProvider().get();
    sr = InstanceProvider.get().getShapeRendererProvider().get();

    gsm = new GameStateManager(this);

  }

  public void render() {

    Gdx.graphics.setTitle(TITLE);

    gsm.update(Gdx.graphics.getDeltaTime());
    gsm.render();
    BBInput.update();

  }

  public void dispose() {
    res.removeAll();
  }

  public void resize(int w, int h) {
  }

  public void pause() {
  }

  public void resume() {
  }

  public SpriteBatch getSpriteBatch() {
    return sb;
  }

  public ShapeRenderer getShapeRenderer() {
    return sr;
  }

  public DynamicCam getDynCam() {
    return dynCam;
  }

  public OrthographicCamera getStaticCam() {
    return staticCam;
  }

}
