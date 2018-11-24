package com.marcelfiore.jungle_ai.game.ai;

public class State {
  private int[] state;

  public State(int[] state) {
    this.state = state;
  }

  public int[] getState() {
    return state;
  }

  @Override
  public String toString() {
    String str = "";
    for (int i = 0; i < state.length; i++) {
      str += state[i];
      if (i != state.length - 1) {
        str += ";";
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof State) {
      if (obj.getState().equals(getState())) {
        return true;
      }
      
      return false;
    }

    return false;
  }
}
