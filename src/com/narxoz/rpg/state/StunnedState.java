package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {

    private int turnsLeft;

    public StunnedState(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, basePower / 2);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (int) Math.ceil(rawDamage * 1.10));
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is stunned and cannot act.");
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
        return false;
    }
}
