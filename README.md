
# Stones-Game

### General Information:
- The following game is played by two players.
- The game board consists of 5 piles of stones. 
- Each pile has a random number of stones between 2-10, determined at the start of a game.
- Players take turns taking away stones from a pile.
- A player can't take all the stones from a pile, and can only take a number that divides the number of current stones in the pile.

Note: The game consists of Business logic. OpenJFX, MVC architecture and Java Persistence, Used GSON to store the game results using JSON formatting in record.json file.

### Game Goal:
- The player who makes the last move wins.

### Game site:
- The Game site consists of:
  -JavaDoc
  -JXR
  -Checkstyle
  -Sunfire report
  -JaCOCO report


### Game Database:
- The game will store the result of the game as follows:
    - The date and time when the game was completed.
    - Name of the winning player
    - Scores which the winner got

## Requirements:

Building the project requires JDK 11,17 or 18 and [Apache Maven](https://maven.apache.org/).
Note: support added in pom.xml for JDK 17.



