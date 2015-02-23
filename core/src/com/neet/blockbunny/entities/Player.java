package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.blockbunny.main.Game;

public class Player extends B2DSprite {

  private int numMacas;

  public Player(Body body) {

    super(body);

    Texture tex = Game.res.getTexture("runningGrant");
    TextureRegion[] sprites = new TextureRegion[15];

    for (int i = 0; i < sprites.length; i++) {
      sprites[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
    }

    animation.setFrames(getPlayerTextures(), 1 / 24f);
    animation.setNome("player");

    width = sprites[0].getRegionWidth();
    height = sprites[0].getRegionHeight();

  }

  public static TextureRegion[] getPlayerTextures() {
    Texture tex = Game.res.getTexture("runningGrant");
    TextureRegion[] sprites = new TextureRegion[12];
    for (int i = 0; i < sprites.length; i++) {
      sprites[i] = new TextureRegion(tex, i % 12 * 42, 0, 42, 73);
    }
    return sprites;
  }

  public void collectMacas() {
    numMacas++;
  }

  public int getNumMacas() {
    return numMacas;
  }

}
