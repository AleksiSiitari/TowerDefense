package main

object Cursor {
  
  var buildAllowed = true
  
  var selected: Int = 1
  
  def buildPossible = {
    PlayMode.towers.forall(x => main.mouseLocation().distanceToPoint(x.position) > 50) && GameMap.getTile(main.mouseLocation()).solid
  }
  
  def clicked = {
    if(buildPossible) {
      selected match {
        case 0 =>
          
        case 1 => if (PlayMode.money - 50 >= 0 ) {
                  PlayMode.towers += new BasicTower(main.mouseLocation()-main.offset)
                  PlayMode.money -= 50
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575 - 35*PlayMode.LargeTexts.size), System.nanoTime(), "$ - " + 50, 255,150,0,40)
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
                  }
      }
    }
    else {
      PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Can't build here", 150,150,0,14)
    }
    
  }
  
  def draw = ???
}