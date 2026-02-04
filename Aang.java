import javax.swing.JOptionPane;

public class Aang extends PCharacter {

    public Aang() {
        super();
    }

    public String getCharacterName() {
        return "Aang";
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println("Aang creates a small breeze.");
        other.takeDamage(1);
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println("Aang pokes with his glider.");
        other.takeDamage(1);
    }

    @Override
    public void autoAttack(PCharacter other) {
        System.out.println(this.getName() + " performs an Airbending slice!");
        other.takeDamage(2);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println("Aang hops on his Air Scooter!");

        this.dodge();
        System.out.println("Aang is moving too fast to hit! (DODGING)");

        this.setSpeed(2);
        System.out.println("Yip Yip! Speed increased by 2.");
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println("Aang switches elements...");

        String[] options = {"Water (Heal)", "Earth (Block)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "Which element do you need?",
            "Aang - Bending Arts (W)",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            System.out.println("Aang draws water from the surroundings to heal.");
            int healAmt = 3;
            this.heal(healAmt);
            System.out.println("Restored " + healAmt + " Health.");
        } else {
            System.out.println("Aang kicks up a rock wall!");
            this.block();
            System.out.println("Aang is BLOCKING.");
        }
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println("Aang channels Zuko's training... Fire Kick!");
        
        other.takeDamage(4);
        
        System.out.println("The flames singe the enemy!");
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println("Aang's tattoos begin to glow...");
        

        GameGUI.addUltChargeTo(other); 

        System.out.println("The four elements surround Aang in a protective sphere!");
        int shieldAmt = 5;
        this.increaseShield(shieldAmt);
        System.out.println("Gained " + shieldAmt + " Shield!");

        System.out.println("Aang unleashes the power of the Avatar!");
        other.takeDamage(4);

        this.setSpeed(2);
        System.out.println("Aang rises into the air! (+2 Speed next turn)");
    }
} 
    
