package main

class BasicTower(position: Vector) extends Towers(position: Vector){
  
  val range: Double = 25.0
  var targetDistance: Option[Double] = None
  
  def getTartget = {
    for(e <- PlayMode.enemies) {
      if (main.distance(this.position, e.position) < this.range) {
        if (!target.isDefined) {
          target = Some(e)
        }
      }
    }
  }
  
  def draw(scale: Int) = {
    main.pushMatrix()
    main.image(image, this.position.x, this.position.y)
    main.popMatrix()
  }
  
  
}