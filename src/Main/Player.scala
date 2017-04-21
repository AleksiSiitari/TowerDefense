package Main

/*
 * Object to keep track of the players stats and lives etc.
 */
object Player {
  var money = 100
  var moneySpent = 0
  var score = 0
  var enemiesSlain = 0
  var lives = 5
  
  def isAlive = (Player.lives > 0)
  
  /*
   * Initialize the Player resetting its values
   */
  def init = {
    money = 100
    moneySpent = 0
    score = 0
    enemiesSlain = 0
    lives = 5
  }
}