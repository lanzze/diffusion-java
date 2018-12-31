
import jiconfont.IconFont;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import sts.Sts;
import sts.analyze.Result;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        Result[] results = Sts.from(new File("F:\\temp\\box\\a.0.dat"), 1024 * 1024);
        Sts.print(results);
    }
}
