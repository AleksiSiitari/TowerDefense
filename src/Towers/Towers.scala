 package Towers

import processing.core._
import Enemies._
import Modes.PlayMode
import Main._
import Graphics._

/*
 * Small abstract class to keep track of everything buildable
 */
abstract class Buildable(var position: Vector) {
  var cost: Int
  var range: Int
}

abstract class Towers(position: Vector) extends Buildable(position: Vector) {
  
  var loadedImage : Option[PImage] = None
  var target: Option[Enemy] = None
  var moveVector = Vector(0,0)
  var image_id = "tower"
  var cd = 0
  
  /*
   * Gets the distance to target. if tower has no target, return 9999
   */
  def targetDistance: Double = {
    if(target.isDefined) {
      (this.position.distanceToPoint(target.get.position))
    }
    else {9999}
  }
  
  def draw(scale: Int) = {
    main.image(image, this.position.x, this.position.y)
  }
  
 /*
  * Draws the range of the tower
  */
 def drawRange = {
    main.pushMatrix()
    main.fill(0, 0, 0, 0)
//    main.ellipseMode(2)
    main.ellipse(this.position.x+main.offset.x, this.position.y+main.offset.y, this.range.toFloat, this.range.toFloat)
    main.popMatrix()
  }
  
  def shoot
  
  def checkTarget = {
    if(target.isDefined) {
      if(targetDistance > range || !target.get.isAlive) {
        target = None
      }
    }
    else {
      None
    }
  }
  
   def getTarget = {
    for(e <- PlayMode.enemies) {
      if (main.distance(this.position, e.position) < this.range) {
        if (!target.isDefined) {
          target = Some(e)
        }
      }
    }
  }
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}

}