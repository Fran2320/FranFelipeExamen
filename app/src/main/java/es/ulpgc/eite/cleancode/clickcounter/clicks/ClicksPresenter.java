package es.ulpgc.eite.cleancode.clickcounter.clicks;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class ClicksPresenter implements ClicksContract.Presenter {

  public static String TAG = ClicksPresenter.class.getSimpleName();

  private WeakReference<ClicksContract.View> view;
  private ClicksState state;
  private ClicksContract.Model model;
  private AppMediator mediator;

  public ClicksPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getClicksState();
  }


  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new ClicksState();
    }

    // call the model and update the state
    state.data = model.getStoredData();

    // use passed state if is necessary
    CounterToClicksState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);

      // update the state if is necessary
      //state.data = savedState.data;
      //String cuenta = savedState.data;
      //int cuent = Integer.parseInt(cuenta);
      //int count =state.cuenta + cuent;
      //state.cuenta=count;
      //state.data = Integer.toString(count);

      //Intentando la cuenta
      state.numAnterior=state.cuenta;
      state.cuenta = savedState.nVecesPulsadas;

      model.setCuenta(state.cuenta);
      state.data = Integer.toString(state.cuenta);

    }
  }

  @Override
  public void onRestart() {
    // Log.e(TAG, "onRestart()");

    // update the model if is necessary
    model.onRestartScreen(state.data);
  }


  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    /*
    // use passed state if is necessary
    NextToClicksState savedState = getStateFromNextScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromNextScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }
    */

    // call the model and update the state
    //state.data = model.getStoredData();

    // update the view
    if(state.clearPulsado==true){
      state.cuenta= state.cuenta-state.numAnterior;
      state.data = Integer.toString(state.cuenta);
      state.clearPulsado=false;
    }

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
    // Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClearPressed() {
     Log.e(TAG, "onClearPressed()");
    int reset = 0;
    state.cuenta=reset;
    model.setCuenta(state.cuenta);
    state.data = Integer.toString(state.cuenta);
    model.setData(state.data);
    Log.e(TAG, state.data);
    state.clearPulsado=true;
    view.get().onDataUpdated(state);

  }

  private void passStateToPreviousScreen(ClicksToCounterState state) {
    mediator.setClicksPreviousScreenState(state);
  }

  private CounterToClicksState getStateFromPreviousScreen() {
    return mediator.getClicksPreviousScreenState();
  }

  @Override
  public void injectView(WeakReference<ClicksContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(ClicksContract.Model model) {
    this.model = model;
  }

}
