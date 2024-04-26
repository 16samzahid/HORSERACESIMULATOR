import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.event.*;

public class HorseRaceGUI extends JFrame {
    JTextField horseName;
    JSlider horseNum, laneNum;
    JComboBox<String> trackLength, horseSymbols, horseColours;
    JButton saveHorses, customiseHorses, betButton;
    JTextArea raceResults;
    JComboBox<String> horseNames;
    Horse[] horseList = new Horse[5];
    boolean customised = false;
    private Race r;
    HorseRaceGUI() {

        //new race textarea where the race will be displayed
        raceResults = new JTextArea();
        raceResults.setEditable(false);
        raceResults.setBackground(Color.WHITE);
        raceResults.setForeground(Color.BLACK);
        raceResults.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(raceResults);
        //ensure
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scroll.setPreferredSize(new Dimension(800, 200));

        //redirecting the output stream to the textarea
        PrintStream printStream = new PrintStream(new CustomOutputStream(raceResults));
        System.setOut(printStream);

        //the top panel for the number of lanes and horses and tracklength
        JPanel numberPanel = new JPanel();
        numberPanel.setBackground(Color.gray);
        //numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.Y_AXIS)); // Set Y_AXIS orientation
        //numberPanel.setBounds(0, 0, 200, 150);
        numberPanel.setBounds(0, 0, 1200, 150);
        numberPanel.setBackground(new Color(190, 160, 230));


        //adding the components to the number panel
        JLabel horsesLabel = new JLabel("Number of Horses: ");
        horseNum = new JSlider(JSlider.HORIZONTAL, 2, 2, 2);
        horseNum.setMajorTickSpacing(1);
        horseNum.setPaintTicks(true);
        horseNum.setPaintLabels(true);
        horseNum.setSnapToTicks(true);

        //the number of lanes that the user can select
        JLabel laneLabel = new JLabel("Number of Lanes: ");
        laneNum = new JSlider(JSlider.HORIZONTAL, 2, 5, 2);
        laneNum.setMajorTickSpacing(1);
        laneNum.setPaintTicks(true);
        laneNum.setPaintLabels(true);
        laneNum.setSnapToTicks(true);
        laneNum.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                horseNum.setMaximum(laneNum.getValue());
            }
        });

        horseNum.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                //set the horseList to the new size of the horseNum
                horseList = new Horse[horseNum.getValue()];
            }
        });

        //dropdown for track length
        JLabel trackLabel = new JLabel("Track Length: ");
        String[] lengths = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50"};
        trackLength = new JComboBox<>(lengths);

        
        numberPanel.add(trackLabel);
        numberPanel.add(trackLength);
        numberPanel.add(laneLabel);
        numberPanel.add(laneNum);
        numberPanel.add(horsesLabel);
        numberPanel.add(horseNum);

        //declare a new race using values provided from sliders and dropdown
        r = new Race(trackLength.getSelectedIndex(), horseNum.getValue(), laneNum.getValue());

        //customise horse frame for customising horses
        JFrame horseFrame = new JFrame();
        horseFrame.setDefaultCloseOperation(horseFrame.DISPOSE_ON_CLOSE);
        horseFrame.setSize(400, 400);
        horseFrame.setTitle("Customise Horses");
        horseFrame.setResizable(false);
        horseFrame.setLayout(null);
        horseFrame.getContentPane().setBackground(new Color(190, 160, 230));

        //create the betting frame
        JFrame bettingFrame = new JFrame();
        bettingFrame.setTitle("Place Bet");
        bettingFrame.setSize(300, 200);

        //selecting the horse to edit
        JLabel selectHorseLabel = new JLabel("Select Horse: ");
        selectHorseLabel.setBounds(50, 50, 100, 30);
        horseNames = new JComboBox<>();
        horseNames.setBounds(150, 50, 150, 30);

        //editing the name
        JLabel horseNameLabel = new JLabel("Horse Name: ");
        horseNameLabel.setBounds(50, 100, 100, 30);
        horseName = new JTextField();
        horseName.setBounds(150, 100, 150, 30);

        String[] symbols = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        horseSymbols = new JComboBox<>(symbols);
        horseSymbols.setBounds(150, 200, 150, 30);
        JLabel horseSymbolLabel = new JLabel("Horse Symbol: ");
        horseSymbolLabel.setBounds(50, 200, 100, 30);

        String[] colours = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Pink", "Black", "White"};
        horseColours = new JComboBox<>(colours);
        horseColours.setBounds(150, 150, 150, 30);
        JLabel horseColourLabel = new JLabel("Horse Colour: ");
        horseColourLabel.setBounds(50, 150, 100, 30);


        saveHorses = new JButton();
        saveHorses.setText("Save Horse");
        //saveHorses.setSize(saveHorses.getPreferredSize());
        saveHorses.setBounds(150, 250, 150, 50);
        saveHorses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String horseNameText = horseName.getText();
                String horseColour = (String) horseColours.getSelectedItem();
                String horseSymbol = (String) horseSymbols.getSelectedItem();
                //String selectedHorse = (String) horseNames.getSelectedItem();
                int horseIndex = horseNames.getSelectedIndex();
                Horse h = new Horse((char) (horseIndex + 97), horseNameText);
                horseList[horseIndex] = h;
                h.setColour(horseColour);
                h.setSymbol(horseSymbol.charAt(0));
                horseNames.removeItemAt(horseIndex);
                horseNames.insertItemAt(horseNameText, horseIndex);
                horseNames.setSelectedIndex(horseIndex);
                horseName.setText("");
                horseColours.setSelectedIndex(0);
                //horseFrame.setVisible(false);

            }
        });

        //adding things to the horse frame
        horseFrame.add(selectHorseLabel);
        horseFrame.add(horseNames);
        horseFrame.add(horseNameLabel);
        horseFrame.add(horseName);
        horseFrame.add(horseColourLabel);
        horseFrame.add(horseColours);
        horseFrame.add(horseSymbolLabel);
        horseFrame.add(horseSymbols);
        horseFrame.add(saveHorses);

        customiseHorses = new JButton();
        customiseHorses.setText("Customise Horses");
        customiseHorses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                horseNames.removeAllItems();
                int horses = horseNum.getValue();
                int selectedTrackLength = Integer.parseInt((String) trackLength.getSelectedItem());
                r.setHorseCount(horses);
                r.setRaceLength(selectedTrackLength);
                for (int i = 0; i < horses; i++) {
                    //String horseName = "Horse " + (i + 1);
                    String horseName = horseList[i] == null ? "Horse " + (i + 1) : horseList[i].getName();
                    Horse h = new Horse((char) (i + 97), horseName);
                    //add the horse to the race
                    r.addHorse(h, i + 1);
                    //add the horse to the dropdown in horseframe
                    horseNames.addItem(horseName);
                    //add the horse to the horseList
                    horseList[i] = h;
                }
                horseFrame.setVisible(true);
            }
        });


        numberPanel.add(customiseHorses);

        //betting frame
        // Add the betting button to the GUI constructor
        betButton = new JButton("Place a Bet");
        JLabel betLabel = new JLabel("Select horse:");
        // Create a combo box to select a horse
        JComboBox<String> horseComboBox = new JComboBox<>();
        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int horses = horseNum.getValue();
                int selectedTrackLength = Integer.parseInt((String) trackLength.getSelectedItem());
                int lanes = laneNum.getValue();
                if (!customised) {
                    r.setHorseCount(horses);
                    r.setRaceLength(selectedTrackLength);
                    for (int i = 0; i < horses; i++) {
                        String horseName = "Horse " + (i + 1);
                        Horse h = new Horse((char) (i + 97), horseName);
                        r.addHorse(h, i + 1);
                    }
                }
                //otherwise set the customised horses using the values from the horseNames dropdown
                else {
                    r.setHorseCount(horses);
                    r.setRaceLength(selectedTrackLength);
                    for (int i = 0; i < horses; i++) {
                        String horseName = (String) horseNames.getItemAt(i);
                        Horse h = new Horse((char) (i + 97), horseName);
                        r.addHorse(h, i + 1);
                    }

                }
                // Clear the combo box before adding items to prevent duplicates
                horseComboBox.removeAllItems();
        
                // Add items to the combo box
                for (Horse horse : r.getHorses()) {
                    if (horse != null) { // Check if the horse object is not null
                        horseComboBox.addItem(horse.getName());
                    }
                }
        
                // Make the betting frame visible
                bettingFrame.setVisible(true);
            }
        });
    }



}