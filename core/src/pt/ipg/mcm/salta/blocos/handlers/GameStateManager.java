package pt.ipg.mcm.salta.blocos.handlers;

import pt.ipg.mcm.salta.blocos.main.Game;
import pt.ipg.mcm.salta.blocos.states.GameState;
import pt.ipg.mcm.salta.blocos.states.Menu;
import pt.ipg.mcm.salta.blocos.states.Play;
import pt.ipg.mcm.salta.blocos.states.SelecaoNivel;
import pt.ipg.mcm.salta.blocos.entities.State;

import java.util.Stack;

public class GameStateManager {

  private Game game;

  private Stack<GameState> gameStates;

  public GameStateManager(Game game) {
    this.game = game;
    gameStates = new Stack<GameState>();
    pushState(State.MENU);
  }

  public void update(float dt) {
    gameStates.peek().update(dt);
  }

  public void render() {
    gameStates.peek().render();
  }

  public Game game() {
    return game;
  }

  private GameState getState(State state) {
    if (state == State.MENU) {
      return new Menu(this);
    }
    if (state == State.PLAY) {
      return new Play(this);
    }
    if (state == State.LEVEL_SELECT) {
      return new SelecaoNivel(this);
    }
    return null;
  }

  public void setState(State state) {
    if (state == null) {
      return;
    }
    popState();
    pushState(state);
  }

  public void pushState(State state) {
    gameStates.push(getState(state));
  }

  public void popState() {
    GameState g = gameStates.pop();
    g.dispose();
  }

}
