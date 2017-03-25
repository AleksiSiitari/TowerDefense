package main

object Cursor {
  
  var buildAllowed = true
  
  var selected: Int = 0
  
  var towerNum = Map[Int, Buildable](1 -> new BasicTower(Vector(0,0)),
                                    2 -> new LongTower(Vector(0,0)),
                                    3 -> new Fire(Vector(0,0)))
                                  
    
  def buildPossible = {
    if (selected == 0) {false}
    else if(towerNum(selected).isInstanceOf[Towers]) {
      PlayMode.towers.forall(x => x.position != GameMap.getTile(main.mouseLocation()).location) && GameMap.getTile(main.mouseLocation()).solid//PlayMode.towers.forall(x => main.mouseLocation().distanceToPoint(x.position) > 50) && GameMap.getTile(main.mouseLocation()).solid
    }
    else {
      !GameMap.getTile(main.mouseLocation()).solid
    }
  }
  
  def clicked() = {
    if(buildPossible) {
      selected match {
        case 0 =>
          
        case 1 => if (Player.money - towerNum(1).cost >= 0 ) {
                  PlayMode.towers += new BasicTower(GameMap.getTile(main.mouseLocation()).location)//new BasicTower(main.mouseLocation()-main.offset)
                  Player.money -= towerNum(1).cost
                  Player.moneySpent += towerNum(1).cost
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + towerNum(1).cost, 255,150,0,40)
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
                  }
        
        case 2 => if (Player.money - towerNum(2).cost >= 0 ) {
                  PlayMode.towers += new LongTower(GameMap.getTile(main.mouseLocation()).location)//new BasicTower(main.mouseLocation()-main.offset)
                  Player.money -= towerNum(2).cost
                  Player.moneySpent += towerNum(2).cost
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + towerNum(2).cost, 255,150,0,40)
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
                  }
        case 3 => if (Player.money - towerNum(3).cost >= 0) {
                  PlayMode.abilities += new Fire(GameMap.getTile(main.mouseLocation()).location)
                  Player.money -= towerNum(3).cost
                  Player.moneySpent += towerNum(3).cost
                  PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + towerNum(3).cost, 255,150,0,40)
                  }
                  else {
                    PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
                  }
      }
    }
    else if (selected != 0){
      PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Can't build here", 150,150,0,14)
    }
    
  }
  
  def isOnTower = {
    PlayMode.towers.exists( x => (x.position + main.offset).distanceToPoint(main.mouseLocation()) < 10  )
  }
  
   
  
  def draw = {
    if (selected != 0 && main.mouseLocation().y < 606) {
      main.ellipseMode(2)
      if (buildPossible) {
        main.pushMatrix()
        main.fill(0, 255, 0, 100)
        main.rect(GameMap.getTile(main.mouseLocation()).x*32, GameMap.getTile(main.mouseLocation()).y*32 ,32 ,32)
        main.ellipse(GameMap.getTile(main.mouseLocation).x*32+main.offset.x, GameMap.getTile(main.mouseLocation).y*32+main.offset.x,towerNum(selected).range.toFloat, towerNum(selected).range.toFloat)
        main.popMatrix()
      }
      else {
        main.pushMatrix()
        main.fill(255, 0, 0, 100)
        main.rect(GameMap.getTile(main.mouseLocation()).x*32, GameMap.getTile(main.mouseLocation()).y*32 ,32 ,32)
        main.ellipse(GameMap.getTile(main.mouseLocation).x*32+main.offset.x, GameMap.getTile(main.mouseLocation).y*32+main.offset.y, towerNum(selected).range.toFloat, towerNum(selected).range.toFloat)
        main.popMatrix()
      }
    }
  }
}