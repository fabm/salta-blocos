package pt.ipg.mcm.salta.blocos.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pt.ipg.mcm.salta.blocos.handlers.DynamicCam;
import pt.ipg.mcm.salta.blocos.handlers.GameStateManager;
import pt.ipg.mcm.salta.blocos.main.Game;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected Game game;
	
	protected SpriteBatch sb;
	protected ShapeRenderer sr;
	protected DynamicCam dynCam;
	protected OrthographicCamera staticCam;
	
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		sr = game.getShapeRenderer();
		dynCam = game.getDynCam();
		staticCam = game.getStaticCam();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
}
