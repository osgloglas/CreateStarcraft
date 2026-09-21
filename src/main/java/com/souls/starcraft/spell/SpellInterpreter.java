package com.souls.starcraft.spell;

import java.util.ArrayList;
import java.util.List;

public class SpellInterpreter {
    public static SpellResult interpret(List<ConstellationType> components) {
        double range = 1.0;
        double damage = 1.0;
        boolean projectile = false;
        boolean flight = false;
        int durationTicks = 20 * 10;
        int speedLevel = 0;
        boolean lightSource = false;
        int maxHealthLevels = 0;
        List<SpellOperation> operations = new ArrayList<>();
        boolean invisibility = false;
        int strengthLevel = 0;
        int absorptionLevel = 0;
        int reachLevel = 0;

        for (ConstellationType component : components) {
            operations.add(new SpellOperation(component));

            switch (component) {
                case FLIGHT -> flight = true;
                case SPEED -> speedLevel++;
                case LIGHT_SOURCE -> lightSource = true;
                case MAX_HEALTH -> maxHealthLevels++;
                case AOE -> {}
                case RANGE -> range += 1.0;
                case DAMAGE -> damage += 1.0;
                case INVISIBILITY -> invisibility = true;
                case PROJECTILE -> projectile = true;
                case STRENGTH -> strengthLevel++;
                case TELEPORTATION -> {}
                case BOMBS -> {}
                case ABSORPTION -> absorptionLevel++;
                case REACH -> reachLevel++;
            }
        }

        return new SpellResult(flight, speedLevel, lightSource, maxHealthLevels, range, damage, invisibility, projectile, durationTicks, 
            operations, strengthLevel, absorptionLevel, reachLevel);
    }

    public record SpellResult(
        boolean flight,
        int speedLevel,
        boolean lightSource,
        int maxHealthLevels,
        double range, 
        double damage,
        boolean invisibility,
        boolean projectile,
        int durationTicks,
        List<SpellOperation> operations,
        int strengthLevel,
        int absorptionLevel,
        int reachLevel
    ) {}

    public static int getProjectileAoeLevel(List<SpellOperation> operations) {
        boolean foundProjectile = false;
        int aoeLevel = 0;

        for (SpellOperation operation : operations) {
            if (operation.type() == ConstellationType.PROJECTILE) {
                foundProjectile = true;
                continue;
            }

            if (foundProjectile && operation.type() == ConstellationType.AOE) {
                aoeLevel++;
            }
        }

        return aoeLevel;
    }

    public static double getTeleportDistance(List<SpellOperation> operations) {
        double distance = 5.0;
        int rangeBeforeTeleport = 0;

        for (SpellOperation operation : operations) {
            if (operation.type() == ConstellationType.RANGE) {
                rangeBeforeTeleport++;
            }

            if (operation.type() == ConstellationType.TELEPORTATION) {
                return distance + (rangeBeforeTeleport * 5.0);
            }
        }

        return 0.0;
    }

    public static int getProjectileBombCount(List<SpellOperation> operations) {
        boolean foundProjectile = false;
        int bombs = 0;

        for (SpellOperation operation : operations) {
            if (operation.type() == ConstellationType.PROJECTILE) {
                foundProjectile = true;
                continue;
            }

            if (foundProjectile && operation.type() == ConstellationType.BOMBS) {
                bombs++;
            }
        }

        return bombs;
    }
}
