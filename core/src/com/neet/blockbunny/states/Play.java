package com.neet.blockbunny.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.neet.blockbunny.entities.ApplesContainer;
import com.neet.blockbunny.entities.GameMap;
import com.neet.blockbunny.entities.Player;
import com.neet.blockbunny.entities.Quadro;
import com.neet.blockbunny.handlers.BBContactListener;
import com.neet.blockbunny.handlers.BBInput;
import com.neet.blockbunny.handlers.Background;
import com.neet.blockbunny.handlers.GameStateManager;
import com.neet.blockbunny.main.Game;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

public class Play extends GameState {

  private final Player player;
  private World world;
  private BBContactListener cl;
  private ApplesContainer applesContainer;

  private Background background;
  private Quadro quadro;
  private GameMap gameMap;


  public static int level;

  public Play(GameStateManager gsm) {

    super(gsm);
    // box 2d
    world = new World(new Vector2(0, -10f), true);
    gameMap = new GameMap(world, level);

    applesContainer = gameMap.createApplesContainet();

    cl = new BBContactListener(applesContainer);
    world.setContactListener(cl);

    dynCam.setBounds(0, gameMap.bounds());

    // create backgrounds
    Texture bgs = Game.res.getTexture("montanha");
    TextureRegion mountains;

    mountains = new TextureRegion(Game.res.getTexture("montanha"), 0, 0, 320, 240);
    background = new Background(mountains, dynCam, 0f);

    player = new Player(world, gameMap.bounds(), cl);

    quadro = new Quadro(player.getBody().getFixtureList().get(1).getFilterData());


  }


  public void handleInput() {
    // keyboard input
    if (BBInput.isPressed(BBInput.BUTTON1)) {
      player.jump();
    }
    if (BBInput.isPressed(BBInput.BUTTON2)) {
      player.switchBlock();
    }

    // mouse/touch input for android
    // left side of screen to switch blocks
    // right side of screen to jump
    if (BBInput.isPressed()) {
      if (BBInput.x < Gdx.graphics.getWidth() / 2) {
        player.jump();
      } else {
        player.switchBlock();
      }
    }
  }


  public void update(float dt) {

    handleInput();

    world.step(Game.STEP, 1, 1);

    applesContainer.update(dt);

    State state = player.update(dt);

    gsm.setState(state);
  }

  public void render() {

    // camera follow player
    dynCam.setPosition(player.getPosition().x * PPM + Game.V_WIDTH / 4, Game.V_HEIGHT / 2);
    dynCam.update();

    // draw bgs
    sb.setProjectionMatrix(staticCam.combined);

    background.render(sb);

    // draw tilemap
    MapRenderer tmRenderer = gameMap.getTiledMapRenderer();
    tmRenderer.setView(dynCam);
    tmRenderer.render();

    // draw player
    sb.setProjectionMatrix(dynCam.combined);
    player.render(sb);

    applesContainer.render(sb);

    // draw hud
    sb.setProjectionMatrix(staticCam.combined);
    quadro.render(sb);


  }

  public void dispose() {
  }

}