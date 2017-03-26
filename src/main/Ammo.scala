package main

import processing.core._

class Ammo(start: Vector, target: Vector, spawnTime: Long, DMG: Int) {
  var position: Vector = start
  var loadedImage : Option[PImage] = None
  var speed: Int = 4
  val image_id = "ammo"
  var moveVector = (this.target - this.position).normalized()*this.speed
  var damage: Int = 40

  def elapsedTime = {
    (System.nanoTime() - spawnTime)/1e9
  }
  
  def isExpired = {
    elapsedTime > 5
  }
  
  def remove = {???}
  
  def collides(e: Enemy) = {
    //Math.sqrt(Math.pow(e.position.x - this.position.x , 2) + Math.pow(e.position.y - this.position.y, 2)) < 20
    this.position.distanceToPoint(e.position - main.offset) < 15
  }
    
  def move = this.position += moveVector
  
  def image() = {
	  if(!loadedImage.isDefined) {
	    loadedImage = Some(Sprites.get(image_id))
	  }
	  loadedImage.get
	}
  
  def draw(scale: Int) = {
    main.pushMatrix()
    main.image(image, this.position.x, this.position.y)
    main.popMatrix()
  }
  
}