
package main
import scala.math._

import processing.core._
import scala.util.Random

abstract class Enemy {
  var maxHP: Int = 100
  var HP: Int = 100
  var speed: Double = 1.0
  var position: Vector = Vector(0,0)
  var loadedImage : Option[PImage] = None
  var curTarget = 0
  var target = Path1.points(curTarget)
  
  val image_id = "enemy"

  def angle : Double = 0.0
  
  def isAlive:Boolean = (HP > 0)
  
  def draw(scale: Int)
  
	def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}

  def checkForWall = {
    if (GameMap.getTile(this.position+Vector(10,0)).solid) {
      this.position += Vector(-5,0)
    }
    else if (GameMap.getTile(this.position+Vector(0,10)).solid) {
      this.position += Vector(0,-5)
    }
    else if (GameMap.getTile(this.position+Vector(-10,0)).solid) {
      this.position += Vector(5,0)
    }
    else if (GameMap.getTile(this.position+Vector(0,-10)).solid) {
      this.position += Vector(0,5)
    }
  }
  
  def checkForTarget = {
    if ( this.position.distanceToPoint(this.target) < 20 && this.curTarget < Path1.points.length) {
      if(curTarget == Path1.points.length-1) {
        
      }
      else {
        curTarget = curTarget + 1
        target = Path1.points(curTarget)
      }
    }
  }
  
  def moveVector = (this.target - this.position).normalized()*this.speed
  
  def move(vector: Vector) = {
    this.position += vector
  }
}