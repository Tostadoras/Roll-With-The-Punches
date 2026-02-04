package src.Characters;

import src.GameGUI;
import src.PCharacter;

public class Junkrat extends PCharacter{
  public Junkrat(){
    }
    public String getCharacterName(){
      return("Junkrat");
    }
    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " shot a bomb!");
        other.takeDamage(1);
      }
    
      public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " shot a bomb!");
        other.takeDamage(1);
      }
    
      public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " threw out his blast bomb!");
        other.takeDamage(3);
        extraRoll();
        roll();
        if(getRoll()==3 || getRoll()==4){
            System.out.println(this.getName() + " threw out another one!");
            other.takeDamage(roll_num);
            this.dodge();
          } 
         }
      public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " threw out his blast bomb!");
        other.takeDamage(2);
        extraRoll();
        roll();
        if(getRoll()==3 || getRoll()==4){
            System.out.println(this.getName() + " threw out another one!");
            other.takeDamage(roll_num);
            this.dodge();
          } 
         }
    
      public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " threw out his bear trap!");
        other.CC();
      }
    
      public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " began rolling his ultimate!");
        GameGUI.addUltChargeTo(other); 
        Channel();
      }
      
      public void moveSeven(PCharacter other) {
        System.out.println(this.getName() + "'s ultimate landed!");
        other.takeDamage(9);
      }
}
