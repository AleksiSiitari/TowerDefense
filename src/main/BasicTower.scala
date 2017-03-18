package main

class BasicTower(position: Vector) extends Towers(position: Vector){
  
  var range = 100.0
  var targetDistance: Option[Double] = None
   
  def draw(scale: Int) = {
    main.pushMatrix()
    main.image(image, this.position.x, this.position.y)
    main.popMatrix()
  }
  
  
}