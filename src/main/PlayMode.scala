package main

import processing.core._
import scala.collection.mutable.Buffer
import scala.math._
import java.awt.event.KeyEvent._
import scala.util._

object PlayMode extends Mode {
  
  var enemies = Buffer[Enemy]()
  var towers = Buffer[Towers]()
  var buildAllowed = true
  
  def init () = {
    enemies.clear()
    GameMap.generateMap()
    enemies += new BasicEnemy
  }
  
  def terminate() = {
    
  }
  
  def draw(dt: Double) = {
    GameMap.draw
    enemies.foreach(_.draw(1))
    Path1.display()
    towers.foreach(_.draw(1))


  }
  
  def update(dt: Double) = {
    enemies.foreach(_.vectorToTarget)
    enemies.foreach(_.move)
    enemies.foreach(_.checkForWall)
    /*
    if(main.mouseClicked() && buildAllowed) {
      towers += new BasicTower(main.mouseLocation())
    }
    * 
    */
  }
  
 
  
  

}