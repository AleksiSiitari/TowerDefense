package Main

import org.junit.Test 
import org.junit.Assert._
import scala.math._

class VectorTest {
  val x = Vector(5.0, 7.0)
  val y = Vector(6.0, -2.0)
  
  @Test def plus() {
    var z = x + y
    assertEquals("Wrong result", Main.Vector(11.0, 5.0).toString, z.toString)
  }
  
   @Test def minus() {
    var z = x - y
    assertEquals("Wrong result", Main.Vector(-1.0, 9.0).toString, z.toString)
  }
   
   @Test def multiplication() {
    var z = x * 3.0
    assertEquals("Wrong result", Main.Vector(15.0, 21.0).toString, z.toString)
  }
   
   @Test def division() {
    var z = y / 2.0
    assertEquals("Wrong result", Main.Vector(3.0, -1.0).toString, z.toString)
  }
   
   @Test def distance() {
    var z = x.distanceToPoint(y)
    assertEquals("Wrong result", (Math.sqrt(Math.pow(x.x - y.x, 2) + Math.pow(x.y - y.y, 2))).toString.take(4), z.toString.take(4))
  }
   
   @Test def normalize() {
    var z = Vector(3.0, 4.0)
    assertEquals("Wrong result", Vector(3.0/5.0, 4.0/5.0).toString, z.normalized().toString)
  }
}