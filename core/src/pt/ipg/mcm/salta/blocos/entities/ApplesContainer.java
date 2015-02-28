package pt.ipg.mcm.salta.blocos.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import pt.ipg.mcm.salta.blocos.main.Game;

public class ApplesContainer {
  private Array<Body> bodies;
  private Array<Apple> apples;
  private int count;
  private World world;

  public ApplesContainer(World world,Array<Apple> apples) {
    this.world = world;
    this.apples = apples;
    bodies = new Array<Body>();
    count = 0;
  }

  public void add(Body body) {
    bodies.add(body);
  }

  public void update(float dt){
    for (int i = 0; i < bodies.size; i++) {
      Body b = bodies.get(i);
      apples.removeValue((Apple) b.getUserData(), true);
      world.destroyBody(bodies.get(i));
      count++;
      Game.res.getSound("bite").play();
    }
    bodies.clear();


  }

  public void render(SpriteBatch sb) {
    for (int i = 0; i < apples.size; i++) {
      apples.get(i).render(sb);
    }
  }
}
