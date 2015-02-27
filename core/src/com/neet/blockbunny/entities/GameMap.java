package com.neet.blockbunny.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.neet.blockbunny.handlers.B2DVars;
import com.neet.blockbunny.handlers.Collision;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

public class GameMap {

  private World world;
  private int level;
  private int tileMapWidth;
  private int tileSize;
  private TiledMap tileMap;
  private Array<Apple> apples;
  private OrthogonalTiledMapRenderer tmRenderer;

  public GameMap(World world,int level) {
    this.world = world;
    this.level = level;
    createMap();
    createApples();
  }

  public int bounds(){
    return tileMapWidth * tileSize;
  }

  private void createMap() {

    // load tile map and map renderer
    try {
      tileMap = new TmxMapLoader().load("res/maps/level" + level + ".tmx");
    } catch (Exception e) {
      Gdx.app.error("level", "O nivel " + level + " não existe");
      Gdx.app.exit();
      return;
    }
    tileMapWidth = tileMap.getProperties().get("width", Integer.class);
    tileSize = tileMap.getProperties().get("tilewidth", Integer.class);
    tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

    // read each of the "red" "green" and "blue" layers
    TiledMapTileLayer layer;
    layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
    createBlocs(layer, B2DVars.BIT_RED_BLOCK);
    layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
    createBlocs(layer, B2DVars.BIT_GREEN_BLOCK);
    layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
    createBlocs(layer, B2DVars.BIT_BLUE_BLOCK);

  }


  private void createApples() {

    // create list of apples
    apples = new Array<Apple>();

    // get all apples in "apples" layer,
    // create bodies for each, and add them
    // to the apples list
    MapLayer ml = tileMap.getLayers().get("apples");
    if (ml == null) {
      return;
    }

    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;
    CircleShape shape = new CircleShape();
    shape.setRadius(8 / PPM);
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.isSensor = true;
    fdef.filter.categoryBits = B2DVars.BIT_APPLE;
    fdef.filter.maskBits = B2DVars.BIT_PLAYER;

    for (MapObject mo : ml.getObjects()) {
      float x = 0;
      float y = 0;
      if (mo instanceof EllipseMapObject) {
        EllipseMapObject emo = (EllipseMapObject) mo;
        x = emo.getEllipse().x / PPM;
        y = emo.getEllipse().y / PPM;
      }
      bdef.position.set(x, y);
      Body body = world.createBody(bdef);
      body.createFixture(fdef).setUserData(Collision.APPLE);

      Apple apple = new Apple();
      body.setUserData(apple);
      apples.add(apple);
    }

    shape.dispose();

  }


  private void createBlocs(TiledMapTileLayer layer, short bits) {

    float ts = layer.getTileWidth();

    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;
    ChainShape cs = new ChainShape();
    Vector2[] v = new Vector2[3];
    v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
    v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
    v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
    cs.createChain(v);
    FixtureDef fd = new FixtureDef();
    fd.friction = 0;
    fd.shape = cs;
    fd.filter.categoryBits = bits;
    fd.filter.maskBits = B2DVars.BIT_PLAYER;

    for (int row = 0; row < layer.getHeight(); row++) {
      for (int col = 0; col < layer.getWidth(); col++) {

        TiledMapTileLayer.Cell cell = layer.getCell(col, row);

        if (cell == null) {
          continue;
        }
        if (cell.getTile() == null) {
          continue;
        }

        bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
        world.createBody(bdef).createFixture(fd);

      }
    }

    cs.dispose();

  }


  public TiledMapRenderer getTiledMapRenderer(){
    return tmRenderer;
  }

  public ApplesContainer createApplesContainet() {
    return new ApplesContainer(world,apples);
  }
}
