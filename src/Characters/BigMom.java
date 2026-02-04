package src.Characters;
import javax.swing.JOptionPane;

import src.PCharacter;

public class BigMom extends PCharacter {

    public BigMom() {
        super();
    }

    public String getCharacterName() {
        return "Big Mom";
    }

    @Override
    public void autoAttack(PCharacter other){
        System.out.println(this.getName() + " attacked!");
      other.takeDamage(2 + this.getStack());
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " swings out Napoleon!");
        other.takeDamage(1 + this.getStack());
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " swings out Napoleon!");
        other.takeDamage(1 + this.getStack());
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " called for Zeus!");
        other.takeDamage(1 + this.getStack());
        other.CC();
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " called for Prometheus!");
        other.takeDamage(4 + this.getStack());
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        moveSeven(other);
        this.Channel();
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " is trying to steal some of " + other.getName() + "'s soul!");
        
        if (other.getDodge() == false && other.getBlock() == false) {
            System.out.println(this.getName() + " it worked!");
            other.takeDamage(3);

            String[] options = {"Feed Self (Max HP)", "Feed Homies (Stack)"};

            int choice = JOptionPane.showOptionDialog(
                null,
                "You stole a soul! Who do you want to feed?",
                "Big Mom's Soul Pocus",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            // choice 0 = Feed Self
            // choice 1 = Feed Homies (or closed window)

            if (choice == 0) {
                this.changeMaxHealth(3);
                this.subtractHealth(-3);
                System.out.println("You consume the portion of soul you stole, gaining 3 max health");
            } else {
                System.out.println("You feed the portion of soul you stole to your homies");
                this.changeStack(1);
                System.out.println("All attacks and abilities do " + this.getStack() + " more damage!");
            }
        } else {
            System.out.println(this.getName() + " couldn't steal a portion of " + other.getName() + "'s soul!");
        }
    }

    public void moveSeven(PCharacter other) {
        System.out.println(this.getName() + " is craving wedding cake!");
        extraRoll();
        if (getRoll() >= 5) {
            System.out.println(this.getName() + " swings out Napoleon in hungry rage!");
            other.takeDamage((2 + this.getStack()));
            this.Channel();
        } else {
            System.out.println(this.getName() + " found some wedding cake to eat!");
            this.endChannel();
        }
    }
}