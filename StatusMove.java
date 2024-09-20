public class StatusMove {
    private String moveName;
    private double[] raisers = { 1, 1.5, 2, 2.5, 3, 3.5, 4 };
    private double[] reducers = { 1, 0.66, 0.5, 0.4, 0.33, 0.28, 0.25 };

    public String useStatusMove(Player player, Player opponent, int playerPokemon, int oppPokemon, int move) {
        String battleText = "";
        moveName = player.getPokemon(playerPokemon).getMove(move).getName();

        // Raiser
        if (moveName.equals("Agility")) {
            battleText += increaseSpeed(player, playerPokemon, 2);
        } else if (moveName.equals("Amnesia")) {
            battleText += increaseSpDefense(player, playerPokemon, 2);
        } else if (moveName.equals("Defense_Curl") || moveName.equals("Harden")) {
            battleText += increaseDefense(player, playerPokemon, 1);
        } else if (moveName.equals("Growth") || moveName.equals("Meditate")) {
            battleText += increaseAttack(player, playerPokemon, 1);
            battleText += "<br/>";
            battleText += increaseSpAttack(player, playerPokemon, 1);
        } else if (moveName.equals("Swords_Dance")) {
            battleText += increaseAttack(player, playerPokemon, 2);
            battleText += "<br/>";
            battleText += increaseSpAttack(player, playerPokemon, 2);
        }
        // Reducer
        // reducerCount: [Atk, Def, SpAtk, SpDef, Spd, Acc]
        else if (moveName.equals("Flash") || moveName.equals("Sand_Attack") || moveName.equals("SmokeScreen")) {
            battleText += decreaseAccuracy(opponent, oppPokemon, 1);
        } else if (moveName.equals("Growl")) {
            battleText += decreaseAttack(opponent, oppPokemon, 1);
        } else if (moveName.equals("Leer") || moveName.equals("Tail_Whip")) {
            battleText += decreaseDefense(opponent, oppPokemon, 1);
        } else if (moveName.equals("Screech")) {
            battleText += decreaseDefense(opponent, oppPokemon, 2);
        } else if (moveName.equals("String_Shot")) {
            battleText += decreaseSpeed(opponent, oppPokemon, 2);
        }
        battleText += "</html>";
        return battleText;
    }

    /* Increaser moves */
    public String increaseAttack(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for raiserCounter, cap at 6
        if (pokemon.getRaiserCount() >= 6) {
            battleText += pokemon.getName() + "'s ATTACK won't go higher!";
            return battleText;
        }
        // Raise Attack by stages
        pokemon.setAtk((int) (pokemon.getAtk() * raisers[stage]));
        battleText += pokemon.getName() + "'s ATTACK";
        battleText += raiseStageDesc(stage);
        // Increase raiserCount
        pokemon.setRaiserCount((pokemon.getRaiserCount() + 1));
        return battleText;
    }

    public String increaseSpAttack(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for raiserCount, cap at 6
        if (pokemon.getRaiserCount() >= 6) {
            battleText += pokemon.getName() + "'s SpATTACK won't go higher!";
            return battleText;
        }
        // Raise Special Attack by stages
        pokemon.setSpAtk((int) (pokemon.getSpAtk() * raisers[stage]));
        battleText += pokemon.getName() + "'s SpATTACK";
        battleText += raiseStageDesc(stage);
        // Increase raiserCount
        pokemon.setRaiserCount((pokemon.getRaiserCount() + 1));
        return battleText;
    }

    public String increaseDefense(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for raiserCount, cap at 6
        if (pokemon.getRaiserCount() >= 6) {
            battleText += pokemon.getName() + "'s DEFENSE won't go higher!";
            return battleText;
        }
        // Raise Defense by stages
        pokemon.setDef((int) (pokemon.getDef() * raisers[stage]));
        battleText += pokemon.getName() + "'s DEFENSE";
        battleText += raiseStageDesc(stage);
        // Increase raiserCount
        pokemon.setRaiserCount((pokemon.getRaiserCount() + 1));
        return battleText;
    }

    public String increaseSpDefense(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for raiserCount, cap at 6
        if (pokemon.getRaiserCount() >= 6) {
            battleText += pokemon.getName() + "'s SpDEFENSE won't go higher!";
            return battleText;
        }
        // Raise SpDefense by stages
        pokemon.setSpDef((int) (pokemon.getSpDef() * raisers[stage]));
        battleText += pokemon.getName() + "'s SpDEFENSE";
        battleText += raiseStageDesc(stage);
        // Increase raiserCount
        pokemon.setRaiserCount((pokemon.getRaiserCount() + 1));
        return battleText;
    }

    public String increaseSpeed(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for raiserCount, cap at 6
        if (pokemon.getRaiserCount() >= 6) {
            battleText += pokemon.getName() + "'s SPEED won't go higher!";
            return battleText;
        }
        // Raise Speed by stages
        pokemon.setSpeed((int) (pokemon.getSpeed() * raisers[stage]));
        battleText += pokemon.getName() + "'s SPEED";
        battleText += raiseStageDesc(stage);
        // Increase raiserCount
        pokemon.setRaiserCount((pokemon.getRaiserCount() + 1));
        return battleText;
    }

    /* Decreasing moves */
    public String decreaseAttack(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for reducerCount, cap at 6
        // reducerCount: [Atk, SpAtk, Def, SpDef, Spd, Acc]
        if (pokemon.getReducerCount(0) >= 6) {
            battleText += pokemon.getName() + "'s ATTACK won't go lower!";
            return battleText;
        }
        // Reduce Attack by stages
        pokemon.setAtk((int) (pokemon.getAtk() * reducers[stage]));
        battleText += pokemon.getName() + "'s ATTACK";
        battleText += reduceStageDesc(stage);
        // Increase reducerCount
        pokemon.setReducerCount(0, (pokemon.getReducerCount(0) + 1));
        return battleText;
    }

    public String decreaseSpAttack(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for reducerCount, cap at 6
        // reducerCount: [Atk, SpAtk, Def, SpDef, Spd, Acc]
        if (pokemon.getReducerCount(1) >= 6) {
            battleText += pokemon.getName() + "'s SpATTACK won't go lower!";
            return battleText;
        }
        // Reduce SpAttack by stages
        pokemon.setSpAtk((int) (pokemon.getSpAtk() * reducers[stage]));
        battleText += pokemon.getName() + "'s SpATTACK";
        battleText += reduceStageDesc(stage);
        // Increase reducerCount
        pokemon.setReducerCount(1, (pokemon.getReducerCount(1) + 1));
        return battleText;
    }

    public String decreaseDefense(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for reducerCount, cap at 6
        // reducerCount: [Atk, SpAtk, Def, SpDef, Spd, Acc]
        if (pokemon.getReducerCount(2) >= 6) {
            battleText += pokemon.getName() + "'s DEFENSE won't go lower!";
            return battleText;
        }
        // Reduce Defense by stages
        pokemon.setDef((int) (pokemon.getDef() * reducers[stage]));
        battleText += pokemon.getName() + "'s DEFENSE";
        battleText += reduceStageDesc(stage);
        // Increase reducerCount
        pokemon.setReducerCount(2, (pokemon.getReducerCount(2) + 1));
        return battleText;
    }

    public String decreaseSpeed(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for reducerCount, cap at 6
        // reducerCount: [Atk, SpAtk, Def, SpDef, Spd, Acc]
        if (pokemon.getReducerCount(4) >= 6) {
            battleText += pokemon.getName() + "'s SPEED won't go lower!";
            return battleText;
        }
        // Reduce Speed by stages
        pokemon.setSpeed((int) (pokemon.getSpeed() * reducers[stage]));
        battleText += pokemon.getName() + "'s SPEED";
        battleText += reduceStageDesc(stage);
        // Increase reducerCount
        pokemon.setReducerCount(4, (pokemon.getReducerCount(4) + 1));
        return battleText;
    }

    public String decreaseAccuracy(Player player, int playerPokemon, int stage) {
        Pokemon pokemon = player.getPokemon(playerPokemon);
        String battleText = "";
        // Check for reducerCount, cap at 6
        // reducerCount: [Atk, SpAtk, Def, SpDef, Spd, Acc]
        if (pokemon.getReducerCount(5) >= 6) {
            battleText += pokemon.getName() + "'s ACCURACY won't go lower!";
            return battleText;
        }
        // Reduce Accuracy of 4 moves by stages
        for (int i = 0; i < 4; i++) {
            pokemon.getMove(i).setAccuracy((int) (pokemon.getMove(i).getAccuracy() * reducers[stage]));
        }
        battleText += pokemon.getName() + "'s ACCURACY";
        battleText += reduceStageDesc(stage);
        // Increase reducerCount
        pokemon.setReducerCount(5, (pokemon.getReducerCount(5) + 1));
        return battleText;
    }

    // Stage description
    public String raiseStageDesc(int stage) {
        String str = "";
        if (stage == 1) {
            str += " rose!";
        } else if (stage == 2) {
            str += " sharply rose!";
        } else {
            str += " rose drastically!";
        }
        return str;
    }

    public String reduceStageDesc(int stage) {
        String str = "";
        if (stage == 1) {
            str = " fell!";
        } else if (stage == 2) {
            str = " harshly fell!";
        } else {
            str = " severely fell!";
        }
        return str;
    }
}
