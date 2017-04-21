package Main

import scala.collection.mutable.Buffer

object Path {
  
  /*
  var points: Buffer[Vector] = Buffer(Vector(712.0, 70.0),
                                    Vector(721.0, 318.0),
                                    Vector(557.0, 317.0),
                                    Vector(558.0, 160.0),
                                    Vector(69.0, 162.0),
                                    Vector(70.0, 288.0),
                                    Vector(393.0, 301.0),
                                    Vector(390.0, 416.0),
                                    Vector(151.0, 430.0),
                                    Vector(150.0, 547.0),
                                    Vector(780.0, 553.0))
                                    * 
                                    */
  var points = Buffer[Vector]()
  
  val filename = "resources/path.txt"

  def loadPath = {
    try {
      for(line <- scala.io.Source.fromFile(filename).getLines()) {
        var splitLine = line.split(",")
        points += Vector(splitLine(0).toDouble, splitLine(1).toDouble)
      }
    } catch {
      case ex: Exception => println("Error: Corrupted path file")
    }
  }
}