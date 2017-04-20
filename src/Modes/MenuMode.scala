package Modes

import Main._
import Graphics._

object MenuMode extends Mode {
  
  var font = main.createFont("Arial", 24, true)
  var settings = false
  var showCredits = false
  var showInstructions = false
  var img = Sprites.get("nebula").get
  var btns = Map[String, Button](
                  "start" -> new Button(400, 300, 250, 35, "Start"),
                  "settings" -> new Button(400, 400, 250, 35, "Settings"),
                  "instructions" -> new Button(400, 450, 250, 35, "Instructions"),
                  "credits" -> new Button(400, 500, 250, 35, "Credits"),
                  "exit" -> new Button(400, 550, 250, 35, "Exit"))
                  
  var settingsbtns = Map[String, Button](
                           "sound" -> new Button(400,400,250,35, "Sound"),
                           "difficulty" -> new Button(400,450, 250,35, "Difficulty"),
                           "back" -> new Button(400,500, 250, 35, "Back"))
                           
  var backButton = Map[String, Button]("back" -> new Button(400, 500, 250, 35, "Back"))
  
  def init = {
    main.background(175,175,175)
  }
  
  def terminate = {
    
  }
  
  def update(dt: Double) = {
    if (settings) {
      settingsbtns.values.foreach(_.update())
    }
    else {
      btns.values.foreach(_.update())
    }
  }
  
  def draw(dt: Double) = {
//    main.pushMatrix()
    main.image(img, 0, 0, 800, 670)
//    main.popMatrix()
    if (settings) {
      settingsbtns.values.foreach(_.draw())
    }
    else if (showCredits) {
      backButton.values.foreach(_.draw())
      main.pushMatrix()
      main.pushStyle()
      main.textAlign(3, 3)
      main.fill(main.fontColor._1, main.fontColor._2, main.fontColor._3)
      main.textFont(font)
      main.text("Made by Aleksi Siitari", main.width/2, main.height/2 - 70)
      main.text("Graphical assets from opengameart.org", main.width/2, main.height/2 - 20)
      main.text("Bonsaiheldin - energyShield effect", main.width/2, main.height/2 + 30)
      main.text("adrix89 - Ground tiles and ice/fire effects", main.width/2, main.height/2 + 60)
      main.text("Kenney (www.kenney.nl) - Enemies", main.width/2, main.height/2 + 90)
      main.text("MillionthVector - Enemies", main.width/2, main.height/2 + 120)
      main.popStyle()
      main.popMatrix()
    }
    else if (showInstructions) {
      backButton.values.foreach(_.draw())
    }
    else {
      btns.values.foreach(_.draw())
    }
  }
  
  def mousePressed(key: Int) = {
    if(key == 37) {
      if(settings) {
        if(settingsbtns("sound").isOn()) {
          
        }
        else if(settingsbtns("difficulty").isOn()) {
          
        }
        else if(settingsbtns("back").isOn()) {
          settings = false
        }
      }
      else if (showCredits) {
        if (backButton("back").isOn()) {
          showCredits = false
        }
      }
      else if (showInstructions) {
        if (backButton("back").isOn()) {
          showInstructions = false
        }  
      }
      else {
        if(btns("start").isOn()) {
          MenuMode.terminate
          PlayMode.init()
          main.mode = PlayMode
        }
        else if(btns("settings").isOn()) {
          settings = true
        }
        else if (btns("instructions").isOn()) {
          showInstructions = true
        }
        else if (btns("credits").isOn()) {
          showCredits = true
        }
        else if (btns("exit").isOn()) {
          main.exit()
        }
        
      }
    }
  }
  
  def keyPressed = {
    ???
  }
  
}