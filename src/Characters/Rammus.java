package src.Characters;

import src.GameGUI;
import src.PCharacter;

public class Rammus extends PCharacter{
  public Rammus(){
  }
  public String getCharacterName(){
    return("Rammus");
  }
  public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
    this.autoAttack(other);
  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
    this.autoAttack(other);
  }

  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
    System.out.println(this.getName() + " used his Q!");
    if(other.isChanneling() == true){
      other.endChannel();
      System.out.println("It interrupted the channel!");
    }
    other.takeDamage(2);
    
  }

  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
    System.out.println(this.getName() + " used his E!");
    System.out.println(this.getName() + " taunted " + other.getName() + "!");
    other.autoAttack(this);
    other.CC();
  }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    System.out.println(this.getName() + "'s W passive will do more damage now!");
    this.changeStack(1);
    System.out.println(this.getName() + " now does " + this.getStack() + " damage when damaged!");
    
  }

  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
    System.out.println(this.getName() + " used his ult!");
    System.out.println(this.getName() + " jumps up!");
    GameGUI.addUltChargeTo(other); 
    this.Channel();
  }


  public void moveSeven(PCharacter other) {
    System.out.println(this.getName()+ " landed!");
    other.takeDamage(3);
    other.CC();
  }


  public void takeDamage(double damage) {
    int intDamage = (int)damage;
    if(this.getBlock() == false && this.getDodge() == false) {
    if(this.getShield()>0) {
      double temp = this.getShield();
      this.changeShield(intDamage);
      if(getShield() <= 0) {
        System.out.println("The shield has been broken!");
        this.subtractHealth(intDamage-temp);
        System.out.println(this.getName() + " took " + (intDamage-temp) + " damage!");
      }
      else {
        System.out.println(this.getName()+ "'s shield took " + intDamage + " damage!");
        System.out.println(this.getName() + "'s shield has " + this.getShield() + " shield remaining!");
      }
    }
    else if(this.getShield()<= 0) {
      this.subtractHealth(intDamage);
      System.out.println(this.getName() + " took " + intDamage + " damage!");
    }
  this.getOpponent().takeDamage(this.getStack());
  if (this.getHealth() <= 0){
    this.setDead(true);
  }
  }
    else if(this.getBlock() == true) {
      System.out.println(this.getName() + " blocked it! ");
    }
    else if(this.getDodge() == true) {
      System.out.println(this.getName() + " dodged it! ");
    }
  }
}