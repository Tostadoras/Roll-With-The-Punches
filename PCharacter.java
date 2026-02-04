import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.*;

public class PCharacter {
  private PCharacter opponent;
  private String name;
  private double health;
  private double max_health;
  private double base_damage;
  private double stack;
  private boolean is_dodging;
  private boolean is_blocking;
  public int roll_num = 1;
  private boolean is_channeling;
  private boolean dead = false;
  private boolean oneDisabled;
  private boolean twoDisabled;
  private boolean threeDisabled;
  private boolean fourDisabled;
  private boolean fiveDisabled;
  private boolean sixDisabled;
  private double shield = 0;
  private boolean isCCd = false;
  
  // --- Speed and Slow Variables ---
  private int speed_buff = 0;
  private int slow_debuff = 0;
  
  private boolean statChangedThisTurn = false;
  
  private boolean canDecay = false;

  public int turn_number = 1;

  Scanner sc = new Scanner(System.in);

  Random rand = new Random();

  public PCharacter getOpponent(){
    return opponent;
  }
  public void setOpponent(PCharacter opp){
    opponent = opp;
  }
  public String getCharacterName(){
    return "This shouldn't be here!";
  }
  public void rollForMove(PCharacter other){
    this.roll();
    if (roll_num == 1){
      if (moveOneDisabled() == false){
      moveOne(other);
      }
      else{
        System.out.println("You rolled a 1, but that move is disabled");
      }
    }
    else if (roll_num == 2){
      if (moveTwoDisabled() == false){
      moveTwo(other);
      }
      else{
        System.out.println("You rolled a 2, but that move is disabled");
      }
    }
    else if (roll_num == 3){
      if (moveThreeDisabled() == false){
      moveThree(other);
      }
      else{
        System.out.println("You rolled a 3, but that move is disabled");
      }
    }
    else if (roll_num == 4){
      if (moveFourDisabled() == false){
      moveFour(other);
      }
      else{
        System.out.println("You rolled a 4, but that move is disabled");
      }
    }
    else if (roll_num == 5){
      if (moveFiveDisabled() == false){
      moveFive(other);
      }
      else{
        System.out.println("You rolled a 5, but that move is disabled");
      }
    }
    else if (roll_num == 6){
      if (moveSixDisabled() == false){
      moveSix(other);
      }
      else{
        System.out.println("You rolled a 6, but that move is disabled");
      }
    }
  }
  public int getRoll() {
    return roll_num;
  }

  public boolean isDead() {
        return dead;
    }
  public boolean checkCC(){
    return isCCd;
  }
  public void JUSTCC(){
    isCCd = true;
  }
  public double getMaxHealth(){
    return max_health;
  }
  public void CC(){
  if (this.getDodge() == false) {
    this.endChannel();
    isCCd = true;
    System.out.println(this.getName() + " got CC'd!");
  }
  else {
    System.out.println(this.getName() + " didn't get CC'd!");
  }
  }
  public void cleanseCC(){
    isCCd = false;
  }
  public void takeDamage(double damage) {
    int intDamage = (int)damage;
    if(is_blocking == false && is_dodging == false) {
    if(this.getShield()>0) {
      double temp = this.getShield();
      this.changeShield(intDamage);
      if(getShield() <= 0) {
        System.out.println("The shield has been broken!");
        this.subtractHealth(intDamage-temp);
        System.out.println(this.getName() + " took " + (intDamage-temp) + " damage!");
      }
      else {
        System.out.println(this.getName()+ "'s shield took " + intDamage + " damage!");
        System.out.println(this.getName() + "'s shield has " + this.getShield() + " shield remaining!");
      }
    }
    else if(this.getShield()<= 0) {
      this.subtractHealth(intDamage);
      System.out.println(this.getName() + " took " + intDamage + " damage!");
    }
  if (this.getHealth() <= 0){
    this.setDead(true);
  }
  }
    else if(is_blocking == true) {
      System.out.println(this.getName() + " blocked it! ");
    }
    else if(is_dodging == true) {
      System.out.println(this.getName() + " dodged it! ");
    }
  }

    public boolean moveOneDisabled(){
      return oneDisabled;
    }
    public void disableMoveOne(){
      oneDisabled = true;
    }
    public void enableMoveOne(){
      oneDisabled = false; 
    }

    public boolean moveTwoDisabled(){
      return twoDisabled;
    }
    public void disableMoveTwo(){
      twoDisabled = true;
    }
    public void enableMoveTwo(){
      twoDisabled = false; 
    }

    public boolean moveThreeDisabled(){
      return threeDisabled;
    }
    public void disableMoveThree(){
      threeDisabled = true;
    }
    public void enableMoveThree(){
      threeDisabled = false; 
    }

    public boolean moveFourDisabled(){
      return fourDisabled;
    }
    public void disableMoveFour(){
      fourDisabled = true;
    }
    public void enableMoveFour(){
      fourDisabled = false; 
    }

    public boolean moveFiveDisabled(){
      return fiveDisabled;
    }
    public void disableMoveFive(){
      fiveDisabled = true;
    }
    public void enableMoveFive(){
      fiveDisabled = false; 
    }

    public boolean moveSixDisabled(){
      return sixDisabled;
    }
    public void disableMoveSix(){
      sixDisabled = true;
    }
    public void enableMoveSix(){
      sixDisabled = false; 
    }

    public void subtractHealth(double damage) {
      health -= damage;
    }
    public void heal(double healAmount) {
      health += healAmount;
      if (health > max_health){
        health = max_health;
      }
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
    public void changeMaxHealth(double max_health){
      this.max_health += max_health;
    }
    public double getDamage() {
        return base_damage;
    }
    public void autoAttack(PCharacter other) {
      System.out.println(this.getName() + " attacked!");
      other.takeDamage(2);
    }

    public void changeDamage(double damage) {
        this.base_damage += damage;
    }
    public boolean isChanneling(){
      return is_channeling;
    }
    public void Channel(){
      is_channeling = true;
    }
    public void endChannel(){
      is_channeling = false;
    }
    public double getStack() {
      return stack;
    }
    public void zeroStack() {
      stack = 0;
    }
    public void turnDecrease(int decrease) {
      turn_number -= decrease;
    }
    public void changeStack(double increase) {
      stack += increase;
    }
    public void block() {
      is_blocking = true;
    }
    public boolean getBlock() {
      return is_dodging; 
    }
    public void endBlock() {
      is_blocking = false;
    }
    public void dodge() {
      is_dodging = true;
    }
    public boolean getDodge() {
      return is_dodging;
    }
    public void endDodge() {
      is_dodging = false;
    }
    
    // The reset logic
    public void resetExtra() {
      this.endDodge();
      this.endBlock();
      this.zeroShield();
      this.canDecay = false; 
    }
    
    public int getTurn() {
      return turn_number;
    }

    public void turnIncrease() {
      turn_number++;
      
      if (statChangedThisTurn) {
          statChangedThisTurn = false;
      } else {
          if (this.speed_buff > 0) System.out.println("Speed buff expired.");
          if (this.slow_debuff > 0) System.out.println("Slow debuff expired.");
          
          this.speed_buff = 0;
          this.slow_debuff = 0;
      }
    }

    public double getShield() {
      return shield;
    }
    public void increaseShield(double increase) {
      shield += increase;
    }
    public void changeShield(double damage) {
      shield -= damage;
    }
    public void zeroShield() {
      shield = 0;
    }
    
    // --- Speed/Slow Methods ---
    public void setSpeed(int amount) {
      if(amount > speed_buff){
        this.speed_buff = amount;
        this.statChangedThisTurn = true; 
      }
    }
    public void setSlow(int amount) {
      if(amount > slow_debuff){
        this.slow_debuff = amount;
        this.statChangedThisTurn = true;
      }
    }
    public int getSpeed() {
        return speed_buff;
    }
    public int getSlow() {
        return slow_debuff;
    }
    
    public boolean canDecay() {
        return canDecay;
    }
    public void setCanDecay(boolean decay) {
        this.canDecay = decay;
    }

  public PCharacter() {
      health = 20;
      max_health = 20;
      base_damage = 1;
      stack = 0;
      is_dodging = false;
      is_blocking = false;
      roll_num = 1;
    dead = false;
  }
  public PCharacter(String name) {
    this.name = name;
    health = 20;
    max_health = 20;
    base_damage = 1;
    stack = 0;
    is_dodging = false;
    is_blocking = false;
    dead = false;
    roll_num = 1;
  }

  // --- ROLL LOGIC ---
  public void roll() {
    int baseRoll = rand.nextInt(6) + 1; 
    int finalRoll = baseRoll + speed_buff - slow_debuff;
    
    if (finalRoll > 6) finalRoll = 6;
    if (finalRoll < 1) finalRoll = 1;
    
    roll_num = finalRoll;
    
    if (speed_buff > 0) System.out.println("(Buffed by Speed +" + speed_buff + ")");
    if (slow_debuff > 0) System.out.println("(Reduced by Slow -" + slow_debuff + ")");
  }

  public void extraRoll() {
        JButton bigRollButton = new JButton("CLICK TO ROLL!");
        bigRollButton.setFont(new Font("Dialog", Font.BOLD, 48));
        bigRollButton.setPreferredSize(new Dimension(500, 200));
        bigRollButton.setBackground(new Color(204, 0, 0));
        bigRollButton.setForeground(Color.YELLOW);
        bigRollButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        bigRollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bigRollButton.setOpaque(true);
        bigRollButton.setContentAreaFilled(true);
        bigRollButton.setFocusPainted(false); 

        JOptionPane pane = new JOptionPane(
            bigRollButton,              
            JOptionPane.PLAIN_MESSAGE,  
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{},             
            null
        );

        JDialog dialog = pane.createDialog(null, "Extra Roll Opportunity!");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        bigRollButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

  public void turn(PCharacter other) {
  if (this.isDead() == false){
  if (this.isChanneling() == false && this.checkCC() == false){
    this.resetExtra();
  System.out.println("");
  System.out.println("");
  System.out.println("Round #" + getTurn());
  System.out.println("It's " + name + "'s turn!");
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

  public void moveOne(PCharacter other) {
    System.out.println(this.getName() + " rolled a 1!");
  }

  public void moveTwo(PCharacter other) {
    System.out.println(this.getName() + " rolled a 2!");
  }

  public void moveThree(PCharacter other) {
    System.out.println(this.getName() + " rolled a 3!");
  }

  public void moveFour(PCharacter other) {
    System.out.println(this.getName() + " rolled a 4!");
  }

  public void moveFive(PCharacter other) {
    System.out.println(this.getName() + " rolled a 5!");
  }

  public void moveSix(PCharacter other) {
    System.out.println(this.getName() + " rolled a 6!");
  }
  
  public void moveSeven(PCharacter other) {
    System.out.println("This is a placeholder. If you are seeing this, something is wrong with the channeling ability code");
  }
}