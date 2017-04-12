package main
import scala.util._

class ToughEnemy extends Enemy{
  maxHP = 200
  HP = 10000
  speed = 1.00
  
  var shieldImg = Sprites.get("energyShield").get
  var img = Sprites.get("ufoRedDamaged").get
  var img2 = Sprites.get("ufoRedDamaged").get
 
  //Chooses a random spawn positon from all possible
  var rand2 = new Random(System.currentTimeMillis())
  var random_index = rand2.nextInt(GameMap.spawnerLocations.length)
  var result = GameMap.spawnerLocations(random_index)
  var timer = 60*7
  
  position = result*32
  
   override def update = {
    if (this.frame > 0) {
      this.frame -= 1
    }
    this.timer -= 1
    if (timer == 0) {
      HP = 100
    }
    
  }
  
  
  override def beingHit(DMG: Int) = {
    this.HP -= DMG
    if (this.frame <= 0 &&  timer >= 0) {
      this.frame = 10
    }
  }
  
  def draw(scale: Int) = {
	  if (frame > 0) {
	    main.pushMatrix()
	    main.translate(this.position.x, this.position.y)
	    main.image(img2, -scale*image.width/2, -scale*image.height/2, image.width, image.height)
	    main.popMatrix()
	  }
	  else {
	    main.pushMatrix()
	    main.translate(this.position.x, this.position.y)
	    main.rotate(this.angle.toFloat)
	    main.image(img, -scale*image.width/2, -scale*image.height/2, image.width, image.height)
	    main.popMatrix()
	  }
	  if(timer >= 0) {
      main.pushMatrix()
	    main.image(shieldImg, this.position.x - 24 , this.position.y - 24) //Hardcoded values because the Energyshield image is different size
	    main.popMatrix()
    }
	}
}