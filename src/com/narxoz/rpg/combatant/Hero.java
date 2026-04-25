package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;

/**
 * Represents a player-controlled hero participating in the tower climb.
 */
public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;

    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense, HeroState initialState) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = initialState;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public HeroState getState() {
        return state;
    }

    public void setState(HeroState newState) {
        System.out.println(name + " is now " + newState.getName() + "!");
        this.state = newState;
    }

    /**
     * Attack value modified by current state
     */
    public int attack() {
        return state.modifyOutgoingDamage(attackPower);
    }

    /**
     * Reduces HP with state modifier
     */
    public void takeDamage(int amount) {
        int modifiedDamage = state.modifyIncomingDamage(amount);
        hp = Math.max(0, hp - modifiedDamage);
        System.out.println(name + " takes " + modifiedDamage + " damage (HP: " + hp + ")");
    }

    /**
     * Heal HP
     */
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " heals " + amount + " HP (HP: " + hp + ")");
    }

    /**
     * Called at start of turn
     */
    public void onTurnStart() {
        state.onTurnStart(this);
    }

    /**
     * Called at end of turn
     */
    public void onTurnEnd() {
        state.onTurnEnd(this);
    }

    /**
     * Can hero act this turn
     */
    public boolean canAct() {
        return state.canAct();
    }
}