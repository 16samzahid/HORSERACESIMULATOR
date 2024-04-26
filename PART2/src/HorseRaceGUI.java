import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.event.*;

public class HorseRaceGUI extends JFrame {
    JSlider horseNum, laneNum;
    JComboBox<String> trackLength;
    JTextArea raceResults;
    Horse[] horseList = new Horse[5];
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


    }

}