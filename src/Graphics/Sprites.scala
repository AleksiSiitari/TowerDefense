 package Graphics

import scala.collection.mutable.Map
import processing.core._
import Main._

object Sprites {
  private val names = Map[String, String] (
      "wall" -> "wall.png",
      "ufoRed" -> "ufoRed.png",
      "ufoRed2" -> "ufoRed2.png",
      "tower" -> "tower.png",
      "LongTower" -> "longTower.png",
      "glass" -> "glass.png", 
      "wall2" -> "wall2.png",
      "ammo" -> "ammo.png",
      "buttonBlur" -> "button_blur.png",
      "towerButton" -> "towerButton.png",
      "longTowerButton" -> "longTowerButton.png",
      "playButton" -> "play.png",
      "ufoBlue" -> "ufoBlue.png",
      "fire" -> "fire.png",
      "ice" -> "ice.png",
      "nebula" -> "nebula.jpg",
      "sprayTower" -> "sprayTower.png",
      "sprayTowerButton" -> "sprayTowerButton.png",
      "ufoRedDamaged" -> "ufoRedDamaged.png",
      "energyShield" -> "energyShield.png",
      "mothership" -> "mothership.png",
      "mothershipDamaged" -> "mothershipDamaged.png",
      "rightClick" -> "rightClick.png",
      "leftClick" -> "leftClick.png",
      "money" -> "money.png",
      "greenUfo" -> "greenUfo.png"
  )
  
  private val imagePath = "resources/images/"
   
  def loadImage(path: String) : PImage = {
    main.loadImage(s"${imagePath}${path}")
  }
	
	private val images = Map[String, PImage]()
	
	// Load all the images
	for(id <- names) {
	  images(id._1) = loadImage(id._2)
	}
	
	// Returns the wanted image if found
	def get(id: String) : PImage = {
	  var i = images.get(id)
	  
	  if(!i.isDefined) {
	    throw new Exception(s"${id} is not a valid image identifier!")
	  }
	  
	  i.get
	}
}