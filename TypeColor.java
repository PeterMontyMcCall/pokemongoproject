import java.awt.Color;

public class TypeColor {
    private Color color;

    public Color getTypeColor(Player player, int pokemon, int move) {
        String type = player.getPokemon(pokemon).getMove(move).getType();

        switch (type) {
            case "Normal":
                color = new Color(170, 170, 153);
                break;
            case "Fire":
                color = new Color(255, 68, 34);
                break;
            case "Water":
                color = new Color(51, 153, 255);
                break;
            case "Electric":
                color = new Color(255, 204, 51);
                break;
            case "Grass":
                color = new Color(119, 204, 85);
                break;
            case "Ice":
                color = new Color(102, 204, 255);
                break;
            case "Fighting":
                color = new Color(187, 85, 68);
                break;
            case "Poison":
                color = new Color(170, 85, 153);
                break;
            case "Ground":
                color = new Color(221, 187, 85);
                break;
            case "Flying":
                color = new Color(136, 153, 255);
                break;
            case "Psychic":
                color = new Color(254, 85, 153);
                break;
            case "Bug":
                color = new Color(170, 187, 34);
                break;
            case "Rock":
                color = new Color(187, 170, 102);
                break;
            case "Ghost":
                color = new Color(102, 102, 187);
                break;
            case "Dragon":
                color = new Color(119, 102, 238);
                break;
        }
        return color;
    }
}
