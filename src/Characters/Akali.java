package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter;

public class Akali extends PCharacter {

    private boolean passiveReady = false;

    public Akali() {
        super();
    }

    public String getCharacterName() {
        return "Akali";
    }
    @Override
    public void resetExtra() {
        if (canDecay()) {
            if (passiveReady) {
                System.out.println("Assassin's Mark expired because it wasn't used.");
                passiveReady = false;
            }
        }

        super.resetExtra(); 

        setCanDecay(true);
    }

    @Override
    public void autoAttack(PCharacter other) {
        if (passiveReady) {
            int bonusDamage = 4; 
            System.out.println(this.getName() + " triggers Assassin's Mark! (Spinning Kama)");
            other.takeDamage(bonusDamage);
            
            passiveReady = false; 
            System.out.println("The mark is consumed.");
        } else {
            System.out.println(this.getName() + " strikes with a Kunai.");
            other.takeDamage(2);
        }
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println("Akali swings her kama!");
        other.takeDamage(1);
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println("Akali swings her kama!");
        other.takeDamage(1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " throws Five Point Strike (Q)!");
        
        other.takeDamage(3);
        
        if(!(other.getDodge())){
            other.setSlow(2);
            System.out.println("The Kunais crippled the enemy! They are SLOWED by 2.");
        if(!(other.getBlock())){
            passiveReady = true;
            setCanDecay(false); 
            System.out.println("Assassin's Mark active!");
        }
    }
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " drops a smoke bomb (Twilight Shroud)!");
        
        this.dodge();
        System.out.println(this.getName() + " is obscured in the smoke (DODGING)!");
        
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " marks the enemy with Shuriken Flip!");
        System.out.println("Akali flips backward to safety!");
        if(!(other.getDodge()) && !(other.getBlock())){
            other.takeDamage(1);
            passiveReady = true;
            setCanDecay(false);
            System.out.println("Assassin's Mark active!");
        }
        String[] options = {"Stay Back (Dodge)", "Dash In (Burst)"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "How do you want to use your E?",
            "Akali Shuriken Flip",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice == 0) {
            this.dodge();
        } else {
            System.out.println("Akali takes the E2 Recast and dashes in!");
            other.takeDamage(2);
        }
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " leaps over the enemy! (Perfect Execution)");
        
        GameGUI.addUltChargeTo(other); 

        double initialDamage = 2;
        other.takeDamage(initialDamage);

        double missingHealth = other.getMaxHealth() - other.getHealth();
        double executeDamage = 2 + (missingHealth * 0.2);
        
        System.out.println("Akali dashes back through for the execute!");
        System.out.println("Execute Damage: " + (int)executeDamage);
        
        other.takeDamage((int)executeDamage);
        if(!(other.getBlock())&&!(other.getDodge())){
            passiveReady = true;
            setCanDecay(false);
            System.out.println("Assassin's Mark active!");
        }
    }
}