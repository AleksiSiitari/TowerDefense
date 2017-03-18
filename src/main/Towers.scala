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
  var targetDistance: Option[Double]
  
  def draw(scale: Int)
  
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position, System.nanoTime())
      cd = 15 
    }
    else {
      cd -= 1
    }
    
  }
  
  def checkTarget = {
    if(target.isDefined && targetDistance.isDefined) {
      if(targetDistance.get > range) {
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