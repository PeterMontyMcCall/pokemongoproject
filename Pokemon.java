// Pokemon.java
// ICS4U0, Nov 29, 2021
// Duc Minh Pham

import java.util.*;
import java.io.File;

public class Pokemon {
  private String name;
  private String type1;
  private String type2;
  private String front_sprite;
  private String back_sprite;
  private boolean fainted;
  private int level;
  private ArrayList<Move> moveset;
  // Pokemon Stats
  private int hpTotal;
  private int hpLeft;
  private int atk;
  private int def;
  private int spAtk;
  private int spDef;
  private int speed;
  // Counter for status move
  private int raiserCount;
  private int[] reducerCount = new int[6]; // Atk Def SpAtk SpDef Spd Acc
  
  // Constructor
  public Pokemon(String name) throws java.io.FileNotFoundException{
    Random rand = new Random();
    Scanner file = new Scanner(new File("libraries/Pokedex.txt"));
    fainted = false;
    moveset = new ArrayList<Move>();
    // Look through Pokedex.txt for the corresponding pokemon
    while (file.hasNext()) {
      String pokeName = file.next();
      if (pokeName.equals(name)) {
        // Assign each attribute accordingly of the pokemon
        this.name = pokeName;
        type1 = file.next();
        type2 = file.next();
        hpTotal = file.nextInt();
        hpLeft = hpTotal;
        atk = file.nextInt();
        def = file.nextInt();
        spAtk = file.nextInt();
        spDef = file.nextInt();
        speed = file.nextInt();
        // Getting images
        front_sprite = file.next();
        back_sprite = file.next();
        // Get 4 moves of the pokemon
        for (int i = 0; i < 4; i++) {
          String move = file.next();
          Move m = new Move(move);
          moveset.add(m);
        }
        break;
      }
      else {
        file.nextLine();
      }
    }
    // Give the pokemon a random level from 75-85
    level = rand.nextInt(11)+75;
    // Increase the stats by 1/50 for each level gained
    hpTotal = hpTotal + (int)((hpTotal * 0.02) * level);
    hpLeft = hpTotal;
    atk = atk + (int)((atk * 0.02) * level);
    def = def + (int)((def * 0.02) * level);
    spAtk = spAtk + (int)((spAtk * 0.02) * level);
    spDef = spDef + (int)((spDef * 0.02) * level);
    speed = speed + (int)((speed * 0.02) * level);
    // Reset counters
    raiserCount = 0;
  }
  
  // Accessors
  public String getName() {
    return name;
  }
  
  public String getType1() {
    return type1;
  }

  public String getType2() {
    return type2;
  }

  public boolean isFaint() {
    return fainted;
  }
  
  public int getLevel() {
    return level;
  }
  
  public int getHPLeft() {
    return hpLeft;
  }
  
  public int getHPTotal() {
    return hpTotal;
  }
  
  public int getAtk() {
    return atk;
  }
  
  public int getDef() {
    return def;
  }
  
  public int getSpAtk() {
    return spAtk;
  }
  
  public int getSpDef() {
    return spDef;
  }

  public int getSpeed() {
    return speed;
  }

  public String getFrontSprite() {
    return front_sprite;
  }

  public String getBackSprite() {
    return back_sprite;
  }
  
  public Move getMove(int i) {
    return moveset.get(i);
  }

  public int getRaiserCount() {
    return raiserCount;
  }
  
  public int getReducerCount(int i) {
    return reducerCount[i];
  }

  // Mutators
  public void setName(String name) {
    this.name = name;
  }
  
  public void setType1(String type) {
    type1 = type;
  }

  public void setType2(String type) {
    type2 = type;
  }

  public void setFaint(boolean fainted) {
    this.fainted = fainted;
  }
    
  public void setHPLeft(int health) {
    hpLeft = health;
  }
  
  public void setHPTotal(int health) {
    hpTotal = health;
  }
  
  public void setAtk(int stat) {
    atk = stat;
  }
  
  public void setDef(int stat) {
    def = stat;
  }
  
  public void setSpAtk(int stat) {
    spAtk = stat;
  }
  
  public void setSpDef(int stat) {
    spDef = stat;
  }

  public void setSpeed(int stat) {
    speed = stat;
  }

  public void setFrontSprite(String str) {
    front_sprite = str;
  }
  
  public void setBackSprite(String str) {
    back_sprite = str;
  }

  public void setRaiserCount(int n) {
    raiserCount = n;
  }
  
  public void setReducerCount(int i, int n) {
    reducerCount[i] = n;
  }

  // Other methods
  // Decrease hp upon attack
  public void decreaseHealth(int damage) {
    hpLeft -= damage;
    // Set hpLeft to 0 if it is negative and check for fainting
    if (hpLeft <= 0) {
      hpLeft = 0;
      fainted = true;
    }
  }
  
  // Add move to the pokemon
  public void addMove(String move) throws java.io.FileNotFoundException {
    Move m = new Move(move);
    moveset.add(m);
  }

  // Get all moves
  public String getMoveSet() {
    String str = "";
    for (int i = 0; i < 4; i++) {
      str += moveset.get(i) + "\n";
    }
    return str;
  }

  // damage = (((((2*level)/5) + 2) * move power * (attack stat/oppnent defense stat)/50 + 2) * Type Effectiveness
  // Calculating attack damage 
  public int attack(Pokemon opp, int move, double effectiveness) {
    double damage = (((((2*level)/5) + 2) * moveset.get(move).getPower() * atk/opp.getDef())/50 + 2) * effectiveness;
    return (int)damage;
  }

  // Calculating spAtk damage
  public int spAttack(Pokemon opp, int move, double effectiveness) {
    double damage = (((((2*level)/5) + 2) * moveset.get(move).getPower() * spAtk/opp.getSpDef())/50 + 2) * effectiveness;
    //System.out.println(level + " " + moveset.get(move).getPower() + " " + spAtk + " " + opp.getDef() + " " + effectiveness);
    return (int)damage;
  }
  
  // toString
  public String toString() {
    String str = "Name: " + name + " Lv: " + level + "\nType: " + type1 + " " + type2 + " HP:" + hpLeft + "/" + hpTotal + "\n";
    str += "Atk: " + atk + " | Def: " + def + " | Sp.Atk: " + spAtk + " | Sp.Def: " + spDef + " | Speed: " + speed + "\n";
    str += getMoveSet();
    str += "Front Srpite: " + front_sprite + " | Back sprite: " + back_sprite + "\n\n";
    return str;
  }
}