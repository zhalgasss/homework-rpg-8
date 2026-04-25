package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

public class BossFloor extends TowerFloor {

    private final String floorName;
    private Monster boss;

    public BossFloor(String floorName) {
        this.floorName = floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n=== " + getFloorName() + " ===");
        System.out.println("A spectral knight blocks the final staircase.");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] The air freezes as the boss draws its blade.");
        boss = new Monster("Spectral Knight", 38, 10);
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] The boss battle begins.");
        int totalDamage = 0;
        int round = 1;

        while (boss.isAlive() && hasLivingHeroes(party) && round <= 8) {
            System.out.println("Boss Round " + round);

            for (Hero hero : party) {
                if (!hero.isAlive()) {
                    continue;
                }

                hero.onTurnStart();
                if (hero.isAlive() && hero.canAct()) {
                    int damage = hero.attack();
                    boss.takeDamage(damage);
                    System.out.println(hero.getName() + " strikes the boss for " + damage
                            + " damage (boss HP: " + boss.getHp() + ")");
                    if (!boss.isAlive()) {
                        System.out.println("The Spectral Knight has fallen.");
                        hero.onTurnEnd();
                        break;
                    }
                }
                hero.onTurnEnd();
            }

            if (!boss.isAlive() || !hasLivingHeroes(party)) {
                break;
            }

            Hero target = weakestLivingHero(party);
            if (target != null) {
                int beforeHp = target.getHp();
                System.out.println("Spectral Knight attacks " + target.getName() + ".");
                boss.attack(target);
                totalDamage += beforeHp - target.getHp();
            }
            round++;
        }

        boolean cleared = !boss.isAlive() && hasLivingHeroes(party);
        String summary = cleared ? "The boss is defeated and the tower summit is reached."
                : "The boss stands undefeated.";
        System.out.println("[Challenge Result] " + summary);
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] The party claims the tower relic and restores some strength.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(5);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The cursed armor crumbles into dust.");
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

    private Hero weakestLivingHero(List<Hero> party) {
        Hero weakest = null;
        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            if (weakest == null || hero.getHp() < weakest.getHp()) {
                weakest = hero;
            }
        }
        return weakest;
    }
}
