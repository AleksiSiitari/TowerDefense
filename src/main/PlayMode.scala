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
  var texts = Buffer[InfoText]()
  var LargeTexts = Buffer[InfoText]()
  var money = 100
  var time = 0
  
  def init () = {
    enemies.clear()
    towers.clear()
    projectiles.clear()
    money = 100
    GameMap.generateMap()
  }
  
  def terminate() = {
    
  }
  
  def mousePressed = {
    if(main.mouseLocation().y < 606) {
      Cursor.clicked
    }
    else {
      if(main.mouseLocation().x < 64) {
        Cursor.selected = 1
      }
      else if (main.mouseLocation().x > 64) {
        Cursor.selected = 2
      }
    }
  }
  
  def keyPressed = {
    Spawner.ready = true
  }
  
  def draw(dt: Double) = {
    main.background(100)
    GameMap.draw
    enemies.foreach(_.draw(1))
    towers.foreach(_.draw(1))
    projectiles.foreach(_.draw(1))
    texts.foreach(_.draw)
    LargeTexts.foreach(_.draw)
    GUI.draw
    Spawner.draw
    Cursor.draw
    
  }
  
  def update(dt: Double) = {
    Spawner.spawnWave
    
    projectiles.foreach(_.move)
    for(p <- projectiles) {
      if(p.isExpired) {  //Delete expired projectiles
        projectiles = projectiles.filter(x => x != p)
      }
      for (e <- enemies) {  //Check for collisions
        if(p.collides(e)) {
          e.HP -= p.damage
          e.speed -= 0.1
          projectiles = projectiles.filter(x => x != p)
        }
      }
    }
    
    for(e <- enemies) {
      e.checkForTarget
      //e.checkForWall
      e.move(e.moveVector)
      e.checkForEnemies
      if(!e.isAlive) {
        enemies = enemies.filter(x => x != e)
        this.money += e.reward
      }
    }

    for(t <- towers) {
      t.checkTarget
      if(!t.target.isDefined){
        t.getTarget
      }
      else{
        t.shoot
      }
    }
    
    /* Check for expired texts */
    for(i <- texts) {
      if(i.isExpired) {                //FIXME: Does not remove expired texts
        texts.filter(_ != i)
      }
    }
    for(i <- LargeTexts) {
      if(i.isExpired) {
        LargeTexts.filter(_ != i)
      }
    }
  }
  
 
  
  

} 
