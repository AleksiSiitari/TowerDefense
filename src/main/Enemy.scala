
package main

import processing.core._
import scala.util.Random

abstract class Enemy {
  var maxHP: Int = 100
  var HP: Int = 100
  var speed: Double = 1.0
  var position: Vector = Vector(0,0)
  var loadedImage : Option[PImage] = None
  var target = main.mouseLocation()
  var moveVector = Vector(0,0)
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
  
  def vectorToTarget = {
    target = main.mouseLocation()
    var vec = this.target - this.position
    moveVector = vec.normalized()
  }
  
  def move = {
    this.position += moveVector*speed
  }
}