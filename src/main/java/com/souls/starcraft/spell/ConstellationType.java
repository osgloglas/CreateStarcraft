package com.souls.starcraft.spell;

public enum ConstellationType {
    //type, then cost
    FLIGHT (10),
    SPEED (15),
    LIGHT_SOURCE (5),
    MAX_HEALTH (20),
    RANGE (7),
    DAMAGE (10),
    AOE (20),
    INVISIBILITY (5),
    PROJECTILE (10),
    STRENGTH (10),
    TELEPORTATION (20),
    BOMBS (25),
    ABSORPTION (20),
    REACH (10);

    private final int manaCost;

    ConstellationType(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getManaCost() {
        return manaCost;
    }
}
