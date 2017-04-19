package Graphics

import Main._

class Tooltips(var location: Vector, var text: String, var numLines: Int) {
  
  var font = main.createFont("Arial", 16, true)

  
  def draw {
    main.pushMatrix()
    main.fill(0,175,0,150)
    main.rect(location.x - main.offset.x, location.y - main.offset.y, 125 + 2*text.length(), 30*numLines, 7 )    //Draw approximately right sized box for the text
    main.textFont(font)
    main.textAlign(1,1)
    main.fill(255,255,255)
    main.text(text, location.x, location.y)
    main.popMatrix
  }
  
}