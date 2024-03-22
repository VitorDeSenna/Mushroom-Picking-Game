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
  int getxpos () {
    return this.xpos;
  }
  int getypos() {
    return this.ypos;
  }
  void setxpos(int x) {
    this.xpos = x;
  }
  void setypos(int y) {
    this.ypos = y;
  }
  int getlarg () {
    return this.larg;
  }
  int getcomp () {
    return this.comp;
  }
  PImage getcesto() {
    return this.cesto;
  }
}

void createBasket() {
  basket = new Basket();
}

void drawBasket() { 
  imageMode(CENTER);
  basket.setxpos(mouseX);
  basket.setypos(height-(int(basket.getlarg()/3.3)));
  image (basket.getcesto(), basket.getxpos(), basket.getypos(), basket.getcomp(), basket.getlarg());
  imageMode(CORNER);
}
