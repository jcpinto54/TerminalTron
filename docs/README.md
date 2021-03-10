# LPOO_78 - Tron Remake
Project name: Tron Remake (Terminal Tron)

## Description: 
Game inspired by Tron, in which each player is a car and that leaves a trail with its color wherever it goes. To win you have to get the other players to cross your track. 

![TronRemake](Images/TronRemakeGIF.gif)

## Functionalities
### Implemented features
**Menu** - A menu to access the game, scores, settings and to end the program.

**Arena construction** - 2 cars and walls on the edges of the screen are added to the initial construction of the arena.

**Car movement** - Cars move automatically according to their orientation. The automatic movement of the cars is done with the help of an auxiliary * thread *.

**Collision detection with walls** - The game detects and processes collisions between cars and walls and between two cars. When a car collides with a wall or another car, it loses a life and appears in a new random position on the map.

**Different types of cars** - Cars leave different tracks (line, circle). We have implemented a wide variety of colors for cars.

**Track cars** - Cars leave a track of walls by the positions they pass. The track is configurable and its color is the same as the color of the car that left the track.

**Scores table** - Table that stores the scores for all games: the winner (player number) and his score (proportional to the duration of the game).

All the code that we consider relevant to test, is being tested.

### Functionality images implemented
#### Main menu:

![MainMenu](Images/MainMenu.png)

#### Options menu:

![Options](Images/Options.png)

#### Three best scores ever:

![HighScores](Images/HighScores.png)

#### Match:

![InGame](Images/InGame.png)

#### Pause menu:

![Pause](Images/Pause.png)

#### End of game screen:

![GameOver](Images/GameOver.png)

### Features to implement

**Two game modes** - One mode in which the track remains, and another in which the track disappears (second mode not implemented).

**Multiplayer** - Possibility to play with more than 2 players.

## Architectural Model
### MVC - Model-View-Controller
The architectural model used was the MVC, an architectural standard widely used in *user interfaces*.
Its development has taken place according to the following *flow chart*, present in the notes of prof. André Restivo.

![MVCFlowChart](Images/MVC.png)

## Design Patterns
### Problem 1
#### Description
The game can be considered to have several different states. One [status] (../ src / main / java / Controller / State / GameState.java) for each menu that the user can access and one more status for the game: *Main Menu*, *Options Menu*, *Pause Menu*, *Game Over*, *High Scores*, *In Game*.
#### Pattern: State
This pattern allows us to deal with all the different states of the game in the same way. The transition of states happens through user input, except for the transition *InGameState* -> *GameOverState* which is caused by the end of the game. Note that this pattern allows each state to function independently.
#### Implementation
Our UML implementation:

![GameStatePattern](Images/GameState.png)

The classes for this *design pattern* are as follows:

* [**MenuState**](../src/main/java/Controller/State/MenuState.java)

* [**OptionsState**](../src/main/java/Controller/State/OptionsState.java)

* [**HighScoresState**](../src/main/java/Controller/State/HighScoresState.java)

* [**InGameState**](../src/main/java/Controller/State/InGameState.java)

* [**PauseState**](../src/main/java/Controller/State/PauseState.java)

* [**GameOverState**](../src/main/java/Controller/State/GameOverState.java)

For the sake of simplicity, we do not show that each concrete state is associated with a different view (an object). The method [*draw*](../src/main/java/View/State/MenuView.java) of each state (with the exception of *InGameState*) calls the *draw* method of the respective view. In turn, the status *InGameState* leaves the discretion of [*ArenaController*](../src/main/java/Controller/ArenaController.java) when designing the screen. The *ArenaController* class is not represented in the diagram for simplicity, but it is an object associated with *InGameState*, which as its name indicates controls what happens in the game.
#### Consequences
One of the consequences of this *Design Pattern* is that the program follows a state machine. The resulting state machine:

![GameStateDiagram](Images/GameStateDiagram.png)

As already mentioned, at the application level, we do not distinguish different states. Therefore, we treat all states as equal. This means that everything related to the functioning of each state must be within its object-state and its view. In this way, this standard helps our program to comply with the *Single Responsibility* principle of the *SOLID* principles.

As the operation of all states is independent, to add a new state, a new menu for example, we can do it without changing the code of the other states, but just adding an object-state, a view, and at least one transition. Thus, this standard contributes to the *Open Closed* principle of the *SOLID* principles.

### Problem 2
#### Description
Due to the fragmentation of the game into several classes, it is necessary to maintain consistency between the different objects so that it is possible to represent the *view* of the arena from the data of the *ArenaModel*. Thus, in order not to draw the view without modifications, it is necessary to implement a type of *View* observer. Thus, it is only necessary to redesign *View* when in fact there have been changes to *ArenaModel*, that is, when observers are notified of any changes to the objects of the observed class.
#### Pattern: Observer
This standard implements an abstract class of objects that will be observed (*Observable*) by objects that implement an *Observer* interface. As the names indicate, the *Observable* class will be "observed" by an object that implements the *Observer* interface. The use of this *design pattern* allows us to treat different observers in the same way, although we have only implemented an object of the class *Observer*.
#### Implementation
Our UML implementation:

![ObserverPattern](Images/ObserverPattern.png)

The implementation of the pattern in our code was done as follows: We added an interface [*Observer*] (../src/main/java/Observer/Observer.java) and an abstract class [*Observable*] (../src/main/java/Observer/Observable.java). In the context of our problem, we made the *ArenaModel* class a class derived from *Observable* and the *ArenaView* class started to implement the *Observer* interface. In addition to this, we also had to add *ArenaView* to *observers* of *ArenaModel*. Thus, whenever we make a change to the objects belonging to the *ArenaModel* class, we notify the observers (in this case the only observer is *ArenaView*) and each one will adapt their data representation. In the context of our problem, *ArenaView* is notified whenever cars advance or cars change direction.

#### Consequences
Applying this standard helps the code comply with the *Open Closed* principle of the *SOLID* principles. This is because if we want to add a new observer of the *ArenaModel* class, it would be simple to do: we would just have to create a new class that would implement the *Observer* interface and that had some kind of configured view. We also needed to add an object of the new class to the observers of *ArenaModel*. Thus, to add new features to the program, in this case different graphical interfaces, we would not have to change code already made, but just add new code.

## Code Smells / Development issues
### Problem 1 - *Code Smell*
#### Description
When designing a car, we need to know if it is the car of player 1 or player 2 so that we can use the right color. We can extend this problem to the design of the walls as well. This problem resulted in a complex *switch statement*.
#### Refactor: Replace Conditional with Polymorphism
We added an *color* attribute to [*Car*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/Model/Car.java#L71) and the [*Wall*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/Model/Wall.java#L12). This resolution adds the *getColor()* method to both objects allowing us to obtain the color to be used when drawing the objects.
### Problem 2 - *Code Smell*
#### Description
In the View package, we had two classes that were removed called *CarView* and *WallView*. Both of these classes had only 1 method *drawCar* and *drawWall* respectively. They had no attributes, they just delegated work. So we were looking at a *Middle Man*.
#### Refactor: Remove Middle Man
In view of this *code smell* we passed the functions *drawCar* and *drawWall* to the class [*ArenaView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/ main/java/View/ArenaView.java#L106), where the entire arena is drawn on the screen. The *CarView* and *WallView* classes have been eliminated.
### Problem 3 - *Code Smell*
#### Description
The canCarMove function checks whether a position collides with any *arena* wall. It belonged to a CarController class. Contrary to what the name implies, it does not access data from a *Car* object. We were faced with the *code smell* "Feature Envy".
#### Refactor: Move Method
The function now belongs to the *PlayerPositionUpdater* class. As CarController only had this function, we eliminated the class.

**Update to the final version**: The canCarMove function has become the [*checkCollision*] function (https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/ java/Controller/PlayerPositionUpdater.java#L31) of the *PlayerPositionUpdater* class. The function started to distinguish different types of collisions: collisions between cars and collisions between a car and a wall.
### Problem 4 - Development problem
#### Description - Multi-Thread Memory Access
In the *InGameState* state, the program has two *threads* running. One *thread* is reading user input to change the directions of the cars and the other *thread* is updating the positions of the cars depending on the respective directions for each car. This form of "work" resulted in random *crashes* of the program by accessing the same memory simultaneously.
#### Solution - Implementing a Mutex
In order to synchronize the threads, we implemented a *mutex*, the class [*MutexSyncronize*](../src/main/java/Controller/MutexSyncronize.java). It works in a very simple way: the class has a *static* indicator that represents the *mutex* block. Before all accesses the memory of each *thread* we block the *mutex* and when we stop accessing the memory, we unlock the *mutex*. Note that if *mutex* is blocked when we access it, *thread* will enter an [empty cycle](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/Controller/MutexSyncronize.java#L8) until *mutex* is unlocked by the other *thread*. In this way, we avoided simultaneous access to the same memory, and the program stopped crashing.
### Problem 5 - *Code Smell*
#### Description
In the menu states where the user interface is done in navigation mode (*MainMenu*, *OptionsMenu*, *PauseMenu*), we had problems with the way we implemented the selected option transition. We initially approached this problem with *switch statements* and therefore we were faced with a *code smell*.
#### Refactor: Extract Method
The refactor most similar to what we did is the *Extract Method*, since we solved the problem with a mathematical expression and replaced the *switch statement* with two [methods](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/MenuView.java#L23). Considering that we know which option is selected with an integer (we will call it *selected*) that its maximum value varies with the number of options that a menu has, the mathematical function works as follows: if we want to go up in the menu, let's decrement *selected* and assign yourself the value of the rest of the division by the number of menu options. To get down in the menu, we do the same process except that we increment *selected*.

For the [options menu](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/OptionsView.java) we have 4 dimensions of this problem: we navigate about which option to change, choosing the color of player 1, choosing the color of player 2 and choosing the tracks for each player (as there are only two different ones, if we change one of them the other one will be chosen by excluding parts).

**Note**: Mathematically the rest of the division of a negative number by a positive number results in a positive number, but the implementation of *Java* does not respect this rule. For that, we had to implement this detail.
### Problem 6 - *Code Smell*
#### Description
When implementing the *State* standard that was explained earlier, we noticed that it was necessary that the game data were not only present for the *InGameState* state, since if that happened when we transitioned to the *PauseState* state when we switched back to the state of the game, the game resumed. In view of this problem, we decided that it was pertinent that the game data (class *ArenaModel*) were associated with the class *Game* and thus the game is "moved" between all states. However, we come across several *code smells* of the type *Data Clumps*. The game state class was associated with the *Game* class in addition to being associated with the game view and data. The game view and data were obtained from *getters* of the *Game* class. We were faced with this:

! [Problem6](Images/Problema6.png)

#### Refactor: *Introduce Parameter Object*
Our solution to this problem was as follows:

! [Solution6](Images/Solução6.png)

Thus, we no longer have repeated objects, and the code becomes more "changeable", in the sense that for the eventual case of replacing any of the *ArenaModel* or *ArenaView* objects, we only have to replace the object in the *Game class*.
### Problem 7 - Development problem
#### Description - Time counting only in specific states
To implement the functionality to show the time elapsed since the beginning of the game, we had to resort to the *Date* class belonging to *Java*. The problem arose when we noticed that we just wanted the program to increase the elapsed time counter when the program's state was *InGameState*. We started counting the elapsed time when we built the *ArenaModel* object, and since *ArenaModel* is associated with the *Game* class and the *Game* and *ArenaModel* objects are built at the beginning of the game, in practice, the elapsed time it was incremented when we were in any state of the game.
#### Solution - Implementation of a *Timer* object
Our approach to this problem was to add a class [*Timer*](../src/main/java/Model/Timer.java) that allows you to interrupt and resume the timing with two methods *start* and *stop*. This class was associated with the *ArenaModel* class, since we consider that the time that has elapsed since the beginning of the game is a "dice" of the game.
### Problem 8 - *Code Smell*
#### Description
In the early stages of the game's development, we thought it was pertinent for the character by which a wall is represented to be part of the object *Wall*. Later, we changed our mind and made the character depend on the "context" in which the wall is inserted. If it is a boundary wall of the arena this character is represented by a space, if it is the wall of a car track, it depends on the car's settings. When implementing this idea, we left the walls with a *getWall* method that always returned the character of a boundary wall.
#### Refactor: *Remove Method*
The *getWall* method was removed and we changed the representation of the boundary walls so that they were represented by the constant character [''](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src /main/java/View/ArenaView.java#L120). Car walls started to be represented together with cars to have access to [wall type](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/ArenaView.java#L112) from the car's track.

## Potential *Code Smells*
### 1. *Switch Statements* for reading and processing user keys
Reading keyboard input and processing it is done with *switch statements*. This means that if we want to add new controls to the game we will have to change code spread across several classes, violating the *Open Closed* principle of the *SOLID* principles. In this situation, we are potentially going to generate a *shotgun surgery code smell*. However, we consider these *switch statements* to be essential.

Reading keyboard input is done in the classes:

* [*ArenaView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/ArenaView.java#L55)

* [*MainMenuView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/MainMenuView.java#L27)

* [*HighScoresView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/HighScoresView.java#L50)

* [*GameOverView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/GameOverView.java#L44)

* [*OptionsView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/OptionsView.java#L49)

* [*GameOverView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/PauseView.java#L26)


### 2. *Selected* from problem 5
Although we have solved the problem of *switch statements* for menu navigation, when we select an option (press Enter) we still have *switch statements*. The ideal solution for this would be to implement another *State* standard: the different states would be the various options that can be selected. We did not implement this solution because we thought it would be "killing a fly with a cannonball". We would add complexity to the code and it would be a much less readable solution than our implementation. However, it would promote the *Single Responsibility* and *Open Closed* principles of the *SOLID* principles.

* *Switch statements* by pressing *Enter*:

* [*MainMenuView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/MainMenuView.java#L36)

* [*PauseMenu*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/PauseView.java#L39)

* 4-dimensional problem - [*OptionsView*](https://github.com/FEUP-LPOO/lpoo-2020-g78/blob/master/src/main/java/View/State/OptionsView.java#L60)

## Testing
Test coverage obtained by *IntelliJ*.

![Coverage](Images/IntelliJCoverage.png)

Link to *IntelliJ testing report* -> https://github.com/FEUP-LPOO/lpoo-2020-g78/tree/master/docs/IntelliJ%20Testing%20Report

We also used *Pitest* to check the robustness of the tests created.

![CoveragePitest](Images/MutationTesting.png)

Link to *mutation testing report* -> https://github.com/FEUP-LPOO/lpoo-2020-g78/tree/master/docs/Mutation%20Testing%20Report

## Self-evaluation
50% - João Castro Pinto
50% - Miguel Silva