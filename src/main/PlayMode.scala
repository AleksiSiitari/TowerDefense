package main

import processing.core._ 
import scala.collection.mutable.Buffer
import scala.math._
import java.awt.event.KeyEvent._
import scala.util._

object PlayMode extends Mode {
  
  var font = main.createFont("Arial", 16, true)
  var bigFont = main.createFont("Arial", 24, true)
  var img = Sprites.get("nebula").get      //Image for the background
 
  var enemies = Buffer[Enemy]()
  var towers = Buffer[Towers]()
  var projectiles = Buffer[Ammo]()
  var abilities = Buffer[Ability]()
  var texts = Buffer[InfoText]()
  var LargeTexts = Buffer[InfoText]()
  var time = 0
  
  var btns = Buffer[InGameButton](new InGameButton(0, 606, 64, 64, 1, "towerButton"),
                                  new InGameButton(64,606, 64, 64, 2, "longTowerButton"),
                                  new InGameButton(512, 606, 64, 64, 10, "playButton"),
                                  new InGameButton(128, 606, 64, 64, 3, "fire"),
                                  new InGameButton(192, 606, 64, 64, 4, "ice"),
                                  new InGameButton(256, 606, 64, 64, 4, "sprayTowerButton")
                                  )
                                  
  var tooltips = Map[InGameButton, Tooltips](btns(0) -> new Tooltips(Vector(0,574), "A Basic Tower \n $50", 2),
                                             btns(1) -> new Tooltips(Vector(64, 574), "A long range tower \n $200", 2),
                                             btns(2) -> new Tooltips(Vector(512, 574), "Press to start the\n next wave", 2),
                                             btns(3) -> new Tooltips(Vector(128, 574), "Set a temporary fire \n to damage enemies \n $100", 3),
                                             btns(4) -> new Tooltips(Vector(192, 574), "Create temporary ice \n to slow enemies \n $50", 3),
                                             btns(5) -> new Tooltips(Vector(256,574), "A short ranged fast shooting tower \n Does not shoot accurately \n $200", 3)
                                             )

  
  def init () = {
    Spawner.init()
//    Spawner.ready = false
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
        else if (main.mouseX > 64 && main.mouseX < 128) {
          Cursor.selected = 2
        }
        else if (main.mouseX > 128 && main.mouseX < 192) {
          Cursor.selected = 3
        }
        else if (main.mouseX > 192 && main.mouseX < 256) {
          Cursor.selected = 4
        }
        else if (main.mouseX > 256 && main.mouseX < 320) {
          Cursor.selected = 5
        }
        else if (main.mouseX > 512 && main.mouseX < 576) {
//          Spawner.ready = true
          if(!Spawner.waveOn) {
//            Spawner.timeUntilNextWave = 0
          }
        }
      }
    }
    else {      //right mouse click clears selection
      Cursor.selected = 0
    }
  }
  
  def keyPressed = {
//    Spawner.ready = true
  }
  
  def draw(dt: Double) = {
    
    main.image(img, 0, 0, 800, 670)
    AnimatedBackground.draw
    
    GameMap.drawGround
    abilities.foreach(_.draw)
    GameMap.drawWalls
    enemies.foreach(_.draw(1))
    towers.foreach(_.draw(1))
    //Draw the range of towers if hovered
    if(Cursor.isOnTower || Cursor.selected != 0) {
      towers.foreach(_.drawRange)
    }
    projectiles.foreach(_.draw(1))
    GUI.draw
    Cursor.draw
    btns.foreach(_.draw())
    texts.foreach(_.draw)
    LargeTexts.foreach(_.draw)
    
    btns.foreach{x => 
      if(x.timeForTooltip) {
        tooltips(x).draw
    }}
    main.pushMatrix()
    main.pushStyle()
    main.textAlign(3, 3)
    main.fill(255, 150, 0)
    main.textFont(bigFont)
    main.text(s"${Spawner.getWaveString}", main.width/2, 10)
    main.text(s"Score: ${Player.score}", main.width-75, 10)
    main.popStyle()
    main.popMatrix()
    
    if(Spawner.shouldDisplayWaveWin()) {
      main.pushMatrix()
      main.pushStyle()
      main.textAlign(3, 3)
      main.fill(255, 150, 0)
      main.textFont(bigFont)
      main.text(Spawner.waveWinString(), main.width/2, main.height/2)
      main.popStyle()
      main.popMatrix()
    }
    
  }
  
  def update(dt: Double) = {
    AnimatedBackground.update
    
    Cursor.isOnTower
    Spawner.updateWaveStatus()
    btns.foreach(_.update())
    abilities.foreach(_.update)
    
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
          e.beingHit(p.damage)
          projectiles = projectiles.filter(x => x != p)
        }
      }
    }
    
    for(e <- enemies) {
      e.update
      e.checkForTarget
      e.move(e.moveVector)
      e.checkForEnemies
      if(!e.isAlive) {
        enemies = enemies.filter(x => x != e)
        Player.money += e.reward
        Player.enemiesSlain += 1
        LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ + " + e.reward, 255,150,0,40)
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
    
    for(a <- abilities) {
      if(a.isExpired) {
        abilities = abilities.filter(x => x != a)              //FIXME: Crashes the game if more than one ability active
      }
      for (e <- enemies) {  //Check for enemy
        if(a.touches(e)) {
          a.effect(e)
        }
      }
    }
    
    
    /* Check for expired texts */
    for(i <- texts) {
      if(i.isExpired) {                //FIXME: Does not remove expired texts properly
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
