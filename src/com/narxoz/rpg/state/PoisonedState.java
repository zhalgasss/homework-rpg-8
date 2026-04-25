package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {

    private int turnsLeft;
    private final int poisonDamage;

    public PoisonedState(int turnsLeft, int poisonDamage) {
        this.turnsLeft = turnsLeft;
        this.poisonDamage = poisonDamage;
    }

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (int) Math.floor(basePower * 0.75));
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (int) Math.ceil(rawDamage * 1.20));
    }

    @Override
    public void onTurnStart(Hero hero) {
        if (!hero.isAlive()) {
            return;
        }
        System.out.println(hero.getName() + " suffers poison damage.");
        hero.takeDamage(poisonDamage);
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
