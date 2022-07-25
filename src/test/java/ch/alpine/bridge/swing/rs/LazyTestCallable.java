// code by clruch
package ch.alpine.bridge.swing.rs;

/* package */ enum LazyTestCallable implements Runnable {
  INSTANCE;

  @Override
  public void run() {
    System.out.println("PrintCallable does nothing and always returns null...");
  }
}
