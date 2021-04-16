package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class CounterPresenter implements CounterContract.Presenter {

  public static String TAG = CounterPresenter.class.getSimpleName();

  private WeakReference<CounterContract.View> view;
  private CounterState state;
  private CounterContract.Model model;

  private AppMediator mediator;

  public CounterPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCounterState();
  }


  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new CounterState();
    }

    // call the model and update the state
    state.data = model.getStoredData();

    /*
    // use passed state if is necessary
    PreviousToCounterState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }
    */
  }

  @Override
  public void onRestart() {
     Log.e(TAG, "onRestart()");

    // update the model if is necessary
    model.onRestartScreen(state.data);
    model.setStoredData(state.data);
    model.setCuenta(state.cuenta);
    Log.e(TAG, state.data);
  }

  @Override
  public void onResume() {
     Log.e(TAG, "onResume()");

    // use passed state if is necessary
    ClicksToCounterState savedState = getStateFromNextScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromNextScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }

    // call the model and update the state
    //state.data = model.getStoredData();

    // update the view
    view.get().onDataUpdated(state);

  }


  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
     Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClicksPressed() { Log.e(TAG, "onClicksPressed()");
  Log.e(TAG, model.getStoredData());
  CounterToClicksState nextState = new CounterToClicksState();
  nextState.data=model.getStoredData();
  passStateToNextScreen(nextState);
  view.get().navigateToNextScreen();
  }

  @Override
  public void onResetPressed() {
    // Log.e(TAG, "onResetPressed()");
    int cuenta = 0;
    model.setCuenta(cuenta);
    state.cuenta=cuenta;
    String count = Integer.toString(cuenta);
    state.data=count;
    view.get().onDataUpdated(state);


  }

  @Override
  public void onIncrementPressed() {
     Log.e(TAG, "onIncrementPressed()");
    int cuenta = model.getCuenta();
    //state.cuenta= cuenta++;
    int num = cuenta+1;
    if(num==10){
      num=0;
    }
    state.cuenta=num;
    model.setCuenta(num);
    String count = Integer.toString(num);
    Log.e(TAG, count);
    state.data=count;
    model.setStoredData(count);
    state.clickEnabled=true;
    state.resetEnabled=true;
    view.get().onDataUpdated(state);


  }

  private void passStateToNextScreen(CounterToClicksState state) {
    mediator.setCounterNextScreenState(state);
  }

  private ClicksToCounterState getStateFromNextScreen() {
    return mediator.getCounterNextScreenState();
  }

  @Override
  public void injectView(WeakReference<CounterContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CounterContract.Model model) {
    this.model = model;
  }

}
