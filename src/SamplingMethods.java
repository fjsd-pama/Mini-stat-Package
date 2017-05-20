import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class SamplingMethods {
    private JFrame samplingmenu;

    //main variables
    private int popsize = 0, sampsize = 0;
    private String method, sampframe, datatype, dispresult = "";
    private ArrayList<Integer> intal = new ArrayList<>();
    private ArrayList<Character> charal = new ArrayList<>();

    //population size components
    private JFrame pop;
    private JTextField tf;

    //type components
    private JFrame dataType;
    private CheckboxGroup choices;

    //sample frame components
    private JFrame frame;
    private TextArea ta;

    //display frame components
    private JFrame display;
    private ArrayList<ArrayList> grp;

    //sample size components
    private JFrame size;
    private JTextField tf2;

    //result components
    private JFrame res;

    SamplingMethods(){
        new Menu();
    }

    class Menu{
        Menu(){
            samplingmenu = new JFrame("Sampling Methods");
            samplingmenu.setResizable(false);
            samplingmenu.setBounds(0, 0, 800, 600);
            samplingmenu.setLocationRelativeTo(null);
            samplingmenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            samplingmenu.getContentPane().setLayout(null);

            JLabel background = new JLabel();
            background.setIcon(new ImageIcon(getClass().getResource("sampling menu.png")));
            background.setBounds(0, 0, 800, 600);

            JLabel randBtn = new JLabel();
            randBtn.setIcon(new ImageIcon(getClass().getResource("simple random.png")));
            randBtn.setBounds(195, 200, 200, 170);
            randBtn.setToolTipText("Picks sample randomly given a sample size");

            JLabel systBtn = new JLabel();
            systBtn.setIcon(new ImageIcon(getClass().getResource("systematic sampling.png")));
            systBtn.setBounds(430, 200, 200, 170);
            systBtn.setToolTipText("Picks sample by calculating kth position");

            JLabel stratBtn = new JLabel();
            stratBtn.setIcon(new ImageIcon(getClass().getResource("stratified sampling.png")));
            stratBtn.setBounds(195, 390, 200, 170);
            stratBtn.setToolTipText("Groups sample into strata and picks sample in each");

            JLabel back = new JLabel();
            back.setIcon(new ImageIcon(getClass().getResource("back.png")));
            back.setBounds(410, 400, 150, 150);
            back.setToolTipText("Back");

            JLabel quit = new JLabel();
            quit.setIcon(new ImageIcon(getClass().getResource("quit.png")));
            quit.setBounds(540, 400, 150, 150);
            quit.setToolTipText("Quit");

            //adding mouse listeners
            randBtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    samplingmenu.dispose();
                    method = "Random";
                    new Pop("SIMPLE RANDOM SAMPLING");
                }
            });

            systBtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    samplingmenu.dispose();
                    method = "Systematic";
                    new Pop("SYSTEMATIC SAMPLING");
                }
            });

            stratBtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    samplingmenu.dispose();
                    method = "Stratification";
                    new Pop("STRATIFICATION SAMPLING");
                }
            });

            back.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    samplingmenu.dispose();
                    new Main();
                }
            });

            quit.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            //adding components
            samplingmenu.add(randBtn);
            samplingmenu.add(systBtn);
            samplingmenu.add(stratBtn);
            samplingmenu.add(quit);
            samplingmenu.add(back);
            samplingmenu.add(background);
            samplingmenu.setVisible(true);
        }
    }

    class Pop {

        Pop(String title) {
            samplingmenu.dispose();
            pop = new JFrame();
            pop.getContentPane().setBackground(Color.CYAN);
            pop.setTitle(title);
            pop.setResizable(false);
            pop.setBounds(100, 100, 433, 606);
            pop.setVisible(true);
            pop.setLocationRelativeTo(null);
            pop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pop.getContentPane().setLayout(null);

            JLabel lbl = new JLabel("ENTER POPULATION SIZE:");
            lbl.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            lbl.setBounds(38, 21, 246, 33);
            pop.getContentPane().add(lbl);

            tf = new JTextField();
            tf.setBounds(46, 177, 328, 33);
            pop.getContentPane().add(tf);
            tf.setColumns(10);

            JButton enter = new JButton("ENTER");
            enter.setForeground(Color.BLACK);
            enter.setBackground(Color.getHSBColor(8, 9, 2));
            enter.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
            enter.setBounds(273, 442, 117, 33);
            pop.getContentPane().add(enter);


            enter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String ps = tf.getText();
                    try {
                        //when nothing is entered
                        if(ps.equals("")){
                            JOptionPane.showMessageDialog(pop,
                                    "Error: Must enter a number. Try again.",
                                    "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        //when something is entered, must check what that is
                        popsize = Integer.parseInt(ps);
                        if (popsize < 2) {
                            JOptionPane.showMessageDialog(pop,
                                    "Population size must be at least 2! Try again.",
                                    "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Type(title);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(pop,
                                "Error: Invalid format. Try again.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        }
    }

    class Type {
        Type(String title) {
            pop.dispose();
            dataType = new JFrame();
            dataType.getContentPane().setBackground(Color.CYAN);
            dataType.setResizable(false);
            dataType.setVisible(true);
            dataType.setBounds(100, 100, 433, 606);
            dataType.setLocationRelativeTo(null);
            dataType.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            dataType.getContentPane().setLayout(null);

            JLabel lbl0 = new JLabel("TYPE OF DATA:");
            lbl0.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            lbl0.setBounds(38, 21, 246, 33);
            dataType.getContentPane().add(lbl0);

            JButton enter0 = new JButton("ENTER");
            enter0.setForeground(Color.BLACK);
            enter0.setBackground(Color.getHSBColor(8, 9, 2));
            enter0.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            enter0.setBounds(273, 442, 117, 33);
            dataType.getContentPane().add(enter0);

            choices = new CheckboxGroup();

            Checkbox num = new Checkbox("NUMERIC", choices, false);
            num.setBounds(156, 94, 128, 33);
            dataType.getContentPane().add(num);

            Checkbox chr = new Checkbox("CHARACTER", choices, false);
            chr.setBounds(156, 169, 95, 33);
            dataType.getContentPane().add(chr);

            enter0.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    Checkbox cb = choices.getSelectedCheckbox();
                    try {
                        datatype = cb.getLabel();
                        new SampleFrame(title);
                    } catch(NullPointerException e) {
                        JOptionPane.showMessageDialog(pop,
                                "Choose one.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    class SampleFrame {

        SampleFrame(String title) {

            dataType.dispose();
            frame = new JFrame();
            frame.setTitle(title);
            frame.getContentPane().setBackground(Color.CYAN);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setBounds(100, 100, 433, 606);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);

            JLabel lbl1 = new JLabel("ENTER SAMPLE FRAME " + "(N = " + popsize + "):");
            lbl1.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            lbl1.setBounds(38, 21, 300, 33);
            frame.getContentPane().add(lbl1);

            JButton enter2 = new JButton("ENTER");
            enter2.setForeground(Color.BLACK);
            enter2.setBackground(Color.getHSBColor(8, 9, 2));
            enter2.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
            enter2.setBounds(273, 442, 117, 33);
            frame.getContentPane().add(enter2);

            ta = new TextArea();
            ta.setBounds(38, 72, 352, 359);
            frame.getContentPane().add(ta);

            JLabel lbl2 = new JLabel("Press space or enter key to separate elements/members.");
            lbl2.setBounds(44, 52, 373, 23);
            frame.getContentPane().add(lbl2);

            enter2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    ArrayList<Integer> numeric = new ArrayList<>();
                    ArrayList<Character> character = new ArrayList<>();
                    String s = ta.getText();

                    String[] toks = s.split("\\s+|\\n");
                    ArrayList<String> al = new ArrayList<>(Arrays.asList(toks));
                    boolean typeerr = false;
                    if (datatype.equals("NUMERIC")) {
                        for (String st : al) {
                            try {
                                int n = Integer.parseInt(st);
                                if (n < 0) {
                                    typeerr = true;
                                    numeric.clear();
                                    JOptionPane.showMessageDialog(pop,
                                            "Input must not be a negative number! Try again.",
                                            "Invalid input",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    numeric.add(n);
                                }
                            }
                            catch (Exception e) {
                                typeerr = true;
                                numeric.clear();
                                JOptionPane.showMessageDialog(pop,
                                        "Input must be a number! Try again.",
                                        "Invalid input",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }

                        boolean sizeerror = dispError(al.size());
                        if(!sizeerror && ! typeerr){
                            intal = numeric;
                            sampframe = returnPopulation(intal);
                            new DispFrame(title);
                        }
                    } else if (datatype.equals("CHARACTER")) {

                        //checks if first token is not equal to NULL
                        String token1 = al.get(0);
                        if (token1.equals("")) {
                            al.remove(token1);
                        }

                        for (String st : al) {
                            //st = st.toUpperCase();
                            int len = st.length();
                            if(len > 1) {
                                JOptionPane.showMessageDialog(pop,
                                        "Input must be A-Z only! Try again.",
                                        "Invalid input",
                                        JOptionPane.ERROR_MESSAGE);
                                typeerr = true;
                                break;
                            } else
                            if ( len == 1 && (st.toUpperCase().charAt(0) < 'A' || st.toUpperCase().charAt(0) > 'Z'  ) ){
                                typeerr = true;
                                character.clear();
                                JOptionPane.showMessageDialog(pop,
                                        "Input must be A-Z only! Try again.",
                                        "Invalid input",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            } else {
                                character.add(st.charAt(0));
                            }
                        }

                        boolean sizeerror = dispError(al.size());
                        if(!sizeerror && !typeerr){
                            charal = character;
                            sampframe = returnPopulation(charal);
                            new DispFrame(title);
                        }
                    }
                }

            });
        }

        boolean dispError(int len){
            if (len <= 0 || len < popsize) {
                JOptionPane.showMessageDialog(pop,
                        "Your input does not meet required size: " + popsize,
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            } else if (len > popsize) {
                JOptionPane.showMessageDialog(pop,
                        "Your input exceeds required size: " + popsize,
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            }
            return false;
        }
    }

    private String returnPopulation(ArrayList al) {
        int len = al.size();
        String result = "";
        if (al.size() != 0) {
            for(int i = 0; i < len; i++) {
                result += String.format("%-14s", "Index " + (i + 1) + ": ");
                result += al.get(i);
                result += "\n";
            }
        }
        return result;
    }

    //returns the picked items from population
    private String returnSample(ArrayList popn, ArrayList<Integer> sampleIndices) {
        String temp = "";
        for (int i = 0; i < sampleIndices.size(); i++) {
            int curr = sampleIndices.get(i);
            temp += String.format("%-14s", "Index " + (curr + 1) + ": ");
            temp += popn.get( curr );
            temp += "\n";
        }
        return temp;
    }

    private void simpleRandom(ArrayList al) {
        ArrayList<Integer> result = new ArrayList<>();
        int randNum;

        //generate random indices
        while(result.size() < sampsize){
            randNum = 0 + (int)(Math.random() * (popsize));

            if(!result.contains(randNum)){
                result.add(randNum);
            }
        }

        dispresult = returnSample(al, result);
    }

    private void systematicSampling(ArrayList al) {
        int lenGiven = al.size();
        ArrayList result = new ArrayList();
        int k = (int)Math.floor(lenGiven / sampsize);
        //pick random starting point
        int start = 0 + (int) (Math.random() * k);

        int items = 1;
        //pick the indicesss
        while(start <= lenGiven && items <= sampsize ){
            result.add(start);
            start += k;
            items++;
        }
        dispresult = returnSample(al, result);
    }

    private ArrayList<ArrayList> groupElements(ArrayList al)
    {
        Collections.sort(al);
        ArrayList< ArrayList > alal = new ArrayList<>();
        int len = al.size();

        // Initialize all elements as initially unvisited
        boolean[] visited = new boolean[len];

        // Traverse all elements
        for(int i = 0; i < len; i++){
            Boolean el = visited[i];
            if( !el ){
                Object obj1 = al.get(i);
                alal.add( new ArrayList() );
                ArrayList curr = alal.get(alal.size() - 1);
                curr.add( obj1 );

                for(int j = i+1; j < len; j++){
                    Object obj2 = al.get(j);
                    if(obj2.equals(obj1)){
                        visited[j] = true;
                        curr.add( obj2 );
                    }
                }
            }
        }

        return alal;
    }

    private String returnStrata(ArrayList<ArrayList> alal){
        String st = "";
        for( int i = 0; i < alal.size(); i++){
            ArrayList curr = alal.get(i);
            st += "STRATA " + (i + 1) + ": " + "\n";
            st += returnPopulation(curr) + "\n";
        }

        return st;
    }

    private String returnPickedStrataSamples(ArrayList<ArrayList> alal, ArrayList<ArrayList> result){
        String stresult = "RANDOM SAMPLE\n";
        for( int i = 0; i < alal.size(); i++){
            ArrayList currorig = alal.get(i);
            ArrayList currresult = result.get(i);
            stresult += "STRATA " + (i + 1) + " (N = " + currorig.size() + ")" +  ": " + "\n";
            stresult += returnSample(currorig, currresult) + "\n";
        }
        return stresult;
    }

    private String stratifiedSampling(ArrayList<ArrayList> given, int samplesize){
        int len = given.size();
        ArrayList<ArrayList> result = new ArrayList<>();
        int s = 0;
        double b = samplesize / 100.0;
        for(int i = 0; i < len; i++){
            ArrayList curr = given.get(i);
            int currlen = curr.size();
            s = (int) Math.ceil(b * currlen);
            result.add( simpleRandomForStratified(curr, s) );
        }

        return returnPickedStrataSamples(given, result);
    }

    private ArrayList<Integer> simpleRandomForStratified(ArrayList al, int samplesize){
        ArrayList<Integer> result = new ArrayList<>();
        int randNum;
        int len = al.size();

        //generate random indices
        while(result.size() < samplesize){
            randNum = 0 + (int)(Math.random() * (len));

            if(!result.contains(randNum)){
                result.add(randNum);
            }
        }

        return result;
    }

    class DispFrame {

        DispFrame(String title) {
            frame.dispose();
            display = new JFrame();
            display.getContentPane().setBackground(Color.CYAN);
            display.setVisible(true);
            display.setTitle(title);
            display.setResizable(false);
            display.setBounds(100, 100, 433, 606);
            display.setLocationRelativeTo(null);
            display.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            display.getContentPane().setLayout(null);

            JLabel bel = new JLabel("SAMPLE FRAME:");
            bel.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            bel.setBounds(38, 21, 246, 33);
            display.getContentPane().add(bel);

            JButton next = new JButton("ENTER");
            next.setForeground(Color.BLACK);
            next.setBackground(Color.getHSBColor(8, 9, 2));
            next.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            next.setBounds(273, 442, 117, 33);
            display.getContentPane().add(next);

            TextArea disp = new TextArea();
            disp.setBounds(38, 60, 352, 359);
            disp.setEditable(false);
            display.getContentPane().add(disp);

            grp = new ArrayList<>();

            //for stratification
            if (method.equals("Stratification")) {
                if (datatype.equals("NUMERIC")){
                    grp = groupElements(intal);
                }else{
                    grp = groupElements(charal);
                }
                sampframe = returnStrata(grp);
            }

            disp.setText(sampframe);

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    new SampleSize(title);
                }

            });
        }
    }

    //sample (n) must not be less than one and greater than population (basis)
    private boolean isSampleSizeValid(int n, int basis) {
        if (n < 1) {
            JOptionPane.showMessageDialog(pop,
                    "Sample size must be greater than 0! Try again.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (n >= basis) {
            String errorMsg1 = "Sample size must not be greater than or equal to the population size: "+popsize+". Try again.";
            String errorMsg2 = "Percentage must not be greater than " + (basis - 1) + ". Try again.";
            if(method.equals("Stratification")){
                JOptionPane.showMessageDialog(pop,
                        errorMsg2,
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(pop,
                        errorMsg1,
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
            }

            return true;
        }
        return false;
    }

    class SampleSize {
        SampleSize(String title) {
            display.dispose();
            size = new JFrame();
            size.getContentPane().setBackground(Color.CYAN);
            size.setVisible(true);
            size.setTitle(title);
            size.setResizable(false);
            size.setBounds(100, 100, 433, 606);
            size.setLocationRelativeTo(null);
            size.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            size.getContentPane().setLayout(null);

            JLabel lbl5 = new JLabel();
            String strLabel1 = "ENTER SAMPLE SIZE " + "(N = " + popsize + "):";
            String strLabel2 = "ENTER PERCENTAGE (1-99):";
            if (method.equals("Stratification")) {
                lbl5.setText(strLabel2);
            }else{
                lbl5.setText(strLabel1);
            }

            lbl5.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            lbl5.setBounds(38, 21, 300, 33);
            size.getContentPane().add(lbl5);

            JButton enter3 = new JButton("ENTER");
            enter3.setForeground(Color.BLACK);
            enter3.setBackground(Color.getHSBColor(8, 9, 2));
            enter3.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
            enter3.setBounds(273, 442, 117, 33);
            size.getContentPane().add(enter3);

            tf2 = new JTextField();
            tf2.setBounds(41, 208, 333, 33);
            size.getContentPane().add(tf2);
            tf2.setColumns(10);


            enter3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    boolean inv = false;
                    boolean error = false;
                    String in = tf2.getText();
                    int n; //will be equated to sample size

                    //if user clicks OK without typing anything, set sample size to 20%
                    if (in.equals("")) {
                        int twentypercent = (int)Math.ceil(popsize * 0.2);
                        sampsize = twentypercent;
                        String msg = "We will choose " + twentypercent + " from population automatically.";
                        JOptionPane.showMessageDialog(pop,
                                msg,
                                "Notice",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        int bound = popsize;
                        if(method.equals("Stratification")){
                            bound = 100;

                        }

                        try {
                            n = Integer.parseInt(in);
                            inv = isSampleSizeValid(n, bound);
                            if(!inv){
                                if (method.equals("Stratification")){
                                    dispresult = stratifiedSampling(grp, n);

                                }else{
                                    sampsize = n;
                                }
                            }
                        }
                        catch (Exception e) {
                            error = true;
                            JOptionPane.showMessageDialog(pop,
                                    "Error: Invalid format. Try again.",
                                    "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }

                    //perform sampling according to method chosen
                    if (!error && !inv) {
                        if (method.equals("Random")) {
                            if(datatype.equals("NUMERIC")){
                                simpleRandom(intal);
                            }else{
                                simpleRandom(charal);
                            }
                            new Result("RANDOM SAMPLE");
                        }
                        else if (method.equals("Systematic")) {
                            if(datatype.equals("NUMERIC")){
                                systematicSampling(intal);
                            }else{
                                systematicSampling(charal);
                            }
                            new Result("RANDOM SAMPLE (Systematic Sampling)");
                        }
                        else if (method.equals("Stratification")) {
                            //stratification's method is activated in DispFrame
                            new Result(title);

                        }
                    }
                }

            });

        }
    }

    class Result {
        Result(String title) {
            size.dispose();
            res = new JFrame();
            res.getContentPane().setBackground(Color.CYAN);
            res.setResizable(false);
            res.setTitle(title);
            res.setVisible(true);
            res.setBounds(100, 100, 433, 606);
            res.setLocationRelativeTo(null);
            res.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            res.getContentPane().setLayout(null);

            JLabel lbl6 = new JLabel();
            lbl6.setFont(new Font("Arial Narrow", Font.BOLD, 20));
            lbl6.setBounds(38, 21, 330, 33);
            res.getContentPane().add(lbl6);

            JButton again = new JButton("TRY AGAIN");
            again.setForeground(Color.BLACK);
            again.setBackground(Color.getHSBColor(8, 9, 2));
            again.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            again.setBounds(273, 442, 117, 33);
            res.getContentPane().add(again);

            TextArea ta2 = new TextArea();
            ta2.setBounds(38, 60, 352, 359);
            ta2.setEditable(false);
            res.getContentPane().add(ta2);

            JButton go = new JButton("GO TO MENU");
            go.setForeground(Color.BLACK);
            go.setBackground(Color.getHSBColor(8, 9, 2));
            go.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            go.setBounds(38, 442, 118, 33);
            res.getContentPane().add(go);

            JButton quit2 = new JButton("QUIT");
            quit2.setForeground(Color.BLACK);
            quit2.setBackground(Color.getHSBColor(8, 9, 2));
            quit2.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
            quit2.setBounds(174, 442, 89, 33);
            res.getContentPane().add(quit2);

            ta2.setText(dispresult);
            if (method.equals("Random")) {
                lbl6.setText("RANDOM SAMPLE:");
            } else if (method.equals("Systematic")) {
                lbl6.setText("RANDOM SAMPLE (Systematic Sampling):");
            } else {
                lbl6.setText("STRATIFIED SAMPLE:");
            }

            again.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    res.dispose();
                    new SampleSize(title);
                }
            });

            go.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    res.dispose();
                    new Menu();
                }
            });

            quit2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    res.dispose();
                    System.exit(0);
                }
            });
        }
    }

}
