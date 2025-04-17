// Move.java
// ICS4U0, Nov 29, 2021
// Duc Minh Pham
// Moves' stats

import java.io.File;
import java.util.*;

public class Move {
  private String name;
  private String type;
  private String category;
  private int power;
  private int accuracy;
  private int pp; // Power point
  
  // Constructor
  public Move(String name) throws java.io.FileNotFoundException {
    Scanner file = new Scanner(new File("libraries/MoveLibrary.txt"));
    // Read through the MoveLibrary.txt to get the corresponding move
    while (file.hasNext()) {
      // Check for corresponding move name
      String str = file.next();
      if (str.equals(name)) {
        this.name = str;
        type = file.next();
        category = file.next();
        power = file.nextInt();
        accuracy = file.nextInt();
        pp = file.nextInt();
        break;
      }
      // Skip a line if name doesn't match
      else {
        file.nextLine();
      }
    }
  } 
  
  // Accessors
  public String getName() {
    return name;
  }
  
  public String getType() {
    return type;
  }
  
  public String getCategory() {
    return category;
  }
  
  public int getPower() {
    return power;
  }
  
  public int getAccuracy() {
    return accuracy;
  }
  
  public int getPP() {
    return pp;
  }
  
  // Mutators
  public void setMove(String name) {
    this.name = name;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public void setPower(int power) {
    this.power = power;
  }
  
  public void setAccuracy(int acc) {
    accuracy = acc;
  }
  
  public void setPP(int pp) {
    this.pp = pp;
  }
  
  // Other methods
  // Decrease pp by 1 upon each move
  public void decreasePP() {
    pp -= 1;
  }

  // toString method
  public String toString() {
    return "[-] " + name + " | Type: " + type + " | Cat.: " + category + " | Power: " + power + " | Acc.: " + accuracy + " | PP: " + pp;
  }
}