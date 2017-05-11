package Main

import scala.collection.mutable.Buffer

object Path {
  
  var points = Buffer[Vector]()
  
  val filename = "resources/path.txt"

  def loadPath = {  //OBSOLETE
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