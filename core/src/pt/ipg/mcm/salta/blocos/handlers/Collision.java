package pt.ipg.mcm.salta.blocos.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;

public enum Collision {
  FOOT, APPLE;

  public boolean isInCollision(Fixture fixture) {
    if (fixture == null) {
      return false;
    }
    return this.equals(fixture.getUserData());
  }

  public boolean isInCollision(Fixture fixtureA, Fixture fixtureB) {
    Gdx.app.log("collision foot?", isInCollision(fixtureA)+":"+ isInCollision(fixtureB));
    return isInCollision(fixtureA) || isInCollision(fixtureB);
  }
}
