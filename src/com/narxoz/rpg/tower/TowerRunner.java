package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            FloorResult result = floor.explore(party);
            System.out.println("Floor summary: " + result.getSummary());
            System.out.println("Damage taken on this floor: " + result.getDamageTaken());

            if (!result.isCleared()) {
                break;
            }

            floorsCleared++;
            if (!hasLivingHeroes(party)) {
                break;
            }
        }

        int heroesSurviving = countLivingHeroes(party);
        boolean reachedTop = floorsCleared == floors.size() && heroesSurviving > 0;
        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        return countLivingHeroes(party) > 0;
    }

    private int countLivingHeroes(List<Hero> party) {
        int alive = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                alive++;
            }
        }
        return alive;
    }
}
