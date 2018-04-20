
import jiconfont.IconFont;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 300);
        IconFontSwing.register(FontAwesome.getIconFont());
        Icon icon = IconFontSwing.buildIcon(FontAwesome.INFO, 100, Color.MAGENTA);
        JLabel lbl = new JLabel(icon);
        frame.add(lbl);
        frame.setVisible(true);
    }
}
