package pt.ipg.mcm.salta.blocos.handlers;


public class BBInput {

  public static int x;
  public static int y;
  public static boolean down;
  public static boolean pdown;

  public static boolean[] keys;
  public static boolean[] pkeys;
  private static final int NUM_KEYS = 7;
  public static final int BUTTON1 = 0;
  public static final int BUTTON2 = 1;
  public static final int ENTER_MENU_BUTTON = 2;
  public static final int LEFT_BUTTON = 3;
  public static final int RIGHT_BUTTON = 4;
  public static final int UP_BUTTON = 5;
  public static final int DOWN_BUTTON = 6;

  static {
    keys = new boolean[NUM_KEYS];
    pkeys = new boolean[NUM_KEYS];
  }

  public static void update() {
    pdown = down;
    for (int i = 0; i < NUM_KEYS; i++) {
      pkeys[i] = keys[i];
    }
  }

  public static boolean isPressed() {
    return down && !pdown;
  }

  public static void setKey(int i, boolean b) {
    keys[i] = b;
  }

  public static boolean isPressed(int i) {
    return keys[i] && !pkeys[i];
  }

}
