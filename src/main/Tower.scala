package main
import processing.core._

abstract class Tower(val position: Vector) {
  //var position: Vector = Vector(0,0)
  var loadedImage : Option[PImage] = None
  var target: Enemy
  var moveVector = Vector(0,0)
  val image_id = "enemy"
  
  def draw(scale: Int)
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}
  
}