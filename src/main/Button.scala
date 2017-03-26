package main

class Button(var x: Int, var y: Int, var w: Int, var h: Int, var text: String) {
  var hover = false
  val blur = Sprites.get("buttonBlur")
  
  var btnFont = main.createFont("Arial", 16, true)
  var btnFontBold = main.createFont("Arial Bold", 16, true)
  
  var color = Array(255f, 255f, 255f)
  
  def isOn() = {
    var px = main.mouseX
    var py = main.mouseY
    
    var rw = if(hover) w+25 else w
    
    (px >= x-rw/2 && px <= x+rw/2 && py >= y-h/2 && py <= y+h/2)
  }
  
  def draw() = {
    main.pushStyle()
    main.pushMatrix()
    main.translate(x, y)
    
    var rw = if(hover) w+25 else w

    main.stroke(0)
    
    var col = color
    
    if(hover) {
      main.image(blur, -rw/2-10, -h/2-3, rw+20, h+20)
      col = col.map(x=>x*0.95f)
    }
    
    main.fill(col(0), col(1), col(2))
    
    main.translate(-rw/2, -h/2)
    main.rect(0, 0, rw, h)
    
    main.fill(0)
    main.textAlign(3, 3)
    main.textFont(if(hover) btnFontBold else btnFont)
    main.text(text, rw/2, h/2-2)
    
    main.popMatrix()
    main.popStyle()
  }
  
  def update() = {
    /*
    if(!hover && isOn) {
      Sound.play("menuClick")		//Waiting for sounds
    }
    * 
    */
    hover = isOn
  }
}


class InGameButton(var x: Int, var y: Int, var w: Int, var h: Int, var number: Int, var image: String) {
  var selected = false
  var hover = false
  val normalImage = Sprites.get(image)
  var onFor = 0
  
  
  def isOn() = Cursor.selected == this.number
  
  def timeForTooltip = onFor > 20
  
  def isHovering() = {
    var px = main.mouseX
    var py = main.mouseY
    
    var rw = w
    
    (px >= x && px <= x+w && py >= y && py <= y+h)
  }
  
  def draw() = {
    main.pushStyle()
    main.pushMatrix()
            
    if(selected || isHovering) {
      main.pushMatrix()
      main.fill(0,255,0,75)
      main.image(normalImage, x, y)
      main.rect(x,y,64,64)
      main.popMatrix()
    }
    else {
      main.image(normalImage, x, y)
    }
        
    
    main.popMatrix()
    main.popStyle()
  }
  
  def update() = {
    selected = isOn
    if(isHovering) {
      onFor += 1
    }
    else {onFor = 0}
  }
}