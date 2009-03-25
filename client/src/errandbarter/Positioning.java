package errandbarter;


import java.util.Vector;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gussoh
 */
public class Positioning extends Thread {

    private static final int N_FAIL_ON_LOST_POSITION = 3;
    private int failedCycles = 0;
    private QualifiedCoordinates lastCoordinates = null;
    private LocationProvider lp;
    private boolean stopping = false;
    private Vector positionListeners = new Vector();

    public Positioning() throws LocationException {
        Criteria c = new Criteria();
        //c.setCostAllowed(false);
        //c.setHorizontalAccuracy(300); // accurate to 300 meters
        // accurate to 300 meters
        lp = LocationProvider.getInstance(c);
        if (lp == null) {
            throw new LocationException("Cannot get locationprovider");
        }
    }

    public void addPositionListener(PositionListener pl) {
        positionListeners.addElement(pl);
    }

    public QualifiedCoordinates getPosition() {
        return lastCoordinates;
    }

    public void stop() {
        stopping = true;
    }

    public void run() {
        if (lp == null) {
            return;
        }

        while (!stopping) {

            try {
                Location l = lp.getLocation(60); // 60 seconds timeout
                lastCoordinates = l.getQualifiedCoordinates();
                if (lastCoordinates != null) {
                    for (int i = 0; i < positionListeners.size(); i++) {
                        PositionListener positionListener = (PositionListener) positionListeners.elementAt(i);
                        positionListener.positionUpdated(this);
                    }
                    failedCycles = 0;
                } else {
                    failedCycles++;
                }
            } catch (LocationException ex) {
                ex.printStackTrace();
                failedCycles++;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                failedCycles++;
            }

            if (failedCycles == N_FAIL_ON_LOST_POSITION) {
                for (int i = 0; i < positionListeners.size(); i++) {
                    PositionListener positionListener = (PositionListener) positionListeners.elementAt(i);
                    positionListener.positionLost(this);
                }
            }

            try {
                Thread.sleep(2000); // might be dumb to get position all the time? power consumption?
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        stopping = false;
    }
}
