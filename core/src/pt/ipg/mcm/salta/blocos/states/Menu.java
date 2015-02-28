package pt.ipg.mcm.salta.blocos.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import pt.ipg.mcm.salta.blocos.entities.Player;
import pt.ipg.mcm.salta.blocos.handlers.Animation;
import pt.ipg.mcm.salta.blocos.handlers.BBInput;
import pt.ipg.mcm.salta.blocos.handlers.Background;
import pt.ipg.mcm.salta.blocos.handlers.GameButton;
import pt.ipg.mcm.salta.blocos.handlers.GameStateManager;
import pt.ipg.mcm.salta.blocos.main.Game;
import pt.ipg.mcm.salta.blocos.entities.State;

public class Menu extends GameState {


  private Background bg;
  private Animation animation;
  private GameButton playButton;
  private Texture title;

  private World world;

  public Menu(GameStateManager gsm) {

    super(gsm);
    Texture tex = Game.res.getTexture("menu");
    title = Game.res.getTexture("title");
    bg = new Background(new TextureRegion(tex), dynCam, 1f);
    bg.setVector(-50, 0);

    animation = new Animation(Player.getPlayerTextures(), 1 / 20f);

    tex = Game.res.getTexture("quadro");
    playButton = new GameButton(new TextureRegion(tex, 0, 34, 58, 27), 160, 100, dynCam);

    dynCam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

    world = new World(new Vector2(0, -9.8f * 5), true);
  }

  public void handleInput() {


    if (playButton.isClicked() || BBInput.isPressed(BBInput.ENTER_MENU_BUTTON)) {
      gsm.setState(State.LEVEL_SELECT);
    }

  }


  public void update(float dt) {

    handleInput();

    world.step(dt / 5, 8, 3);

    bg.update(dt);
    animation.update(dt);

    playButton.update();

  }

  public void render() {

    sb.setProjectionMatrix(dynCam.combined);

    bg.render(sb);

    playButton.render(sb);

    sb.begin();
    sb.draw(animation.getFrame(), 146, 31);
    sb.draw(title, 10, 150);
    sb.end();

  }

  public void dispose() {
    // everything is in the resource manager pt.ipg.mcm.salta.blocos.handlers.Content
  }

}
