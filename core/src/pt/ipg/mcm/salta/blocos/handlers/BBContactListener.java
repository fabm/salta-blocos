package pt.ipg.mcm.salta.blocos.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import pt.ipg.mcm.salta.blocos.entities.ApplesContainer;

public class BBContactListener implements ContactListener, FloorContact {

  private int numFootContacts;
  private ApplesContainer applesContainer;

  public BBContactListener(ApplesContainer applesContainer) {
    super();
    this.applesContainer = applesContainer;
  }

  public void beginContact(Contact contact) {

    if (contact == null) {
      return;
    }

    Fixture fa = contact.getFixtureA();
    Fixture fb = contact.getFixtureB();


    if (fa == null || fb == null) {
      return;
    }

    if (Collision.APPLE.isInCollision(fa)) {
      applesContainer.add(fa.getBody());
    }
    if (Collision.APPLE.isInCollision(fb)) {
      applesContainer.add(fb.getBody());
    }
    if (Collision.FOOT.isInCollision(fa, fb)) {
      numFootContacts++;
    }
  }

  public void endContact(Contact contact) {

    Fixture fa = contact.getFixtureA();
    Fixture fb = contact.getFixtureB();

    if (fa == null || fb == null) {
      return;
    }

    if (Collision.FOOT.isInCollision(fa, fb)) {
      Gdx.app.log("isInFloor", "decrement");
      numFootContacts--;
    }

  }

  public void preSolve(Contact c, Manifold m) {
  }

  public void postSolve(Contact c, ContactImpulse ci) {
  }

  @Override
  public boolean isInFloor() {
    Gdx.app.log("isInFloor", numFootContacts + "");
    return numFootContacts > 0;
  }
}
