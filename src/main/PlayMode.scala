package main

import processing.core._
import scala.collection.mutable.Buffer
import scala.math._
import java.awt.event.KeyEvent._
import scala.util._

object PlayMode extends Mode {
  
  var enemies = Buffer[Enemy]()
  var towers = Buffer[Towers]()
  var projectiles = Buffer[Ammo]()
  var buildAllowed = true
  
  def init () = {
    enemies.clear()
    GameMap.generateMap()
    enemies += new BasicEnemy
  }
  
  def terminate() = {
    
  }
  
  def mousePressed = {
    towers += new BasicTower(main.mouseLocation)
  }
  
  def draw(dt: Double) = {
    GameMap.draw
    enemies.foreach(_.draw(1))
    towers.foreach(_.draw(1))
    projectiles.foreach(_.draw(1))


  }
  
  def update(dt: Double) = {
    projectiles.foreach(_.move)
    for(p <- projectiles) {
      if(p.isExpired) {
        projectiles = projectiles.filter(x => x != p)
      }
    }
    
    for(e <- enemies) {
      e.checkForTarget
      e.checkForWall
      e.move(e.moveVector)
    }

    for(t <- towers) {
      t.checkTarget
      if(!t.target.isDefined){
        t.getTarget
      }
      else{t.shoot}
    }
  }
  

} 