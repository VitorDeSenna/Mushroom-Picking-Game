import processing.sound.*;
//install sound under Sketch > import library > add library

byte controller; //Used to control which state the game is in (eg. intro, ending)
int starterTime; //Stores the exact time that the game begins, after the intro
MushroomGood[] mushroomsG; //Array of "Good Mushrooms"
MushroomBad[] mushroomsB; //Array of "Bad Mushrooms"
int counter; //Used in "Falling_Mushroom" tab to control the flow of the game
int counterG; //Stores how many "Good Mushrooms" the player collected
int counterB; //Stores how many "Bad Mushrooms" the player collected

void setup() {
  //Initialize the playable screen, white background and no mouse cursor present
  size(720, 720);
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
void draw() {
  //Draws the intro on screen with images created at setup
  if (controller == 0) {
    background(255);
    image (grandmaSpeech, width*0.18, 0, width*0.83, height*0.72);
    image (grandmaIntro, -20, height/1.6, width/2.5, height/2.5);
    image (instructions, 300, height*0.8);

    //Draws the main game phase on screen
  } else if (controller == 1) {

    //Draws the background trees
    background (255);
    image (tree, width*(-0.15), height*(-0.11112), width-(width*0.375), height+(height*0.11112));
    image (tree, width*0.525, height*(-0.11112), width-(width*0.375), height+(height*0.11112));
    image (tree, width*0.2, height*(-0.11112), width-(width*0.375), height+(height*0.11112));

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
    image (grandmaFinalSpeech, width*0.18, 0, width*0.83, height*0.72);
    image (ending, -20, height/1.6, width/2.5, height/2.5);
    image (endInstructions, 300, height*0.8);
  }
}

void keyPressed() {
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
