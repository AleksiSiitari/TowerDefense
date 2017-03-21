package main

object Cursor {
  
  var buildAllowed = true
  
  var selected: Int = 1
  
  def buildPossible = {
    PlayMode.towers.forall(x => x.position != GameMap.getTile(main.mouseLocation()).location) && GameMap.getTile(main.mouseLocation()).solid//PlayMode.towers.forall(x => main.mouseLocation().distanceToPoint(x.position) > 50) && GameMap.getTile(main.mouseLocation()).solid
  }
  
  def clicked = {
    if(buildPossible) {
      selected match {
        case 0 =>
          
        case 1 => if (PlayMode.money - 50 >= 0 ) {
                  PlayMode.towers += new BasicTower(GameMap.getTile(main.mouseLocation()).location)//new BasicTower(main.mouseLocation()-main.offset)
                  PlayMode.money -= 50
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + 50, 255,150,0,40)
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
                  }
        
        case 2 => if (PlayMode.money - 200 >= 0 ) {    //TODO: fix hardcoded values!
                  PlayMode.towers += new LongTower(GameMap.getTile(main.mouseLocation()).location)//new BasicTower(main.mouseLocation()-main.offset)
                  PlayMode.money -= 200
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + 200, 255,150,0,40)
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
  
   
  
  def draw = {
    if (selected != 0 && main.mouseLocation().y < 606) {
      if (buildPossible) {
        main.pushMatrix()
        main.fill(0, 255, 0, 100)
        main.rect(GameMap.getTile(main.mouseLocation()).x*32, GameMap.getTile(main.mouseLocation()).y*32 ,32 ,32)
        main.popMatrix()
      }
      else {
        main.pushMatrix()
        main.fill(255, 0, 0, 100)
        main.rect(GameMap.getTile(main.mouseLocation()).x*32, GameMap.getTile(main.mouseLocation()).y*32 ,32 ,32)
        main.popMatrix()
      }
    }
  }
}