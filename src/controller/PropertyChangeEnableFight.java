package controller;

public interface PropertyChangeEnableFight extends PropertyChangeEnable {
    /** The character has died **/
    String DEATH = "death";
    /** An attack has been performed, (it may still be blocked) **/
    String ATTACK = "aStart";
    /** An attack missed (no chance of success) **/
    String ATTACK_MISS = "aMiss";
    /** Although the attack was not a miss, it was blocked! **/
    String ATTACK_BLOCK = "aBlock";
    /** The health of a character has been changed **/
    String HEALTH_CHANGED = "hChange";
    /** The character took damage */
    String TAKE_DAMAGE = "aDamaged";
}
