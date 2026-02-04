import javax.swing.JOptionPane;

public class Smolder extends PCharacter {

    public Smolder() {
        super();
    }

    public String getCharacterName() {
        return "Smolder";
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " spits a small fireball!");
        other.takeDamage(1);
        
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " scratches the enemy!");
        other.takeDamage(1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " uses Super Scorcher Breath (Q)!");

        double damage = 3;
        
        double bonusDamage = this.getStack() / 2; 
        
        if (this.getStack() > 5) {
            System.out.println("The breath explodes in a massive radius!");
            bonusDamage += 2;
        }

        System.out.println("The fireball burns with " + (int)this.getStack() + " stacks of power!");
        other.takeDamage((int)(damage + bonusDamage));
        
        this.changeStack(1);
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " sneezes fiery goo! (Achoo!)");
        
        other.takeDamage(2);
        this.changeStack(1);
        System.out.println("Dragon Practice! Stacks: " + (int)this.getStack());
        other.setSlow(2);
        System.out.println("The goo slows the enemy by 2!");
        
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " takes flight (Flap, Flap, Flap)!");

        String[] options = {"Fly Fast (Speed Up)", "Fly High (Dodge)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "How do you want to fly?",
            "Smolder E",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            System.out.println("Smolder zooms over the terrain!");
            other.takeDamage(1);
            this.changeStack(1);
            System.out.println("Dragon Practice! Stacks: " + (int)this.getStack());
            this.setSpeed(2);
        } else {
            System.out.println("Smolder flies high out of reach!");
            this.dodge();
            other.takeDamage(1);
            this.changeStack(1);
            System.out.println("Dragon Practice! Stacks: " + (int)this.getStack());
        }
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " yells: 'MMOOOMMMM!'");

        GameGUI.addUltChargeTo(other); 

        int damage = 3;
        if (this.getStack() > 5) {
            damage += 3;
            System.out.println("Mom is angry! (Bonus Damage)");
        }
        other.takeDamage(damage);
        this.changeStack(1);
        System.out.println("Dragon Practice! Stacks: " + (int)this.getStack());

        int healAmt = 3;
        this.heal(healAmt);
        System.out.println("Smolder feels safer near Mom and heals for " + healAmt + "!");
    }
}