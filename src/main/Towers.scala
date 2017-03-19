package main

import processing.core._

abstract class Towers(val position: Vector) {
  //var position: Vector = Vector(0,0)
  var loadedImage : Option[PImage] = None
  var target: Option[Enemy] = None
  var moveVector = Vector(0,0)
  var range: Double
  val image_id = "tower"
  var cd = 0
  
  def targetDistance: Double = {
    if(target.isDefined) {
      (this.position.distanceToPoint(target.get.position))
    }
    else {9999}
  }
  
  def draw(scale: Int)
  
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position - main.offset, System.nanoTime())
      cd = 20 
    }
    else {
      cd -= 1
    }
    
  }
  
  def checkTarget = {
    if(target.isDefined) {
      if(targetDistance > range || !target.get.isAlive) {
        target = None
      }
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