package main
import scala.util.Random

class BasicEnemy extends Enemy {
  maxHP = 100
  HP = 100
  speed = 1.0
 
  //Chooses a random spawn positon from all possible
  var rand2 = new Random(System.currentTimeMillis())
  var random_index = rand2.nextInt(GameMap.spawnerLocations.length)
  var result = GameMap.spawnerLocations(random_index)
  
  position = result*32
  
  
  def draw(scale: Int) = {
	  main.pushMatrix()
	  main.translate(this.position.x, this.position.y)
	  main.rotate(this.angle.toFloat)
	  main.image(image, -scale*image.width/2, -scale*image.height/2, image.width*scale, image.height*scale)
	  main.popMatrix()
	}
  
}