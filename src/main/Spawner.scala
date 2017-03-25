package main

import processing.core._

object Spawner {
  
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
  
  
}