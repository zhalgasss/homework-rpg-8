package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.StunnedState;
import java.util.List;

public class TrapFloor extends TowerFloor {

    private final String floorName;

    public TrapFloor(String floorName) {
        this.floorName = floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] Loose tiles and dart holes cover the hallway.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] The trap corridor activates.");
        int totalDamage = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }

            int beforeHp = hero.getHp();
            System.out.println(hero.getName() + " is struck by hidden darts.");
            hero.takeDamage(5);
            totalDamage += beforeHp - hero.getHp();

            if (hero.isAlive() && hero.getState() instanceof NormalState) {
                hero.setState(new StunnedState(1));
                System.out.println(hero.getName() + " is shocked by the trap mechanism.");
                break;
            }
        }

        boolean cleared = hasLivingHeroes(party);
        String summary = cleared ? "The party crosses the trap corridor." : "The party falls in the trap hall.";
        System.out.println("[Challenge Result] " + summary);
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] A hidden cache contains an old potion.");
        Hero target = firstLivingHero(party);
        if (target != null) {
            target.heal(3);
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The dart launchers run out of ammo.");
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Hero firstLivingHero(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return hero;
            }
        }
        return null;
    }
}
