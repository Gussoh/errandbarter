/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter;

import java.util.Vector;

/**
 *
 * @author Gussoh
 */
public interface DataListener {

    public void onErrandsList(Vector errands, String command, String[] arguments);

    public void onViewErrand(Errand errand, String command, String[] arguments);

    public void onUserInfo(User user, String command, String[] arguments);

    public void onOK(String command, String[] arguments);

    public void onError(Exception e);
}
