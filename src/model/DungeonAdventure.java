package model;

/**
 * Creates the dungeon with a difficulty and map.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class DungeonAdventure {
    /**
     * Saves Dungeon info
     */
    private final Dungeon myDungeon;

    /**
     * Public constructor for DungeonAdventure
     */
    public DungeonAdventure() {
        myDungeon = new Dungeon();
    }

    /**
     * Creates a new game given a difficulty
     * @param theDifficulty String difficulty name //TODO enums
     */
    public void newGame(String theDifficulty) { //TODO Do we want this to just deal with just difficulty or do we want to pass it with a default map?
//        Maze map = new Maze(4, 5, 5);  //TODO implement maze
//        newGame(theDifficulty, map);
    }

    /**
     * Creates a new game given a difficulty and map
     * @param theDifficulty String difficulty name
     * @param theMap Maze that makes up the map
     */
    public void newGame(String theDifficulty, Maze theMap) {
//        if(theDifficulty.equals("Easy")) {
//            TODO Fill in
//        } else if(theDifficulty.equals("Normal")) {
//
//        } else if(theDifficulty.equals("Hard")) {
//
//        } else {
//            throw new IllegalArgumentException("Error: Not a difficulty type");
//        }
    }

    /**
     * Get the Dungeon
     * @return the Dungeon
     */
    public Dungeon getMyDungeon() {
        return myDungeon;
    }
}