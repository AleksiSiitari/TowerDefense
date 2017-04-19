package Main

object Player {
  var money = 100
  var moneySpent = 0
  var score = 0
  var enemiesSlain = 0
  var lives = 5
  
  def isAlive = (Player.lives > 0)
  
  def init = {
    money = 100
    moneySpent = 0
    score = 0
    enemiesSlain = 0
    lives = 5
  }
}