package main

object Cursor {
  
  var buildAllowed = true
  
  var selected: Int = 1
  
  def buildPossible = {
    PlayMode.towers.forall(x => main.mouseLocation().distanceToPoint(x.position) > 50)
  }
  
  def clicked = {
    if(buildPossible) {
      selected match {
        case 0 =>
          
        case 1 => if (PlayMode.money - 50 >= 0 ) {
                  PlayMode.towers += new BasicTower(main.mouseLocation() - main.offset)
                  PlayMode.money -= 50
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!")
                  }
      }
    }
    
  }
  
  def draw = ???
}