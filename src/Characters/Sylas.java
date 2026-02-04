package src.Characters;

import src.PCharacter;

public class Sylas extends PCharacter{

// I ended up never making Sylas because the most realistic way to
// Create his ultimate would be to simply create a switch statement for every other
// Character's ultimate (and then that could involve odd scenarios with stacking).
// It would have ended up with him feeling very weak against characters with specific
// Ultimates like Yami, unless I created a change in how it worked JUST FOR SYLAS

public void autoAttack(PCharacter other) {
      System.out.println(this.getName() + " auto attacked!");
      other.takeDamage(2);
      for(int i = 0; i < this.getStack(); i++){
        System.out.println("and auto attacked!");
        other.takeDamage(2);
        this.changeStack(-1);
      }
}

public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
    other.takeDamage(1);

  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
    other.takeDamage(1);
  }

  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
    this.changeStack(1);
    other.takeDamage(3);

  }

  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
    System.out.println("Sylas used his W!");
    other.takeDamage(1);



  }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    this.dodge();
    
  }

  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
    if (other instanceof Sylas) {
            System.out.println("HIJACK: You cannot steal from another Sylas!");
            return; // End the move early
    }
    System.out.println("Sylas Hijacked " + other.getName() + "'s ultimate!");
    if(other instanceof Sylas){
        //TODO finish sylas
    }
  }
  
  public void moveSeven(PCharacter other) {
    System.out.println("This is a placeholder. If you are seeing this, something is wrong with the channeling ability code");
  }
}
