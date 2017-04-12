package main

object EndMode extends Mode {
  
  var f = main.createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on
  var img = Sprites.get("nebula").get
  var btns = Map[String, Button]("menu" -> new Button(400, 400, 250, 35, "Menu"))
  
  def init = {}
  
  def terminate = {}
  
  def update(dt: Double) = {
    btns.values.foreach(_.update())
  }
  
  def draw(dt: Double) = {
    main.pushMatrix()
    main.image(img, 0, 0, 800, 670)
    main.popMatrix()
    btns.values.foreach(_.draw())
    main.textAlign(3, 3)
    main.textFont(f,35)
    main.fill(204, 153, 0)
    main.text("You lost", 400, 150)
    main.textFont(f,30)
    main.text("Score: " + Player.score, 400, 200)
    main.text("Money Spent: $ " + Player.moneySpent, 400, 250)
    main.text("Enemies Slain: " + Player.enemiesSlain, 400, 300)

  }
  
  def mousePressed(key: Int) = {
    if (key == 37) {
      if(btns("menu").isOn()) {
        EndMode.terminate
        MenuMode.init
        main.mode = MenuMode
      }
    }
  }
  
  def keyPressed = ???
  
}