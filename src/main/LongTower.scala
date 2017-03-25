package main

class LongTower(position: Vector) extends Towers(position: Vector) {
  
  var range = 500
  var cost = 200
  cd = 0
  image_id = "LongTower"
    
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position - main.offset, System.nanoTime())
      cd = 100 
    }
    else {
      cd -= 1
    }
  }
  
  
  
  
}