// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

public class Race{
   private int raceLength;
   private Horse[] horses = new Horse[5]; //max horses is 5
   private int horseCount;
   private int lanes;
   private boolean finished = false;
   private boolean placedBet = false;
   //private int lanes;

   public Race(int distance, int count, int lanes) {
      //horses = new Horse[count];
      this.raceLength = distance;
      this.horseCount = count;
      this.lanes = lanes;
      //initialize the horses array to null
      for(int i = 0; i < 5; ++i) {
         this.horses[i] = null;
      }
      for (int i = 0; i < lanes; i++) {
         this.horses[i] = new Horse('z', "EMPTY LANE");
      }
   }

   public Race(int i) {
    //TODO Auto-generated constructor stub
   }

   public boolean hasPlacedBet() {
      return this.placedBet;
   }

   public void setPlacedBet(boolean placedBetValue, Horse h) {
      // Clear the bet from any previously betted horse
      clearAllBets();
      
      // Place the bet on the selected horse
      for(int i = 0; i < horseCount; i++) {
          if(h == horses[i]) {
              horses[i].placeBet();
          }
      }
      
      this.placedBet = placedBetValue;
   }
   
   // Method to clear the bet from all horses
   private void clearAllBets() {
         for(int i = 0; i < horseCount; i++) {
            if(horses[i] != null) {
               horses[i].removeBet();
            }
         }
   }
  

   public boolean isFinished() {
      return this.finished;
   }

   public void setHorseCount(int count) {
      this.horseCount = count;
   }

   public void setRaceLength(int distance) {
      this.raceLength = distance;
   }

   public Horse[] getHorses() {
      return this.horses;
   }

   public int getRaceLength() {
      return this.raceLength;
   }

   public void addHorse(Horse theHorse, int laneNumber) {
      if (laneNumber > 0 && laneNumber < 5) {
         this.horses[laneNumber - 1] = theHorse;
      } else {
         System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
      }

   }

   //startRace method which is called in HorseRaceGUI.java to start the race
   public void startRaceGUI(JTextArea raceResults, JTextArea stats, int raceIndex) {
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
         this.printRace(raceResults, stats, raceIndex);


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
      printStats(stats, raceIndex);

   }

   private void moveHorse(Horse theHorse) {
      //if the horse has not fallen, then move the horse forward
      if (!theHorse.hasFallen()) {
         //if the random number is less than the confidence, then move the horse forward
         if (Math.random() < theHorse.getConfidence()) {
            theHorse.moveForward();
         }
         //if the horse has fallen, then set the confidence to 0.1 less than the current confidence
         if (Math.random() < 0.05 * theHorse.getConfidence() * theHorse.getConfidence() && theHorse.getConfidence() > 0.1){
            theHorse.fall();
            theHorse.setConfidence(theHorse.getConfidence() - 0.1);
         }
      }
      //if the horse won the race, then set the confidence to 0.1 more than the current confidence
      if (this.raceWonBy(theHorse)&& theHorse.getConfidence() < 1.0) {
         theHorse.setConfidence(theHorse.getConfidence() + 0.1);
      }

   }

   private boolean raceWonBy(Horse theHorse) {
      return theHorse.getDistanceTravelled() == this.raceLength;
   }

   private void printRace(JTextArea raceResults, JTextArea stats, int raceIndex) {
      //JTextArea raceResults = gui.getRaceResults();
      raceResults.setText("");
      this.multiplePrint('=', this.raceLength + 3);
      //raceResults.append("\n");
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
         // else {
         //    this.printLane(this.horses[i]);
         //    PrintStream var10000 = System.out;
         //    var10000.print("EMPTY LANE");
         //    System.out.println();
         // }
      }

      this.multiplePrint('=', this.raceLength + 3);
      System.out.println();

      //printing the winning horse
      for(i = 0; i < lanes; ++i) {
         if (this.horses[i] != null && this.raceWonBy(this.horses[i])) {
            System.out.println();
            Horse var4 = this.horses[i];
            System.out.println("And the winner is " + var4.getName() + "!");
         }
      }



      // Inside the printRace() method in the Race class
      if (this.horses[i] != null && this.raceWonBy(this.horses[i]) && placedBet) {
         if (this.horses[i].isBettedOn()) {
            System.out.println();
            Horse var4 = this.horses[i];
            System.out.println("Congratulations! You have won your bet on " + var4.getName() + "!");
         } else {
            System.out.println();
            System.out.println("Sorry, you lost your bet. Better luck next time!");
         }
      }


      boolean allFallen = true;

      for(int n = 0; n < horseCount; ++n) {
         if (this.horses[n] != null && !this.horses[n].hasFallen()) {
            allFallen = false;
         }
      }

      if (allFallen) {
         System.out.println();
         System.out.println("All horses have fallen. No winner.");
      }

      //raceResults.append("Frame: ");
      //System.out.print("f");

   }

   private void printLane(Horse theHorse) {
      int spacesBefore = theHorse.getDistanceTravelled();
      int spacesAfter = this.raceLength - theHorse.getDistanceTravelled();
      System.out.print('|');
      this.multiplePrint(' ', spacesBefore);
      if (theHorse.hasFallen()) {
         System.out.print('X');
      } 
      else if (theHorse.getSymbol() == 'z') {
         System.out.print(' ');
      }
      else {
         System.out.print(theHorse.getSymbol());
      }

      this.multiplePrint(' ', spacesAfter);
      System.out.print('|');
   }

   private void multiplePrint(char aChar, int times) {
      for(int i = 0; i < times; ++i) {
         System.out.print(aChar);
      }

   }

   public void printStats(JTextArea stats, int raceIndex) {
      if(raceIndex == 0){
         stats.append("Race Statistics:");
      }
      stats.append("\n");
      stats.append("Race " + (raceIndex) + " Statistics:");
      stats.append("\n");
      stats.append("---------------");
      stats.append("\n");

      // Find the winning horse
      Horse winningHorse = null;
      double winningSpeed = Double.MIN_VALUE;
      for (int i = 0; i < horseCount; i++) {
          Horse horse = horses[i];
          if (horse != null) {
              double speed = (double) horse.getDistanceTravelled() / (double) raceLength;
              stats.append(horse.getName() + ": Speed = " + String.format("%.2f", speed) + " units/s");
              stats.append("\n");
              if (speed > winningSpeed) {
                  winningSpeed = speed;
                  winningHorse = horse;
              }
          }
      }

      // Print time taken for winning horse to complete the race
      if (winningHorse != null) {
          int winningTime = winningHorse.getDistanceTravelled(); // Distance is equivalent to time in this simulation
          long minutes = TimeUnit.SECONDS.toMinutes(winningTime);
          long seconds = winningTime - TimeUnit.MINUTES.toSeconds(minutes);
          stats.append("Winner: " + winningHorse.getName() + ", Speed = " + String.format("%.2f", winningSpeed) + " units/s, Time = " + minutes + " minutes, " + seconds + " seconds");
          stats.append("\n");
         } else {
          stats.append("No horse won the race.");
      }
      stats.append("\n");
   }

public void startRace() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'startRace'");
}
   

}
