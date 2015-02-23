package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neet.blockbunny.handlers.B2DVars;
import com.neet.blockbunny.main.Game;

public class Quadro {
	
	private Player player;
	
	private TextureRegion container;
	private TextureRegion[] blocks;
	private TextureRegion maca;
	private BitmapFont font;
	
	public Quadro(Player player) {
		
		this.player = player;
		
		Texture tex = Game.res.getTexture("quadro");
		
		container = new TextureRegion(tex, 0, 0, 32, 32);
		
		blocks = new TextureRegion[3];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16);
		}
		
		maca = new TextureRegion(tex, 80, 0, 16, 18);
		
		font = new BitmapFont();

	}
	
	public void render(SpriteBatch sb) {
		
		sb.begin();
		
		// draw container
		sb.draw(container, 32, 200);
		
		// draw blocks
		short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;
		if((bits & B2DVars.BIT_RED_BLOCK) != 0) {
			sb.draw(blocks[0], 40, 208);
		}
		else if((bits & B2DVars.BIT_GREEN_BLOCK) != 0) {
			sb.draw(blocks[1], 40, 208);
		}
		else if((bits & B2DVars.BIT_BLUE_BLOCK) != 0) {
			sb.draw(blocks[2], 40, 208);
		}
		
		// draw maca
		sb.draw(maca, 100, 208);


		font.draw(sb,String.valueOf(player.getNumMacas()),122,221);
		sb.end();
		
	}
	
}
