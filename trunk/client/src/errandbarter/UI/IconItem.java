/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Gussoh
 */
public class IconItem extends CustomItem {

    private final Image icon;

    public IconItem(Image icon) {
        super("");
        this.icon = icon;
        
    }

    protected int getMinContentWidth() {
        return icon.getWidth();
    }

    protected int getMinContentHeight() {
        return icon.getHeight();
    }

    protected int getPrefContentWidth(int height) {
        return icon.getWidth();
    }

    protected int getPrefContentHeight(int width) {
        return icon.getHeight();
    }

    protected void paint(Graphics g, int w, int h) {
        g.drawImage(icon, 0, 0, Graphics.LEFT | Graphics.TOP);
    }

    protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        return false;
    }

}
