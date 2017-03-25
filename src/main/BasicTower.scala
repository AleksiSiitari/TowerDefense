package main

class BasicTower(position: Vector) extends Towers(position: Vector){
  
  var range = 100
  var cost = 50
  cd = 0
  //var targetDistance: Option[Double] = None
  
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position - main.offset, System.nanoTime())
      cd = 50 
    }
    else {
      cd -= 1
    }
  }
  
  
}