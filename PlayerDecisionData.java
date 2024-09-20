public class PlayerDecisionData {
    private int priority;
    private int move;
    private int pokemonChoice;

    // Accessors
    public int getPriority() {
        return priority;
    }

    public int getMove() {
        return move;
    }
    public int getPokemonChoice() {
        return pokemonChoice;
    }
    
    // Mutators
    public void setPriority(int n) {
        priority = n;
    }    

    public void setMove(int n) {
        move = n;
    }
    public void setPokemonChoice(int n) {
        pokemonChoice = n;
    }
}
