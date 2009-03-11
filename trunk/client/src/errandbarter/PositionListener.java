package errandbarter;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gussoh
 */
public interface PositionListener {
    public void positionUpdated(Positioning p);
    public void positionLost(Positioning p);
}
