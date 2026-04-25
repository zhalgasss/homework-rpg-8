package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonedState;
import java.util.ArrayList;
import java.util.List;

public class BattleFloor extends TowerFloor {

    private final String floorName;
    private final List<Monster> monsters = new ArrayList<>();

    public BattleFloor(String floorName) {
        this.floorName = floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] Venomous monsters crawl out of the shadows.");
        monsters.clear();
        monsters.add(new Monster("Venom Spider", 18, 7));
        monsters.add(new Monster("Tower Rat", 14, 6));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] Combat begins.");
        int totalDamage = 0;
        int round = 1;

        while (hasLivingHeroes(party) && hasLivingMonsters() && round <= 6) {
            System.out.println("Round " + round);

            for (Hero hero : party) {
                if (!hero.isAlive()) {
                    continue;
                }

                hero.onTurnStart();
                if (hero.isAlive() && hero.canAct()) {
                    Monster target = firstLivingMonster();
                    if (target != null) {
                        int damage = hero.attack();
                        target.takeDamage(damage);
                        System.out.println(hero.getName() + " hits " + target.getName() + " for " + damage
                                + " damage (monster HP: " + target.getHp() + ")");
                        if (!target.isAlive()) {
                            System.out.println(target.getName() + " is defeated.");
                        }
                    }
                }
                hero.onTurnEnd();
            }

            if (!hasLivingMonsters() || !hasLivingHeroes(party)) {
                break;
            }

            for (Monster monster : monsters) {
                if (!monster.isAlive()) {
                    continue;
                }
                Hero target = firstLivingHero(party);
                if (target == null) {
                    break;
                }
                int beforeHp = target.getHp();
                System.out.println(monster.getName() + " attacks " + target.getName() + ".");
                monster.attack(target);
                totalDamage += beforeHp - target.getHp();

                if (monster.getName().equals("Venom Spider")
                        && target.isAlive()
                        && target.getState() instanceof NormalState) {
                    target.setState(new PoisonedState(2, 3));
                }
            }
            round++;
        }

        boolean cleared = hasLivingHeroes(party) && !hasLivingMonsters();
        String summary = cleared ? "The party survived the ambush." : "The party was overwhelmed in battle.";
        System.out.println("[Challenge Result] " + summary);
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] The party finds healing herbs.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(4);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The corridor falls silent again.");
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    private boolean hasLivingMonsters() {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Monster firstLivingMonster() {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return monster;
            }
        }
        return null;
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
