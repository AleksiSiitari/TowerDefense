package main

object MenuMode extends Mode {
  
  var settings = false
  var showInstructions = false
  var img = Sprites.get("nebula").get
  var btns = Map[String, Button](
                  "start" -> new Button(400, 300, 250, 35, "Start"),
                  "settings" -> new Button(400, 400, 250, 35, "Settings"),
                  "instructions" -> new Button(400, 450, 250, 35, "Instructions"),
                  "exit" -> new Button(400, 500, 250, 35, "Exit"))
                  
  var settingsbtns = Map[String, Button](
                           "sound" -> new Button(400,400,250,35, "Sound"),
                           "difficulty" -> new Button(400,450, 250,35, "Difficulty"),
                           "back" -> new Button(400,500, 250, 35, "Back"))
  
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
    main.pushMatrix()
    main.image(img, 0, 0, 800, 670)
    main.popMatrix()
    if (settings) {
      settingsbtns.values.foreach(_.draw())
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