package es.ulpgc.eite.cleancode.clickcounter.counter;

import java.lang.ref.WeakReference;


public interface CounterContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void navigateToNextScreen();
    void onDataUpdated(CounterViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);


    void onResume();
    void onStart();
    void onRestart();
    void onBackPressed();
    void onPause();
    void onDestroy();

    void onClicksPressed();
    void onResetPressed();
    void onIncrementPressed();
  }

  interface Model {
    String getStoredData();
    void onDataFromNextScreen(String data);
    void onRestartScreen(String dato);
    void onDataFromPreviousScreen(String data);
    int getCuenta();
    void setCuenta(int cuenta);
    void setStoredData(String dato);
    int getNumVeces();
    void setNumVeces(int num);
  }

}
