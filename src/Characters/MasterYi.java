package src.Characters;
import javax.swing.JOptionPane;

import src.GameGUI;
import src.PCharacter;

public class MasterYi extends PCharacter {

    private int strikeCounter = 0;
    
    private boolean wujuActive = false;
    
    private boolean slowImmune = false;

    public MasterYi() {
        super();
    }

    public String getCharacterName() {
        return "Master Yi";
    }

    @Override
    public double getStack(){
        return strikeCounter;
    }
    @Override
    public void resetExtra() {
        if (canDecay()) {
            if (wujuActive) {
                System.out.println("Wuju Style effect has faded.");
                wujuActive = false;
            }
            if (slowImmune) {
                System.out.println("Highlander immunity has faded.");
                slowImmune = false;
            }
        }

        if (slowImmune && this.getSlow()>0) {
            System.out.println("Highlander cleanses all slows!");
            this.setSlow(0);
        }

        super.resetExtra(); 

        setCanDecay(true);
    }

    @Override
    public void autoAttack(PCharacter other) {
        strikeCounter++;
        System.out.println(this.getName() + " performs a Basic Attack technique!");
        
        int damage = 2; 

        if (wujuActive) {
            damage += 2;
            System.out.println("Wuju Style empowers the strike! (+2 Damage)");
        }

        other.takeDamage(damage);

        if (strikeCounter >= 3) {
            System.out.println("DOUBLE STRIKE! Master Yi strikes again instantly!");
            other.takeDamage(damage); 
            strikeCounter = 0; 
        }
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println(this.getName() + " pokes with his sword.");
        other.takeDamage(1); 
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println(this.getName() + " slashes quickly.");
        other.takeDamage(1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " casts Alpha Strike!");
        
        this.dodge();
        System.out.println("Master Yi vanishes into the spirit realm (DODGING)!");
        
        other.takeDamage(2);
        
        strikeCounter++;
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " prepares to Meditate!");

        String[] options = {"Deep Meditation (Heal)", "Quick Parry (Block)"};

        int choice = JOptionPane.showOptionDialog(
            null,
            "How do you want to Meditate?",
            "Master Yi - Meditate (W)",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            System.out.println("Master Yi channels deeply...");
            int healAmt = 3;
            this.heal(healAmt);
            System.out.println("Master Yi healed for " + healAmt + " HP!");
            
            strikeCounter += 2;
            System.out.println("Deep Meditation focuses the mind. (+2 Double Strike Stacks)");
            
            this.Channel(); 
        } else {
            System.out.println("Master Yi readies his sword for a parry!");
            this.block();
            
            strikeCounter++;
            System.out.println("Ready to counter-attack. (+1 Double Strike Stack)");
        }
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " activates Wuju Style!");
        
        System.out.println("His blade glows! Next turn's Basic Attacks will deal +2 Damage.");
        
        wujuActive = true;
        setCanDecay(false); 
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println("HIGHLANDER! Master Yi moves with unmatched speed!");

        GameGUI.addUltChargeTo(other);

        // if (this.getSlow() > 0) {
        //     System.out.println("Master Yi cannot be slowed! (Slows removed)");
        //     this.setSlow(0);
        // }
        
        slowImmune = true;

        this.setSpeed(2);
        System.out.println("Master Yi gains +3 Speed for the next turn!");
        
        setCanDecay(false);

        System.out.println("He strikes wildly during his ultimate!");
        this.autoAttack(other);
        this.autoAttack(other); 
    }
}