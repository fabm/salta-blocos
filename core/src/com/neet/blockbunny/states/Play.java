package com.neet.blockbunny.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.neet.blockbunny.entities.Maca;
import com.neet.blockbunny.entities.Quadro;
import com.neet.blockbunny.entities.Player;
import com.neet.blockbunny.handlers.B2DVars;
import com.neet.blockbunny.handlers.BBContactListener;
import com.neet.blockbunny.handlers.BBInput;
import com.neet.blockbunny.handlers.Background;
import com.neet.blockbunny.handlers.CameraMov;
import com.neet.blockbunny.handlers.GameStateManager;
import com.neet.blockbunny.main.Game;

import static com.neet.blockbunny.handlers.B2DVars.PPM;

public class Play extends GameState {

  private World world;
  private BBContactListener cl;
  private CameraMov comMov;

  private Player player;

  private TiledMap tileMap;
  private int tileMapWidth;
  private int tileSize;
  private OrthogonalTiledMapRenderer tmRenderer;

  private Array<Maca> macas;

  private Background[] backgrounds;
  private Quadro quadro;

  public static int nivel;

  public Play(GameStateManager gsm) {

    super(gsm);

    // box 2d
    world = new World(new Vector2(0, -10f), true);
    cl = new BBContactListener();
    world.setContactListener(cl);

    // create player
    criaPlayer();

    // create walls
    criaMapa();
    camMovel.setBounds(0, tileMapWidth * tileSize);

    criaMacas();

    // create backgrounds
    Texture bgs = Game.res.getTexture("montanha");
    TextureRegion mountains;
    backgrounds = new Background[1];

    mountains = new TextureRegion(Game.res.getTexture("montanha"), 0, 0, 320, 240);
    backgrounds[0] = new Background(mountains, camMovel, 0f);

    // create hud
    quadro = new Quadro(player);

    // set up box2d camMovel
    comMov = new CameraMov();
    //comMov.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
    comMov.setBounds(0, (tileMapWidth * tileSize) / PPM);

  }

  private void criaPlayer() {

    // create bodydef
    BodyDef bdef = new BodyDef();
    bdef.type = BodyType.DynamicBody;
    bdef.position.set(0 / PPM, 200 / PPM);
    bdef.fixedRotation = true;
    bdef.linearVelocity.set(1f, 0f);

    // create body from bodydef
    Body body = world.createBody(bdef);

    // create box shape for player collision box
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(13 / PPM, 13 / PPM);

    // create fixturedef for player collision box
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.density = 1;
    fdef.friction = 0;
    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
    fdef.filter.maskBits = B2DVars.BIT_RED_BLOCK | B2DVars.BIT_MACA;

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
    body.createFixture(fdef).setUserData("foot");

    shape.dispose();

    // create new player
    player = new Player(body);
    body.setUserData(player);

    MassData md = body.getMassData();
    md.mass = .8f;
    body.setMassData(md);


  }

  private void criaMapa() {

    // load tile map and map renderer
    try {
      tileMap = new TmxMapLoader().load("res/maps/nivel" + nivel + ".tmx");
    } catch (Exception e) {
      Gdx.app.error("nivel","O nivel "+ nivel +" não existe");
      Gdx.app.exit();
    }
    tileMapWidth = tileMap.getProperties().get("width", Integer.class);
    tileSize = tileMap.getProperties().get("tilewidth", Integer.class);
    tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

    // read each of the "red" "green" and "blue" layers
    TiledMapTileLayer layer;
    layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
    criaBlocos(layer, B2DVars.BIT_RED_BLOCK);
    layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
    criaBlocos(layer, B2DVars.BIT_GREEN_BLOCK);
    layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
    criaBlocos(layer, B2DVars.BIT_BLUE_BLOCK);

  }

  /**
   * Creates box2d bodies for all non-null tiles
   * in the specified layer and assigns the specified
   * category bits.
   *
   * @param layer the layer being read
   * @param bits  category bits assigned to fixtures
   */
  private void criaBlocos(TiledMapTileLayer layer, short bits) {

    // tile size
    float ts = layer.getTileWidth();

    BodyDef bdef = new BodyDef();
    bdef.type = BodyType.StaticBody;
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

    // go through all cells in layer
    for (int row = 0; row < layer.getHeight(); row++) {
      for (int col = 0; col < layer.getWidth(); col++) {

        // get cell
        Cell cell = layer.getCell(col, row);

        // check that there is a cell
        if (cell == null) {
          continue;
        }
        if (cell.getTile() == null) {
          continue;
        }

        // create body from cell
        bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
        world.createBody(bdef).createFixture(fd);

      }
    }

    cs.dispose();

  }

  private void criaMacas() {

    // create list of macas
    macas = new Array<Maca>();

    // get all macas in "macas" layer,
    // create bodies for each, and add them
    // to the macas list
    MapLayer ml = tileMap.getLayers().get("macas");
    if (ml == null) {
      return;
    }

    BodyDef bdef = new BodyDef();
    bdef.type = BodyType.StaticBody;
    CircleShape shape = new CircleShape();
    shape.setRadius(8 / PPM);
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.isSensor = true;
    fdef.filter.categoryBits = B2DVars.BIT_MACA;
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
      body.createFixture(fdef).setUserData("maca");
      Maca maca = new Maca(body);
      body.setUserData(maca);
      macas.add(maca);
    }

    shape.dispose();

  }

  /**
   * Apply upward force to player body.
   */
  private void playerJump() {
    if (cl.playerCanJump()) {
      player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
      player.getBody().applyForceToCenter(0, 200, true);
      Game.res.getSound("jump").play();
    }
  }

  /**
   * Switch player mask bits to next block.
   */
  private void switchBlocks() {

    // get player foot mask bits
    Filter filter = player.getBody().getFixtureList().get(1).getFilterData();
    short bits = filter.maskBits;

    // switch to next block bit
    // red -> green -> blue
    if (bits == B2DVars.BIT_RED_BLOCK) {
      bits = B2DVars.BIT_GREEN_BLOCK;
    } else if (bits == B2DVars.BIT_GREEN_BLOCK) {
      bits = B2DVars.BIT_BLUE_BLOCK;
    } else if (bits == B2DVars.BIT_BLUE_BLOCK) {
      bits = B2DVars.BIT_RED_BLOCK;
    }

    // set player foot mask bits
    filter.maskBits = bits;
    player.getBody().getFixtureList().get(1).setFilterData(filter);

    // set player mask bits
    bits |= B2DVars.BIT_MACA;
    filter.maskBits = bits;
    player.getBody().getFixtureList().get(0).setFilterData(filter);

    // play sound
    Game.res.getSound("change").play();

  }

  public void handleInput() {

    // keyboard input
    if (BBInput.isPressed(BBInput.BUTTON1)) {
      playerJump();
    }
    if (BBInput.isPressed(BBInput.BUTTON2)) {
      switchBlocks();
    }

    // mouse/touch input for android
    // left side of screen to switch blocks
    // right side of screen to jump
    if (BBInput.isPressed()) {
      if (BBInput.x < Gdx.graphics.getWidth() / 2) {
        switchBlocks();
      } else {
        playerJump();
      }
    }

  }

  public void update(float dt) {

    handleInput();

    world.step(Game.STEP, 1, 1);

    Array<Body> bodies = cl.getBodies();
    for (int i = 0; i < bodies.size; i++) {
      Body b = bodies.get(i);
      macas.removeValue((Maca) b.getUserData(), true);
      world.destroyBody(bodies.get(i));
      player.collectMacas();
      Game.res.getSound("bite").play();
    }
    bodies.clear();

    // update player
    player.update(dt);

    // check player win
    if (player.getBody().getPosition().x * PPM > tileMapWidth * tileSize) {
      Game.res.getSound("victory").play();
      gsm.setState(State.LEVEL_SELECT);
    }

    // check player failed
    if (player.getBody().getPosition().y < 0) {
      Game.res.getSound("die").play();
      gsm.setState(State.MENU);
    }
    if (player.getBody().getLinearVelocity().x < 0.1f) {
      Game.res.getSound("die").play();
      gsm.setState(State.MENU);
    }

    // update macas
    for (int i = 0; i < macas.size; i++) {
      macas.get(i).update(dt);
    }

  }

  public void render() {

    // camera follow player
    camMovel.setPosition(player.getPosition().x * PPM + Game.V_WIDTH / 4, Game.V_HEIGHT / 2);
    camMovel.update();

    // draw bgs
    sb.setProjectionMatrix(camFixa.combined);

    backgrounds[0].render(sb);

    // draw tilemap
    tmRenderer.setView(camMovel);
    tmRenderer.render();

    // draw player
    sb.setProjectionMatrix(camMovel.combined);
    player.render(sb);

    // draw macas
    for (int i = 0; i < macas.size; i++) {
      macas.get(i).render(sb);
    }


    // draw hud
    sb.setProjectionMatrix(camFixa.combined);
    quadro.render(sb);


  }

  public void dispose() {
  }

}