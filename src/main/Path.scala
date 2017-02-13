package main

abstract class Path {
  val start = GameMap.spawnerLocations(0)
  val end = GameMap.spawnerLocations(0) + Vector(5*32,4*32)
  val radius: Float
}

object Path1 {
  val start = GameMap.spawnerLocations(0)*32
  val end = GameMap.spawnerLocations(0) + Vector(9*32,8*32)
  val radius:Float = 7
  
  def display() {  // Display the path.
    main.strokeWeight(radius*2);
    main.stroke(0,100);
    main.line(start.x,start.y,end.x,end.y);
    main.strokeWeight(1);
    main.stroke(0);
    main.line(start.x,start.y,end.x,end.y);
  }

}