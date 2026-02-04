import javax.swing.JOptionPane;

public class Zed extends PCharacter {

    private boolean shadowActive = false;
    private boolean markActive = false;
    private double damageStored = 0;

    public Zed() {
        super();
    }

    public String getCharacterName() {
        return "Zed";
    }

    private void inflictDamage(PCharacter target, double amount) {
        double hpBefore = target.getHealth();
        double shieldBefore = target.getShield();

        target.takeDamage(amount);

        double hpAfter = target.getHealth();
        double shieldAfter = target.getShield();

        double actualDamageDealt = (hpBefore - hpAfter) + (shieldBefore - shieldAfter);

        if (markActive) {
            if (actualDamageDealt > 0) {
                damageStored += actualDamageDealt;
                System.out.println("[Death Mark stored: " + (int)actualDamageDealt + "]");
            } else {
                System.out.println("[Death Mark: Attack was Blocked/Dodged. 0 Damage stored.]");
            }
        }
    }

    @Override
    public void resetExtra() {
        if (canDecay()) {
            if (markActive) {
                System.out.println("\n--- DEATH MARK DETONATION ---");
                double popDamage = damageStored * 0.3; 
                
                if (popDamage < 1 && damageStored > 0){
                    popDamage = 1; 
                }
                System.out.println("The mark POPS dealing " + (int)popDamage + " echo damage!");
                if (this.getOpponent() != null) {
                    this.getOpponent().takeDamage((int)popDamage);
                }
                markActive = false;
                damageStored = 0;
            }
            if (shadowActive) {
                System.out.println("The Living Shadow fades away.");
                shadowActive = false;
            }
        }
        super.resetExtra(); 
        setCanDecay(true);
    }

    @Override
    public void autoAttack(PCharacter other) {
        int damage = 2; 

        if (other.getHealth() <= (other.getMaxHealth() / 2)) {
            damage += 1;
            System.out.println("Passive: Contempt for the Weak triggers! (Bonus Damage)");
        } else {
            System.out.println(this.getName() + " strikes with his wrist blade.");
        }

        inflictDamage(other, damage);
    }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        System.out.println("Zed strikes with his blades");
        inflictDamage(other, 1);
    }

    public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        System.out.println("Zed strikes with his blades");
        inflictDamage(other, 1);
    }

    public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " throws a Razor Shuriken (Q)!");
        
        int damage = 3;
        inflictDamage(other, damage);
        if (shadowActive) {
            System.out.println("The Living Shadow mimics the shuriken! (Double Hit)");
            inflictDamage(other, damage);
        }
        
        
    }

    public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " prepares Living Shadow (W)!");

        String[] options = {"Swap (Dodge)", "Cast (Offensive Setup)"};

        int choice = JOptionPane.showOptionDialog(
            null, "How do you want to use your Shadow?", "Zed - Living Shadow",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]
        );

        if (choice == 0) {
            System.out.println("Zed swaps places with his shadow to evade!");
            this.dodge();
        } else {
            System.out.println("Zed casts his shadow forward!");
            System.out.println("Next ability (Q or E) will be mirrored by the shadow!");
            shadowActive = true;
            setCanDecay(false); 
        }
    }

    public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " spins in a Shadow Slash (E)!");
        
        int damage = 2;
        int slowAmt = 1;

        if (shadowActive) {
            System.out.println("The Living Shadow mimics the slash!");
            damage += 2; 
            slowAmt += 1;
        }
        
        inflictDamage(other, damage);
        other.setSlow(slowAmt);
        System.out.println("Target is Slowed by " + slowAmt + ".");
    }

    public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println("DEATH MARK! Zed vanishes into the shadows...");
        GameGUI.addUltChargeTo(other); 
        this.dodge();
        System.out.println("Zed becomes untargetable (DODGE)!");
        System.out.println("SPLORT! The Death Mark applies.");
        markActive = true; 
        damageStored = 0;
        System.out.println("The Mark is ticking... Damage dealt next turn will echo!");
        shadowActive = true;
        setCanDecay(false);
    }
}