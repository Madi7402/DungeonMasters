package model;

public enum SpecialSkill {
        /**
         * Special Skill for the Warrior Hero Class.
         */
        CRUSHING_BLOW(40),
        /**
         * Special Skill for the Priestess Hero Class.
         */
        HEAL(100),
        /**
         * Special Skill for the Thief Hero class.
         */
        SURPRISE_ATTACK(40),
        /**
         * Placeholder value when no Special Skill is defined.
         */
        NONE(0);

        /**
         * Percentage chance of success as a double (e.g. 40.0 is a 40% chance)
         */
        private final double myChance; // chance special attack is successful

        /**
         * Construct a SpecialSkill with the predefined chance.
         * @param theChance the chance of success as a double (e.g. 40.0 is a 40% chance
         */
        SpecialSkill(final double theChance) {
                this.myChance = theChance;
        }

        /**
         * Get the chance of the special skill succeeding.
         * @return a double representing the percentage chance of success.
         */
        public double getChance() {
                return myChance;
        }
}
