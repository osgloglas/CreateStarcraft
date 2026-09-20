package com.souls.starcraft.spell;

import java.util.List;

public class SpellInterpreter {
    public static SpellResult interpret(List<ConstellationType> components) {
        double range = 1.0;
        double damage = 1.0;
        boolean projectile = false;

        for (ConstellationType component : components) {
            switch (component) {
                case RANGE -> range += 1.0;

                case DAMAGE -> damage += 1.0;

                case PROJECTILE -> projectile = true;
            }
        }

        return new SpellResult(range, damage, projectile);
    }

    public record SpellResult(double range, double damage, boolean projectile) {}
}
