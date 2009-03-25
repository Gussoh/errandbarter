/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import javax.microedition.lcdui.Spacer;

/**
 *
 * @author Gussoh
 */
public class SpacerTwo extends Spacer {

    public SpacerTwo(int minWidth, int minHeight) {
        super(minWidth, minHeight);
        setLayout(LAYOUT_NEWLINE_AFTER | LAYOUT_NEWLINE_BEFORE);
    }
}
