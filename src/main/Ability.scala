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
  
  def isExpired = duration < 0
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}
  
  def update = this.duration -= 1
  
  def effect(enemy: Enemy)
  
   def draw = {
	  main.pushMatrix()
	  main.image(image, this.position.x - main.offset.x, this.position.y - main.offset.y)
	  main.popMatrix()
	}
}


class Fire(location: Vector) extends Ability(location: Vector) {
  
  var duration = 10*60
  var range = 70
  var image_id = "fire"
  var cost = 100
  
  def effect(enemy: Enemy) = {
    enemy.HP -= 1
  }
  
}