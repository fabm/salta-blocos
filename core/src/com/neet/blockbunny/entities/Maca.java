package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.blockbunny.main.Game;

public class Maca extends B2DSprite {
	
	public Maca(Body body) {
		
		super(body);

		Texture tex = Game.res.getTexture("quadro");
		TextureRegion[] sprites = new TextureRegion[]{new TextureRegion(tex, 80, 0, 16, 18)};
		animation.setFrames(sprites, 1f);
    animation.setNome("maca");
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
		
	}
	
}
