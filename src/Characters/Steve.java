package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter;

public class Steve extends PCharacter {

    public Steve() {
        super();
    }

    public String getCharacterName() {
        return "Steve";
    }

    // --- HELPER: Get gear tier based on Stacks ---
    private String getGearName() {
        int res = (int)this.getStack();
        if (res < 2) return "Wood";
        if (res < 4) return "Stone";
        if (res < 7) return "Iron";
        return "Diamond";
    }

    @Override
    public void autoAttack(PCharacter other) {
        System.out.println(this.getName() + " attacks with a " + getGearName() + " Sword!");
        double damage = 2;
        damage += (this.getStack() * 0.5); 
        other.takeDamage(damage);
        this.changeStack(1);
        System.out.println("Steve mined resources! Current Stacks: " + (int)this.getStack());
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println("Steve punches the tree... err, enemy.");
        other.takeDamage(1);
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println("Steve punches the enemy.");
        other.takeDamage(1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " gets hungry...");

        String[] options = {"Steak (Big Heal)", "Golden Apple (Shield+Heal)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "What do you want to eat?",
            "Steve - Inventory",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            // Steak: Heals
            System.out.println("*Burp* Steve eats a Cooked Porkchop.");
            this.heal(3);
            System.out.println("Healed for 3 HP!");
        } else {
            // Gapple: Heals + shieldds
            System.out.println("Steve eats a Golden Apple!");
            this.heal(2);
            this.increaseShield(3); // Absorption hearts
            System.out.println("Healed 2 HP and gained 3 Absorption (Shield)!");
        }
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " draws his Bow fully back.");
        other.takeDamage(3);
        other.setSlow(2);
        System.out.println("CRIT! The arrow slows the enemy by 2!");
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " checks his hotbar.");

        String[] options = {"Shield (Block)", "Potion of Swiftness (Speed)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "Use Off-hand item:",
            "Steve - Utility",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            // Shield
            System.out.println("Steve raises his Shield!");
            this.block();
        } else {
            // Speed pot
            System.out.println("Steve splashes a Potion of Swiftness!");
            this.setSpeed(3); 
            System.out.println("Speed increased by 3!");
        }
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println("ACHIEVEMENT GET: Cover Me in Debris!");
        GameGUI.addUltChargeTo(other); 
        System.out.println("Steve equips full Netherite Armor!");
        this.changeMaxHealth(3); 
        this.heal(3);
        this.increaseShield(2); 
        System.out.println("CRITICAL HIT with Sharpness V Axe!");
        double damage = 3 + (this.getStack() * 0.5);
        other.takeDamage(damage);
        this.changeStack(2); 
        System.out.println("Steve found diamonds in the loot! (+2 Stacks)");
    }
}