package main
import processing.core._

abstract class Ability(position: Vector) extends Buildable(position: Vector) {
  
  var duration: Int
  var cost: Int
  var range: Int
  var image_id: String
  var loadedImage : Option[PImage] = None
  
  def touches(enemy: Enemy): Boolean = {
    enemy.position.distanceToPoint(this.position) < range
  }
  
  def isExpired = duration <= 0
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}
  
  def update
  
  def effect(enemy: Enemy)
  
   def draw = {
	  main.pushMatrix()
	  main.translate(this.position.x, this.position.y)
	  main.image(image, -image.width/2, -image.height/2, image.width, image.height)
	  main.popMatrix()
	}
}


class Fire(location: Vector) extends Ability(location: Vector) {
  
  var duration = 60*10
  var range = 70
  var image_id = "fire"
  var cost = 100
  
  def update = {
    duration -= 1
  }
  
  def effect(enemy: Enemy) = {
    enemy.HP -= 1
  }
  
}