public class Veigar extends PCharacter{
    public Veigar(){
        
    }
    public String getCharacterName(){
        return("Veigar");
    }
    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        this.autoAttack(other);
      }
    
      public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        this.autoAttack(other);
      }
    
      public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " used his W!");
        other.takeDamage(4+this.getStack());
      }
    
      public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " used his E!");
        other.CC();
      }
    
      public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " has harnessed his evil!");
        this.changeStack(1);
        System.out.println(this.getName() + "'s damaging abilities all do " + this.getStack() + " more damage!");
        
      }
      
      public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " used his Ult!");
        GameGUI.addUltChargeTo(other); 
        if(other.getHealth() <= (other.getMaxHealth()/2)){
            System.out.println("It's going to deal double damage!");
            other.takeDamage(2*(2+this.getStack()));
        }
        else{
            other.takeDamage(2+this.getStack());
        }
      }
    
      public void moveSeven(PCharacter other) {
        System.out.println("This is a placeholder. If you are seeing this, something is wrong with the channeling ability code");
      }
}
