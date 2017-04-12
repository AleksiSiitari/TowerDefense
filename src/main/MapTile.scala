package main
import scala.collection.mutable.Buffer
import processing._


class MapTile(image: String, var x: Int, var y: Int, var solid: Boolean) {
  
  var img = Sprites.get(image).get
  var location = Vector(x*32, y*32)
  
  def draw() = {
    main.pushMatrix()
    main.image(img, x*32, y*32, 32, 32)
    main.popMatrix()
  }
 
  
}

object GameMap {
  val width = 25
  val height = 19
  
  val blocks = Array.ofDim[MapTile](width, height)
  //var generatedMap : PImage = _
  
  var mapTiles = Buffer(
      "*************************",
      "SSS---------------------*",
      "SSS---------------------*",
      "*********************---*",
      "*------------------**---*",
      "*------------------**---*",
      "*---************---**---*",
      "*---************---**---*",
      "*-------------**---**---*",
      "*-------------**--------*",
      "**********----**--------*",
      "**********----***********",
      "***-----------***********",
      "***-----------***********",
      "***----******************",
      "***----******************",
      "***---------------------*",
      "***--------------------E*",
      "*************************"
  )
  
  var spawnerLocations = Buffer[Vector]()
  var endLocation = Buffer[Vector]()
 
/*
  for(x <- 0 until width; y <- 0 until height) {
    var id = "tile_mud"
    var solid = false
    
    if(mapTiles(y)(x) == '*') {
      id = "tile_wall"
      solid = true
    }
    
    blocks(x)(y) = new MapTile(id, x, y, solid)
  }
  * 
  */

  
  // Draws the blocks in the map
  def drawGround() = {
    for(x <- 0 until width; y <- 0 until height) {
      // Draw each block individually
      if(!blocks(x)(y).solid)
      blocks(x)(y).draw
    }
  }
  
  def drawWalls() = {
    for(x <- 0 until width; y <- 0 until height) {
      if(blocks(x)(y).solid) {
        blocks(x)(y).draw
      }
    }
  }
  
  // Gets the tile at coordinate x, y

  def getTile(x: Double, y: Double) : MapTile = {
    var x_pos = (x/32).toInt
    var y_pos = (y/32).toInt
    
    blocks(x_pos)(y_pos)
  }

  
  def getTile(pos: Vector) : MapTile = {
    getTile(pos.x, pos.y)
  }
  /*
  // Generate the map background from the layout set above
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
  */
 
  def generateMap() = {
    for(x <- 0 until width; y <- 0 until height) {
      var id = "grass2"
      var solid = false
    
      if(mapTiles(y)(x) == '*') {
        id = "wall2"
        solid = true
      }
      else if (mapTiles(y)(x) == 'S') {
        spawnerLocations += Vector(x,y)
      }
       else if (mapTiles(y)(x) == 'E') {
         id = "finish"
         endLocation += Vector(x,y)
      }

      
    
    blocks(x)(y) = new MapTile(id, x, y, solid)
  }
    
  }

}