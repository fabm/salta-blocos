package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.neet.blockbunny.main.Game;

public class Apple extends B2DSprite {

  private final TextureRegion reg;

  public Apple() {
		
		Texture tex = Game.res.getTexture("quadro");
		reg = new TextureRegion(tex, 80, 0, 16, 18);

		width = reg.getRegionWidth();
		height = reg.getRegionHeight();
		
	}


  @Override
  protected TextureRegion getTextureRegion() {
    return reg;
  }


}
