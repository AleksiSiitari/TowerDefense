 package Enemies

import scala.math._
import scala.util._
import processing.core._
import Main._
import Modes._
import Graphics._

abstract class Enemy {
  var maxHP: Int = 100
  var HP: Int = maxHP
  var reward: Int = 10
  var speed: Double = 1.0
  var position: Vector = Vector(0,0)
  var loadedImage : Option[PImage] = None
  var curTarget = 0
  var target = Path.points(curTarget)
  var rand = new Random
  var image_id = "ufoRed"
  var frame = 0
  
  def angle : Double = 0.0
  
  def beingHit(DMG: Int) = {
    this.HP -= DMG
    this.speed -= 0.1
    if (this.frame <= 0) {
      this.frame = 10
    }
  }
  
  def update = {
    if (this.frame > 0) {
      this.frame -= 1
    }
  }
  
  def isAlive:Boolean = HP > 0
  
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
  
  def checkForEnemies = {
    for(e <- PlayMode.enemies) {
      if((this.position).distanceToPoint(e.position) < 20) {
        this.position = this.position - (e.position - this.position).normalized()*3
      }
    }
  }
  
  def checkForTarget = {
    if ( this.position.distanceToPoint(this.target) < 10 && this.curTarget < Path.points.length) {
      if(curTarget == Path.points.length-1) {  //Has reached the end
        this.HP = 0
        Player.lives -= 1
      }
      else {
        curTarget = curTarget + 1
        target = Path.points(curTarget)
      }
    }
  }
  
  def isHit = {
    this.speed -= 0.1
  }
  
  def moveVector = (this.target - this.position).normalized()*this.speed
  
  def move(vector: Vector) = {
    this.position += vector
  }
}