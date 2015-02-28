package pt.ipg.mcm.salta.blocos.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pt.ipg.mcm.salta.blocos.main.Game;

public class Background {
	
	private Texture image;
	private OrthographicCamera gameCam;
	private float scale;
	
	private float x;
	private float y;
	private int numDrawX;
	private int numDrawY;
	
	private float dx;
	private float dy;
	
	public Background(TextureRegion image, OrthographicCamera cam, float scale) {
		this.image = image.getTexture();
		this.gameCam = cam;
		this.scale = scale;
		numDrawX = Game.V_WIDTH / image.getRegionWidth() + 1;
		numDrawY = Game.V_HEIGHT / image.getRegionHeight() + 1;
	}
	
	public void setVector(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(float dt) {
		x += (dx * scale) * dt;
		y += (dy * scale) * dt;
	}

	public void render(SpriteBatch sb) {
		
		float x = ((this.x + gameCam.viewportWidth / 2 - gameCam.position.x) * scale) % image.getWidth();
		float y = ((this.y + gameCam.viewportHeight / 2 - gameCam.position.y) * scale) % image.getHeight();
		
		sb.begin();

		int deslocColuna = x > 0 ? -1 : 0;
		int deslocLinha = y > 0 ? -1 : 0;
		for(int row = 0; row < numDrawY; row++) {
			for(int col = 0; col < numDrawX; col++) {
				sb.draw(image, x + (col + deslocColuna) * image.getWidth(), y + (deslocLinha + row) * image.getHeight());
			}
		}

		sb.end();
		
	}
	
}
