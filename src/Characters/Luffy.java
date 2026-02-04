package src.Characters;

import src.GameGUI;
import src.PCharacter;

public class Luffy extends PCharacter{
  public Luffy(){
    }
    public String getCharacterName(){
      return("Luffy");
    }

  @Override
  public void autoAttack(PCharacter other){
    System.out.println(this.getName() + " attacked!");
    other.takeDamage(2 + this.getStack());
  }
  public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
    System.out.println(this.getName() + " used Gum-Gum Pistol!");
    other.takeDamage(1);
  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
    System.out.println(this.getName() + " used Gum-Gum Pistol!");
    other.takeDamage(1);
  }
  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
    System.out.println(this.getName() +" used Gum-Gum Gatling!");
    int gatlingShots = 1;
    int evilShots = 1;
    other.takeDamage(2 + this.getStack());
    while (evilShots != 0){
      this.extraRoll();
      this.roll();
      if (getRoll() <= 3) {
      other.takeDamage(1 + this.getStack());
        gatlingShots++;
      System.out.println("Another hit landed!");
      }
      else {
        System.out.println("The gatling ended!");
        evilShots= 0;
      }
    }
    System.out.println(this.getName() + " landed " + gatlingShots + " gatling shots!");

  }
  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
    System.out.println(this.getName() + " used Gum-Gum scythe!");
    other.takeDamage(3 + this.getStack());
  }
  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    System.out.println(this.getName() + " used Gum-Gum balloon!");
    this.block();
  }
  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
    System.out.println(this.getName() + " geared up!");
    this.changeStack(1);
    this.changeDamage(1);
    System.out.println(this.getName() + " is currently gear " + (int)this.getStack() + "!");
    System.out.println("All attacks and abilities do +" + this.getStack() + " more damage!");
    GameGUI.addUltChargeTo(other); 
  }
}