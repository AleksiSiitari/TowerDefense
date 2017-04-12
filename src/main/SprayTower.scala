package main

import scala.util._

class SprayTower (position: Vector) extends Towers(position: Vector){
  
  var rand = new Random
  var range = 75
  var cost = 200
  var damage = 10
  cd = 0
  image_id = "sprayTower"
    
  def shoot = {    //SprayTower shoots in the general direction of the enemy so the bullet direction is somewhat randomized
    if(cd <= 0) {
      var target = Vector((this.target.get.position.x - main.offset.x) + rand.nextInt(20 + 1 + 10) - 20 , (this.target.get.position.y - main.offset.y) + rand.nextInt(20 + 1 + 10) - 20)
      PlayMode.projectiles += new Ammo(this.position, target, System.nanoTime(), this.damage)
      cd = 7
    }
    else {
      cd -= 1
    }
  }
  
  
}
