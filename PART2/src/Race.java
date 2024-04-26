import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import java.io.PrintStream;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse[] horses = new Horse[5]; //max horses is 5
    private int horseCount;
    private int lanes;
    private boolean finished = false;
    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     * @param count the number of horses in the race
     * @param lanes the number of lanes in the race
     */
    public Race(int distance, int count, int lanes) {
        //horses = new Horse[count];
        this.raceLength = distance;
        this.horseCount = count;
        this.lanes = lanes;
        //initialize the horses array to null
        for(int i = 0; i < 5; ++i) {
           this.horses[i] = null;
        }
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber > 0 && laneNumber < 5) {
           this.horses[laneNumber - 1] = theHorse;
        } else {
           System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
  
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    //startRace method which is called in HorseRaceGUI.java to start the race
    public void startRace() {
      //boolean finished = false;
      //Horse winningHorse = null;
      //for loop to reset the horses to the start
      int i;
      for(i = 0; i < lanes; ++i) {
         if (this.horses[i] != null) {
            this.horses[i].goBackToStart();
         }
      }

      //while loop to move the horses while the race is not yet finished
      while(!finished) {
         for(i = 0; i < horseCount; ++i) {
            if (this.horses[i] != null) {
               this.moveHorse(this.horses[i]);
            }
         }

         //print the race
         this.printRace();


         //check if any of the horses have won the race yet
         for(int j = 0; j < horseCount; j++) {
            if (this.horses[j] != null && this.raceWonBy(this.horses[j])) {
               finished = true;
            }
         }

         //check if all horses have fallen
         boolean allFallen = true;
         

         for(int n = 0; n < horseCount; ++n) {
            if (this.horses[n] != null && !this.horses[n].hasFallen()) {
               allFallen = false;
            }
         }

         //if they have all fallen, then finish the race
         if (allFallen) {
            finished = true;
         }

         //sleep for 100 milliseconds
         try {
            TimeUnit.MILLISECONDS.sleep(100L);
         } catch (Exception var5) {
         }
      }

   }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                //confidence decreases by 1 when the horse falls
                theHorse.setConfidence(theHorse.getConfidence()-0.1);
            }
        }
        if(raceWonBy(theHorse)){
            theHorse.setConfidence(theHorse.getConfidence()+0.1);
        
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
      this.multiplePrint('=', this.raceLength + 3);
      System.out.println();

      int i;

      for(i = 0; i < lanes; ++i) {
         if (this.horses[i] != null) {
            this.printLane(this.horses[i]);
            PrintStream var10000 = System.out;
            String var10001 = this.horses[i].getName();
            if(this.horses[i].getName()=="EMPTY LANE"){
               var10000.print(" EMPTY LANE");
            }
            else{
               var10000.print(" " + var10001 + " (Current confidence: " + String.format("%.1f", this.horses[i].getConfidence()) + ")");
            }
            System.out.println();
         }
      }
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public void setHorseCount(int count) {
      this.horseCount = count;
   }

   public void setRaceLength(int distance) {
      this.raceLength = distance;
   }
}
