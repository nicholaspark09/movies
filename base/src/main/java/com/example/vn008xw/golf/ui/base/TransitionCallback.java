package com.example.vn008xw.golf.ui.base;

import android.transition.Transition;

/**
 * Created by vn008xw on 8/26/17.
 */

public abstract class TransitionCallback implements Transition.TransitionListener {

  @Override
  public void onTransitionStart(Transition transition) {} // NOP

  @Override
  public void onTransitionEnd(Transition transition) {} // NOP

  @Override
  public void onTransitionCancel(Transition transition) {} // NOP

  @Override
  public void onTransitionPause(Transition transition) {} // NOP

  @Override
  public void onTransitionResume(Transition transition) {}// NOP
}
