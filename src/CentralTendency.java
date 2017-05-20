import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

class CentralTendency {
    private JFrame ctmenu;
    private double max, min;
    private Color backcolor = Color.WHITE;
    private String nextbutton = "Next";
    private static final int width = 500, height = 400;
    private int id, size, datatype, intervaltype, numintervals, openintervaltype, centraltendency, freqtotal, medianInt;
    private String[] modes;
    private double mean, medianFloat, var, sd;
    private float freqtimesCMtotal, freqtimesCMSquaredtotal;
    private String title;
    private ArrayList<ArrayList> closedintervaldataset;
    private ArrayList< ArrayList > openintervaldataset;
    private ArrayList<String> dataset;

    private JFrame titleFrame, typeFrame, dataFrame, displayFrame, mFrame, iFrame;

    CentralTendency(){
        if(iFrame != null){
            iFrame.dispose();
        }
        closedintervaldataset = new ArrayList<>();
        openintervaldataset = new ArrayList<>();
        dataset = new ArrayList<>();
        new Menu();
    }

    class Menu{
        private JLabel background, ungrouped, grouped, back, quit;
        Menu(){
            ctmenu = new JFrame("Measures of Central Tendency");
            ctmenu.setResizable(false);
            ctmenu.setBounds(0, 0, 800, 600);
            ctmenu.setLocationRelativeTo(null);
            ctmenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ctmenu.getContentPane().setLayout(null);

            background = new JLabel();
            background.setIcon(new ImageIcon(getClass().getResource("central tendency menu.png")));
            background.setBounds(0, 0, 800, 600);

            ungrouped = new JLabel();
            ungrouped.setIcon(new ImageIcon(getClass().getResource("ungrouped data.png")));
            ungrouped.setBounds(195, 200, 200, 170);
            ungrouped.setToolTipText("Generates results out of a produced summary table from sample");

            grouped = new JLabel();
            grouped.setIcon(new ImageIcon(getClass().getResource("grouped data.png")));
            grouped.setBounds(430, 200, 200, 170);
            grouped.setToolTipText("Generates results out of a frequency distribution from interval inputs");

            back = new JLabel();
            back.setIcon(new ImageIcon(getClass().getResource("back.png")));
            back.setBounds(410, 400, 150, 150);
            back.setToolTipText("Back");

            quit = new JLabel();
            quit.setIcon(new ImageIcon(getClass().getResource("quit.png")));
            quit.setBounds(540, 400, 150, 150);
            quit.setToolTipText("Quit");

            //adding mouse listeners
            String ud = "Ungrouped Data";
            String g = "Grouped Data";
            ungrouped.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    ctmenu.dispose();
                    id = 1;
                    new DescriptionState(ud);
                }
            });

            grouped.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    ctmenu.dispose();
                    id = 2;
                    new DescriptionState(g);
                }
            });

            back.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    ctmenu.dispose();
                    new Main();
                }
            });

            quit.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            //adding components
            ctmenu.add(ungrouped);
            ctmenu.add(grouped);
            ctmenu.add(quit);
            ctmenu.add(back);
            ctmenu.add(background);
            ctmenu.setVisible(true);
        }

    }

    class DescriptionState{
        private JButton nxt1;
        private TextArea ta1;
        private String label1 = "Enter a brief description of the data *";
        private String label2 = "NOTE: The description could be composed of anything.";
        private Color lbl1Color = Color.BLACK;
        private Color lbl2Color = Color.RED;

        DescriptionState(String str) {
            ctmenu.dispose();
            titleFrame = new JFrame(str);
            titleFrame.getContentPane().setBackground(backcolor);
            titleFrame.setResizable(false);
            titleFrame.setSize(width, height);
            titleFrame.setLocationRelativeTo(null);
            titleFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            titleFrame.getContentPane().setLayout(null);

            JLabel lbl1 = new JLabel(label1);
            lbl1.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl1.setBounds(20, 13, 350, 35);
            lbl1.setForeground(lbl1Color);

            ta1 = new TextArea();
            ta1.setBounds(20, 47, 350, 260);

            JLabel lbl2 = new JLabel(label2);
            lbl2.setBounds(20, 300, 350, 30);
            lbl2.setForeground(lbl2Color);

            nxt1 = new JButton(nextbutton);
            nxt1.setBounds(390, 280, 90, 40);
            nxt1.setEnabled(true);
            nxt1.setForeground(Color.BLACK);
            nxt1.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt1.setBackground(Color.WHITE);
            nxt1.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt1.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt1.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt1.addActionListener(new NextButtonListener1());

            titleFrame.add(lbl1);
            titleFrame.add(lbl2);
            titleFrame.add(ta1);
            titleFrame.add(nxt1);
            titleFrame.setVisible(true);
        }

        //action listener
        class NextButtonListener1 implements ActionListener {
            public void actionPerformed(ActionEvent arg0) {
                String s = ta1.getText();
                String ps = s;
                if (s.equals("")) {
                    JOptionPane.showMessageDialog(titleFrame,
                            "ERROR: You must enter a data description. Try again.", "FAILURE TO PROCEED",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    title = s;
                    if(id==1){
                        new UngroupedTypeState(title);
                    }else if(id==2){
                        new GroupedTypeState(title);
                    }
                }
            }
        }

    }

    class UngroupedTypeState{
        private JButton nxt2;
        private String forlbl = "Which type do you prefer?  *";
        private String str = "What is the size of the data?  *";
        private Checkbox intopt, floatopt;
        private CheckboxGroup options;
        private JTextField tf1;
        private String ss;

        UngroupedTypeState(String st) {
            titleFrame.dispose();
            ss = st;
            typeFrame = new JFrame(st);
            typeFrame.setSize(width, height);
            typeFrame.getContentPane().setBackground(backcolor);
            typeFrame.setLocationRelativeTo(null);
            typeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            typeFrame.getContentPane().setLayout(null);
            typeFrame.setResizable(false);

            JLabel lbl3 = new JLabel(forlbl);
            lbl3.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl3.setBounds(20, 13, 350, 35);
            lbl3.setForeground(Color.BLACK);

            options = new CheckboxGroup();
            intopt = new Checkbox("int", options, false);
            intopt.setFont(new Font("Arial", Font.BOLD, 22));
            intopt.setForeground(Color.RED);
            intopt.setBounds(40, 50, 100, 40);
            floatopt = new Checkbox("float", options, false);
            floatopt.setFont(new Font("Arial", Font.BOLD, 22));
            floatopt.setForeground(Color.RED);
            floatopt.setBounds(40, 80, 100, 40);

            JLabel lbl4 = new JLabel(str);
            lbl4.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl4.setBounds(20, 180, 350, 35);
            lbl4.setForeground(Color.BLACK);

            tf1 = new JTextField();
            tf1.setBounds(20, 230, 290, 35);

            JLabel lbl5 = new JLabel("* Required");
            lbl5.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            lbl5.setBounds(390, 320, 100, 35);
            lbl5.setForeground(Color.RED);

            nxt2 = new JButton(nextbutton);
            nxt2.setBounds(390, 270, 90, 40);
            nxt2.setEnabled(true);
            nxt2.setForeground(Color.BLACK);
            nxt2.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt2.setBackground(Color.WHITE);
            nxt2.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt2.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt2.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt2.addActionListener(new NextButtonListener2());

            typeFrame.add(lbl3);
            typeFrame.add(intopt);
            typeFrame.add(floatopt);
            typeFrame.add(lbl4);
            typeFrame.add(tf1);
            typeFrame.add(lbl5);
            typeFrame.add(nxt2);
            typeFrame.setVisible(true);
        }

        class NextButtonListener2 implements ActionListener {
            public void actionPerformed(ActionEvent arg0) {
                boolean err1 = typeErrorCatch();
                boolean err2 = sizeErrorCatch();
                if (!err1 && !err2) {
                    new UngroupedDataState(ss);
                }
            }
        }

        boolean typeErrorCatch() {
            boolean typeError;
            Checkbox cb = options.getSelectedCheckbox();
            try {
                String s = cb.getLabel();
                if (s.equals("int")) { datatype = 1; }
                else { datatype = 2; }
                typeError = false;
            } catch(NullPointerException e) {
                typeError = true;
                JOptionPane.showMessageDialog(typeFrame,
                        "Error: You must choose a type of data.",
                        "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
            }
            return typeError;
        }

        boolean sizeErrorCatch() {
            boolean sizeError = false;
            String ps = tf1.getText();
            try {
                if(ps.equals("")){
                    sizeError = true;
                    JOptionPane.showMessageDialog(typeFrame,
                            "Error: You must enter a population size. Try again.", "FAILURE TO PROCEED",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    size = Integer.parseInt(ps);
                    if (size < 2) {
                        sizeError = true;
                        JOptionPane.showMessageDialog(typeFrame,
                                "Error: Your population size must be at least 2. Try again.", "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                sizeError = true;
                JOptionPane.showMessageDialog(typeFrame,
                        "Error: Your population size must be an integer. Try again.", "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
            }
            return sizeError;
        }

    }

    private Color blend(Color c0, Color c1) {
        double totalAlpha = c0.getAlpha() + c1.getAlpha();
        double weight0 = c0.getAlpha() / totalAlpha;
        double weight1 = c1.getAlpha() / totalAlpha;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
    }

    class GroupedTypeState {
        private JButton nxt2;
        private String datatypelabel = "Which data type do you prefer? *"; //int or float
        private String numofintervalsstr = "How many number of intervals? *";
        private String intervaltypelabel = "What interval type do you prefer? *"; //close-ended or open-ended
        private Checkbox intopt, floatopt;
        private CheckboxGroup datatypeoptions;
        private Checkbox closeopt, openopt;
        private CheckboxGroup intervaltypeoptions;
        private JTextField intervalssizefield;
        private String titlelocal;

        GroupedTypeState(String title){
            titleFrame.dispose();
            titlelocal = title;
            typeFrame = new JFrame(title);
            typeFrame.setSize(width, height);
            typeFrame.getContentPane().setBackground(backcolor);
            typeFrame.setLocationRelativeTo(null);
            typeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            typeFrame.getContentPane().setLayout(null);
            typeFrame.setResizable(false);

            JLabel lbl3 = new JLabel(datatypelabel);
            lbl3.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl3.setBounds(20, 13, 350, 35);
            lbl3.setForeground(Color.BLACK);

            datatypeoptions = new CheckboxGroup();
            intopt = new Checkbox("int", datatypeoptions, false);
            intopt.setFont(new Font("Arial", Font.BOLD, 22));
            intopt.setForeground(Color.RED);
            intopt.setBounds(40, 50, 100, 40);
            floatopt = new Checkbox("float", datatypeoptions, false);
            floatopt.setFont(new Font("Arial", Font.BOLD, 22));
            floatopt.setForeground(Color.RED);
            floatopt.setBounds(40, 80, 100, 40);

            JLabel lbl4 = new JLabel(numofintervalsstr);
            lbl4.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl4.setBounds(20, 130, 350, 35);
            lbl4.setForeground(Color.BLACK);

            intervalssizefield = new JTextField();
            intervalssizefield.setBounds(20, 180, 290, 35);

            JLabel lbl5 = new JLabel(intervaltypelabel);
            lbl5.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl5.setBounds(20, 240, 350, 35);
            lbl5.setForeground(Color.BLACK);

            intervaltypeoptions = new CheckboxGroup();
            closeopt = new Checkbox("close-ended", intervaltypeoptions, false);
            closeopt.setFont(new Font("Arial", Font.BOLD, 22));
            closeopt.setForeground(Color.RED);
            closeopt.setBounds(40, 280, 140, 40);
            openopt = new Checkbox("open-ended", intervaltypeoptions, false);
            openopt.setFont(new Font("Arial", Font.BOLD, 22));
            openopt.setForeground(Color.RED);
            openopt.setBounds(40, 310, 140, 40);

            JLabel lbl6 = new JLabel("* Required");
            lbl6.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            lbl6.setBounds(390, 320, 100, 35);
            lbl6.setForeground(Color.RED);
            //lbl6

            nxt2 = new JButton(nextbutton);
            nxt2.setBounds(390, 270, 90, 40);
            nxt2.setEnabled(true);
            nxt2.setForeground(Color.BLACK);
            nxt2.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt2.setBackground(Color.WHITE);
            nxt2.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt2.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt2.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt2.addActionListener(new NextButtonListener2());

            typeFrame.add(lbl3);
            typeFrame.add(intopt);
            typeFrame.add(floatopt);
            typeFrame.add(lbl4);
            typeFrame.add(intervalssizefield);
            typeFrame.add(lbl5);
            typeFrame.add(openopt);
            typeFrame.add(closeopt);
            typeFrame.add(nxt2);
            typeFrame.add(lbl6);
            typeFrame.setVisible(true);
        }

        class NextButtonListener2 implements ActionListener {
            public void actionPerformed(ActionEvent arg0) {
                boolean err1 = datatypeErrorCatch();
                boolean err2 = numOfIntervalsErrorCatch();
                boolean err3 = intervaltypeErrorCatch();
                if (!err1 && !err2 && !err3) {
                    if(intervaltype==2){
                        //For asking open interval type from user
                        String txt = "Which class interval is open-ended? Enter 1-3.\n";
                        txt += "LEGEND: 1 = first, 2 = last, 3 = both";
                        int choice;
                        boolean inv = false, error = false;

                        //do while openintervaltype is invalid
                        do {
                            String input = JOptionPane.showInputDialog(typeFrame, txt);
                            if ( input != null ) {
                                try {
                                    choice = Integer.parseInt(input);
                                    if(choice < 0 || choice > 3){
                                        inv = true;
                                    }else{
                                        inv = false;
                                    }

                                    if(!inv){
                                        openintervaltype = choice;
                                        error = false;
                                    }else{
                                        error = true;
                                    }
                                } catch (Exception e) {
                                    error = true;
                                }

                                if(input.equals("")){
                                    JOptionPane.showMessageDialog(typeFrame,
                                            "Error: Must enter a number. Try again.",
                                            "Invalid input",
                                            JOptionPane.ERROR_MESSAGE);
                                }else if (inv || error) {
                                    JOptionPane.showMessageDialog(typeFrame,
                                            "Error: Must be 1-3 only. Try again.",
                                            "Invalid input",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                //when user cancels
                                error = false;
                            }
                        } while (error == true);
                    }

                    new GroupedDataState(titlelocal);
                }
            }
        }

        boolean datatypeErrorCatch() {
            boolean typeError = false;
            Checkbox cb = datatypeoptions.getSelectedCheckbox();
            try {
                String s = cb.getLabel();
                if (s.equals("int")) { datatype = 1; }
                else { datatype = 2; }
            } catch(NullPointerException e) {
                typeError = true;
                JOptionPane.showMessageDialog(typeFrame,
                        "Error: You must choose a type of data.",
                        "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
            }
            return typeError;
        }

        private boolean intervaltypeErrorCatch() {
            boolean intervalError = false;
            Checkbox cb = intervaltypeoptions.getSelectedCheckbox();
            try {
                String s = cb.getLabel();
                if (s.equals("close-ended")) { intervaltype = 1; }
                else {
                    intervaltype = 2;
                }
            } catch(NullPointerException e) {
                intervalError = true;
                JOptionPane.showMessageDialog(typeFrame,
                        "Error: You must choose a type of interval.",
                        "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
            }
            return intervalError;
        }

        boolean numOfIntervalsErrorCatch() {
            boolean numIntervalError = false;
            String ps = intervalssizefield.getText();
            try {
                if(ps.equals("")){
                    numIntervalError = true;
                    JOptionPane.showMessageDialog(typeFrame,
                            "Error: You must enter the number of intervals. Try again.", "FAILURE TO PROCEED",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    numintervals = Integer.parseInt(ps);
                    if (numintervals < 2) {
                        numIntervalError = true;
                        JOptionPane.showMessageDialog(typeFrame,
                                "Error: Your number of intervals must be at least 2. Try again.", "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (Exception e) {
                numIntervalError = true;
                JOptionPane.showMessageDialog(typeFrame,
                        "Error: Your number of intervals must be an integer. Try again.", "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
            }
            return numIntervalError;
        }
    }

    class UngroupedDataState {

        //public static JFrame dataFrame;
        private JButton nxt3, dispBtn;
        //public static ArrayList<Float> floatal = new ArrayList<Float>();
        //public static ArrayList<Integer> intal = new ArrayList<Integer>();
        TextArea ta2, dispData;
        private String l = "Enter data (N = " + size + ") *";
        private String localtitle;
        private JLabel lbl6;
        private String note = "NOTE: Separate each element via space or enter key";

        boolean lenErr = false;
        boolean formatErr = false;

        boolean sizeError(int len) {
            if (len < 0 || len < size) {
                JOptionPane.showMessageDialog(dataFrame,
                        "Error: Your input does not meet required size: " + size, "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            } else if (len > size) {
                JOptionPane.showMessageDialog(dataFrame,
                        "Error: Your input exceeds required size: " + size, "FAILURE TO PROCEED",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            }
            return false;
        }

        ArrayList sampleCatcher(ArrayList<String> arr) {
            ArrayList<String> data_al = new ArrayList<>();
            int type;
            if (datatype == 1) { //int
                type = 1;
            } else { //float
                type = 2;
            }

            String token1 = arr.get(0);
            if (token1.equals("")) {
                arr.remove(token1);
            }

            boolean typeerr = false;
            for (String st : arr) {
                try {
                    if (type == 1) {
                        int n = Integer.parseInt(st);
                        //i_al.add(n);
                        data_al.add(st);
                    } else {
                        double f = Double.parseDouble(st);
                        if (st.contains(".")) {
                            //f_al.add(f);
                            data_al.add(st);
                        } else {
                            formatErr = true;
                            JOptionPane.showMessageDialog(dataFrame,
                                    "Error: Input must be a floating point number." + " Try again.", "FAILURE TO PROCEED",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    }
                    formatErr = false;
                } catch (Exception e) {
                    formatErr = true;
                    typeerr = true;
                    String errorMsg;
                    if (type == 1) {
                        data_al.clear();
                        errorMsg = "Error: Input must be an integer.";
                    } else {
                        data_al.clear();
                        errorMsg = "Error: Input must be a floating point number.";
                    }
                    JOptionPane.showMessageDialog(dataFrame,
                            errorMsg + " Try again.", "FAILURE TO PROCEED",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            boolean sizeerror = sizeError(arr.size());
            lenErr = sizeerror;
            if (!sizeerror && !typeerr) {
                lenErr = false;
                return data_al;
            }
            return new ArrayList();
        }

        UngroupedDataState(String title) {
            typeFrame.dispose();
            localtitle = title;
            dataFrame = new JFrame(title);
            dataFrame.setSize(width, height);
            dataFrame.getContentPane().setBackground(backcolor);
            dataFrame.setLocationRelativeTo(null);
            dataFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            dataFrame.getContentPane().setLayout(null);
            dataFrame.setResizable(false);

            lbl6 = new JLabel(l);
            lbl6.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl6.setBounds(20, 13, 350, 35);
            lbl6.setForeground(Color.BLACK);

            ta2 = new TextArea();
            ta2.setEditable(true);
            ta2.setBounds(20, 47, 250, 260);

            dispData = new TextArea();
            dispData.setEditable(false);
            dispData.setBounds(285, 47, 200, 200);

            JLabel label = new JLabel("Data Set");
            label.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            label.setBounds(285, 13, 80, 35);

            JLabel lbl7 = new JLabel(note);
            lbl7.setBounds(20, 300, 350, 30);
            lbl7.setForeground(Color.RED);

            dispBtn = new JButton("Display");
            dispBtn.setBounds(360, 250, 100, 40);
            dispBtn.setEnabled(true);
            dispBtn.setForeground(Color.BLACK);
            dispBtn.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            dispBtn.setBackground(Color.WHITE);
            dispBtn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    dispBtn.setBackground(blend(Color.YELLOW, Color.CYAN));
                }

                public void mouseExited(MouseEvent evt) {
                    dispBtn.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            dispBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    String s = ta2.getText();
                    String[] toks = s.split("\\s+|\\n");
                    ArrayList<String> al = new ArrayList<>(Arrays.asList(toks));

                    dataset = sampleCatcher(al);
                    String dataStr = "";
                    for (int i = 0; i < dataset.size(); i++) {
                        dataStr = dataStr + "Index " + i + ": " + dataset.get(i);
                        dataStr = dataStr + "\n";
                    }
                    dispData.setText(dataStr);
                }
            });

            nxt3 = new JButton(nextbutton);
            nxt3.setBounds(360, 300, 100, 40);
            nxt3.setEnabled(true);
            nxt3.setForeground(Color.BLACK);
            nxt3.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt3.setBackground(Color.WHITE);
            nxt3.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt3.setBackground(blend(Color.YELLOW, Color.CYAN));
                }

                public void mouseExited(MouseEvent evt) {
                    nxt3.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            nxt3.addActionListener(new NextButtonListener3());

            dataFrame.add(lbl6);
            dataFrame.add(dispBtn);
            dataFrame.add(ta2);
            dataFrame.add(label);
            dataFrame.add(dispData);
            dataFrame.add(lbl7);
            dataFrame.add(nxt3);
            dataFrame.setVisible(true);
        }

        class NextButtonListener3 implements ActionListener {

            public void actionPerformed(ActionEvent arg0) {
                String s = ta2.getText();
                String[] toks = s.split("\\s+|\\n");
                ArrayList<String> al = new ArrayList<>(Arrays.asList(toks));

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "", dialogButton);
                if (answer == JOptionPane.YES_OPTION) {
                    if (id == 1) {
                        dataset = sampleCatcher(al);
                        if (!lenErr && !formatErr) {
                            new UngroupedMeasureState(dataset, localtitle);
                        }
                    }
                }else if (answer == JOptionPane.NO_OPTION) {
                    lbl6.setText("Edit data: " + "(N = " + size + ")");
                }

            }
        }
    }

    class GroupedDataState{
        private JButton nxt3;
        TextArea typedata;
        TextArea instruction;

        private String toplabel = "Enter " + numintervals + " intervals.";
        private JLabel lbl6;
        private String note = "FORMAT:" +
                "\nLower CL, Upper CL, Frequency" +
                "\nseparated via space" +
                "\n\nNOTE:" +
                "\nIntervals must be separated " +
                "\nby enter key and must" +
                "\nfollow correct format." +
                "\n\nEXAMPLE: " +
                "\n4 10 5" +
                "\n11 17 3";

        GroupedDataState(String title) {
            typeFrame.dispose();
            dataFrame = new JFrame(title);
            dataFrame.setSize(width, height);
            dataFrame.getContentPane().setBackground(backcolor);
            dataFrame.setLocationRelativeTo(null);
            dataFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            dataFrame.getContentPane().setLayout(null);
            dataFrame.setResizable(false);

            lbl6 = new JLabel(toplabel);
            lbl6.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl6.setBounds(20, 13, 350, 35);
            lbl6.setForeground(Color.BLACK);

            typedata = new TextArea();
            typedata.setEditable(true);
            typedata.setBounds(20, 47, 250, 260);

            JLabel instructionlabel = new JLabel("INSTRUCTION");
            instructionlabel.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            instructionlabel.setBounds(285, 13, 150, 35);

            instruction = new TextArea();
            instruction.setEditable(false);
            instruction.setBounds(285, 47, 200, 220);
            instruction.setForeground(Color.BLUE);
            instruction.setText(note);

            nxt3 = new JButton(nextbutton);
            nxt3.setBounds(360, 300, 100, 40);
            nxt3.setEnabled(true);
            nxt3.setForeground(Color.BLACK);
            nxt3.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt3.setBackground(Color.WHITE);
            nxt3.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt3.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt3.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            nxt3.addActionListener(new NextButtonListener3());

            dataFrame.add(lbl6);
            dataFrame.add(typedata);
            dataFrame.add(instructionlabel);
            dataFrame.add(instruction);
            dataFrame.add(nxt3);
            dataFrame.setVisible(true);
        }

        class NextButtonListener3 implements ActionListener {

            public void actionPerformed(ActionEvent arg0) {
                freqtotal = 0;
                freqtimesCMtotal = 0;
                freqtimesCMSquaredtotal = 0;
                if(intervaltype==1){
                    closedintervaldataset.clear();
                }else{
                    openintervaldataset.clear();
                }
                String s = typedata.getText();
                String[] toks = s.split("\\s+|\\n");
                ArrayList<String> al = new ArrayList<>(Arrays.asList(toks));
                int len = al.size();
                int required = numintervals * 3;
                int intlowercl, intuppercl, freq, intprev = -1;
                Float floatlowercl, floatuppercl, floatprev = -1.f;

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int answer =  JOptionPane.showConfirmDialog (null, "Do you want to continue?","", dialogButton);
                if(answer==JOptionPane.YES_OPTION){
                    if (len < 0 || len < required){
                        JOptionPane.showMessageDialog(dataFrame,
                                "Error: Your input does not meet required intervals: " + numintervals, "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (len > required){
                        JOptionPane.showMessageDialog(dataFrame,
                                "Error: Your input exceeds required intervals: " + numintervals, "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    }else{
                        int validcount = 0;
                        for(int i = 0; i < len; i+=3){
                            if(datatype==1){
                                intlowercl = (Integer) parse("int", "Lower CL", al.get(i));
                                intuppercl = (Integer) parse("int", "Upper CL", al.get(i+1));
                                freq = (Integer) parse("int", "Frequency", al.get(i+2));

                                if(intlowercl!=-2 && intuppercl!=-2 && freq!=-2){
                                    if(intlowercl < intuppercl){
                                        if(intprev < intlowercl){
                                            intprev = intuppercl;

                                            process(intlowercl, intuppercl, freq);
                                            validcount++;
                                        }else{
                                            String message = "Error: Lower CL (" + intlowercl + ") must be greater than " + "Previous Upper CL " + "(" +  intprev + "). Try again.";
                                            JOptionPane.showMessageDialog(dataFrame,
                                                    message, "Invalid input",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }else{
                                        String message = "Error: Lower CL (" + intlowercl + ") must be lesser than  Upper CL (" + intuppercl + "). Try again.";
                                        JOptionPane.showMessageDialog(dataFrame,
                                                message, "Invalid input",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }else if(datatype==2){
                                floatlowercl = (Float) parse("float", "Lower CL", al.get(i));
                                floatuppercl = (Float) parse("float", "Upper CL", al.get(i+1));
                                freq = (Integer) parse("int", "Frequency", al.get(i+2));

                                if(floatlowercl.compareTo(-2.f)!=0 && floatuppercl.compareTo(-2.f)!=0 && freq!=-2){
                                    if(floatlowercl.compareTo(floatuppercl) < 0){
                                        if(floatprev.compareTo(floatlowercl) < 0){
                                            floatprev = floatuppercl;

                                            process(floatlowercl, floatuppercl, freq);
                                            validcount++;
                                        }else{
                                            String message = "Error: Lower CL (" + floatlowercl + ") must be greater than " + "Previous Upper CL " + "(" +  floatprev + "). Try again.";
                                            JOptionPane.showMessageDialog(dataFrame,
                                                    message, "Invalid input",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }else{
                                        String message = "Error: Lower CL (" + floatlowercl + ") must be lesser than  Upper CL (" + floatuppercl + "). Try again.";
                                        JOptionPane.showMessageDialog(dataFrame,
                                                message, "Invalid input",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }

                        if(validcount==numintervals){
                            new GroupedDisplayState(title);
                        }else{
                            openintervaldataset.clear();
                            closedintervaldataset.clear();
                        }
                    }

                }else{
                    lbl6.setText("Edit intervals: (" + numintervals + " intervals)");
                }
            }
        }

        //updates freqtotal, freqtimesCMtotal, freqtimesCMSquaredtotal, and closed or open interval dataset
        void process(Object lowercl, Object uppercl, int freq){
            if(intervaltype==1){
                float trueLowerCL = getTrueLowerCL(lowercl);
                float trueUpperCL = getTrueUpperCL(uppercl);
                float classmark = getMidpoint(trueLowerCL, trueUpperCL);
                ArrayList arr = new ArrayList();
                String cl;
                if(datatype==1){
                    cl = String.format("%d - %d", lowercl, uppercl);
                }else{
                    cl = String.format("%.2f - %.2f", lowercl, uppercl);
                }

                arr.add(cl);
                arr.add(freq);
                freqtotal += freq;
                String truecl = String.format("%.2f - %.2f", trueLowerCL, trueUpperCL);
                arr.add(truecl);
                arr.add(classmark);
                Float freqtimesCM = freq * classmark;
                freqtimesCMtotal += freqtimesCM;
                arr.add(freqtimesCM);
                Float freqtimesCMSquared = freq * classmark * classmark;
                freqtimesCMSquaredtotal += freqtimesCMSquared;
                arr.add(freqtimesCMSquared);
                closedintervaldataset.add(arr);
            }else{
                ArrayList arr = new ArrayList();
                String cl;
                if(datatype==1){
                    if(openintervaltype==1){
                        cl = String.format("≤ %d", uppercl);
                    }else if(openintervaltype==2){
                        cl = String.format("%d ≥", lowercl);
                    }else {
                        cl = String.format("%d ≥ and ≤ %d", lowercl, uppercl);
                    }
                }else{
                    if(openintervaltype==1){
                        cl = String.format("≤ %.2f", uppercl);
                    }else if(openintervaltype==2){
                        cl = String.format("%.2f ≥", lowercl);
                    }else {
                        cl = String.format("%.2f ≥ and ≤ %.2f", lowercl, uppercl);
                    }
                }

                arr.add(cl);
                arr.add(freq);
                freqtotal += freq;
                openintervaldataset.add(arr);
            }
        }

        float getTrueLowerCL(Object lowercl) {
            float trueLowerCL;
            if(datatype==1){
                trueLowerCL = (Integer)lowercl - 0.5f;
            }else {
                trueLowerCL = (Float)lowercl - 0.005f;
            }

            return trueLowerCL;
        }

        float getTrueUpperCL(Object uppercl) {
            float trueUpperCL;
            if (datatype == 1) {
                trueUpperCL = (Integer)uppercl + 0.5f;
            } else{
                trueUpperCL = (Float) uppercl + 0.005f;
            }
            return trueUpperCL;
        }

        float getMidpoint(Float trueLowerCL, Float trueUpperCL) {
            float midpoint = (trueLowerCL + trueUpperCL) / 2;
            return midpoint;
        }

        Object parse(String datatype, String inputted, String number){
            if(datatype.equals("int")){
                try{
                    Integer res = Integer.parseInt(number);
                    if(res < 0){
                        String message = "Error: " +  inputted + " (" + number + ") must be positive. Try again.";
                        JOptionPane.showMessageDialog(dataFrame,
                                message, "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }else{
                        return res;
                    }
                }catch(Exception e){
                    String message = "Error: " +  inputted + " (" + number + ") must be an integer. Try again.";
                    JOptionPane.showMessageDialog(dataFrame,
                            message, "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }else if(datatype.equals("float")){
                try{
                    Float res = Float.parseFloat(number);
                    if (number.contains(".")) {
                        if(res.compareTo(0.f)>=0){
                            return res;
                        }else{
                            String message = "Error: " +  inputted + " (" + number + ") must be positive. Try again.";
                            JOptionPane.showMessageDialog(dataFrame,
                                    message, "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                            return -2.f;
                        }
                    } else {
                        String message = "Error: " +  inputted + " (" + number + ") must be a floating point number." + " Try again.";
                        JOptionPane.showMessageDialog(dataFrame,
                                message,
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                        return -2.f;
                    }
                }catch(Exception e){
                    String message = "Error: " +  inputted + " (" + number + ") must be a floating point number." + " Try again.";
                    JOptionPane.showMessageDialog(dataFrame,
                            message,
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                    return -2.f;
                }
            }

            return -2;
        }
    }

    public class GroupedDisplayState{
        private JButton nxt3;
        private JLabel totalsTxtField, totalsfreq;
        private JTable table;
        private DefaultTableModel model;
        private JScrollPane pane;
        private String localTitle;
        private JLabel lbl6;
        private String txt;


        GroupedDisplayState(String title) {
            dataFrame.dispose();
            localTitle = title;
            displayFrame = new JFrame();
            displayFrame.setSize(width, height);
            displayFrame.getContentPane().setBackground(backcolor);
            displayFrame.setLocationRelativeTo(null);
            displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            displayFrame.getContentPane().setLayout(null);
            displayFrame.setResizable(false);

            lbl6 = new JLabel(localTitle);
            lbl6.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl6.setForeground(Color.BLACK);

            table = new JTable(){
                //Implement table cell tool tips.
                public String getToolTipText(MouseEvent e) {
                    String tip = null;
                    Point p = e.getPoint();
                    int rowIndex = rowAtPoint(p);
                    int colIndex = columnAtPoint(p);

                    try {
                        tip = getValueAt(rowIndex, colIndex).toString();
                    } catch (RuntimeException e1) {
                        //catch null pointer exception if mouse is over an empty line
                    }
                    return tip;
                }
            };

            nxt3 = new JButton(nextbutton);
            nxt3.setEnabled(true);
            nxt3.setForeground(Color.BLACK);
            nxt3.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt3.setBackground(Color.WHITE);
            nxt3.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt3.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt3.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            nxt3.addActionListener(new NextButtonListener3());

            // Change a JTable Background Color, Font Size, Font Color, Row Height
            table.setBackground(Color.LIGHT_GRAY);
            table.setForeground(Color.BLACK);
            Font headerFont = new Font("Verdana", Font.BOLD, 18);
            table.getTableHeader().setFont(headerFont);
            Font rowFont = new Font("Verdana",Font.PLAIN,16);
            table.setFont(rowFont);
            table.setRowHeight(30);
            table.setDefaultEditor(Object.class, null); //to disable cell editing

            // create a table model and set a Column Identifiers to this model
            model = new DefaultTableModel();
            pane = new JScrollPane(table);
            if (intervaltype == 1) {
                lbl6.setBounds(50, 0, 900, 50);
                pane.setBounds(50, 50, 600, 350);
                nxt3.setBounds(550, 430, 100, 40);
                displayFrame.setBounds(100, 100, 700, 545);

                totalsTxtField = new JLabel();
                txt = "• Σ f = " + freqtotal + "  • Σ fx = " + freqtimesCMtotal +
                        "  • Σ fx^2 = " + freqtimesCMSquaredtotal;
                totalsTxtField.setText(txt);
                totalsTxtField.setFont(headerFont);
                totalsTxtField.setForeground(Color.BLUE);
                totalsTxtField.setBounds(50, 420, 500, 50);
                displayFrame.add(totalsTxtField);

                displayFrame.setTitle("Close-ended Interval");
                Object[] columns = {"Class Limit", "Freq (f)", "True CL", "CM (x)", "fx", "fx^2"};
                model.setColumnIdentifiers(columns);
                table.setModel(model);

                table.getColumnModel().getColumn(0).setPreferredWidth(100);
                table.getColumnModel().getColumn(2).setPreferredWidth(100);

            } else if (intervaltype == 2) {
                lbl6.setBounds(40, 13, 350, 35);
                pane.setBounds(40, 47, 420, 260);
                nxt3.setBounds(360, 320, 100, 40);
                displayFrame.setSize(width, height);

                totalsfreq = new JLabel();
                totalsfreq.setFont(headerFont);
                txt = "• Σ f = " + freqtotal;
                totalsfreq.setText(txt);
                totalsfreq.setFont(headerFont);
                totalsfreq.setForeground(Color.BLUE);
                totalsfreq.setBounds(40, 320, 100, 35);
                displayFrame.add(totalsfreq);

                displayFrame.setTitle("Open-ended Interval");
                Object[] columns = {"Class Limit","Frequency"};

                model.setColumnIdentifiers(columns);
                table.setModel(model);
            }

            // create an array of objects to set the row data
            if(intervaltype==1){
                Object[] row = new Object[6];
                int len = closedintervaldataset.size();
                for (int i = 0; i < len; i++) {
                    ArrayList al = closedintervaldataset.get(i);
                    Object cl = al.get(0);
                    Object freq = al.get(1);
                    Object truecl = al.get(2);
                    Object classmark = al.get(3);
                    Object fx = al.get(4);
                    Object fxx = al.get(5);

                    row[0] = cl;
                    row[1] = freq;
                    row[2] = truecl;
                    row[3] = classmark;
                    row[4] = fx;
                    row[5] = fxx;
                    model.addRow(row);
                }
            }else{
                Object[] row = new Object[2];
                int len = openintervaldataset.size();
                for (int i = 0; i < len; i++) {
                    ArrayList al = openintervaldataset.get(i);
                    Object cl = al.get(0);
                    Object freq = al.get(1);
                    row[0] = cl;
                    row[1] = freq;
                    model.addRow(row);
                }
            }

            //to center elements in table
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            int cols = table.getColumnCount();
            for(int i = 0;i < cols ;i++){
                table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            }

            displayFrame.add(lbl6);
            displayFrame.add(pane);
            displayFrame.add(nxt3);
            displayFrame.setVisible(true);
        }

        class NextButtonListener3 implements ActionListener {

            public void actionPerformed(ActionEvent arg0) {
                new GroupedMeasureState(title);
            }
        }
    }

    class UngroupedMeasureState {

        //public JFrame mFrame;
        private JButton nxt4, gen;
        private TextArea ta3;
        private CheckboxGroup opts;
        private Checkbox meanB, medianB, modeB, allB;

        UngroupedMeasureState(ArrayList<String> arr, String s) {
            dataFrame.dispose();
            mFrame = new JFrame(s + "  N = " + size);
            mFrame.setSize(width, height);
            mFrame.getContentPane().setBackground(backcolor);
            mFrame.setLocationRelativeTo(null);
            mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mFrame.getContentPane().setLayout(null);
            mFrame.setResizable(false);

            JLabel lbl8 = new JLabel("Measure of Central Tendency");
            lbl8.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl8.setBounds(20, 13, 350, 35);
            lbl8.setForeground(Color.BLACK);

            opts = new CheckboxGroup();
            meanB = new Checkbox("Mean", opts, false);
            meanB.setFont(new Font("Arial", Font.BOLD, 22));
            meanB.setForeground(Color.RED);
            meanB.setBounds(40, 60, 100, 40);
            medianB = new Checkbox("Median", opts, false);
            medianB.setFont(new Font("Arial", Font.BOLD, 22));
            medianB.setForeground(Color.RED);
            medianB.setBounds(40, 90, 100, 40);
            modeB = new Checkbox("Mode", opts, false);
            modeB.setFont(new Font("Arial", Font.BOLD, 22));
            modeB.setForeground(Color.RED);
            modeB.setBounds(40, 120, 100, 40);
            allB = new Checkbox("All", opts, false);
            allB.setFont(new Font("Arial", Font.BOLD, 22));
            allB.setForeground(Color.RED);
            allB.setBounds(40, 150, 100, 40);

            gen = new JButton("Generate");
            gen.setBounds(300, 120, 120, 40);
            gen.setEnabled(true);
            gen.setForeground(Color.BLACK);
            gen.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            gen.setBackground(Color.WHITE);
            gen.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    gen.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    gen.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            gen.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    Checkbox s = opts.getSelectedCheckbox();
                    try {
                        String t = s.getLabel();
                        if (t.equals("Mean")) { centraltendency = 1; }
                        else if (t.equals("Median")) { centraltendency = 2; }
                        else if (t.equals("Mode")) { centraltendency = 3; }
                        else if (t.equals("All")) {centraltendency = 4; }
                        compute(arr);
                        if (centraltendency == 1) {
                            ta3.setText("MEAN:  " + mean + "\n" +
                                    "VARIANCE:  " + var + "\n" +
                                    "STANDARD DEVIATION:  " + sd + "\n");
                        } else if (centraltendency == 2) {
                            if (datatype == 1) {
                                ta3.setText("MEDIAN:  " + medianInt + "\n" +
                                            "RANGE: " + (int) (max - min) + "\n");
                            } else {
                                ta3.setText("MEDIAN:  " + medianFloat + "\n" +
                                            "RANGE: " + (max - min) + "\n");
                            }
                        } else if (centraltendency == 3) {
                            String res = "MODE(s): ";
                            String[] mods = mode(arr);
                            int len = mods.length;
                            if (len == size) {
                                ta3.setText("MODE(s): no mode");
                            } else {
                                for (int i = 0; i < len; i++) {
                                    res = res + mods[i] + " ";
                                }
                                if (len == 1) {
                                    res = res + "unimodal";
                                } else if (len == 2) {
                                    res = res + "bimodal";
                                } else if (len >= 3) {
                                    res = res + "multimodal";
                                }
                                ta3.setText(res);
                            }
                        } else {
                            String meanX = "MEAN:  " + mean;
                            String varX = "VARIANCE:  " + var;
                            String sdX = "STANDARD DEVIATION:  " + sd;
                            String medX;
                            String modX = "MODE(s): ";
                            String[] mods = mode(arr);
                            int len = mods.length;
                            if (datatype == 1) {
                                medX = "MEDIAN:  " + medianInt + "\n" +
                                        "RANGE: " + (int) (max - min);
                            } else {
                                medX = "MEDIAN:  " + medianFloat + "\n" +
                                        "RANGE: " + (max - min);
                            }

                            if (len == size) {
                                modX = modX + "no mode";
                            } else {
                                for (int i = 0; i < len; i++) {
                                    modX = modX + mods[i] + " ";
                                }
                                if (len == 1) {
                                    modX = modX + "unimodal";
                                } else if (len == 2) {
                                    modX = modX + "bimodal";
                                } else if (len >= 3) {
                                    modX = modX + "multimodal";
                                }
                            }

                            ta3.setText(meanX + "\n" + varX + "\n" + sdX + "\n" +
                                    "\n" + "\n" + medX + "\n" + "\n" + modX);
                        }
                    } catch(NullPointerException e) {
                        JOptionPane.showMessageDialog(mFrame,
                                "Error: You must choose one.", "CANNOT GENERATE RESULTS",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            });

            JLabel lbl9 = new JLabel("Results");
            lbl9.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl9.setBounds(20, 170, 280, 35);
            lbl9.setForeground(Color.BLACK);

            ta3 = new TextArea();
            ta3.setBounds(20, 205, 305, 145);
            ta3.setEditable(false);

            nxt4 = new JButton(nextbutton);
            nxt4.setBounds(390, 270, 90, 40);
            nxt4.setEnabled(true);
            nxt4.setForeground(Color.BLACK);
            nxt4.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt4.setBackground(Color.WHITE);
            nxt4.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt4.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt4.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ta3.getText().equals("")){
                        JOptionPane.showMessageDialog(mFrame,
                                "Error: You must generate results first.", "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        new Interpretation(title, "Ungrouped");
                    }
                }

            });

            mFrame.add(lbl8);
            mFrame.add(meanB);
            mFrame.add(medianB);
            mFrame.add(modeB);
            mFrame.add(allB);
            mFrame.add(gen);
            mFrame.add(lbl9);
            mFrame.add(ta3);
            mFrame.add(nxt4);
            mFrame.setVisible(true);
        }

        double mean(ArrayList<String> al) {
            double sum = 0;
            int len = al.size();
            for (int i = 0; i < len; i++) {
                if (datatype == 1) { //int
                    int el = Integer.parseInt(al.get(i));
                    sum = sum + el;
                } else {
                    double el = Double.parseDouble(al.get(i));
                    sum = sum + el;
                }
            }
            return sum / len;
        }

        double variance(ArrayList<String> al) {
            double ans = 0;
            int len = al.size();
            for (int i = 0; i < len; i++) {
                if (datatype == 1) {
                    int el = Integer.parseInt(al.get(i));
                    ans = (ans + Math.pow((el - mean), 2));
                } else {
                    double el = Double.parseDouble(al.get(i));
                    ans = (ans + Math.pow((el - mean), 2));
                }
            }
            return ans / (len - 1);
        }

        double median(ArrayList<String> arr) {
            ArrayList<Integer> alInt = new ArrayList<>();
            ArrayList<Double> alFloat = new ArrayList<>();
            int len = size;
            if (datatype == 1) {
                for (String s : arr) {
                    int n = Integer.parseInt(s);
                    alInt.add(n);
                }
                Collections.sort(alInt);
                max = (int) alInt.get(len - 1);
                min = (int) alInt.get(0);
            } else {
                for (String s : arr) {
                    double n = Double.parseDouble(s);
                    alFloat.add(n);
                }
                Collections.sort(alFloat);
                max = alFloat.get(len - 1);
                min = alFloat.get(0);
            }
            double med = 0;
            //FOR ODD
            if (len % 2 != 0) {
                int in = (len + 1) / 2;
                if (datatype == 1) {
                    int el = Integer.parseInt(alInt.get(in).toString());
                    med = el;
                } else {
                    double el = Double.parseDouble(alFloat.get(in).toString());
                    med = el;
                }

                //FOR EVEN
            } else {
                int x1 = (len / 2) - 1;
                int x2 = ((len + 2) / 2) - 1;
                if (datatype == 1) {
                    int el1 = Integer.parseInt(alInt.get(x1).toString());
                    int el2 = Integer.parseInt(alInt.get(x2).toString());
                    med = (el1 + el2) / 2;
                } else {
                    double el1 = Double.parseDouble(alFloat.get(x1).toString());
                    double el2 = Double.parseDouble(alFloat.get(x2).toString());
                    med = (el1 + el2) / 2;
                }
            }
            return med;
        }

        String[] mode(ArrayList<String> a) {
            java.util.List<String> mods = new ArrayList<>(  );
            int maxCount = 0;
            for (int i = 0; i < a.size(); ++i) {
                int count = 0;
                for (int j = 0; j < a.size(); ++j) {
                    if (a.get(j).equals(a.get(i))) {
                        ++count;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    mods.clear();
                    if (!mods.contains(a.get(i))) {
                        mods.add(a.get(i));
                    }
                } else if (count == maxCount) {
                    if (!mods.contains(a.get(i))) {
                        mods.add(a.get(i));
                    }
                }
            }
            return mods.toArray(new String[mods.size()]);
        }

        void compute(ArrayList<String> a) {
            if (datatype == 1) { //int
                if (centraltendency == 1) { //mean
                    mean = mean(a);
                    var = variance(a);
                    sd = Math.sqrt(var);
                } else if (centraltendency == 2) { //median
                    medianInt = (int) median(a);
                } else if (centraltendency == 3) { //mode
                    modes = mode(a);
                } else {
                    mean = mean(a);
                    var = variance(a);
                    sd = Math.sqrt(var);
                    medianInt = (int) median(a);
                    modes = mode(a);
                }
            } else if (datatype == 2) { //float
                if (centraltendency == 1) { //mean
                    mean = mean(a);
                    var = variance(a);
                    sd = Math.sqrt(var);
                } else if (centraltendency == 2) { //median
                    medianFloat = median(a);
                } else if (centraltendency == 3) { //mode
                    modes = mode(a);
                } else {
                    mean = mean(a);
                    var = variance(a);
                    sd = Math.sqrt(var);
                    medianFloat = median(a);
                    modes = mode(a);
                }
            }
        }
    }

    class GroupedMeasureState {
        private JButton nxt4, gen;
        private TextArea ta3;
        private CheckboxGroup opts;
        private Checkbox meanB, medianB, modeB, all;

        GroupedMeasureState(String title) {
            displayFrame.dispose();
            mFrame = new JFrame(title + "  (intervals = " + numintervals + ")");
            mFrame.setSize(width, height);
            mFrame.getContentPane().setBackground(backcolor);
            mFrame.setLocationRelativeTo(null);
            mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mFrame.getContentPane().setLayout(null);
            mFrame.setResizable(false);

            JLabel lbl8 = new JLabel("Measure of Central Tendency");
            lbl8.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl8.setBounds(20, 13, 350, 35);
            lbl8.setForeground(Color.BLACK);

            opts = new CheckboxGroup();
            meanB = new Checkbox("Mean", opts, false);
            meanB.setFont(new Font("Arial", Font.BOLD, 22));
            meanB.setForeground(Color.RED);
            meanB.setBounds(40, 50, 100, 30);
            medianB = new Checkbox("Median", opts, false);
            medianB.setFont(new Font("Arial", Font.BOLD, 22));
            medianB.setForeground(Color.RED);
            medianB.setBounds(40, 80, 100, 30);
            modeB = new Checkbox("Mode", opts, false);
            modeB.setFont(new Font("Arial", Font.BOLD, 22));
            modeB.setForeground(Color.RED);
            modeB.setBounds(40, 110, 100, 30);
            all = new Checkbox("All", opts, false);
            all.setFont(new Font("Arial", Font.BOLD, 22));
            all.setForeground(Color.RED);
            all.setBounds(40, 140, 100, 30);

            gen = new JButton("Generate");
            gen.setBounds(300, 120, 120, 40);
            gen.setEnabled(true);
            gen.setForeground(Color.BLACK);
            gen.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            gen.setBackground(Color.WHITE);
            gen.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    gen.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    gen.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });

            gen.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    Checkbox s = opts.getSelectedCheckbox();
                    try {
                        String t = s.getLabel();
                        if (t.equals("Mean")){
                            centraltendency = 1;
                        }else if (t.equals("Median")) {
                            centraltendency = 2;
                        } else if (t.equals("Mode")) {
                            centraltendency = 3;
                        }else{
                            centraltendency = 4;
                        }

                        //FOR CLOSE INTERVAL
                        if(intervaltype==1){
                            compute(); //this is important
                            String meanres = "MEAN:  " + mean + "\n" +
                                    "VARIANCE:  " + var + "\n" +
                                    "STANDARD DEVIATION:  " + sd + "\n\n";
                            String medianres = "MEDIAN: not computed\n\n";
                            String moderes = modeString();

                            if(centraltendency==1){
                                ta3.setText(meanres);
                            }else if(centraltendency==2){
                                ta3.setText(medianres);
                            }else if(centraltendency==3){
                                ta3.setText(moderes);
                            }else{
                                String res = meanres + medianres + moderes;
                                ta3.setText(res);
                            }

                            //FOR OPEN INTERVAL
                        }else{
                            compute(); //this is important
                            String meanres = "MEAN: cannot be computed\n\n";
                            String medianres = "MEDIAN: not computed\n\n";
                            String moderes = modeString();
                            if(centraltendency==1){
                                ta3.setText(meanres);
                            }else if(centraltendency==2){
                                ta3.setText(medianres);
                            }else if(centraltendency==3){
                                ta3.setText(moderes);
                            }else{
                                String res = meanres + medianres + moderes;
                                ta3.setText(res);
                            }
                        }
                    } catch(NullPointerException e) {
                        JOptionPane.showMessageDialog(mFrame,
                                "Error: You must choose one.", "CANNOT GENERATE RESULTS",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            });

            JLabel lbl9 = new JLabel("Results");
            lbl9.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl9.setBounds(20, 170, 280, 35);
            lbl9.setForeground(Color.BLACK);

            ta3 = new TextArea();
            ta3.setBounds(20, 205, 305, 145);
            ta3.setEditable(false);

            nxt4 = new JButton(nextbutton);
            nxt4.setBounds(390, 270, 90, 40);
            nxt4.setEnabled(true);
            nxt4.setForeground(Color.BLACK);
            nxt4.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt4.setBackground(Color.WHITE);
            nxt4.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt4.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt4.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ta3.getText().equals("")){
                        JOptionPane.showMessageDialog(mFrame,
                                "Error: You must generate results first.", "FAILURE TO PROCEED",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        new Interpretation(title, "Grouped");
                    }
                }
            });

            mFrame.add(lbl8);
            mFrame.add(meanB);
            mFrame.add(medianB);
            mFrame.add(modeB);
            mFrame.add(all);
            mFrame.add(gen);
            mFrame.add(lbl9);
            mFrame.add(ta3);
            mFrame.add(nxt4);
            mFrame.setVisible(true);
        }

        double mean() {
            double mean = freqtimesCMtotal / freqtotal;
            return mean;
        }

        double variance(){
            double var = ((freqtotal*freqtimesCMSquaredtotal) - (freqtimesCMtotal*freqtimesCMtotal)) / (freqtotal * (freqtotal - 1.f));
            return var;
        }

        double stddev(){
            double sd = Math.sqrt(var);
            return sd;
        }

        String[] mode() {
            List<String> mods = new ArrayList<>();
            if(intervaltype==1){
                int maxCount = 0;
                int len = closedintervaldataset.size();
                for(int i = 0; i < len; i++){
                    ArrayList al = closedintervaldataset.get(i);
                    String currinterval = (String)al.get(0); //gets interval
                    int count = (Integer) al.get(1); //gets frequency

                    if(count > maxCount){
                        maxCount = count;
                        mods.clear();
                        if (!mods.contains(currinterval)) {
                            mods.add(currinterval);
                        }
                    }else if (count == maxCount) {
                        if (!mods.contains(currinterval)) {
                            mods.add(currinterval);
                        }
                    }
                }
            }else{
                int maxCount = 0;
                int len = openintervaldataset.size();
                for(int i = 0; i < len; i++){
                    ArrayList al = openintervaldataset.get(i);
                    String currinterval = (String)al.get(0); //gets interval
                    int count = (Integer) al.get(1); //gets frequency

                    if(count > maxCount){
                        maxCount = count;
                        mods.clear();
                        if (!mods.contains(currinterval)) {
                            mods.add(currinterval);
                        }
                    }else if (count == maxCount) {
                        if (!mods.contains(currinterval)) {
                            mods.add(currinterval);
                        }
                    }
                }
            }

            return mods.toArray(new String[mods.size()]);
        }

        void compute() {
            if(intervaltype==1){
                //median cannot be calculated
                mean = mean();
                var = variance();
                sd = stddev();
                modes = mode();
            }else{
                //mean and median cannot be calculated
                modes = mode();
            }
        }

        String modeString(){
            String moderes = "MODE(s): ";
            String[] mods = mode();
            int len = mods.length;
            if (len == size) {
                ta3.setText("no mode");
            } else {
                for (int i = 0; i < len; i++) {
                    moderes = moderes + mods[i] + " ";
                }
                if (len == 1) {
                    moderes = moderes + "unimodal";
                } else if (len == 2) {
                    moderes = moderes + "bimodal";
                } else if (len >= 3) {
                    moderes = moderes + "multimodal";
                }
            }
            return moderes;
        }
    }

    class Interpretation {
        private TextArea ta4;
        private JButton nxt5;
        private JLabel lbl10;
        private String classname; //Ungrouped, Grouped

        Interpretation(String s, String classname) {
            mFrame.dispose();
            this.classname = classname;
            iFrame = new JFrame(s);
            iFrame.setSize(width, height);
            iFrame.getContentPane().setBackground(backcolor);
            iFrame.setLocationRelativeTo(null);
            iFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            iFrame.getContentPane().setLayout(null);
            iFrame.setResizable(false);

            lbl10 = new JLabel("Enter the interpretation for the output: *");
            lbl10.setFont(new Font("Arial Narrow", Font.BOLD, 24));
            lbl10.setBounds(20, 13, 450, 35);
            lbl10.setForeground(Color.BLACK);

            ta4 = new TextArea();
            ta4.setBounds(20, 47, 350, 260);

            nxt5 = new JButton(nextbutton);
            nxt5.setBounds(390, 280, 90, 40);
            nxt5.setEnabled(true);
            nxt5.setForeground(Color.BLACK);
            nxt5.setFont(new Font("Arial Narrow", Font.BOLD, 22));
            nxt5.setBackground(Color.WHITE);
            nxt5.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    nxt5.setBackground(blend(Color.YELLOW, Color.CYAN));
                }
                public void mouseExited(MouseEvent evt) {
                    nxt5.setBackground(Color.WHITE); //blend(Color.BLUE, Color.CYAN)
                }
            });
            nxt5.addActionListener(new NextButtonListener5());

            iFrame.add(lbl10);
            iFrame.add(ta4);
            iFrame.add(nxt5);
            iFrame.setVisible(true);
        }

        class NextButtonListener5 implements ActionListener {
            public void actionPerformed(ActionEvent arg0) {
                String s = ta4.getText();
                String ps = s;
                if (s.equals("")) {
                    JOptionPane.showMessageDialog(iFrame,
                            "ERROR: You must enter an interpretation for the output. Try again.", "FAILURE TO PROCEED",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    String msg = "Do you want to generate results using the same data set?";
                    int answer = JOptionPane.showConfirmDialog(iFrame, msg);
                    if (answer == JOptionPane.YES_OPTION) {
                        iFrame.dispose();
                        if(classname.equals("Ungrouped")){
                            new UngroupedMeasureState(dataset, title);
                        }else{
                            new GroupedMeasureState(title);
                        }
                    } else if (answer == JOptionPane.NO_OPTION) {
                        iFrame.dispose();
                        new CentralTendency();
                    }
                }
            }
        }
    }
}
