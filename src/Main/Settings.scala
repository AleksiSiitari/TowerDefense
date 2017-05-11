package Main

object Settings {
  
  var difficulty = 1
  var mapNum = 0
  var availableMaps = GameMap.maps.keys.toArray
  var selectedMap = availableMaps(mapNum)
  
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
  
  def changeMap = {
    if (mapNum >= availableMaps.size - 1) {
      mapNum = 0
    }
    else {
      mapNum += 1
    }
    GameMap.mapTiles = GameMap.maps(availableMaps(mapNum))
    Path.points = GameMap.paths(availableMaps(mapNum))
  }
  
  def mapText = "Current map: " + availableMaps(mapNum).split('.').head
  
  def difficultyMultiplier = 0.75 + 0.25 * difficulty
}