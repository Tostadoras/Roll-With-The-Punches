package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter; 

public class Yami extends PCharacter {
    
    public Yami() {
        super();
    }

    public String getCharacterName() {
        return "Yami";
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " drew an Exodia card!");
        this.changeStack(1);
        System.out.println(this.getName() + " has " + this.getStack() + " Exodia cards!");
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " drew an Exodia card!");
        this.changeStack(1);
        System.out.println(this.getName() + " has " + this.getStack() + " Exodia cards!");
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " summoned Dark Magician");
        int magicianShots = 1;
        int megaEvilShots = 1;
        other.takeDamage(3);
        while (megaEvilShots != 0) {
            this.extraRoll();
            this.roll();
            if (getRoll() == 4 || getRoll() == 5) {
                other.takeDamage(3);
                magicianShots++;
                System.out.println("The Dark Magician landed another hit!");
            } else {
                System.out.println("The Dark Magician's barrage ended!");
                megaEvilShots = 0;
            }
        }
        System.out.println(this.getName() + "'s Dark Magician landed " + magicianShots + " magic shots!");
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " summoned Kuriboh!");
        this.block();
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " summoned Pot of Greed, which allows them to draw 2 cards from their deck!");
        int drawShots = 0;
        int megaEvilShots = 1;
        while (megaEvilShots < 3) {
            this.extraRoll();
            this.roll();
            if (getRoll() == 1 || getRoll() == 2) {
                drawShots++;
                this.changeStack(1);
                System.out.println(this.getName() + " drew an Exodia card!");
            }
            megaEvilShots++;
        }
        System.out.println(this.getName() + " drew " + drawShots + " Exodia cards!");
        System.out.println(this.getName() + " has " + this.getStack() + " Exodia cards!");
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        
        String[] options = {"Yes", "No"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "Do you want to use your Exodia cards?",
            "Yami's Choice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0] 
        );

        // choice 0 = Yes
        // choice 1 = No (or closed window)

        if (choice == 1 || choice == -1) { 
            System.out.println(this.getName() + " decided to not use their Exodia cards");
        } 
        else if (choice == 0) {
            GameGUI.addUltChargeTo(other); 
            
            if (getStack() >= 5) {
                System.out.println("EXODIA: OBLITERATE!!!");
                other.setHealth(-658008);
                other.setDead(true);
            }
            else { // Stack < 5
                System.out.println(this.getName() + " consumed all their Exodia cards");
                double epicDamage = (this.getStack() * 4);
                other.takeDamage(epicDamage);
                this.zeroStack();
            }
        }
    }
}