package com.neet.blockbunny.states;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.neet.blockbunny.entities.Player;
import com.neet.blockbunny.handlers.Animation;
import com.neet.blockbunny.handlers.BBInput;
import com.neet.blockbunny.handlers.Background;
import com.neet.blockbunny.handlers.GameButton;
import com.neet.blockbunny.handlers.GameStateManager;
import com.neet.blockbunny.main.Game;

public class Menu extends GameState {
	
	private boolean debug = false;
	
	private Background bg;
	private Animation animation;
	private GameButton playButton;
	private Texture title;
	
	private World world;

	public Menu(GameStateManager gsm) {
		
		super(gsm);
		Texture tex = Game.res.getTexture("menu");
		title = Game.res.getTexture("title");
		bg = new Background(new TextureRegion(tex), camMovel, 1f);
		bg.setVector(-40, 0);

		animation = new Animation(Player.getPlayerTextures(), 1 / 20f);
		
		tex = Game.res.getTexture("quadro");
		playButton = new GameButton(new TextureRegion(tex, 0, 34, 58, 27), 160, 100, camMovel);
		
		camMovel.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
	}

	public void handleInput() {


		if(playButton.isClicked()||BBInput.isPressed(BBInput.ENTER_MENU_BUTTON)) {
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
		
		sb.setProjectionMatrix(camMovel.combined);
		
		// draw background
		bg.render(sb);

		// draw button
		playButton.render(sb);
		
		// draw bunny
		sb.begin();
		sb.draw(animation.getFrame(), 146, 31);
		sb.draw(title,10,150);
		sb.end();

	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
