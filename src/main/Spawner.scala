package main

import processing.core._
import scala.collection.mutable._
import scala.util._

class Wave(var enemies: Map[String, Int], var winPoints: Int, var spawnDelay: Double) {}

object Wave {
  def apply(w: Wave) = {
    var counts = Map[String, Int]()
    counts ++= w.enemies
    new Wave(counts, w.winPoints, w.spawnDelay)
  }
}

object Spawner {
  
  var timeBetweenWaves = 10.0
  
  var waveNum = 0
  var nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
  var waveOn = false
  var currentConfig : Wave = _
  var lastSpawn = System.nanoTime()
  var waveEnded = 0l  
  
  
  var waves = Map[Int, Wave](
      1 -> new Wave(
        Map("normal" -> 1 ),// Spawn 1 of these
        50,                 // You get 50 score for beating the wave (modified by difficulty)
        2.00                // And one enemy spawns every 2 second (modified by difficulty)
      ),
      2 -> new Wave(
        Map("normal" -> 3),
        50,
        2.00
      ),
      3 -> new Wave(
        Map("normal" -> 5),
        50,
        2.00
      
      ),
      4 -> new Wave(
        Map("normal" -> 10),
        50,
        2.00
      ),
      5 -> new Wave(
        Map("normal" -> 15,
            "fast" -> 3   ),
        75,
        2.00  
      )
  )
    
    
  def setWaveConfiguration() = {
    if(waves.get(waveNum).isDefined) {  // This wave has been defined, use it
      currentConfig = Wave(waves(waveNum))
    } else {  // If we have no more waves, just keep using the last wave
      currentConfig =  Wave(waves(waves.keys.max))
    }
  }
  
  def init() = {
    waveNum = 0
    nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
    waveOn = false
  }
      
  def timeUntilNextWave = {      //TODO: implement a way to skip the waiting
    if(waveOn) {
      0
    } else {
      var curTime = System.nanoTime()
      (nextWaveStart - curTime)/1e9
    }
  }
  
  def shouldDisplayWaveWin() = {
    // Display the waveWin for 5 seconds
    (System.nanoTime() - waveEnded)/1e9 < 5
  }
  
  def waveWinString() = {
    // Display this after you win a round
    s"Wave ${waveNum} ended!"
  }
  
  def getEnemiesLeft() = {
    //count the number of enemies left
    currentConfig.enemies.values.sum + PlayMode.enemies.size
  }
  
  
  def getWaveString() = {
    // What is displayed at the bottom of the game
    if(waveOn) {
      s"Wave ${waveNum} with ${getEnemiesLeft} enemies left!"
    } else {
      "New wave starting in "+ f"$timeUntilNextWave%1.1f" + " seconds!"
    }
  }
  
  def updateWaveStatus() = {
    if(!waveOn) {
      if(timeUntilNextWave <= 0) {  // Time between rounds ran out, start a new wave
        waveNum += 1
        waveOn = true
        setWaveConfiguration()
      }
    } else {
      (nextWaveStart - System.nanoTime())/1e9
      if((System.nanoTime() - lastSpawn)/1e9 > currentConfig.spawnDelay /** Settings.spawnMultiplier*/){   //TODO: Implement difficulty settings here!
        // if been over spawnDelay seconds since last spawn, generate a new enemy
        
        var possible = currentConfig.enemies.filter(_._2 > 0).keys.toArray
        if(possible.size > 0) {
          var enemy_type = possible(main.random(0, possible.size).toInt)
          var e = generateEnemy(enemy_type)
          PlayMode.enemies += e
          currentConfig.enemies(enemy_type) -= 1
          lastSpawn = System.nanoTime()
        }
      }
      
      if(getEnemiesLeft == 0) { // The player beat all the enemies
        waveOn = false
        waveEnded = System.nanoTime()
        nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
        Player.score += (currentConfig.winPoints)  //*Settings.getScoreMultiplier()).toInt //TODO: Also difficulty settings here!
      }
    }  
  }
  
  def generateEnemy(name: String): Enemy = {
    if(name == "normal") {
      new BasicEnemy()
    } else {
      new FastEnemy()
    }
  }
  
  
}















