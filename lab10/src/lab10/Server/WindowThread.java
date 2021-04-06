package lab10.Server;

public class WindowThread extends Thread {
    public void run() {
        ServerWindow.show();
    }
}
