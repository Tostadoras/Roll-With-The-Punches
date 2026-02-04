public class Ezreal extends PCharacter{
  public Ezreal(){
    }
    public String getCharacterName(){
      return("Ezreal");
    }
    private boolean w_up = false;
    public boolean check_w(){
      return w_up;
    }
    public void w_false(){
      w_up = false;
    }
    public void w_true(){
      w_up = true;
    }
    public void CC(){
        if (this.getDodge() == false) {
        JUSTCC();
        System.out.println(this.getName() + " got CC'd!");
        this.w_false();
        }
        else {
            System.out.println(this.getName() + " didn't get CC'd!");
        }
      }
    public void w_pop(PCharacter other){
      if(this.check_w()){
        System.out.println(this.getName() + "'s W popped!");
        other.takeDamage(3);
      this.w_false();
    }
  }

    public void autoAttack(PCharacter other) {
        other.takeDamage(2);
        if(this.check_w()){
            this.w_pop(other);
        }
      }

    public void moveOne(PCharacter other) {
        System.out.println(this.getName() + " rolled a 1!");
        other.takeDamage(1);
        if(this.check_w()){
            this.w_pop(other);
        }
      }
    
      public void moveTwo(PCharacter other) {
        System.out.println(this.getName() + " rolled a 2!");
        other.takeDamage(1);
        if(this.check_w()){
            this.w_pop(other);
        }
      }
    
      public void moveThree(PCharacter other) {
        System.out.println(this.getName() + " rolled a 3!");
        System.out.println(this.getName() + " used his Q!");
        other.takeDamage(1);
        this.w_pop(other);
        if(other.getDodge() == false){
            extraRoll();
            rollForMove(other);
        }
      }
    
      public void moveFour(PCharacter other) {
        System.out.println(this.getName() + " rolled a 4!");
        System.out.println(this.getName() + " threw out his W!");
        if(other.getDodge() == false){
            System.out.println("It landed!");
            w_true();
        }
      }
    
      public void moveFive(PCharacter other) {
        System.out.println(this.getName() + " rolled a 5!");
        System.out.println(this.getName() + " used his E!");
        other.takeDamage(1);
        this.w_pop(other);
        System.out.println(this.getName() + " dashed away!");
        this.dodge();
      }
    
      public void moveSix(PCharacter other) {
        System.out.println(this.getName() + " rolled a 6!");
        System.out.println(this.getName() + " threw out his ult!");
        GameGUI.addUltChargeTo(other); 
        Channel();
      }
      
      public void moveSeven(PCharacter other) {
        System.out.println(this.getName() + "'s ult finally reached " + other.getName() + "!");
        other.takeDamage(7);
        this.w_pop(other);
      }




      public void turn(PCharacter other) {
        if (this.isDead() == false){
        if (this.isChanneling() == false && this.checkCC() == false){
          this.resetExtra();
        System.out.println("");
        System.out.println("");
        System.out.println("Round #" + getTurn());
        System.out.println("It's " + this.getName() + "'s turn!");
        System.out.println("You have " + this.getHealth() + " health remaining!");
        System.out.println(other.getName()+ " has " + other.getHealth() + " health ");
        if(other.getShield() > 0) {
          System.out.print("and " + other.getShield() + " shield ");
        }
        System.out.print("remaining!");
          System.out.println("Do you want to roll(1) or attack(2)?" );
          String choice = sc.nextLine();
          if (choice.equals("1")) {
            this.rollForMove(other);
            }
          else if(choice.equals("2")) {
            this.autoAttack(other);
          }
          else {
            System.out.println("Please type 1 or 2");
            this.turn(other);
          }
          this.turnIncrease();
          other.turn(this);
        }
      else if(this.isChanneling()==true){
        this.moveSeven(other);
        this.endChannel();
        other.turn(this);
      }
      else if(this.checkCC()==true){
        other.turnDecrease(1);
        this.cleanseCC();
        other.turn(this);
        
      }
      }
          else{
            System.out.println(this.getName() + " is dead!");
            System.out.println("Game over! " + other.getName() + " wins!");
            System.exit(0);
          }
      
        }
}
