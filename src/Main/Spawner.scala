 package Main

import processing.core._
import Main._
import Modes._
import Enemies._
import collection.mutable.Map

/*
 * Class for individual waves
 */
class Wave(var enemies: Map[String, Int], var winPoints: Int, var spawnDelay: Double) 

/*
 * Companion object for the Wave class
 */
object Wave {
  def apply(w: Wave) = {
    var counts = Map[String, Int]()     // Keep count of enemies to be spawned   
    counts ++= w.enemies
    new Wave(counts, w.winPoints, w.spawnDelay)
  }
}

/*
 * Object for spawning enemies according to the waves
 */
object Spawner {
  
  var timeBetweenWaves = 10.0
  var waveNum = 0
  var nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
  var waveOn = false
  var currentConfig : Wave = _
  var lastSpawn = System.nanoTime()
  var waveEnded = 0l  
  
  // Information about the waves in game 
  var waves = Map[Int, Wave](
      1 -> new Wave(
        Map("normal" -> 1),// Spawn 1 of these
        (50 * Settings.difficultyMultiplier).toInt,                 // Score for beating the wave
        2.00 - (1 - Settings.difficultyMultiplier)               // Delay between enemies spawning
      ),
      2 -> new Wave(
        Map("normal" -> 3),
        (50 * Settings.difficultyMultiplier).toInt,
        2.00 - (1 - Settings.difficultyMultiplier)
      ),
      3 -> new Wave(
        Map("normal" -> 7),
        (50 * Settings.difficultyMultiplier).toInt,  
        2.00 - (1 - Settings.difficultyMultiplier)
      
      ),
      4 -> new Wave(
        Map("normal" -> 10,
            "fast" -> 2),
        (50 * Settings.difficultyMultiplier).toInt,  
        2.00 - (1 - Settings.difficultyMultiplier)
      ),
      5 -> new Wave(
        Map("normal" -> 15,
            "fast" -> 3,
            "tough" -> 2),
        (75 * Settings.difficultyMultiplier).toInt,  
        1.50 - (1 - Settings.difficultyMultiplier)
      ),
      6 -> new Wave(
        Map("mothership" -> 1,
            "normal" -> 10,
            "fast" -> 10),
        (75 * Settings.difficultyMultiplier).toInt,  
        1.50 - (1 - Settings.difficultyMultiplier)
      ),
      7 -> new Wave(
        Map("normal" -> 20,
            "fast" -> 10,
            "tough" -> 10,
            "mothership" -> 2),
        (75 * Settings.difficultyMultiplier).toInt,  
        1.00 - (1 - Settings.difficultyMultiplier)
      ),
      8 -> new Wave(
        Map("fast" -> 30,
            "tough" -> 15,
            "mothership" -> 5),
        (75 * Settings.difficultyMultiplier).toInt,  
        1.00 - (1 - Settings.difficultyMultiplier)
      )
  )
    
  /*
   * Decide which wave to use
   */
  def setWaveConfiguration() = {
    if(waves.get(waveNum).isDefined) {  // if this wave has been defined, use it
      currentConfig = Wave(waves(waveNum))
    } else {  // else just keep using the last wave
      currentConfig =  Wave(waves(waves.keys.max))
    }
  }
  
  def init() = {
    waveNum = 0
    nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
    waveOn = false
  }
      
  /*
   * Count the time until next wave
   */
  def timeUntilNextWave = {
    if(waveOn) {
      0
    } else {
      var curTime = System.nanoTime()
      (nextWaveStart - curTime)/1e9
    }
  }
  
  // Indicates if wave winning text should be displayed
  def shouldDisplayWaveWin(): Boolean = {
        (System.nanoTime() - waveEnded)/1e9 < 5
  }
  
  // Display this after you win a round
  def waveWinString() = {
    s"Wave ${waveNum} ended!"
  }
  
  // Count the number of enemies left
  def getEnemiesLeft() = {
    currentConfig.enemies.values.sum + PlayMode.enemies.size
  }
  
  // Display texts about wave
  def getWaveString() = {
    if(waveOn) {
      "Wave " + waveNum
    } else {
      "New wave starting in "+ f"$timeUntilNextWave%1.1f" + " seconds!"
    }
  }
  
  /*
   * Updates the wave status 
   */
  def updateWaveStatus() = {
    if(!waveOn) {
      if(timeUntilNextWave <= 0) {  // Time between rounds ran out, start a new wave
        if (waveNum >= 1) {
          Player.score += waves(waveNum).winPoints
        }
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
          Modes.PlayMode.enemies += e
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
  
  /*
   * Generate enemies from the names
   */
  def generateEnemy(name: String): Enemy = {
    if(name == "normal") {
      new BasicEnemy()
    } else if (name == "fast") {
      new FastEnemy()
    }
    else if (name == "tough") {
      new ToughEnemy()
    }
    else {
      new Mothership()
    }
  }
  
  
}















