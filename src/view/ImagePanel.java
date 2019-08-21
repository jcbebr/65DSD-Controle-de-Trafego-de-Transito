package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 21/04/2019
 */
class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(String imageName) {
        this(new ImageIcon("images/" + imageName + ".png").getImage());
    }

    private ImagePanel(Image img) {
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        super.setPreferredSize(size);
        super.setMinimumSize(size);
        super.setMaximumSize(size);
        super.setSize(size);
        super.setLayout(null);

        this.img = img;
    }

    

}
