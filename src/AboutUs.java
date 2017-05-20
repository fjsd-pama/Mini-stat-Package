import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutUs {
    private JFrame frame;

    public AboutUs(){
        frame = new JFrame("About Us");
        frame.setResizable(false);
        frame.setBounds(0, 0, 800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(getClass().getResource("app desc.png")));
        background.setBounds(0, 0, 800, 600);

        JLabel back = new JLabel();
        back.setIcon(new ImageIcon(getClass().getResource("back.png")));
        back.setBounds(410, 400, 150, 150);
        back.setToolTipText("Back");

        JLabel quit = new JLabel();
        quit.setIcon(new ImageIcon(getClass().getResource("quit.png")));
        quit.setBounds(540, 400, 150, 150);
        quit.setToolTipText("Quit");

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new Main();
            }
        });

        quit.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //adding components
        frame.add(quit);
        frame.add(back);
        frame.add(background);
        frame.setVisible(true);
    }
}
