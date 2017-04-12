package main

class BasicTower(position: Vector) extends Towers(position: Vector){
  
  var range = 100
  var cost = 50
  var damage = 30
  cd = 0
  //var targetDistance: Option[Double] = None
  
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position, System.nanoTime(), this.damage)
      cd = 50 
    }
    else {
      cd -= 1
    }
  }
  
  
}