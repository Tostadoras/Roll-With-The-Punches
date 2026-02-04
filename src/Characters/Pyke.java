package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter;

public class Pyke extends PCharacter {
    
    public Pyke() {
        super(); 
    }

    public String getCharacterName() {
        return "Pyke";
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " used his auto attack!");
        other.takeDamage(1);
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " used his auto attack!");
        other.takeDamage(1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " used his Q!");

        String[] options = {"Charge Up", "Instant Use"};

        int choice = JOptionPane.showOptionDialog(
            null,                               
            "Do you want to charge it up or instantly use it?",
            "Pyke Ability Choice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        
        if (choice == 1) { 
            // User clicked use instantly
            other.takeDamage(2);
        } else {
            // User clicked charge it up
            this.Channel();
            System.out.println(this.getName() + " is charging up his hook!");
        }
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " camouflaged and hid!");
        dodge();
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " used his E!");
        other.takeDamage(1);
        other.CC();
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " used his ultimate!");
        
        GameGUI.addUltChargeTo(other); 
        
        if (other.getHealth() > 7) {
            other.takeDamage(2);
        } else if (other.getHealth() <= 7) {
            System.out.println("\"Rest now...\"");
            other.setHealth(-999); // Just like in League!
            other.setDead(true);
        }
    }

    public void moveSeven(PCharacter other) {
        System.out.println(this.getName() + " throws out his charged-up hook!");
        other.takeDamage(2);
        other.CC();
    }
}