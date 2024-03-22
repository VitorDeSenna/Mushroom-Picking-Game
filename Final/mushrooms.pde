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
    ypos = (int) random (height*0.05, height/2.7);              
    fall = false; // if has ever started falling
    canCollect = true; //before being collected or off screen
    collected = false;
    fallTime = fallT; //the time in which it will fall according to when the main game started
    tipo = loadImage ("bom.png");
  }
  int getfallTime() {
    return this.fallTime;
  }

  void setfallTime(int t) {
    this.fallTime = t;
  }

  int getxpos () {
    return this.xpos;
  }
  boolean getcanCollect() {
    return this.canCollect;
  }
  void setcanCollect(boolean cc) {
    this.canCollect = cc;
  }
  boolean getfall() {
    return this.fall;
  }
  boolean getcollected() {
    return this.collected;
  }
  void setcollected(boolean c) {
    this. collected = c;
  }
  void setfall(boolean a) {
    this.fall = a;
  }
  int getypos () {
    return this.ypos;
  }
  PImage gettipo() {
    return this.tipo;
  }
  void setxpos(int a) {
    this.xpos = a;
  }
  void setypos (int b) {
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
    ypos = (int) random (height*0.05, height/2);
    fall = false;
    fallTime = fallT;
    canCollect = true;
    collected = false;
    tipo = loadImage ("melhor.png");
  }

  int getfallTime() {
    return this.fallTime;
  }

  void setfallTime(int t) {
    this.fallTime = t;
  }
  boolean getcanCollect() {
    return this.canCollect;
  }
  void setcanCollect(boolean cc) {
    this.canCollect = cc;
  }
  boolean getfall() {
    return this.fall;
  }
  void setfall(boolean a) {
    this.fall = a;
  }
  boolean getcollected() {
    return this.collected;
  }
  void setcollected(boolean c) {
    this. collected = c;
  }
  int getxpos () {
    return this.xpos;
  }
  int getypos () {
    return this.ypos;
  }
  PImage gettipo() {
    return this.tipo;
  }
  void setxpos(int a) {
    this.xpos = a;
  }
  void setypos (int b) {
    this.ypos = b;
  }
}
void createMushroom () { //a mushroom is created with a randomly assigned fall time up to 35.000
  for (int i=0; i< mushroomsG.length; i++) {
    mushroomsG[i] = new MushroomGood((int) random (35000));
  }
  for (int i=0; i< mushroomsB.length; i++) {
    mushroomsB[i] = new MushroomBad((int) random (35000));
  }
}
