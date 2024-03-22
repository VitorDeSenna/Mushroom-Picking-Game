void fallingMush() {
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
      if ((mushroomsG[i].getypos() > (basket.getypos() - (basket.getlarg()/3.3))) && ((mushroomsG[i].getxpos() >= (basket.getxpos() - (basket.getcomp()/2))) && (mushroomsG[i].getxpos() <= (basket.getxpos() + (basket.getcomp()/2)) - 30)) && (mushroomsG[i].getcanCollect() == true)) {
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
    if ((mushroomsB[i].getypos() > (basket.getypos() - (basket.getlarg()/3.3))) && ((mushroomsB[i].getxpos() >= (basket.getxpos() - (basket.getcomp()/2))) && (mushroomsB[i].getxpos() <= (basket.getxpos() + (basket.getcomp()/2)) - 30)) && (mushroomsB[i].getcanCollect() == true)) {
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
