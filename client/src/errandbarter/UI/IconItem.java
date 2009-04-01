/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Gussoh
 */
public class IconItem extends CustomItem {

    private final Image icon;
    private MIDlet midlet;
    private Font normalFont = Font.getDefaultFont();
    private Font boldFont = Font.getFont(normalFont.getFace(), normalFont.getStyle() | Font.STYLE_BOLD, normalFont.getSize());
    private int colorFG;
    private int colorBG;
    private int height = 32;
    private int padding = 5;
    private String text;
    private String label;

    public IconItem(MIDlet midlet, Image icon, String label, String text) {
        super(null); // to avoid drawing an ugly text above the icon
        this.midlet = midlet;
        this.icon = icon;
        this.text = text;
        this.label = label;

        setLayout(LAYOUT_NEWLINE_AFTER | LAYOUT_NEWLINE_BEFORE);

        colorFG = Display.getDisplay(midlet).getColor(Display.COLOR_FOREGROUND);
        colorBG = Display.getDisplay(midlet).getColor(Display.COLOR_BACKGROUND);
        height = Math.max(normalFont.getHeight(), icon.getHeight());
    }

    public String getText() {
        return text;
    }

    public String getLabel() { /* This does not seem to change the rendering. How is the GUI accessing the label? */
        return label;
    }

    public void setLabel(String label) {
        //super.setLabel(label); // will repaint the ugly label
        this.label = label;
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    protected int getMinContentWidth() {
        return icon.getWidth() + padding + boldFont.stringWidth(label) + padding + normalFont.stringWidth(text);
    }

    protected int getMinContentHeight() {
        return height;
    }

    protected int getPrefContentWidth(int height) {
        return icon.getWidth() + padding + boldFont.stringWidth(label) + padding + normalFont.stringWidth(text);
    }

    protected int getPrefContentHeight(int width) {
        return height;
    }

    protected void paint(Graphics g, int w, int h) {
        /*g.setColor(colorBG);
        g.fillRect(0, 0, w, h);*/

        g.drawImage(icon, 0, 0, Graphics.LEFT | Graphics.TOP);

        g.setFont(boldFont);
        g.setColor(colorFG);
        g.drawString(label, icon.getWidth() + padding, 0, Graphics.LEFT | Graphics.TOP);

        g.setFont(normalFont);
        g.setColor(colorFG);
        g.drawString(text, icon.getWidth() + padding + boldFont.stringWidth(label) + padding, 0, Graphics.LEFT | Graphics.TOP);
    }

    protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        return false;
    }
}
