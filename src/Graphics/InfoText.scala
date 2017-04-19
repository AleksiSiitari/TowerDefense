package Graphics

import Main._

class InfoText(val location: Vector,val spawnTime: Long, val text: String, val r: Int, val g: Int, val b: Int, val size: Int) {
  
  
  var font = main.createFont("Arial Bold", size)
  
  def elapsedTime = {
    (System.nanoTime() - spawnTime)/1e9
  }
  
  def isExpired = elapsedTime > 7
  
  def draw = {
    main.pushStyle()
    main.fill(r, g, b, (255-elapsedTime*75).toInt)
    main.textAlign(3,3)
    main.textFont(font)
    main.text(this.text, location.x, location.y)
    main.popStyle()
  }
  
}