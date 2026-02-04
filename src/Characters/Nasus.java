package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter;

public class Nasus extends PCharacter{
    public String getCharacterName(){
        return("Nasus");
    }
    public void autoAttack(PCharacter other) {
      System.out.println(this.getName() + " attacked with an empowered attack!");
      other.takeDamage(2 + this.getStack());
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
        System.out.println(this.getName() + " prepares Siphoning Strike (Q)!");
        String[] options = {"Farm Minion (+Stack)", "Attack Enemy (Damage)"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Do you want to Stack or Attack?",
            "Nasus Q Choice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            // Player chose farming minions
            System.out.println("Nasus farmed a minion with his Q!");
            this.changeStack(1); // Increase stack by 1
            System.out.println("Stacks increased! Empowered autos now do +" + (int)this.getStack() + " damage.");
        } else {
            // Player chose to attack the enemy
            double qDamage = 3 + this.getStack();
            
            System.out.println("Nasus brings the cane down on " + other.getName() + "!");
            other.takeDamage(qDamage);
        }
    }

  public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " prepares Siphoning Strike (Q)!");

        String[] options = {"Farm Minion (+Stack)", "Attack Enemy (Damage)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "Do you want to Stack or Attack?",
            "Nasus Q Choice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            System.out.println("Nasus farmed a minion with his Q!");
            this.changeStack(1); // Increase stack by 1
            System.out.println("Stacks increased! Empowered autos now do +" + (int)this.getStack() + " damage.");
        } else {
            double qDamage = 3 + this.getStack();
            
            System.out.println("Nasus brings the cane down on " + other.getName() + "!");
            other.takeDamage(qDamage);
        }
    }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
    System.out.println("Nasus uses his spirit fire!");
    System.out.println("The Spirit Fire lingers, melting the enemy's defenses!");
            // Removes shield
            if (other.getShield() > 0) {
                System.out.println("Spirit Fire melted " + (int)other.getShield() + " points of shielding!");
                other.zeroShield();
            } else {
                System.out.println("The enemy had no shields to melt!");
            }
            other.takeDamage(2); 
        }
  

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println("SHURIMA! Nasus enacts the Fury of the Sands and grows giant!");

        GameGUI.addUltChargeTo(other); 

        int bonusHealth = 3;
        this.changeMaxHealth(bonusHealth);
        this.heal(bonusHealth);
        System.out.println("Nasus gains " + bonusHealth + " Max Health and heals!");

        int stormDamage = 1;
        other.takeDamage(stormDamage);
        System.out.println("The sandstorm around Nasus burns " + other.getName() + " for " + stormDamage + " damage.");

        String[] options = {"Rapid Siphoning (+1 Stacks)", "Wither (Slow Enemy 3)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose your Ascended power:",
            "Fury of the Sands (R)",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            // Increase stack
            this.changeStack(1);
            System.out.println("Nasus gained 1 Stacks instantly! His stack is now: " + (int)this.getStack());
        } else {
            // Slows enemy
            System.out.println("Nasus casts Wither! " + other.getName() + " ages rapidly and is slowed by 3.");
            other.setSlow(3); 
        }
    }
  
  public void moveSeven(PCharacter other) {
    System.out.println("This is a placeholder. If you are seeing this, something is wrong with the channeling ability code");
  }
}
