import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Final extends PApplet {


//install sound under Sketch > import library > add library

byte controller; //Used to control which state the game is in (eg. intro, ending)
int starterTime; //Stores the exact time that the game begins, after the intro
MushroomGood[] mushroomsG; //Array of "Good Mushrooms"
MushroomBad[] mushroomsB; //Array of "Bad Mushrooms"
int counter; //Used in "Falling_Mushroom" tab to control the flow of the game
int counterG; //Stores how many "Good Mushrooms" the player collected
int counterB; //Stores how many "Bad Mushrooms" the player collected

public void setup() {
  //Initialize the playable screen, white background and no mouse cursor present
  
  noCursor();
  background (255);

  //Initializes the variables used in the game
  counter = 0;
  counterG = 0;
  counterB = 0;
  controller = 0;
  starterTime = -1;
  mushroomsG = new MushroomGood[30];
  mushroomsB = new MushroomBad [5];

  //Creates both the background music and the sound when gathering mushrooms, as seen on "sounds" tab
  createGatheringSound();
  createBackgroundMusic();

  //Creates the images used on the game, as seen in relevant tabs
  createIntro();
  createTree();
  createMushroom();

  //Creates the "Basket" object, used to catch mushrooms
  createBasket();
}
public void draw() {
  //Draws the intro on screen with images created at setup
  if (controller == 0) {
    background(255);
    image (grandmaSpeech, width*0.18f, 0, width*0.83f, height*0.72f);
    image (grandmaIntro, -20, height/1.6f, width/2.5f, height/2.5f);
    image (instructions, 300, height*0.8f);

    //Draws the main game phase on screen
  } else if (controller == 1) {

    //Draws the background trees
    background (255);
    image (tree, width*(-0.15f), height*(-0.11112f), width-(width*0.375f), height+(height*0.11112f));
    image (tree, width*0.525f, height*(-0.11112f), width-(width*0.375f), height+(height*0.11112f));
    image (tree, width*0.2f, height*(-0.11112f), width-(width*0.375f), height+(height*0.11112f));

    /*Draws each mushroom on screen, if they have not been collected or are out
     of the playable screen. One loop for each array (good and bad mushrooms)*/
    for (int i=0; i<mushroomsB.length; i++) {
      if (mushroomsB[i].getcollected() == false && mushroomsB[i].getypos() < height) {
        image(mushroomsB[i].gettipo(), mushroomsB[i].getxpos(), mushroomsB[i].getypos(), 30, 30);
      }
    }
    for (int i=0; i<mushroomsG.length; i++) {
      if (mushroomsG[i].getcollected() == false && mushroomsG[i].getypos() < height) {
        image(mushroomsG[i].gettipo(), mushroomsG[i].getxpos(), mushroomsG[i].getypos(), 30, 30);
      }
    }
    //Runs the function seen on "Falling_Mushroom" tab
    fallingMush();

    //Draws the basket on its position, relative to the mouse position
    drawBasket();

    //Draws the ending screen
  } else if (controller == 2) {
    /*Less than 20 mushrooms total collected and less than 3 bad mushrooms
     collected, "not enough mushrooms ending" is created*/
    if (((counterG + counterB) < 20) && (counterB < 3)) {
      createEnding(0);
    }
    //Gathered more than 3 bad mushrooms, "crazy ending" created
    else if (counterB >= 3) {
      createEnding(1);
    }
    //Gathered enough mushrooms and less than 3 bad mushrooms, "good ending created"
    else if ((counterB < 3) && ((counterG + counterB) >= 20)) {
      createEnding(2);
    }
    //Draws the relevant ending created on last step
    background (255);
    image (grandmaFinalSpeech, width*0.18f, 0, width*0.83f, height*0.72f);
    image (ending, -20, height/1.6f, width/2.5f, height/2.5f);
    image (endInstructions, 300, height*0.8f);
  }
}

public void keyPressed() {
  //Listens for the "ENTER" key to be pressed at appropriate times to initialize the game
  if (key == ENTER && controller ==0) {
    controller = 1;
    starterTime = millis();
    background.play();
  }

  if (key == ENTER && controller == 2) {
    controller = 0;
    setup();
  }
}
PImage tree;
public void createTree (){
  tree = loadImage ("tree.png");
}
// Load tree images
//create the basket image and it's size
Basket basket;
class Basket {
  int larg;
  int comp;
  int xpos;
  int ypos;
  PImage cesto;
  Basket () {
    larg = 100;
    comp = 100;
    xpos = 0;
    ypos = 720;
    cesto = loadImage ("basket.png");
  }
  public int getxpos () {
    return this.xpos;
  }
  public int getypos() {
    return this.ypos;
  }
  public void setxpos(int x) {
    this.xpos = x;
  }
  public void setypos(int y) {
    this.ypos = y;
  }
  public int getlarg () {
    return this.larg;
  }
  public int getcomp () {
    return this.comp;
  }
  public PImage getcesto() {
    return this.cesto;
  }
}

public void createBasket() {
  basket = new Basket();
}

public void drawBasket() { 
  imageMode(CENTER);
  basket.setxpos(mouseX);
  basket.setypos(height-(PApplet.parseInt(basket.getlarg()/3.3f)));
  image (basket.getcesto(), basket.getxpos(), basket.getypos(), basket.getcomp(), basket.getlarg());
  imageMode(CORNER);
}
//ending conditions
PImage ending;
PImage grandmaFinalSpeech;
PImage endInstructions;
public void createEnding(int a){
  if (a == 0){
    ending = loadImage ("grandmaSad.jpg");
    grandmaFinalSpeech = loadImage ("grandmaSpeechNotEnough.jpg"); //if not enough mushrooms were taken
    endInstructions = loadImage ("endInstructions.jpg");
  }
  
  if (a == 1){
    ending = loadImage ("grandmaCrazy.jpg");
    grandmaFinalSpeech = loadImage ("grandmaSpeechCrazy.jpg"); //if too many green mushrooms were taken
    endInstructions = loadImage ("endInstructions.jpg");
  }
  
  if (a == 2){
    ending = loadImage ("grandmaIntro.jpg");
    grandmaFinalSpeech = loadImage ("grandmaSpeechGood.jpg"); // if enough red mushrooms were taken
    endInstructions = loadImage ("endInstructions.jpg");
  }
}
public void fallingMush() {
/*Sets a mushroom to start falling if its "fallTime" variable
is less than the time elapsed since the main game phase started*/
    if (counter < 35) {
       for (int i=0; i< mushroomsG.length; i++) {
         if ((mushroomsG[i].getfallTime() <= (millis() - starterTime)) && (mushroomsG[i].getfall() == false)){
           mushroomsG[i].setfall(true);
           counter++;
         }
       }
     for (int i=0; i< mushroomsB.length; i++) {
       if ((mushroomsB[i].getfallTime() <= (millis() - starterTime)) && (mushroomsB[i].getfall() == false)){
         mushroomsB[i].setfall(true);
         counter++;
       }
     }
     //if all 35 mushrooms started falling already, the counter keeps going up to 220 to leave time for the last mushroom to finish falling before proceeding to the ending screen.
    }else if (counter < 220){
        counter++;
      }else{ //stops the music and enters the ending screen
        background.stop();
        controller = 2;
      }


  for (int i=0; i< mushroomsG.length; i++) { //check if the position of a mushroom that can be collected is the same as the basket, in which case the gathering sound is played and the mushroom is "collected"
      if ((mushroomsG[i].getypos() > (basket.getypos() - (basket.getlarg()/3.3f))) && ((mushroomsG[i].getxpos() >= (basket.getxpos() - (basket.getcomp()/2))) && (mushroomsG[i].getxpos() <= (basket.getxpos() + (basket.getcomp()/2)) - 30)) && (mushroomsG[i].getcanCollect() == true)) {
        mushroomsG[i].setcollected(true);
        mushroomsG[i].setcanCollect(false);
        counterG++;
        gathering.play();
      }
    if (mushroomsG[i].getfall() == true && mushroomsG[i].getcollected() == false) {
      mushroomsG[i].setypos((mushroomsG[i].getypos())+5);
      if (mushroomsG[i].getypos() > (basket.getypos() - basket.getlarg()/4)){
        mushroomsG[i].setcanCollect(false);
      }
    }
  }
  for (int i=0; i< mushroomsB.length; i++) {
    if ((mushroomsB[i].getypos() > (basket.getypos() - (basket.getlarg()/3.3f))) && ((mushroomsB[i].getxpos() >= (basket.getxpos() - (basket.getcomp()/2))) && (mushroomsB[i].getxpos() <= (basket.getxpos() + (basket.getcomp()/2)) - 30)) && (mushroomsB[i].getcanCollect() == true)) {
        mushroomsB[i].setcollected(true);
        mushroomsB[i].setcanCollect(false);
        counterB++;
        gathering.play();
      }
    if (mushroomsB[i].getfall() == true && mushroomsB[i].getcollected() == false) {
      mushroomsB[i].setypos((mushroomsB[i].getypos())+5);
      if (mushroomsB[i].getypos() > (basket.getypos() - basket.getlarg()/4)){
        mushroomsB[i].setcanCollect(false);
      }
    }
  }
}
//images change names in the file, make sure these names are the same ones in the files (capital letters)

PImage grandmaIntro;
PImage grandmaSpeech;
PImage instructions;
public void createIntro (){
  grandmaIntro = loadImage ("grandmaIntro.jpg");
  grandmaSpeech = loadImage ("grandmaSpeech.jpg");
  instructions = loadImage ("instructions.jpg");
}
class MushroomGood { //red Mushrooms
  int xpos;
  int ypos;
  boolean fall;
  boolean collected;
  boolean canCollect;
  int fallTime;
  PImage tipo;
  MushroomGood(int fallT) {
    xpos = (int) random (1, width-30);                        //A mushroom position
    ypos = (int) random (height*0.05f, height/2.7f);              
    fall = false; // if has ever started falling
    canCollect = true; //before being collected or off screen
    collected = false;
    fallTime = fallT; //the time in which it will fall according to when the main game started
    tipo = loadImage ("bom.png");
  }
  public int getfallTime() {
    return this.fallTime;
  }

  public void setfallTime(int t) {
    this.fallTime = t;
  }

  public int getxpos () {
    return this.xpos;
  }
  public boolean getcanCollect() {
    return this.canCollect;
  }
  public void setcanCollect(boolean cc) {
    this.canCollect = cc;
  }
  public boolean getfall() {
    return this.fall;
  }
  public boolean getcollected() {
    return this.collected;
  }
  public void setcollected(boolean c) {
    this. collected = c;
  }
  public void setfall(boolean a) {
    this.fall = a;
  }
  public int getypos () {
    return this.ypos;
  }
  public PImage gettipo() {
    return this.tipo;
  }
  public void setxpos(int a) {
    this.xpos = a;
  }
  public void setypos (int b) {
    this.ypos = b;
  }
}
class MushroomBad { //green mushrooms
  int xpos;
  int ypos;
  boolean fall;
  boolean collected;
  boolean canCollect;
  int fallTime;
  PImage tipo;
  MushroomBad(int fallT) {
    xpos = (int) random (1, width-30);
    ypos = (int) random (height*0.05f, height/2);
    fall = false;
    fallTime = fallT;
    canCollect = true;
    collected = false;
    tipo = loadImage ("melhor.png");
  }

  public int getfallTime() {
    return this.fallTime;
  }

  public void setfallTime(int t) {
    this.fallTime = t;
  }
  public boolean getcanCollect() {
    return this.canCollect;
  }
  public void setcanCollect(boolean cc) {
    this.canCollect = cc;
  }
  public boolean getfall() {
    return this.fall;
  }
  public void setfall(boolean a) {
    this.fall = a;
  }
  public boolean getcollected() {
    return this.collected;
  }
  public void setcollected(boolean c) {
    this. collected = c;
  }
  public int getxpos () {
    return this.xpos;
  }
  public int getypos () {
    return this.ypos;
  }
  public PImage gettipo() {
    return this.tipo;
  }
  public void setxpos(int a) {
    this.xpos = a;
  }
  public void setypos (int b) {
    this.ypos = b;
  }
}
public void createMushroom () { //a mushroom is created with a randomly assigned fall time up to 35.000
  for (int i=0; i< mushroomsG.length; i++) {
    mushroomsG[i] = new MushroomGood((int) random (35000));
  }
  for (int i=0; i< mushroomsB.length; i++) {
    mushroomsB[i] = new MushroomBad((int) random (35000));
  }
}
SoundFile gathering;
SoundFile background;
public void createGatheringSound (){
  gathering = new SoundFile (this, "gathering.wav");
}

public void createBackgroundMusic (){
  background = new SoundFile (this, "background.wav");
}
  public void settings() {  size(720, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Final" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
