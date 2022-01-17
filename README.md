# Project 2-1


group 10, phase 1

## Running the application

To run this program you will need to have a java version of at least 16,
you also need to have gradle installed on your pc. If these requirements
are met then you will need to change you terminal directory to the one 
where the project is located using the cd command eg:
```shell script
cd desktop/Project_2-1
```
When you have changed directory into the project where build.gradle is located
you will have to run the following command:
```shell script
gradle run
```
If this is the first time you run the program then it will first have to install all the dependencies
afterwards it should launch the program and enable you to play the game.
In the main menu you can select which player is a human player, and which is an AI.
You can let an AI play against an AI. If you wish to play again, you must restart the game.
If the AI stops playing mid game, you can relaunch it through the 'Start AI' button in the top menu.

## Rules of the game

The game is similar to normal chess in the way that it has the same pieces and same board structure. However,
the goal of the game is to simply captured the king instead of putting him in checkmate. The player can
only move the piece that he/she has rolled with the dice, so you are not free to move everything which
makes it a bit more complicated. There is no check or checkmate, only capturing the opponents king wins
you the game.

## Agents

* Random Agent: this agent plays random moves at each turn.
* Greedy Agent: this agent also plays randomly but always prioritises taking a piece.
* Search Agent: this agent uses an expectiminimax tree to choose the best move. To find the value of a leaf node it uses a rule to evaluate it.
* Cheat Agent: this agent works the same as a search except for the fact that it is allowed to cheat, i.e. It ignores the dice roles to choose its move.
* NN Agent: this agent is the same a search agent. However instead of a rule, it uses a neural network to evaluate the value of a leaf node.
* GA agent: this agent is the same a search agent. It also uses a function to evaluate nodes however instead of just summing some factors, it 
multiplies each factor by a weight. The weights were learned through a genetic algorithm.

## Authors

*Guillaume Bams* <br>
*Roman Ilic* <br>
*Piotr Lewandowski* <br>
*Dino Pasic* <br>
*Konstantin Sandfort* <br>
*Nawar Zarifeh*

## Known Issues

* The neural network agent works has been partially trained, however it does not play super strong because there is a problem in the training implementation.
* The GUI might not work if you change the display size.