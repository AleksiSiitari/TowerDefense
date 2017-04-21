package Main

import scala.collection.mutable.Buffer
import processing.core._
import scala.io.Source._
import Graphics.Sprites


class MapTile(image: String, var x: Int, var y: Int, var solid: Boolean) {
  
  var img = Sprites.get(image).get
  var location = Vector(x*32, y*32)
  
  def draw() = {
    main.image(img, x*32, y*32, 32, 32)
  }
 
  
}

object GameMap {
  val width = 25
  val height = 19
  
  val blocks = Array.ofDim[MapTile](width, height)
  val filename = "resources/map.txt"
  var generatedMap : PImage = _
  
  var mapTiles = Buffer[String]()
  var spawnerLocations = Buffer[Vector]()
  var endLocation = Buffer[Vector]()

  /*
   * Loads the map from file
   */
  def loadMap() = {
    try {
      for(line <- scala.io.Source.fromFile(filename).getLines()) {
        mapTiles += line
      }
    } catch {
      case ex: Exception => println("Error: Corrupted map file")
    }
  }
  
  /*
   *  Draws the Groundblocks in the map
   */
  def drawGround() = {
    for(x <- 0 until width; y <- 0 until height) {
      // Draw each block individually
      if(!blocks(x)(y).solid)
      blocks(x)(y).draw
    }
  }
  /*
   * Draw the walls of the map
   * Separate functions for ground and walls to stop abilities from showing up on top of the walls etc.
   */
  def drawWalls() = {
    for(x <- 0 until width; y <- 0 until height) {
      if(blocks(x)(y).solid) {
        blocks(x)(y).draw
      }
    }
  }
  
  /*
   *  Gets the tile at coordinate x, y
   */
  def getTile(x: Double, y: Double) : MapTile = {
    var x_pos = (x/32).toInt
    var y_pos = (y/32).toInt
    
    blocks(x_pos)(y_pos)
  }

  
  def getTile(pos: Vector) : MapTile = {
    getTile(pos.x, pos.y)
  }
  /*
  // Generate the map image from the buffer
  def generateMapBackground() = {
    var pg = main.createGraphics(main.width, main.height)
    pg.beginDraw()
    for(x <- 0 until width; y <- 0 until height) {
      // Draw each block individually
      blocks(x)(y).draw(pg)
    }
    pg.endDraw()
    GameMap.generatedMap = pg
  }
  * 
  */
  
 
  def generateMap() = {
    for(x <- 0 until width; y <- 0 until height) {
      var id = "glass"
      var solid = false
    
      if(mapTiles(y)(x) == '*') {
        id = "wall"
        solid = true
      }
      else if (mapTiles(y)(x) == 'S') {
        spawnerLocations += Vector(x,y)
      }


      
    
    blocks(x)(y) = new MapTile(id, x, y, solid)
  }
    
  }

}