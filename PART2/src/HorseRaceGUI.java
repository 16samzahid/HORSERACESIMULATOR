import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.event.*;

public class HorseRaceGUI extends JFrame {
    JTextArea raceResults;
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
    }
}