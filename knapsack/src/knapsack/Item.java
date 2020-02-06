package knapsack;
import java.util.Random;
import java.util.Scanner;

/**
  * An Item in the knapsack problem is simply an object with a weight 
  * and a value.
  * <BR>DO NOT MODIFY THIS CLASS
  * @author Sophie Quigley
  */
public class Item {
    
    /**
     * Weight of item
     */
    int weight;
    /**
     * Value of Item
     */
    int value;

    /**
     * Returns a new Item with specified weight and value
     * @param weight Weight of new Item
     * @param value  Value of new Item
     */
    Item (int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
    /**
     * Returns a new Item whose weight and value are read from the Scanner
     * @param in Scanner (standard input)
     */
    Item (Scanner in) {
        weight = in.nextInt();
        while (weight <0){
            System.out.println("Error: weights cannot be negative");
            weight = in.nextInt();            
        }
        value = in.nextInt();
        while (value <0){
            System.out.println("Error: values cannot be negative");
            value = in.nextInt();            
        }        
    }
    
    /**
     * Returns a new Item whose weight and value are randomly generated
     * @param rand Random number generator used in Item creation
     * @param maxvalue weight and value are between 1 and maxvalue+1
     */
    Item (Random rand, int maxvalue) {
        weight = rand.nextInt(maxvalue)+1;
        value = rand.nextInt(maxvalue)+1;
    }
    
    /**
     * Returns a string describing the Item
     * @return Description of Item
     */
    @Override
    public String toString() {
        return "item of weight " + weight + " and value " + value;
    }
    
    /**
     * Getter for weight of Item
     * @return The Item's weight
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     * Getter for value of Item
     * @return The Item's value 
     */
    public int getValue() {
        return value;
    }

}
