package com.neet.blockbunny.states;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.neet.blockbunny.entities.B2DSprite;
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
	
	private World world;
	private Box2DDebugRenderer b2dRenderer;
	
	private Array<B2DSprite> blocks;
	
	public Menu(GameStateManager gsm) {
		
		super(gsm);
		
		Texture tex = Game.res.getTexture("menu");
		Texture tex1 = Game.res.getTexture("menu");
		bg = new Background(new TextureRegion(tex), cam, 1f);
		bg.setVector(-20, 0);
		
		tex = Game.res.getTexture("bunny");
		tex1 = Game.res.getTexture("runningGrant");
		TextureRegion[] reg = new TextureRegion[16];
		for(int i = 0; i < reg.length; i++) {
			reg[i] = new TextureRegion(tex1, i * 165, 0, 165, 290);
		}
		animation = new Animation(reg, 1 / 6f);
		
		tex = Game.res.getTexture("hud");
		playButton = new GameButton(new TextureRegion(tex, 0, 34, 58, 27), 160, 100, cam);
		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		//world = new World(new Vector2(0, 0), true);
		b2dRenderer = new Box2DDebugRenderer();
		
		createTitleBodies();
		
	}
	
	private void createTitleBodies() {
		
		Texture tex = Game.res.getTexture("hud");
		TextureRegion[] blockSprites = new TextureRegion[3];
		for(int i = 0; i < blockSprites.length; i++) {
			blockSprites[i] = new TextureRegion(tex, 58 + i * 5, 34, 5, 5);
		}
		blocks = new Array<B2DSprite>();
		
		int[][] spellBlock = {
			{1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
			{1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
			{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
			{1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1}
		};
		int[][] spellBunny = {
			{1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0},
			{1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
			{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0},
			{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
		};
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(2f / PPM, 2f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 29; col++) {
				if(spellBlock[row][col] == 0) continue;
				bdef.position.set((62 + col * 6 + col) / PPM, (180 + (5 - row) * 6) / PPM);
				Body tbbody = world.createBody(bdef);
				tbbody.createFixture(fdef);
				B2DSprite sprite = new B2DSprite(tbbody);
				sprite.setAnimation(blockSprites[MathUtils.random(2)], 0);
				blocks.add(sprite);
			}
		}
		
		// bottom blocks
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 29; col++) {
				if(spellBunny[row][col] == 0) continue;
				bdef.position.set((62 + col * 6 + col) / PPM, (140 + (5 - row) * 6) / PPM);
				Body bbbody = world.createBody(bdef);
				bbbody.createFixture(fdef);
				B2DSprite sprite = new B2DSprite(bbbody);
				sprite.setAnimation(blockSprites[MathUtils.random(2)], 0);
				blocks.add(sprite);
			}
		}
		
		shape.dispose();
		
	}
	
	public void handleInput() {


		// mouse/touch input
		if(playButton.isClicked()||BBInput.isPressed(BBInput.ENTER_MENU_BUTTON)) {
			Game.res.getSound("crystal").play();
			gsm.setState(State.LEVEL_SELECT);
		}


	}
	
	public void update(float dt) {
		
		handleInput();
		
		world.step(dt / 5, 8, 3);
		
		bg.update(dt);
		animation.update(dt);
		
		playButton.update(dt);
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		// draw background
		bg.render(sb);
		
		// draw button
		playButton.render(sb);
		
		// draw bunny
		sb.begin();
		sb.draw(animation.getFrame(), 146, 31);
		sb.end();
		
		// debug draw box2d
		if(debug) {
			cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
			b2dRenderer.render(world, cam.combined);
			cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		}
		
		// draw title
		for(int i = 0; i < blocks.size; i++) {
			blocks.get(i).render(sb);
		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
