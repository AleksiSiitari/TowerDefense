package main

import scala.collection.mutable.Buffer
import scala.util._

object AnimatedBackground {
  
  var rand = new Random()
  
  var stars = Array[Star]( new Star(Vector(-1,-1), Vector(-2,-2)), new Star(Vector(-1,-1), Vector(-2,-2)),
                           new Star(Vector(-1, -1), Vector(-2,-2)))
  
  def update = {
    for(s <- stars) {
      s.move()
      if(s.position.y < 0 || s.position.x < 0) {
        s.position = Vector(800 + rand.nextInt(1000), 670 + rand.nextInt(1000))
        s.direction = Vector(-rand.nextInt(300), -rand.nextInt(300))
      }
    }
    
  }
  
  def draw = {
    stars.foreach(_.draw()) 
  }
}

class Star(var position: Vector, var direction: Vector) {
  
  def move() = {
    this.position += (direction - position).normalized()*7
  }
  
  def draw() = {
    main.pushMatrix()
    main.stroke(200)
    main.strokeWeight(2)
    main.line(this.position.x, this.position.y, this.position.x + (direction - position).normalized().x*5, this.position.y + (direction - position).normalized().y*5)
    main.popMatrix()
  }
}