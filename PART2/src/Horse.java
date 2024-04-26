import java.util.Random;

/**
 * The Horse class represents the horses in the race
 * It has a name and symbol to show its status and methods that allow it to do things
 * @author Sameeha Zahid
 * @version Version 1 - 26/03/2024
 */
public class Horse
{
    private String name;
    private char symbol;
    private int distance;
    private double confidence;
    private boolean fallen;
    private String colour;
    private boolean bettedOn;

    public Horse(char horseSymbol, String horseName) {
        bettedOn = false;
        this.symbol = horseSymbol;
        this.name = horseName;
        Random random = new Random();
        double randomNumber = 0.1 + (0.9 - 0.1) * random.nextDouble();
        this.confidence = randomNumber;
        this.colour = "Red";
    }

    //accessor methods
    //retrieve the confidence of the horse
    public double getConfidence()
    {
        return this.confidence;
    }
    
    //retrieve the distance travelled by the horse
    public int getDistanceTravelled()
    {
        return this.distance;
    }
    
    //retrieve the name of the horse
    public String getName()
    {
        return this.name;
    }
    
    //retrieve the symbol of the horse
    public char getSymbol()
    {
        return this.symbol;
    }

    //retrieve whether the horse has fallen
    public boolean hasFallen()
    {
        return this.fallen;
    }

    //mutator methods
    //make the horse fall
    public void fall()
    {
        this.fallen = true;
    }
    
    //set the distance travelled by the horse to 0
    public void goBackToStart()
    {
        this.distance = 0;
    }
    
    //make the horse move forward
    public void moveForward()
    {
        this.distance++;
    }

    //set the confidence of the horse
    //@param newConfidence the new confidence of the horse
    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    //set the symbol of the horse
    //@param newSymbol the new symbol of the horse
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
