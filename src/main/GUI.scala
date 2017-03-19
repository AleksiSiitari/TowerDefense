package main
import processing.core._

object GUI {
  var f = main.createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on
  var loadedImage : Option[PImage] = None
  val UIrect = "UIrectangle"
  
  def rectImage = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(UIrect))
	  }
	  loadedImage.get
	}
  
  def draw = {
    main.textAlign(3, 3)
    main.textFont(f,40)
    main.fill(204, 153, 0)
    main.text("$ " + PlayMode.money, 670, 635)
	  main.image(rectImage, 0,605)
	  main.image(rectImage, 64, 605)
	  main.image(rectImage, 128, 605)
	  main.image(rectImage, 192, 605)
	  main.image(rectImage, 256, 605)
	  main.image(rectImage, 320, 605)
	  main.image(rectImage, 384, 605)
	  main.image(rectImage, 448, 605) 
  }
}