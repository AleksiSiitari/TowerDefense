package main

abstract class Mode {
  
  
  def init
  
  def terminate
  
  def update(dt: Double)
  
  def draw(dt: Double)
  
  def mousePressed(key: Int)
  
  def keyPressed
  
}