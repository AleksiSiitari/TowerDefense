package Towers

import processing.core._
import Graphics.Sprites
import Enemies._
import Main._

/*
 * Class for the projectiles the towers shoot
 */
class Ammo(start: Vector, target: Vector, spawnTime: Long, DMG: Int) {
 
  var position: Vector = start
  var loadedImage : Option[PImage] = None
  var speed: Int = 4
  val image_id = "ammo"
  var moveVector = (this.target - this.position).normalized()*this.speed
  var damage: Int = 40

  /*
   * Keeps track of the time each ammunition has existed
   */
  def elapsedTime = {
    (System.nanoTime() - spawnTime)/1e9
  }
  
  def isExpired = {
    elapsedTime > 5
  }
  
  def remove = {???}
  
  /*
   * Check if this ammunition is touching certain Enemy e
   */
  def collides(e: Enemy) = {
    this.position.distanceToPoint(e.position /*- main.offset*/) < 20
  }
    
  /*
   * Move this ammunition towards its target
   */
  def move = this.position += moveVector
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}
  
  def draw(scale: Int) = {
    main.image(image, this.position.x, this.position.y)
  }
  
}