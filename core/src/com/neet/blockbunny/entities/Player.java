package com.neet.blockbunny.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.neet.blockbunny.handlers.Animation;
import com.neet.blockbunny.handlers.B2DVars;
import com.neet.blockbunny.handlers.Collision;
import com.neet.blockbunny.handlers.FloorContact;
import com.neet.blockbunny.main.Game;
import com.neet.blockbunny.states.State;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

public class Player extends B2DSprite {


  private final Animation animation;
  private final int bounds;
  private final FloorContact floorContact;

  public Player(World world, int bounds, FloorContact floorContact) {
    this.bounds = bounds;
    this.floorContact = floorContact;
    animation = new Animation();
    Texture tex = Game.res.getTexture("runningGrant");
    TextureRegion[] sprites = new TextureRegion[15];

    for (int i = 0; i < sprites.length; i++) {
      sprites[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
    }

    animation.setFrames(getPlayerTextures(), 1 / 24f);

    width = sprites[0].getRegionWidth();
    height = sprites[0].getRegionHeight();

    create(world);

  }

  @Override
  protected TextureRegion getTextureRegion() {
    return animation.getFrame();
  }

  public State update(float dt) {
    State state = null;
    if (getBody().getPosition().x * PPM > bounds) {
      Game.res.getSound("victory").play();
      state = State.LEVEL_SELECT;
    }

    if (getBody().getPosition().y < 0) {
      Game.res.getSound("die").play();
      state = State.MENU;
    }

    if (getBody().getLinearVelocity().x < 0.1f) {
      Game.res.getSound("die").play();
      state = State.MENU;
    }

    animation.update(dt);
    return state;
  }

  public void switchBlock() {

    // get player foot mask bits
    Filter filter = getBody().getFixtureList().get(1).getFilterData();

    // switch to next block bit
    // red -> green -> blue
    if (filter.maskBits == B2DVars.BIT_RED_BLOCK) {
      filter.maskBits = B2DVars.BIT_GREEN_BLOCK;
    } else if (filter.maskBits == B2DVars.BIT_GREEN_BLOCK) {
      filter.maskBits = B2DVars.BIT_BLUE_BLOCK;
    } else if (filter.maskBits == B2DVars.BIT_BLUE_BLOCK) {
      filter.maskBits = B2DVars.BIT_RED_BLOCK;
    }

    // set player maca mask bits
    getBody().getFixtureList().get(1).setFilterData(filter);
    filter.maskBits |= B2DVars.BIT_APPLE;
    getBody().getFixtureList().get(0).setFilterData(filter);

    // play sound
    Game.res.getSound("change").play();

  }


  protected Body create(World world) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.position.set(0 / PPM, 200 / PPM);
    bdef.fixedRotation = true;
    bdef.linearVelocity.set(1f, 0f);

    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(13 / PPM, 13 / PPM);

    // create fixturedef for player collision box
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.density = 1;
    fdef.friction = 0;
    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
    fdef.filter.maskBits = B2DVars.BIT_RED_BLOCK | B2DVars.BIT_APPLE;

    // create player collision box fixture
    body.createFixture(fdef);
    shape.dispose();

    // create box shape for player foot
    shape = new PolygonShape();
    shape.setAsBox(13 / PPM, 3 / PPM, new Vector2(0, -13 / PPM), 0);

    // create fixturedef for player foot
    fdef.shape = shape;
    fdef.isSensor = true;
    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
    fdef.filter.maskBits = B2DVars.BIT_RED_BLOCK;

    // create player foot fixture
    body.createFixture(fdef).setUserData(Collision.FOOT);

    shape.dispose();

    MassData md = body.getMassData();
    md.mass = .8f;
    body.setMassData(md);
    return body;
  }

  public static TextureRegion[] getPlayerTextures() {
    Texture tex = Game.res.getTexture("runningGrant");
    TextureRegion[] sprites = new TextureRegion[12];
    for (int i = 0; i < sprites.length; i++) {
      sprites[i] = new TextureRegion(tex, i % 12 * 42, 0, 42, 73);
    }
    return sprites;
  }

  public void jump() {
    if(floorContact.isInFloor()){
      getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
      getBody().applyForceToCenter(0, 200, true);
      Game.res.getSound("jump").play();
    }
  }
}
