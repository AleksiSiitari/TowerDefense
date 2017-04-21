package Main

object Settings {
  
  var difficulty = 1
  
  def increaseDifficulty() = {
    if(difficulty > 2) { difficulty = 1}
    else {difficulty += 1}
  }
  
  def difficultyText = {
    if (difficulty == 1) {
      "Difficulty: Easy"
    }
    else if (difficulty == 2) {
      "Difficulty: Medium"
    }
    else {
      "Difficulty: Hard"
    }
  }
  
  def difficultyMultiplier = 0.75 + 0.25 * difficulty
}