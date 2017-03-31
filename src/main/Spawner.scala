package main

import processing.core._
import scala.collection.mutable._
import scala.util._

class Wave(var enemies: Map[String, Int], var winPoints: Int, var spawnDelay: Double) {}

object Wave {
  def apply(w: Wave) = {
    // Do a deep copy of the map so we don't accidentally reset the zombie counts
    var counts = Map[String, Int]()
    counts ++= w.enemies
    new Wave(counts, w.winPoints, w.spawnDelay)
  }
}

object Spawner {
  /*
  var f = main.createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on
  var waveNum = 0
  var ready = false
  var waveOn = false
  var waveTimer = 0
  
  def waves(num: Int): Array[Enemy] = num match { 
    case 0 => Array(new BasicEnemy)
    case 1 => Array(new BasicEnemy,new BasicEnemy,new BasicEnemy)
    case 2 => Array(new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy)
    case 3 => Array(new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy)
    case 4 => Array(new FastEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy)
    case _ => Array(new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,
                    new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy,new BasicEnemy
                    )
  }
  
  def noEnemies = PlayMode.enemies.isEmpty
  
  def spawnWave = {
    if(noEnemies && ready) {
      ready = false
      waves(waveNum).foreach(x => PlayMode.enemies += x)
      for(e <- waves(waveNum)) {
        var cd = 10
      }
      waveNum += 1
    }
  }
  
  
  def draw = {
  if(noEnemies && !ready){
    main.textAlign(3, 3)
    main.textFont(f,40)
    main.fill(204, 153, 0)
    if (waveNum == 0) {
      main.text("Press \"Space\" to start first wave", 400, 325)
    }
    else {
      main.text("Press \"Space\" to continue", 400, 325)
    }
  }
  }
  * 
  */
  
  var timeBetweenWaves = 10.0 // seconds
  
  var waveNum = 0
  var nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
  var waveOn = false
  var currentConfig : Wave = _
  var lastSpawn = System.nanoTime()
  var waveEnded = 0l  
  
  
  var waves = Map[Int, Wave](
      1 -> new Wave(
        Map("normal" -> 1 ),// Spawn 1 of these
        50, // You get 50 score for beating the wave (modified by difficulty)
        2.00 // And one enemy spawns every 5 second (modified by difficulty)
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
      
      )
  )
    
    
  def setWaveConfiguration() = {
    if(waves.get(waveNum).isDefined) {
      // This wave has been defined, use it
      currentConfig = Wave(waves(waveNum))
    } else {
      // If we have no more waves, just keep using the last wave
      currentConfig =  Wave(waves(waves.keys.max))
    }
  }
  
  def init() = {
    waveNum = 0
    nextWaveStart = System.nanoTime() + timeBetweenWaves*1e9
    waveOn = false
  }
      
  var timeUntilNextWave = {      //FIXME: maybe a function???
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
    // The number of zombies are left is equal to the sum of the zombie spawner counts
    // and the number of zombies currently alive
    currentConfig.enemies.values.sum + PlayMode.enemies.size
  }
  
  
  def getWaveString() = {
    // What is displayed at the bottom of the game
    if(waveOn) {
      s"Wave ${waveNum} | ${getEnemiesLeft} enemies left!"
    } else {
      def truncateAt(n: Double, p: Int): Double = { val s = math pow (10, p); (math floor n * s) / s }
      "New wave starting in "+Math.max(truncateAt(timeUntilNextWave, 1), 0)+" seconds!"
    }
  }
  
  def updateWaveStatus() = {
    if(!waveOn) {
      if(timeUntilNextWave <= 0) {
        // Time between rounds ran out, start a new wave
        waveNum += 1
        waveOn = true
        setWaveConfiguration()
      }
    } else {
      (nextWaveStart - System.nanoTime())/1e9
      if((System.nanoTime() - lastSpawn)/1e9 > currentConfig.spawnDelay){   //TODO: Implement difficulty settings here!
        // Been over spawnDelay seconds since last spawn, generate a new enemy
        
        var possible = currentConfig.enemies.filter(_._2 > 0).keys.toArray
        if(possible.size > 0) {
          
          var enemy_type = possible(main.random(0, possible.size).toInt)
          var e = generateEnemy(enemy_type)
          PlayMode.enemies += e
          val asd = currentConfig.enemies(enemy_type) - 1
          currentConfig.enemies(enemy_type) = asd
          lastSpawn = System.nanoTime()
        }
      }
      
      if(getEnemiesLeft == 0) {
        // The player beat all the zombies
        
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















