// Player.java
// ICS4U0, Nov 29, 2021
// Duc Minh Pham
// Storing pokemons

import java.util.*;
import java.io.File;

public class Player {
  private ArrayList<Pokemon> pokemonBag;
  private String name; 
  private boolean lost;
  
  // Constructor
  public Player() {
    pokemonBag = new ArrayList<Pokemon>();
    lost = false;
  }

  public Player(String name) {
    pokemonBag = new ArrayList<Pokemon>();
    this.name = name;
    lost = false;
  }
  
  // Accessing Pokemon
  public Pokemon getPokemon(int i) {
    return pokemonBag.get(i);
  }

  // Accessors
  public String getName() {
    return name;
  }

  public boolean getLost() {
    return lost;
  }

  // Mutators
  public void setName(String name) {
    this.name = name;
  }
  
  public void setLost(boolean b) {
    lost = b;
  }

  // Other methods
  // Add new pokemon to bag
  public void addPokemon(String poke) throws java.io.FileNotFoundException{
    Pokemon p = new Pokemon(poke);
    pokemonBag.add(p);
  }
  
  // Get 6 random pokemons
  public void getRandomPokemon() throws java.io.FileNotFoundException {
    // If bag already have 6 pokemon, break
    if (pokemonBag.size() == 6) {
      return;
    }
    
    Random rand = new Random();
    Scanner file = new Scanner(new File("libraries/Pokedex.txt"));
    // Get 6 uniqe index from 1 -> pokedex length
    int[] uniqueIndex = new int[6];
    for (int i = 0; i < 6; i++) {
      int num = rand.nextInt(79);
      // Check for uniqueness
      boolean uniqueNum = false;
      while(uniqueNum == false) {
        for (int j = 0; j <= i; j++) {
          if (num == uniqueIndex[j]) {
            uniqueNum = false;
            // Set arr[i] as a new random number
            num = rand.nextInt(14);
            break;
          }
          uniqueNum = true;
        }
      }
      uniqueIndex[i] = num;
    }

    // Sorting uniqueIndex
    Arrays.sort(uniqueIndex);
    
    int skipLine = uniqueIndex[0]; // Skip n lines to get to the next destination
    // Get 6 pokemons
    for (int i = 0; i < 6; i++) {
      if (i > 0) {
        skipLine = uniqueIndex[i] - uniqueIndex[i-1];
      }
      // Pass skipLine-1 (file starts at 0, skipLine at 1) lines to get to the desireable line of uniqueIndex[i]
      for (int j = 0; j < skipLine-1; j++) {
        file.nextLine();
      }
      // Add that pokemon to player's bag
      String pokemon = file.next();
      addPokemon(pokemon);
      // Skip the rest of the file, only want the name
      file.nextLine();
    }
  }

  // Check for losing
  public boolean isLost() {
    for (int i = 0; i < 6; i++) {
      // If 1 single pokemon is not fainted, return true
      if (pokemonBag.get(i).isFaint() == false) {
        lost = false;
        return false;
      }
    }
    lost = true;
    return true;
  }
  
  // toString
  public String toString() {
    String str = "<> " + name + ":\n";
    for (int i = 0; i < pokemonBag.size(); i++) {
      str += (i+1) + ") ";
      str += pokemonBag.get(i);
    }
    return str;
  }
}