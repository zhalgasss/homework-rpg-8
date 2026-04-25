package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {

    private int turnsLeft;

    public BerserkState(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (int) Math.ceil(basePower * 1.60));
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (int) Math.ceil(rawDamage * 1.25));
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " fights with berserk fury.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsLeft--;
        if (turnsLeft <= 0 && hero.isAlive()) {
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
