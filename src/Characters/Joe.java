package src.Characters;

import src.GameGUI;
import src.PCharacter;

public class Joe extends PCharacter{
  private double health = 15;
  private double max_health = 15;

  public Joe(){
    health = 15;
    max_health = 15;
  }
    public String getCharacterName(){
      return("Joe");
    }
    

  public double getMaxHealth(){
    return max_health;
  }

  public void subtractHealth(double damage) {
      health -= damage;
    }

  public double getHealth(){
    return health;
  }
  
  public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
    other.takeDamage(1);
  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
    other.takeDamage(2);
  }

  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
    other.takeDamage(3);
  }

  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
    other.takeDamage(4);
  }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    other.takeDamage(5);
  }

  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
    other.takeDamage(6);
    GameGUI.addUltChargeTo(other); 
  }
}
