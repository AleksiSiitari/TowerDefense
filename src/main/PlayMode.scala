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
  var time = 0
  
  var btns = Buffer[InGameButton](new InGameButton(0, 606, 64, 64, 1, "towerButton", "towerButtonSelected"),
                                  new InGameButton(64,606, 64, 64, 2, "longTowerButton", "longTowerButtonSelected")
                                  )
                                  
  var tooltips = Map[InGameButton, Tooltips](btns(0) -> new Tooltips(Vector(0,574), "A Basic Tower \n $50", 2),
                                             btns(1) -> new Tooltips(Vector(64, 574), "A long range tower \n $200", 2)
                                             )

  
  def init () = {
    Spawner.ready = false
    enemies.clear()
    towers.clear()
    projectiles.clear()
    Player.init
    time = 0
    Spawner.waveNum = 0
    GameMap.generateMap()
    
  }
  
  def terminate() = {
    
  }
  
  def mousePressed(key: Int) = {
    if (key == 37) {
      if(main.mouseY < 606) {
        Cursor.clicked
      }
      else {
        if(main.mouseX < 64) {
          Cursor.selected = 1
        }
        else if (main.mouseX > 64) {
          Cursor.selected = 2
        }
      }
    }
    else {      //right mouse clears selection
      Cursor.selected = 0
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
    if(Cursor.isOnTower || Cursor.selected != 0) {
      towers.foreach(_.drawRange)
    }
    projectiles.foreach(_.draw(1))
    GUI.draw
    Spawner.draw
    Cursor.draw
    btns.foreach(_.draw())
    texts.foreach(_.draw)
    LargeTexts.foreach(_.draw)
    
    btns.foreach{x => 
      if(x.timeForTooltip) {
        tooltips(x).draw
    }}
    
  }
  
  def update(dt: Double) = {
    Cursor.isOnTower
    Spawner.spawnWave
    btns.foreach(_.update())
    
    if(!Player.isAlive) {
      PlayMode.terminate()
      main.mode = EndMode
    }
    
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
        Player.money += e.reward
        Player.enemiesSlain += 1
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
