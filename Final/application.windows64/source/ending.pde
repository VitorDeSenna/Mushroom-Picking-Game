//ending conditions
PImage ending;
PImage grandmaFinalSpeech;
PImage endInstructions;
void createEnding(int a){
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
