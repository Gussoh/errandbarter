/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

// A simple MIDlet for testing various alerts.
public class AlertTest extends MIDlet
        implements CommandListener {

    // An abstract class for our alert tests.
    public abstract class AlertRunner {

        protected static final int ONE_SECOND = 1000;
        protected static final int FIVE_SECONDS = 5000;

        public AlertRunner(String title) {
            _title = title;
        }

        public abstract Alert doAlert();

        public String getTitle() {
            return _title;
        }

        public String toString() {
            return getTitle();
        }
        private String _title;
    }

    // An alert test for a simple timed alert.
    public class TimedAlert extends AlertRunner {

        public TimedAlert(String title) {
            super(title);
        }

        public Alert doAlert() {
            Alert a = new Alert(getTitle());
            a.setString("Times out after 5 seconds...");
            a.setTimeout(FIVE_SECONDS);

            showAlert(a);

            return a;
        }
    }

    // An alert test for a simple modal alert.
    public class ModalAlert extends AlertRunner {

        public ModalAlert(String title) {
            super(title);
        }

        public Alert doAlert() {
            Alert a = new Alert(getTitle());
            a.setString("Waits to be dismissed");
            a.setTimeout(Alert.FOREVER);

            showAlert(a);

            return a;
        }
    }

    // An alert test that displays an alert with
    // a gauge whose value is increased every second.
    // The alert can be dismissed only after the gauge
    // reaches its maximum value.
    public class ProgressAlert extends AlertRunner
            implements CommandListener, Runnable {

        private static final int MAX = 10;

        public ProgressAlert(String title) {
            super(title);
        }

        public Alert doAlert() {
            _gauge = new Gauge(null, false, MAX, 0);
            _gauge.setValue(0);
            _done = false;

            _alert = new Alert(getTitle());
            _alert.setString("Counts to " + MAX +
                    " and then lets you dismiss it");
            _alert.setCommandListener(this);
            _alert.setIndicator(_gauge);

            // Set a _very_ long timeout
            _alert.setTimeout(ONE_SECOND * 3600);

            showAlert(_alert);
            new Thread(this).start();

            return _alert;
        }

        public void commandAction(Command c,
                Displayable d) {
            if (_done || _gauge.getValue() >= MAX) {
                showList();
            }
        }

        private void done() {
            _alert.addCommand(
                    new Command("Done", Command.OK, 1));
            _done = true;
        }

        // A thread that bumps the value of the counter
        // every second.
        public void run() {
            int val = _gauge.getValue();
            Thread thread = Thread.currentThread();

            try {
                while (val < MAX) {
                    thread.sleep(ONE_SECOND);
                    _gauge.setValue(++val);
                }
            } catch (InterruptedException e) {
            }

            done();
        }
        private Alert _alert;
        private int _counter;
        private boolean _done;
        private Gauge _gauge;
    }

    // An alert test that displays a continuously
    // running gauge before automatically timing out.
    public class BusyAlert extends AlertRunner {

        public BusyAlert(String title) {
            super(title);
        }

        public Alert doAlert() {
            _gauge = new Gauge(null, false,
                    Gauge.INDEFINITE,
                    Gauge.CONTINUOUS_RUNNING);

            _alert = new Alert(getTitle());
            _alert.setString("Runs for 5 seconds and " +
                    "times out automatically");
            _alert.setIndicator(_gauge);
            _alert.setTimeout(FIVE_SECONDS);

            showAlert(_alert);

            return _alert;
        }
        private Alert _alert;
        private Gauge _gauge;
    }

    // Standard MIDlet code. Displays a list of
    // available alert tests and runs the test once
    // it's been chosen.
    private Display display;
    public static final Command exitCommand =
            new Command("Exit",
            Command.EXIT, 1);

    public AlertTest() {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            exitMIDlet();
        } else if (c == List.SELECT_COMMAND) {
            int index = _alertList.getSelectedIndex();
            _alertRunners[index].doAlert();
        }
    }

    protected void destroyApp(boolean unconditional)
            throws MIDletStateChangeException {
        exitMIDlet();
    }

    public void exitMIDlet() {
        notifyDestroyed();
    }

    public Display getDisplay() {
        return display;
    }

    protected void initMIDlet() {

        // The list of alert tests....

        _alertRunners = new AlertRunner[]{
                    new TimedAlert("Timed alert"),
                    new ModalAlert("Modal alert"),
                    new ProgressAlert("Progress alert"),
                    new BusyAlert("Busy alert")
                };

        _alertList = new List("Alert Testing", List.IMPLICIT);
        _alertList.setCommandListener(this);

        for (int i = 0; i < _alertRunners.length; ++i) {
            _alertList.append(_alertRunners[i].toString(), null);
        }

        showList();
    }

    protected void pauseApp() {
    }

    private void showAlert(Alert a) {
        getDisplay().setCurrent(a, _alertList);
    }

    private void showList() {
        getDisplay().setCurrent(_alertList);
    }

    protected void startApp()
            throws MIDletStateChangeException {
        if (display == null) {
            display = Display.getDisplay(this);
            initMIDlet();
        }
    }
    private List _alertList;
    private AlertRunner[] _alertRunners;
}
