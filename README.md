
# Project Overview

This project simulates a card-based game, employing object-oriented programming concepts. It features distinct modules to define card behaviors, manage game logic, and facilitate player interactions.

## File Structure and Class Descriptions

### Playing Cards Module (`playingcards`)

This module defines all card types and their unique abilities. Each card inherits from the base `Cards` class and provides specific implementations for its behavior.

#### `Cards.java`:
Base class for all card types, encapsulating shared properties and methods like:
- `getManaCost()`: Returns the mana cost of the card.
- `getAttackPower()`: Retrieves the attack power of the card.
- `useAbility(target)`: Placeholder method to be overridden by subclasses to implement card-specific abilities.

#### `Disciple.java`:
Represents a card with a healing ability.
- `useAbility(target)`: Heals the specified target card, increasing its health.

#### `EmpressThorina.java`:
A powerful card with a board-clearing ability.
- `useAbility(target)`: Eliminates all enemy cards on the field.

#### `GeneralKocioraw.java`:
Buffs allied cards.
- `useAbility(target)`: Increases the attack power of all allied cards.

#### `KingMudface.java`:
Specializes in healing allied cards.
- `useAbility(target)`: Heals all allied cards on the field.

#### `LordRoyce.java`:
A card focused on debuffing the strongest enemy card.
- `useAbility(target)`: Reduces the attack of the strongest enemy card.

#### `Miraj.java`:
Unique ability to swap its health with an enemy card.
- `useAbility(target)`: Exchanges health points with the specified target card.

#### `TheCursedOne.java`:
Alters an enemy card's health and attack.
- `useAbility(target)`: Switches the attack and health values of the target card.

#### `TheRipper.java`:
Inflicts a debilitating effect on enemy cards.
- `useAbility(target)`: Reduces the attack power of an enemy card.

### Main Module (`main`)

This module orchestrates the game mechanics, handling the field, players, and match logic.

#### `Main.java`:
Entry point of the application. Initializes the game, sets up players and decks, and starts the matches.

#### `Coords.java`:
Utility class for managing coordinate-based operations, likely related to the positioning of cards on the game board.
- `getX()`, `getY()`: Accessors for coordinate values.

#### `Deck.java`:
Manages a player's collection of cards.
- `shuffle()`: Shuffles the deck to randomize card order.
- `drawCard()`: Draws a card from the deck for the player.

#### `Field.java`:
Represents the game board where cards are played.
- `placeCard(card, coords)`: Places a card at a specific position.
- `removeCard(coords)`: Removes a card from the field.
- `getCardAt(coords)`: Retrieves the card at a given position.

#### `Player.java`:
Represents a player in the game.
- `drawCard()`: Adds a card to the player’s hand from their deck.
- `playCard(card, coords)`: Plays a card from the hand onto the field.
- `takeDamage(amount)`: Reduces the player's health by the specified amount.

#### `Test.java`:
Contains test cases or demonstrations for various game features.

#### `Match.java`:
Central class for simulating a match between two players.

### Key Observations from `Match.java`

#### Field and Player Setup:
- It initializes `Field` and `Player` objects for managing the game board and the two players.
- Players are instantiated using decks provided via input.

#### Constants:
- Defines constants for player rows on the board (e.g., `PLAYER2_FRONT_ROW`, `PLAYER1_BACK_ROW`).
- Includes a `MAX_MANA` constant, which likely represents the maximum mana a player can accumulate.

#### Game State Variables:
- `playerTurn`: Tracks which player's turn it is.
- `turnCounter`: Counts the number of turns that have elapsed.
- `gameEnded`: Boolean flag indicating whether the game has concluded.

#### Constructor:
- Accepts an `Input` object to initialize game components such as players and the field.

From the snippet, the class seems focused on coordinating player actions, managing turns, and monitoring the game's progression.

### Key Methods in `Match.java`

#### `startGame(StartGameInput gameInput)`:
Prepares the match by:
- Setting the starting player.
- Selecting decks and heroes for both players.
- Shuffling decks using a provided seed.
- Drawing initial cards.
- Allocating initial mana to each player.

#### `startRound()`:
Executes setup tasks for each round:
- Updates mana for both players, capped at `MAX_MANA`.
- Draws one card for each player.
- Resets attack statuses on the field.
- Resets the "used" status of player heroes, allowing them to act in the new round.

#### `resetMatch()`:
Resets all match elements:
- Clears the game board.
- Resets player states (e.g., mana, cards, health).
- Resets the turn counter.
- Sets the `gameEnded` flag to `false`.

#### `playing(ArrayList<ActionsInput> input)`:
Processes a list of actions for the match:
- Uses an `ObjectMapper` to handle and log game events.
- Iterates over `ActionsInput` to perform specific game actions.
- Returns results as an `ArrayNode`, likely representing the outcomes of the actions.

### Observations
- The `startGame()` and `startRound()` methods emphasize setting up and managing the state of the game and players.
- `resetMatch()` ensures the game can be replayed without lingering states.
- `playing()` is central to executing the match's logic, iterating through player actions and applying them to the game state.

### Conclusion

The `Match.java` class serves as the core controller of the game's lifecycle, implementing mechanisms for state initialization, turn-based progression, and action execution in adherence to the game's rules. By leveraging encapsulated interactions with `Player`, `Field`, and `Cards`, it ensures consistent state management through methods such as `startGame()`, `startRound()`, and `playing()`. Its design integrates input parsing, resource management (e.g., mana allocation and card drawing), and gameplay logic, enabling systematic turn and round transitions.

The use of constants like `MAX_MANA` and specific row identifiers ensures a clear and efficient mapping of gameplay parameters. Additionally, the modularity provided by the `playing()` method, which processes actions dynamically, positions the class as a flexible and extensible component for future enhancements. Overall, `Match.java` demonstrates a rigorously defined structure that provides precise control over gameplay flow, making it a pivotal element in the system's architecture.
