package Main

import processing.core._
import scala.math._
import java.awt.event.KeyEvent._
import scala.util._
import Modes._


/*
 * TODO:
 * 
 * -Lisää torni ja vihollistyyppejä	(vihollinen jolla suojakilpi aluksi ?)
 * -tiedostosta luku esim .txt (yhdistä myös Path ja MapTile)
 * -lisää tasoja
 * -tekstien yhtenästäminen jne.
 * -animaatiot
 * -siirrä tornien kuvia pari pikseliä ylöspäin
 * -draw ellipse bugi, toteuta tornien kantama jollain muulla tavalla.
 * -Valikot loppuun
 * -HP indikaattori
 * -Vaihda kuva toughEnemy luokalle
 * -lisää konfiguroitavuutta
 * 
 */
object main extends PApplet {
  
  var f = createFont("Arial", 16, true)
  var fontColor = (255, 150, 0)
  var mode: Mode = EndMode
	var last_update = System.nanoTime()
	var last_draw = System.nanoTime()
	var offset = Vector(16,16)       //For compensating the offset in calculations
	var fastMode = false
 	
	def mouseLocation() = {
	  Vector(mouseX, mouseY)
	}
  
  /*
   * Return a distance between two vectors
   */
  def distance(one: Vector, another: Vector): Double = {
    sqrt(pow((another.x - one.x),2) + pow((another.y-one.y),2))
  }
	
  override def setup() : Unit = { 
  	size(800, 670)
  	frameRate(60)
  	background(100)
  	GameMap.loadMaps
    mode = MenuMode
  	mode.init
  } 

  override def draw() : Unit = {
    var cur_t = System.nanoTime()
    
    // Calculate the delta time and call the state functions accordingly
    // dt is measured in milliseconds
    
    var dut = (cur_t-last_update)/10e6
    mode.update(dut)
    last_update = System.nanoTime()
    
    var ddt = (cur_t-last_draw)/10e6
    mode.draw(ddt)
    last_draw = System.nanoTime()
    
    // Debug draw the fps and dt in ms
//    fill(0, 0, 0)
//    textFont(f,15)
//    text(s"${(100.0/(dut)).round.toString} FPS", 25, 40)

  }
	
  /*
   * Send the mouse button pressed to the mode
   */
  override def mousePressed = {
        mode.mousePressed(mouseButton)
  }
  
  override def keyPressed() = {
    if (key == 32){
      mode.keyPressed
    }
  }

	
  /**
   * The main method that makes the application run and show.
   */
  def main(args: Array[String]) {
    val frame = new javax.swing.JFrame("TowerDefense")

    frame.getContentPane().add(this)
    init
    frame.setSize(800,670)
    frame.pack
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)
    frame.setMinimumSize(new java.awt.Dimension(645, 540))
    
    if(System.getProperty("os.name").contains("Windows")) {
      // Windows disagrees with linux when it comes to window sizing
      frame.setMinimumSize(new java.awt.Dimension(800, 700))
    }
    
    frame.setResizable(false)
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    
    var dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    var x = ((dimension.getWidth() - frame.getWidth()) / 2).toInt
    var y = ((dimension.getHeight() - frame.getHeight()) / 2).toInt
    frame.setLocation(x, y);
  }
}
