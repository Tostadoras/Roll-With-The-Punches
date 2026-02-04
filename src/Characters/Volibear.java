package src.Characters;

import src.GameGUI;
import src.PCharacter;

public class Volibear extends PCharacter{
  public Volibear(){
    }
    public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
    System.out.println(this.getName() + " used his auto attack!");
    other.takeDamage(1);
  }
  public String getCharacterName(){
    return("Volibear");
  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
    System.out.println(this.getName() + " used his auto attack!");
    other.takeDamage(1);
  }

  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
    System.out.println(this.getName() + " used his Bite!");
    other.takeDamage(2);
    extraRoll();
    roll();
    if(roll_num < 4){
        other.takeDamage(2);
        heal(2);
    }
  }

  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
    System.out.println(this.getName() + " used his stun claw!");
    other.takeDamage(1);
    other.CC();
  }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    System.out.println(this.getName() + " started gathering a storm...");
    this.Channel();
  }

  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
    System.out.println(this.getName() + " used his Ultimate!");
    GameGUI.addUltChargeTo(other); 
    this.changeStack(1);
    this.changeMaxHealth(3);
    this.heal((3));
    if(other.moveFiveDisabled()){
        other.disableMoveSix();
        System.out.println(other.getName() + " can't roll a 6 anymore!");
    }
    if(other.moveFourDisabled()){
        other.disableMoveFive();
        System.out.println(other.getName() + " can't roll a 5 anymore!");
    }
    if(other.moveThreeDisabled()){
        other.disableMoveFour();
        System.out.println(other.getName() + " can't roll a 4 anymore!");
    }
    if(other.moveTwoDisabled()){
        other.disableMoveThree();
        System.out.println(other.getName() + " can't roll a 3 anymore!");
    }
    if(other.moveOneDisabled()){
        other.disableMoveTwo();
        System.out.println(other.getName() + " can't roll a 2 anymore!");
    }
    other.disableMoveOne();
    System.out.println(other.getName() + " can't roll a 1 anymore!");
  }
  
  public void moveSeven(PCharacter other) {
    System.out.println("Volibear's storm struck!");
    other.takeDamage(4);
    this.increaseShield(4);
  }
}
