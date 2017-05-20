import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    private JFrame mainFrame;

    Main(){
        mainFrame = new JFrame("Mini-stat Package");
        mainFrame.setResizable(false);
        mainFrame.setBounds(0, 0, 800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(getClass().getResource("main menu.png")));
        background.setBounds(0, 0, 800, 600);

        JLabel first = new JLabel();
        first.setIcon(new ImageIcon(getClass().getResource("sampling methods.png")));
        first.setBounds(195, 200, 200, 170);
        first.setToolTipText("Simulates Random, Systematic, and Stratified Sampling");

        JLabel sec = new JLabel();
        sec.setIcon(new ImageIcon(getClass().getResource("presenting data.png")));
        sec.setBounds(430, 200, 200, 170);
        sec.setToolTipText("Generates a Summary Table and a Frequency Distribution Table");

        JLabel third = new JLabel();
        third.setIcon(new ImageIcon(getClass().getResource("measure of central tendency.png")));
        third.setBounds(195, 390, 200, 170);
        third.setToolTipText("Calculates Mean, Median, and Mode for Grouped and Ungrouped Data");

        JLabel aboutus = new JLabel();
        aboutus.setIcon(new ImageIcon(getClass().getResource("about us.png")));
        aboutus.setBounds(410, 400, 150, 150);
        aboutus.setToolTipText("About Us");

        JLabel quit = new JLabel();
        quit.setIcon(new ImageIcon(getClass().getResource("quit.png")));
        quit.setBounds(540, 400, 150, 150);
        quit.setToolTipText("Quit");

        first.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                new SamplingMethods();
            }
        });

        sec.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                new DataPresentation();
            }
        });

        third.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                new CentralTendency();
            }
        });

        aboutus.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                new AboutUs();
            }
        });

        quit.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //adding components
        mainFrame.add(first);
        mainFrame.add(sec);
        mainFrame.add(third);
        mainFrame.add(quit);
        mainFrame.add(aboutus);
        mainFrame.add(background);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
