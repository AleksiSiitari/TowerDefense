package Graphics

import processing.core._  
import Main._

object GUI {
  var f = main.createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on
  var loadedImage : Option[PImage] = None
  var loadedImage2 : Option[PImage] = None
  val UIrect = "UIrectangle"
  val play = "playButton"
  
  def playImage = {
     if(!loadedImage2.isDefined) {
	    loadedImage2 = Some(Sprites.get(play))
	  }
	  loadedImage2.get
  } 
  
  def rectImage = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(UIrect))
	  }
	  loadedImage.get
	}
  
  def draw = {
    main.textAlign(3, 3)
    main.textFont(f,40)
    main.fill(255, 140, 0)
    main.text("$ " + Player.money, 670, 635)
    main.textAlign(1,3)
    main.textFont(f, 25)
    main.text("Lives: " + Player.lives, 10, 10)
  }
}