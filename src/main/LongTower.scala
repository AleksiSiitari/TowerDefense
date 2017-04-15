package main

class LongTower(position: Vector) extends Towers(position: Vector) {
  
  var range = 300
  var cost = 200
  var damage = 40
  cd = 0
  image_id = "LongTower"
    
  def shoot = {
    if(cd <= 0) {
      PlayMode.projectiles += new Ammo(this.position, this.target.get.position - main.offset, System.nanoTime(), this.damage)
      cd = 100 
    }
    else {
      cd -= 1
    }
  }
  
  
  
  
}