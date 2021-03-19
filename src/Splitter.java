import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Splitter {

    //GUI
    public JFrame window;

    public File file;
    public ImageIcon image;
    public BufferedImage bufferedImage;
    public int width, height;

    public int splitWidth, splitHeight;

    public void Start() {
        window = new JFrame("Atlas Splitter");
        window.setSize(700, 500);
        window.setLayout(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton open = new JButton("Open file");
        open.setSize(150, 50);
        open.setLocation(275, 300);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int returnValue = chooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    image = new ImageIcon(file.getPath());
                    width = image.getIconWidth();
                    height = image.getIconHeight();
                    try { bufferedImage = ImageIO.read(file); } catch (Exception err) { err.printStackTrace(); }

                    String widthoutput = JOptionPane.showInputDialog("Enter sprite width here:");
                    String heightoutput = JOptionPane.showInputDialog("Enter sprite height here: ");
                    splitWidth = Integer.parseInt(widthoutput);
                    splitHeight = Integer.parseInt(heightoutput);

                    Crop();
                }
            }
        });

        window.add(open);
        window.setVisible(true);
    }

    public void Crop() {
        File spriteStorage = new File(file.getPath().toString().split("[.]")[0] + "_output");
        spriteStorage.mkdir();

        int index = 0;
        for (int x = 0; x < (width / splitWidth); x++) {
            for (int y = 0; y < (height / splitHeight); y++) {
                int xIndex = x * splitWidth;
                int yIndex = y * splitHeight;

                File outputFile = new File(spriteStorage.getPath() + "/sprite" + index + ".png");
                BufferedImage croppedSprite = bufferedImage.getSubimage(xIndex, yIndex, splitWidth, splitHeight);
                try { ImageIO.write(croppedSprite, "png", outputFile); } catch (Exception err) { err.printStackTrace(); }

                index++;
            }
        }
    }

    public static void main(String[] args) {
        new Splitter().Start();
    }
}
