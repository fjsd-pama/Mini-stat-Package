import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataPresentation {
    private JFrame presentationmenu;

    private int id, popsize = 0;
    private Color backcolor = Color.WHITE;
    private Color buttons = Color.CYAN;
    private String subtype = "", datadesc = "";
    private JFrame inputstate, tablestate;

    private ArrayList<Integer> intal;
    private ArrayList<Double> floatal;
    private ArrayList numlabel;
    private ArrayList charal;
    private ArrayList strcharal;

    private String numLabel = "NUMERIC LABEL",
            charLabel = "CHARACTER",
            strLabel = "STRING",
            intLabel = "INTEGER",
            floatLabel = "FLOAT";

    DataPresentation(){
        intal = new ArrayList<>();
        floatal = new ArrayList<>();
        numlabel = new ArrayList();
        charal = new ArrayList();
        strcharal = new ArrayList();
        new Menu();
    }

    class Menu{
        private JLabel background, categorical, numerical, back, quit;
        Menu(){
            presentationmenu = new JFrame("Presenting Data");
            presentationmenu.setResizable(false);
            presentationmenu.setBounds(0, 0, 800, 600);
            presentationmenu.setLocationRelativeTo(null);
            presentationmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            presentationmenu.getContentPane().setLayout(null);

            background = new JLabel();
            background.setIcon(new ImageIcon(getClass().getResource("data-presentation menu.png")));
            background.setBounds(0, 0, 800, 600);

            categorical = new JLabel();
            categorical.setIcon(new ImageIcon(getClass().getResource("categorical data.png")));
            categorical.setBounds(195, 200, 200, 170);
            categorical.setToolTipText("Generates a pie chart out of a summary table");

            numerical = new JLabel();
            numerical.setIcon(new ImageIcon(getClass().getResource("num data.png")));
            numerical.setBounds(430, 200, 200, 170);
            numerical.setToolTipText("Generates a histogram out of a frequency distribution table");

            back = new JLabel();
            back.setIcon(new ImageIcon(getClass().getResource("back.png")));
            back.setBounds(410, 400, 150, 150);
            back.setToolTipText("Back");

            quit = new JLabel();
            quit.setIcon(new ImageIcon(getClass().getResource("quit.png")));
            quit.setBounds(540, 400, 150, 150);
            quit.setToolTipText("Quit");

            //adding mouse listeners
            categorical.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    presentationmenu.dispose();
                    id = 1;
                    new InputState("CATEGORICAL DATA");
                }
            });

            numerical.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    presentationmenu.dispose();
                    id = 2;
                    new InputState("NUMERICAL DATA");
                }
            });

            back.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    presentationmenu.dispose();
                    new Main();
                }
            });

            quit.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            //adding components
            presentationmenu.add(categorical);
            presentationmenu.add(numerical);
            presentationmenu.add(quit);
            presentationmenu.add(back);
            presentationmenu.add(background);
            presentationmenu.setVisible(true);
        }
    }

    class InputState {
        JButton enterButton;
        JTextField textField;
        TextArea titleTextArea, sampleTextArea;
        JLabel typeLabel, titleLabel, sizeLabel, sampleLabel, rem;
        Checkbox numBox, chrBox, strBox, intBox, floatBox;
        CheckboxGroup options;

        InputState(String title) {
            inputstate = new JFrame();
            inputstate.setTitle(title);
            inputstate.getContentPane().setBackground(backcolor);
            inputstate.setResizable(false);
            inputstate.setBounds(100, 100, 830, 545);
            inputstate.setLocationRelativeTo(null);
            inputstate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            inputstate.getContentPane().setLayout(null);

            typeLabel = new JLabel("TYPE OF DATA:");
            typeLabel.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            typeLabel.setBounds(61, 21, 128, 33);
            inputstate.getContentPane().add(typeLabel);

            enterButton = new JButton("VIEW TABLE");
            enterButton.setForeground(Color.BLACK);
            enterButton.setBackground(buttons);
            enterButton.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            enterButton.setBounds(352, 460, 200, 33);
            inputstate.getContentPane().add(enterButton);

            options = new CheckboxGroup();

            numBox = new Checkbox("NUMERIC LABEL", options, false);
            numBox.setFont(new Font("Dialog", Font.BOLD, 13));
            numBox.setBounds(277, 21, 128, 33);

            chrBox = new Checkbox("CHARACTER", options, false);
            chrBox.setFont(new Font("Dialog", Font.BOLD, 13));
            chrBox.setBounds(474, 21, 98, 33);

            strBox = new Checkbox("STRING", options, false);
            strBox.setFont(new Font("Dialog", Font.BOLD, 13));
            strBox.setBounds(670, 21, 95, 33);

            intBox = new Checkbox("INTEGER", options, false);
            intBox.setFont(new Font("Dialog", Font.BOLD, 13));
            intBox.setBounds(277, 21, 128, 33);

            floatBox = new Checkbox("FLOAT", options, false);
            floatBox.setFont(new Font("Dialog", Font.BOLD, 13));
            floatBox.setBounds(474, 21, 98, 33);

            if (id == 1) {
                inputstate.getContentPane().add(numBox);
                inputstate.getContentPane().add(chrBox);
                inputstate.getContentPane().add(strBox);
            } else if (id == 2) {
                inputstate.getContentPane().add(intBox);
                inputstate.getContentPane().add(floatBox);
            }

            titleLabel = new JLabel("\tTITLE:");
            titleLabel.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            titleLabel.setBounds(61, 121, 58, 24);
            inputstate.getContentPane().add(titleLabel);

            titleTextArea = new TextArea();
            titleTextArea.setBounds(277, 121, 495, 63);
            inputstate.getContentPane().add(titleTextArea);

            sizeLabel = new JLabel("POPULATION SIZE:");
            sizeLabel.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            sizeLabel.setBounds(61, 71, 156, 24);
            inputstate.getContentPane().add(sizeLabel);

            textField = new JTextField();
            textField.setBounds(277, 74, 495, 24);
            inputstate.getContentPane().add(textField);
            textField.setColumns(10);

            sampleLabel = new JLabel("SAMPLE FRAME:");
            sampleLabel.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            sampleLabel.setBounds(61, 212, 137, 24);
            inputstate.getContentPane().add(sampleLabel);

            sampleTextArea = new TextArea();
            sampleTextArea .setBounds(277, 252, 495, 189);
            inputstate.getContentPane().add(sampleTextArea);

            rem = new JLabel("PRESS SPACE OR ENTER KEY TO SEPARATE ELEMENTS");
            rem.setBounds(277, 216, 444, 22);
            inputstate.getContentPane().add(rem);

            inputstate.setVisible(true);
            inputstate.setResizable(false);

            enterButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent arg0) {
                    Checkbox b = options.getSelectedCheckbox();
                    String forTitle = titleTextArea.getText();
                    String forSample = sampleTextArea.getText();
                    String forSize = textField.getText();

                    ErrorCatch err = new ErrorCatch(b, forSize, forTitle, forSample);
                    boolean errorType = err.typeError;
                    boolean errorSize = err.sizeError;
                    boolean errorTitle = err.titleError;
                    boolean errorSample = err.sampleError;

                    if (!errorType && !errorSize && !errorTitle && !errorSample) {
                        if (id == 1) {
                            if (subtype.equals(numLabel)) {
                                new TableManipulation(numlabel, "CATEGORICAL DATA");
                            } else if (subtype.equals(charLabel)) {
                                new TableManipulation(charal, "CATEGORICAL DATA");
                            } else if (subtype.equals(strLabel)) {
                                new TableManipulation(strcharal, "CATEGORICAL DATA");
                            }
                        } else if (id == 2) {
                            if (subtype.equals(intLabel)) {
                                new TableManipulation(intal, "NUMERICAL DATA");
                            } else if (subtype.equals(floatLabel)) {
                                new TableManipulation(floatal, "NUMERICAL DATA");
                            }
                        }
                    }
                }
            });
        }

        class ErrorCatch {
            boolean typeError = false, sizeError = false, titleError = false, sampleError = false;

            ErrorCatch(Checkbox box, String tfStr, String taTitleStr, String taSampleStr) {
                typeErrorCatch(box);
                sizeErrorCatch(tfStr);
                titleErrorCatch(taTitleStr);
                sampleErrorCatch(taSampleStr);
            }

            void typeErrorCatch(Checkbox check) {
                Checkbox cb = check;
                try {
                    subtype = cb.getLabel();
                } catch(NullPointerException e) {
                    typeError = true;
                    JOptionPane.showMessageDialog(inputstate,
                            "Error: You must choose a type of data.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            void sizeErrorCatch(String str) {
                String ps = str;
                try {
                    if(ps.equals("")){
                        sizeError = true;
                        JOptionPane.showMessageDialog(inputstate,
                                "Error: You must enter a population size. Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    popsize = Integer.parseInt(ps);
                    if (popsize < 2) {
                        sizeError = true;
                        JOptionPane.showMessageDialog(inputstate,
                                "Error: Your population size must be at least 2. Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    sizeError = true;
                    JOptionPane.showMessageDialog(inputstate,
                            "Error: Your population size must be an integer. Try again.",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            void titleErrorCatch(String s) {
                String ps = s;
                if (s.equals("")) {
                    titleError = true;
                    JOptionPane.showMessageDialog(inputstate,
                            "Error: You must enter a data description. Try again.",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    titleError = false;
                    datadesc = ps;
                }

            }

            void sampleErrorCatch(String st) {
                String s = st;
                String[] toks = s.split("\\s+|\\n");
                ArrayList<String> al = new ArrayList<>(Arrays.asList(toks));
                Parser pa = new Parser();

                if (id == 1) {
                    if (subtype.equals(numLabel)) {
                        numlabel = pa.numericSampleParser(al, numLabel);
                        if (!pa.lenError && !pa.formatError) {
                            sampleError = false;
                        } else {
                            sampleError = true;
                        }
                    } else if (subtype.equals(charLabel)) {
                        charal = pa.charSampleParser(al);
                        if (!pa.lenError && !pa.formatError) {
                            sampleError = false;
                        } else {
                            sampleError = true;
                        }
                    } else if (subtype.equals(strLabel)) {
                        strcharal = pa.strCharSampleParser(al);
                        if (!pa.lenError && !pa.formatError) {
                            sampleError = false;
                        } else {
                            sampleError = true;
                        }
                    }
                } else if (id == 2) {
                    if (subtype.equals(intLabel)) {
                        intal = pa.numericSampleParser(al, intLabel);
                        if (!pa.lenError && !pa.formatError) {
                            sampleError = false;
                        } else {
                            sampleError = true;
                        }
                    } else if (subtype.equals(floatLabel)) {
                        floatal = pa.numericSampleParser(al, floatLabel);
                        if (!pa.lenError && !pa.formatError) {
                            sampleError = false;
                        } else {
                            sampleError = true;
                        }
                    }
                }
            }
        }

        class Parser {
            boolean lenError = false;
            boolean formatError = false;

            boolean dispError(int len){
                if (len < 0 || len < popsize){
                    JOptionPane.showMessageDialog(inputstate,
                            "Your input does not meet required size: " + popsize,
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                    return true;
                } else if (len > popsize){
                    JOptionPane.showMessageDialog(inputstate,
                            "Your input exceeds required size: " + popsize,
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                    return true;
                }
                return false;
            }

            //use to parse samples of subtype "int", "float" or "numlabel"
            ArrayList numericSampleParser(ArrayList<String> al, String subtype){
                ArrayList<Double> floatal = new ArrayList<>();
                ArrayList<Integer> intal = new ArrayList<>();
                ArrayList<String> numlabelal = new ArrayList<>();

                //assign type for easier checking later on
                int type = 0;
                if(subtype.equals(floatLabel)) {//float
                    type = 1;
                }else if(subtype.equals(intLabel)) {//int
                    type = 2;
                }else if(subtype.equals(numLabel)){//numlabel
                    type = 3;
                }

                String token1 = al.get(0);
                if(token1.equals("")){
                    al.remove(token1);
                }

                boolean typeerr = false;
                for (String st : al) {
                    try {
                        if (type == 1) {
                            double f = Double.parseDouble(st);
                            if (st.contains(".")) {
                                floatal.add(f);
                            } else {
                                formatError = true;
                                JOptionPane.showMessageDialog(inputstate,
                                        "Error: Input must be a floating point number." + " Try again.",
                                        "Invalid input",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        } else {
                            int n = Integer.parseInt(st);
                            if (type == 2) {
                                intal.add(n);
                            } else if (type == 3) {
                                numlabelal.add(String.valueOf(n));
                            }
                        }
                        formatError = false;
                    } catch (Exception e) {
                        formatError = true;
                        typeerr = true;
                        String errorMsg;
                        if (type == 1) {
                            floatal.clear();
                            errorMsg = "Error: Input must be a floating point number.";
                        } else {
                            if (type == 2) {
                                intal.clear();
                            } else if (type == 3) {
                                numlabelal.clear();
                            }
                            errorMsg = "Error: Input must be an integer.";
                        }
                        JOptionPane.showMessageDialog(inputstate,
                                errorMsg + " Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                boolean sizeerror = dispError(al.size());
                lenError = sizeerror;
                if (!sizeerror && ! typeerr) {
                    lenError = false;
                    if(type == 1){
                        return floatal;
                    }else{
                        if (type == 2) {
                            return intal;
                        } else if (type == 3) {
                            return numlabelal;
                        }
                    }
                }
                return new ArrayList(); //empty ArrayList
            }

            //use to parse samples of subtype "char"
            ArrayList charSampleParser(ArrayList<String> al){
                ArrayList<Character> charal = new ArrayList<>();

                //checks if first token is not equal to NULL
                String token1 = al.get(0);
                if(token1.equals("")){
                    al.remove(token1);
                }

                boolean typeerr = false;
                for (String st : al) {
                    //Pattern letter = Pattern.compile("[a-zA-z]");
                    //Matcher hasLetter = letter.matcher(st);
                    int len = st.length();
                    if (len > 1) {
                        formatError = true;
                        typeerr = true;
                    } else {
                        String stCopy = st.toUpperCase();
                        char ch = stCopy.charAt(0);
                        if( len == 1 && (ch < 'A' || ch > 'Z'  ) ) {
                            //if( len==1 && !hasLetter.find())
                            formatError = true;
                            typeerr = true;
                        } else {
                            charal.add(st.charAt(0));
                        }
                    }

                    //input is not a character
                    if(formatError){
                        charal.clear();
                        JOptionPane.showMessageDialog(inputstate,
                                "Input must be A-Z only! Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                boolean sizeerror = dispError(al.size());
                lenError = sizeerror;
                if(!sizeerror && !typeerr){
                    lenError = false;
                    return charal;
                }

                return new ArrayList(); //empty ArrayList
            }

            //use to parse samples of subtype "strChar"
            ArrayList strCharSampleParser(ArrayList<String> al){
                ArrayList<String> strCharal = new ArrayList<>();

                //checks if first token is not equal to NULL
                String token1 = al.get(0);
                if(token1.equals("")){
                    al.remove(token1);
                }

                boolean typeerr = false;
                Pattern digit = Pattern.compile("[0-9]");
                Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

                String errorMsg = "";
                for (String st : al) {
                    Matcher hasDigit = digit.matcher(st);
                    Matcher hasSpecialChar = special.matcher(st);
                    if (hasDigit.find()) {
                        formatError = true;
                        typeerr = true;
                        errorMsg = "Input must not contain numbers!";
                    } else
                    if ( hasSpecialChar.find() ) {
                        formatError = true;
                        typeerr = true;
                        errorMsg = "Input must not contain special characters!";
                    } else {
                        strCharal.add(st);
                    }

                    //input is not valid
                    if(typeerr){
                        strCharal.clear();
                        JOptionPane.showMessageDialog(inputstate,
                                errorMsg + " Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                boolean sizeerror = dispError(al.size());
                lenError = sizeerror;
                if(!sizeerror && !typeerr){
                    lenError = false;
                    return strCharal;
                }

                return new ArrayList(); //empty ArrayList
            }
        }

        class TableManipulation {
            int opt = 0;
            JLabel titleDisplay;
            JTable table;
            JButton viewGraphButton, backMenuButton;
            DefaultTableModel model;
            Font headerFont, rowFont;
            JTextField totalTxtField;
            JScrollPane pane;
            int popsize = 0;
            float toDisplayInPercent;

            ////////// FOR NUMERICAL DATA TYPE ONLY ///////////
            double doubleMinNum, doubleMaxNum, doubleRange;
            int intMinNum, intMaxNum, intRange;
            int k;
            ArrayList<NumericalRowUnit> rowUnitsAl = new ArrayList<>();
            ArrayList al = new ArrayList();
            JTextField populationTxtField;
            int numericSubtype; //1=int, 2=double
            int displayMode; //1=normal; collapse: 2=lowerCL, 3=upperCL, 4=both CL

            TableManipulation(ArrayList given, String st) {
                al = given;
                // create JFrame and JTable
                popsize = al.size();
                tablestate = new JFrame(st);
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

                titleDisplay = new JLabel(datadesc + "    (N = " + popsize + " )");
                titleDisplay.setFont(new Font("Arial Narrow", Font.BOLD, 20));
                if (id == 1) {
                    titleDisplay.setBounds(110, 0, 710, 50);
                } else if (id == 2) {
                    titleDisplay.setBounds(100, 0, 1100, 50);
                }
                tablestate.getContentPane().add(titleDisplay);

                // create a table model and set a Column Identifiers to this model
                model = new DefaultTableModel();
                if (id == 1) {
                    Object[] columns = {"Value Labels","Percentage (%) "};
                    model.setColumnIdentifiers(columns);
                    table.setModel(model);
                } else if (id == 2) {
                    Object[] columns = {"Class Limit", "True CL", "Midpoint", "Frequency", "Percentage (%)", "CF", "C%"};
                    model.setColumnIdentifiers(columns);
                    table.setModel(model);
                    table.getColumnModel().getColumn(4).setPreferredWidth(150); //resize column Percentage (%)

                    //For asking displayMode from user
                    String txt = "What mode do you want to display the FDT?\n";
                    txt += "Enter 1-4 (*1=normal, *collapse: 2=lowerCL, \n3=upperCL, 4=both).";
                    int n;
                    boolean inv = false, error;
                    //do while displayMode is invalid
                    do {
                        String in = JOptionPane.showInputDialog(inputstate, txt);
                        if (in != null) {
                            try {
                                n = Integer.parseInt(in);
                                opt = n;
                                if(n < 0 || n > 4){
                                    inv = true;
                                }else{
                                    inv = false;
                                }

                                if(!inv){
                                    displayMode = n;
                                    error = false;
                                }else{
                                    error = true;
                                }
                            } catch (Exception e) {
                                error = true;
                            }

                            if (inv || error) {
                                JOptionPane.showMessageDialog(inputstate,
                                        "Error: Must be 1-4 only. Try again.",
                                        "Invalid input",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            error = true;
                            JOptionPane.showMessageDialog(inputstate,
                                    "Error: Must enter a number. Try again.",
                                    "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } while (error == true);
                }

                inputstate.dispose();
                // Change A JTable Background Color, Font Size, Font Color, Row Height
                table.setBackground(Color.LIGHT_GRAY);
                table.setForeground(Color.black);
                headerFont = new Font("Verdana", Font.BOLD, 18);
                table.getTableHeader().setFont(headerFont);
                rowFont = new Font("Verdana",Font.PLAIN,14);
                table.setFont(rowFont);
                table.setRowHeight(30);
                table.setDefaultEditor(Object.class, null); //to disable cell editing

                //next button
                viewGraphButton = new JButton("VIEW GRAPH");
                viewGraphButton.setForeground(Color.BLACK);
                viewGraphButton.setBackground(buttons);//Color.getHSBColor(8, 9, 2)
                viewGraphButton.setFont(new Font("Arial Narrow", Font.BOLD, 18));
                //viewGraphButton.setBounds(80, 390, 50, 20);
                tablestate.getContentPane().add(viewGraphButton);

                backMenuButton = new JButton("BACK TO MENU");
                backMenuButton.setForeground(Color.BLACK);
                backMenuButton.setBackground(buttons);//Color.getHSBColor(8, 9, 2)
                backMenuButton.setFont(new Font("Arial Narrow", Font.BOLD, 18));
                //backMenuButton.setBounds(140, 390, 50, 20);
                tablestate.getContentPane().add(backMenuButton);

                backMenuButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        tablestate.dispose();
                        new Menu();
                    }

                });

                // create JTextFields
                totalTxtField = new JTextField();
                totalTxtField.setEditable(false);
                totalTxtField.setFont(headerFont);


                //set bounds, create JScrollPane, and process data
                pane = new JScrollPane(table);
                Collections.sort(al);
                if (id == 1) {
                    totalTxtField.setBounds(520, 417, 190, 30);
                    tablestate.setBounds(100, 100, 830, 545);
                    pane.setBounds(110, 50, 600, 350);//370, 120, 600, 400
                    viewGraphButton.setBounds(220, 460, 200, 30);
                    backMenuButton.setBounds(430, 460, 200, 30);
                    summaryTable( groupElements(al) );
                    totalTxtField.setText(String.format("%s%.2f%s", "Total: ", toDisplayInPercent, "%") );
                    toDisplayInPercent = 0;
                } else if (id == 2) {
                    if (subtype.equals(intLabel)) {
                        numericSubtype = 1;
                    } else if (subtype.equals(floatLabel)) {
                        numericSubtype = 2;
                    }
                    populationTxtField = new JTextField();
                    populationTxtField.setBounds(870, 500, 90, 30);
                    populationTxtField.setEditable(false);
                    populationTxtField.setFont(headerFont);
                    tablestate.add(populationTxtField);
                    totalTxtField.setBounds(970, 500, 190, 30);
                    tablestate.setSize(1200, 800);
                    pane.setBounds(100, 50, 1000, 400);//370, 120, 600, 400
                    viewGraphButton.setBounds(360, 550, 200, 30);
                    backMenuButton.setBounds(570, 550, 200, 30);
                    rowUnitsAl = prepareRowUnits(al);
                    tallyFrequencies( groupElements(al), rowUnitsAl, numericSubtype); //type is either 1=int, 2=double
                    displayFreqDistTable(rowUnitsAl, numericSubtype, displayMode);
                    NumericalRowUnit last = rowUnitsAl.get(rowUnitsAl.size()-1);
                    totalTxtField.setText( String.format("%s%.2f%s", "Total: ", last.getPercentCumulative() , "%") );
                    populationTxtField.setText("N = " + last.getCumulativeFreq());
                }

                tablestate.setLayout(null);
                tablestate.add(pane);

                //to center elements in table
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                int cols = table.getColumnCount();
                for(int i = 0;i < cols ;i++){
                    table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
                }

                tablestate.add(totalTxtField);
                tablestate.setLocationRelativeTo(null);
                tablestate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tablestate.setVisible(true);
                tablestate.getContentPane().setBackground(backcolor);
                tablestate.setResizable(false);
                tablestate.getContentPane().setLayout(null);

                viewGraphButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        int answer = 0;
                        String msg1 = "Do you want to view the pie chart?";
                        String msg2 = "Do you want to view the histogram?";
                        String msg3 = "Histogram can't be generated.";
                        if(id==1){
                            answer = JOptionPane.showConfirmDialog(tablestate, msg1);
                        }else if (id == 2 && opt == 1){
                            answer = JOptionPane.showConfirmDialog(tablestate, msg2);
                        }
                        if (answer == JOptionPane.YES_OPTION) {
                            if (id == 1) {
                                //display categorical summary table
                                new PieChart("CATEGORICAL DATA", datadesc, al);
                            }else if(id == 2){
                                if (opt == 1) {
                                    new Histogram("NUMERICAL DATA", datadesc);
                                } else {
                                    JOptionPane.showMessageDialog(tablestate, msg3);
                                }
                            }
                        } else if (answer == JOptionPane.NO_OPTION) {
                            // returns to menu
                            tablestate.dispose();
                            new Menu();
                        }
                    }

                });
            }

            ArrayList<ArrayList> groupElements(ArrayList al) {
                //Collections.sort(al);
                ArrayList< ArrayList > alal = new ArrayList<>();
                int len = al.size();

                // Initialize all elements as initially unvisited
                boolean[] visited = new boolean[len];

                // Traverse all elements
                for (int i = 0; i < len; i++) {
                    Boolean el = visited[i];
                    if ( !el ) {
                        Object obj1 = al.get(i);

                        alal.add( new ArrayList() );
                        ArrayList curr = alal.get(alal.size() - 1);
                        curr.add( obj1 );

                        for (int j = i+1; j < len; j++) {
                            Object obj2 = al.get(j);
                            if (obj2.equals(obj1)) {
                                visited[j] = true;
                                curr.add( obj2 );
                            }
                        }
                    }
                }

                return alal;
            }

            ////////////// METHODS FOR CATEGORICAL DATA ////////////////
            void summaryTable(ArrayList<ArrayList> alal) {
                // create an array of objects to set the row data
                Object[] row = new Object[2];
                int len = alal.size();
                float curr;
                for (int i = 0; i < len; i++) {
                    ArrayList al = alal.get(i);
                    Object obj = al.get(0);
                    int size = al.size(); //size of inner ArrayList
                    row[0] = obj;
                    curr = size/(float)popsize * 100;
                    row[1] = String.format("%.2f", curr);
                    toDisplayInPercent += curr;
                    model.addRow(row);
                }
            }

            class PieChart extends JFrame {

                PieChart(String applicationTitle, String chartTitle, ArrayList al) {
                    super(applicationTitle);
                    getContentPane().setBackground(backcolor);
                    setResizable(false);
                    setBounds(100, 100, 500, 500); //433, 606//1366, 768
                    setLocationRelativeTo(null);
                    getContentPane().setLayout(null);

                    PieDataset dataset = createDataset(groupElements(al));
                    JFreeChart chart = createChart(dataset, chartTitle + "  N = " + popsize);
                    ChartPanel chartPanel = new ChartPanel(chart);
                    chartPanel.setPreferredSize(new Dimension(500, 270));
                    setContentPane(chartPanel);
                    setVisible(true);
                }

                private  PieDataset createDataset(ArrayList<ArrayList> alal) {
                    DefaultPieDataset result = new DefaultPieDataset();
                    int len = alal.size();
                    for (int i = 0; i < len; i++) {
                        ArrayList al = alal.get(i);
                        Object obj = al.get(0);
                        int size = al.size();
                        double value = size/(float)popsize * 100;
                        String s = String.format("%.2f", size/(float)popsize * 100);
                        result.setValue(obj + " = " + s, value);
                    }
                    return result;

                }

                private JFreeChart createChart(PieDataset dataset, String title) {

                    JFreeChart chart = ChartFactory.createPieChart(
                            title,      // chart title
                            dataset,    // data
                            false,      // include legend
                            true,
                            false
                    );

                    PiePlot plot = (PiePlot) chart.getPlot();
                    plot.setStartAngle(290);
                    plot.setDirection(Rotation.CLOCKWISE);
                    plot.setForegroundAlpha(0.5f);
                    return chart;

                }
            }
            //////////// END OF METHODS FOR CATEGORICAL DATA /////////////

            ////////////// METHODS FOR NUMERICAL DATA ///////////////////
            //numericSubtype: 1=int, 2=double; this also sets minNum, maxNum, and range
            ArrayList prepareRowUnits(ArrayList al){
                ArrayList rowUnitsAl = new ArrayList<NumericalRowUnit>();
                NumericalRowUnit prev = new NumericalRowUnit();
                NumericalRowUnit currRU;
                int len = al.size();

                if (numericSubtype==1){
                    intMinNum = (Integer)al.get(0);
                    intMaxNum = (Integer)al.get(len-1);
                    intRange = intMaxNum - intMinNum;
                }else if(numericSubtype==2){
                    doubleMinNum = (Double)al.get(0);
                    doubleMaxNum = (Double)al.get(len-1);
                    doubleRange = doubleMaxNum - doubleMinNum;
                }else{
                    System.out.println("Wrong data type! 1 or 2 only");
                }

                k = (int) Math.ceil(1 + 3.322*(Math.log10(popsize)));
                System.out.println("k:" + String.valueOf(k));
                System.out.println("popsize: " + String.valueOf(popsize));
                if (numericSubtype == 1) {
                    if(intRange==0){
                        currRU = new NumericalRowUnit(intMinNum, intMaxNum, prev, popsize);
                        rowUnitsAl.add( currRU );
                    }else{
                        int classWidth = (int) Math.ceil(intRange/(float)k);
                        System.out.println("cw: " + classWidth);
                        int curr = intMinNum;
                        int end = intMaxNum;
                        int ub = 0; //for assigning upperBound value later on
                        //int ctr = 1;
                        //ctr <= k &&
                        while(ub <= end){
                            ub = curr+classWidth-1;
                            currRU = new NumericalRowUnit(curr, ub, prev, popsize);
                            rowUnitsAl.add(currRU);
                            prev = currRU;
                            curr += classWidth;
                            //ctr++;
                        }
                    }

                }else if (numericSubtype == 2) {
                    if(doubleRange==0) {
                        currRU = new NumericalRowUnit(doubleMinNum, doubleMaxNum, prev, popsize);
                        rowUnitsAl.add( currRU );
                    }else{
                        //double classWidth = Math.ceil(doubleRange/k);
                        Double cw = (doubleRange / k);
                        Double classWidth = new BigDecimal(cw.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                        System.out.println("cw: " + classWidth);
                        double curr = doubleMinNum;
                        double end = doubleMaxNum;
                        System.out.println("end: " + end);
                        double ub = 0; //for assigning upperBound value later on
                        //int ctr = 1;
                        //curr <= end &&
                        while(Double.compare(ub, end) <= 0){
                            ub = curr + classWidth;
                            currRU = new NumericalRowUnit(curr, ub, prev, popsize);
                            rowUnitsAl.add(currRU);
                            prev = currRU;
                            curr += classWidth;
                        }
                    }
                }
                return rowUnitsAl;
            }

            //type: 1=int numericSubtype, 2=double numericSubtype
            void tallyFrequencies(ArrayList<ArrayList> alal, ArrayList<NumericalRowUnit> numericClasses, int type){
                int len = alal.size();
                Integer a = null;
                Double b = null;

                for(int i = 0; i < len; i++){
                    ArrayList al = alal.get(i);
                    if(type==1){
                        a = (Integer)al.get(0);
                    }else{
                        b = (Double)al.get(0);
                    }

                    int size = numericClasses.size(); //size of numericClasses
                    for(int j = 0; j < size; j++){
                        NumericalRowUnit rowUnit = numericClasses.get(j);
                        if(type==1){
                            if( a >= rowUnit.getIntLowerCL() && a <= rowUnit.getIntUpperCL() ){
                                rowUnit.increaseFrequency(al.size());
                                break;
                            }
                        }else if(type==2){
                            if( Double.compare(b, rowUnit.getTrueLowerCL()) >=0 && Double.compare(b, rowUnit.getTrueUpperCL()) <= 0){
                                rowUnit.increaseFrequency(al.size());
                                break;
                            }
                        }
                    }
                }
            }

            void displayFreqDistTable(ArrayList<NumericalRowUnit> numericClasses, int type, int displayMode){
                // create an array of objects to set the row data
                Object[] row = new Object[7];
                //// ADD ROW UNITS TO TABLE ////
                int rows = numericClasses.size();
                for(int i = 0; i < rows; i++){
                    NumericalRowUnit obj = numericClasses.get(i);
                    if(type==1){
                        if(displayMode==1){
                            row[0] = String.format("%d-%d", obj.getIntLowerCL(), obj.getIntUpperCL());
                        }else if(displayMode==2){
                            row[0] = String.format("≤ %d",obj.getIntUpperCL());
                        }else if(displayMode==3){
                            row[0] = String.format("%d ≥", obj.getIntLowerCL());
                        }else{
                            //displayMode==4
                            row[0] = String.format("≤ %d and ≥ %d", obj.getIntLowerCL(), obj.getIntUpperCL());
                        }
                    }else{
                        //if type==2
                        if(displayMode==1){
                            row[0] = String.format("%.2f - %.2f", obj.getFloatLowerCL(), obj.getFloatUpperCL());
                        }else if(displayMode==2){
                            row[0] = String.format("≤ %.2f", obj.getFloatUpperCL());
                        }else if(displayMode==3){
                            row[0] = String.format("%.2f ≥", obj.getFloatLowerCL());
                        }else{
                            //displayMode==4
                            row[0] = String.format("≤ %.2f and ≥ %.2f", obj.getFloatLowerCL(), obj.getFloatUpperCL());
                        }
                    }

                    if(displayMode==1){
                        row[1] = String.format("%.2f - %.2f", obj.getTrueLowerCL(), obj.getTrueUpperCL());
                        row[2] = String.format("%.2f", obj.getMidpoint());
                    }else {
                        //if user collapses (chooses 2-3 displayMode)
                        row[1] = String.format(" - ");
                        row[2] = String.format(" - ");
                    }

                    row[3] = obj.getFrequency();
                    row[4] = String.format("%.2f", obj.getPercentage());
                    row[5] = obj.getCumulativeFreq();
                    row[6] = String.format("%.2f", obj.getPercentCumulative());
                    model.addRow(row);
                }
            }

            class Histogram extends JFrame {
                Histogram(String applicationTitle, String chartTitle) {
                    super(applicationTitle);
                    // based on the dataset we create the chart
                    JFreeChart barchart = ChartFactory.createBarChart(chartTitle, "Midpoint", "Frequency", createDataset(), PlotOrientation.VERTICAL, false, true, false);

                    CategoryPlot plot = barchart.getCategoryPlot();
                    plot.getDomainAxis().setCategoryMargin(0.0);
                    BarRenderer br = (BarRenderer) plot.getRenderer();
                    br.setMaximumBarWidth(.35);

                    ChartPanel panel = new ChartPanel(barchart);
                    getContentPane().setLayout(new BorderLayout());
                    getContentPane().add(panel);
                    pack();
                    setVisible(true);
                }

                private CategoryDataset createDataset() {
                    // create the dataset...
                    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    int ruaLen = rowUnitsAl.size();
                    for(int i = 0; i < ruaLen; i++){
                        NumericalRowUnit a = rowUnitsAl.get(i);
                        dataset.addValue(a.getFrequency(), "Midpoint", String.format("%.2f", a.getMidpoint()));
                    }

                    return dataset;

                }
            }
            //////////// END OF METHODS FOR NUMERICAL DATA ////////////
        }
    }

    class NumericalRowUnit {
        int popsize; //basis for calculating percentage
        int type; //1=int num data, 2=float num data

        //CL means class limit
        int intLowerCL, intUpperCL; //for int numerical data
        double floatLowerCL, floatUpperCL; //for float numerical data
        double trueLowerCL, trueUpperCL, midpoint;
        int frequency, cumulativeFreq;
        double percentage;
        double percentCumulative;
        NumericalRowUnit parent; //basis for cumulativeFreq or Percentage

        /////////// SETTTERS /////////////////
        void setType(int type) {
            this.type = type;
        }

        void setIntLowerCL(int intLowerCL) {
            this.intLowerCL = intLowerCL;
        }

        void setIntUpperCL(int intUpperCL) {
            this.intUpperCL = intUpperCL;
        }

        void setFloatLowerCL(double floatLowerCL) {
            this.floatLowerCL = floatLowerCL;
        }

        void setFloatUpperCL(double floatUpperCL) {
            this.floatUpperCL = floatUpperCL;
        }

        void setPopSize(int popSize) {
            this.popsize = popSize;
        }

        void setParent(NumericalRowUnit parent) {
            this.parent = parent;
        }

        NumericalRowUnit() {
            setParent(null);
        }


        NumericalRowUnit(int lowerBound, int upperBound, NumericalRowUnit parent, int popsize) {
            setType(1);
            setIntLowerCL(lowerBound);
            setIntUpperCL(upperBound);
            setParent(parent);
            setPopSize(popsize);
        }

        NumericalRowUnit(double lowerBound, double upperBound, NumericalRowUnit parent, int popsize) {
            setType(2);
            setFloatLowerCL(lowerBound);
            setFloatUpperCL(upperBound);
            setParent(parent);
            setPopSize(popsize);
        }
        /////// END OF SETTERS ///////////////

        /////////// GETTERS /////////////////
        int getIntLowerCL() {
            return intLowerCL;
        }

        double getFloatLowerCL() {
            return floatLowerCL;
        }

        int getIntUpperCL() {
            return intUpperCL;
        }

        double getFloatUpperCL() {
            return floatUpperCL;
        }

        double getTrueLowerCL() {
            if(type==1){
                trueLowerCL = intLowerCL - 0.5;
            }else if(type==2){
                trueLowerCL = floatLowerCL - 0.005; //floatLowerCL - (5 * zeroPointOnePlaces(floatLowerCL));
                System.out.println("TLCL" + String.valueOf(trueLowerCL));
                //trueLowerCL = floatLowerCL - 0.005;
            }

            return trueLowerCL;
        }

        private double zeroPointOnePlaces(Double CL) {
            //Counts number of decimal places
            System.out.println("cl: "+CL.toString());
            String[] splitter = CL.toString().split("\\.");
            int decPlaces = splitter[1].length();
            System.out.println("decplaces: " + decPlaces);

            double res = 0.1;
            for(int i = 1; i < decPlaces; i++) {
                res *= 0.1;
            }
            return res;
        }

        double getTrueUpperCL() {
            if (type == 1) {
                trueUpperCL = intUpperCL + 0.5;
            } else if (type == 2) {
                trueUpperCL = floatUpperCL + 0.005; //floatUpperCL + (5 * zeroPointOnePlaces(floatUpperCL));
                System.out.println("TUCL" + String.valueOf(trueUpperCL));
            }
            return trueUpperCL;
        }

        double getMidpoint() {
            midpoint = (trueLowerCL + trueUpperCL) / 2;
            return midpoint;
        }

        int getFrequency() {
            return frequency;
        }

        void increaseFrequency(int addSize) {
            frequency = frequency + addSize;
        }

        int getCumulativeFreq() {
            int toAdd = 0;
            if(parent!=null){
                toAdd = this.getParent().getCumulativeFreq();
            }
            cumulativeFreq = toAdd + this.getFrequency();
            return cumulativeFreq;
        }

        double getPercentage() {
            if (getFrequency()==0) {
                return 0;
            } else {
                percentage = (getFrequency() / (float) popsize) * 100;
            }
            return percentage;
        }

        double getPercentCumulative() {
            double toAdd = 0;
            if (parent!=null) {
                toAdd = this.getParent().getPercentCumulative();
            }
            percentCumulative = toAdd + this.getPercentage();
            return percentCumulative;
        }

        NumericalRowUnit getParent() {
            return parent;
        }
        /////// END OF GETTERS ///////////////
    }
}
