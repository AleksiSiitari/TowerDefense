 package Main

import scala.math._

class Vector(var x: Float, var y: Float) {
	def +(other: Vector) = new Vector(x+other.x, y+other.y)
  def -(other: Vector) = new Vector(x-other.x, y-other.y)
	def *(scale: Float) = new Vector(x*scale, y*scale)
	def *(scale: Double) = new Vector(x*scale.toFloat, y*scale.toFloat)
	def /(scale: Float) = new Vector(x/scale, y/scale)
	def /(scale: Double) = new Vector(x/scale.toFloat, y/scale.toFloat)
	def length = sqrt(x*x+y*y)
	
	/**
	 * Returns the angle from this vector to the other in radians
	 */
	def angle(other: Vector) : Float = {
	  (-atan2(this.x-other.x, this.y-other.y)).toFloat
	}
	
	/**
	 * Returns the distance to another location
	 */
	def distanceToPoint(other: Vector): Float = {
		sqrt(pow(other.x - this.x, 2) + pow(other.y - this.y , 2)).toFloat
	}
	
	/**
	 * Normalizes this vector
	 */
	def normalize() : Unit = {
	  if(length > 0) {
	    var l = length.toFloat
  	  this.x = this.x/l
  	  this.y = this.y/l
	  }
	}

	/**
	 * Returns the normalized vector
	 */
	def normalized() : Vector = {
	  var vec = Vector(this)
	  vec.normalize()
	  vec
	}
	
	override def toString() = {
	  s"Vector(${this.x}, ${this.y})"
	}
}

// Companion object for the vector class

object Vector {
  def apply(x: Int, y: Int) = {
    new Vector(x, y)
  }
  
  def apply(x: Float, y: Float) = {
    new Vector(x, y)
  }
  
  def apply(x: Double, y: Double) = {
    new Vector(x.toFloat, y.toFloat)
  }
  
  def apply(v: Vector) = {
    new Vector(v.x, v.y)
  }
}