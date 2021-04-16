package es.ulpgc.eite.cleancode.clickcounter.counter;

public class CounterModel implements CounterContract.Model {

  public static String TAG = CounterModel.class.getSimpleName();

  private String data;
  private int cuenta=0;
  private int numVeces=0;

  public CounterModel(String data) {
    this.data = data;
  }

  @Override
  public int getCuenta() {
    // Log.e(TAG, "getStoredData()");
    return cuenta;
  }
  public void setCuenta(int num){
    cuenta=num;
  }

  @Override
  public int getNumVeces() {
    // Log.e(TAG, "getStoredData()");
    return numVeces;
  }
  @Override
  public void setNumVeces(int num) {
    // Log.e(TAG, "getStoredData()");
    numVeces = num;
  }

  @Override
  public String getStoredData() {
    // Log.e(TAG, "getStoredData()");
    return data;
  }
  public void setStoredData(String dato){
    data=dato;
  }

  @Override
  public void onRestartScreen(String dato) {
    // Log.e(TAG, "onRestartScreen()");
  }

  @Override
  public void onDataFromNextScreen(String data) {
    // Log.e(TAG, "onDataFromNextScreen()");
  }

  @Override
  public void onDataFromPreviousScreen(String data) {
    // Log.e(TAG, "onDataFromPreviousScreen()");
  }
}
