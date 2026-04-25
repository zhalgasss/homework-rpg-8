package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;

public class SanctuaryFloor extends TowerFloor {

    private final String floorName;

    public SanctuaryFloor(String floorName) {
        this.floorName = floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n--- Entering " + getFloorName() + " ---");
        System.out.println("A warm light fills the chamber.");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] The heroes lower their weapons and catch their breath.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] There is no enemy here, only recovery.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(6);
            }
        }
        String summary = "The party recovers in the sanctuary.";
        System.out.println("[Challenge Result] " + summary);
        return new FloorResult(true, 0, summary);
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] No loot is awarded in the sanctuary.");
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The blessing fades as the party leaves.");
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }
}
