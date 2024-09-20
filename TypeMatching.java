// TypeMatching.java
// ICS4U0, Nov 29, 2021
// Duc Minh Pham
// Check move effectiveness

import java.util.*;
import java.io.File;

public class TypeMatching {
  // Check move effectiveness against a pokemon
  public double typeMatch(String moveType, Pokemon p) throws java.io.FileNotFoundException{
    Scanner file = new Scanner(new File("libraries/TypeChart.txt"));
    double effective = 1;
    boolean matched = false;
    while(file.hasNext()) {
      // Set str as the first column (move type)
      String str = file.next();
      String preStr = "";
      // Check for the matching move type
      if (str.equals(moveType)) {
        // Set str2 as the second volum (pokemon type)
        String str2 = file.next();
        // Set eft as effectiveness
        double eft = file.nextDouble();
        // Double type matching
        // Then check for the matching pokemon 2 types
        if (str2.equals(p.getType1()) || str2.equals(p.getType2())) {
          effective = effective * eft;
        }
        // Set preStr as str when it finds a match
        preStr = str;
        matched = true;
      }
      // Skip a line if doesn't match
      else {
        // Break out of loop if no longer in the matching range
        if (!preStr.equals(str) && matched == true) {
          break;
        }
        file.nextLine();
      }
    }
    return effective;
  }
}

// TypeMatching t = new TypeMatching();
// t.typeMatch(player.getPokemon(i).getMove(x).getType(), player.getPokemon(j).getType)