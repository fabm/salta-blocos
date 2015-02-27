package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.neet.blockbunny.handlers.Animation;
import com.neet.blockbunny.handlers.B2DVars;

public abstract class B2DSprite {

  protected Body body;
  protected float width;
  protected float height;

  protected abstract TextureRegion getTextureRegion();


  public void render(SpriteBatch sb) {
    sb.begin();
    sb.draw(getTextureRegion(), (getBody().getPosition().x * B2DVars.PPM - width / 2), (int) (getBody().getPosition().y * B2DVars.PPM - height / 2));
    sb.end();
  }

  public Body getBody() {
    return body;
  }

  public Vector2 getPosition() {
    return body.getPosition();
  }
}
