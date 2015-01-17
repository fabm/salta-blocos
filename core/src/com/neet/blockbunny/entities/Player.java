package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.blockbunny.main.Game;

public class Player extends B2DSprite {
	
	private int numCrystals;
	private int totalCrystals;
	
	public Player(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("bunny");
		Texture tex1 = Game.res.getTexture("runningGrant");
		TextureRegion[] sprites = new TextureRegion[15];

		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
		}
		
		animation.setFrames(sprites, 1 / 12f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
		
	}
	
	public void collectCrystal() { numCrystals++; }
	public int getNumCrystals() { return numCrystals; }
	
	public void setTotalCrystals(int i) { totalCrystals = i; }
	public int getTotalCrystals() { return totalCrystals; }
	
}
