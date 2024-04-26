/**
 * The Horse class represents the horses in the race
 * It has a name and symbol to show its status and methods that allow it to do things
 * @author Sameeha Zahid
 * @version Version 1 - 26/03/2024
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private double confidence;
    private boolean fallen;

    //Constructor of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
    }
    
}
