
# Tic-Tac-Toe Application

## Overview
This is a distributed Tic-Tac-Toe game application developed in **Java** using **Spring Boot**. The application allows two players, each running a separate instance of the game, to play against each other. The game instances communicate with each other, exchanging moves, and ensure synchronization of the game state, even in cases of connection disruptions. A **Game Server** component is introduced to orchestrate the game, ensure the validity of moves, and handle synchronization.
  
## Assignment Specification
This project fulfills the following assignment:
> Develop a Tic-Tac-Toe application that can play a 3x3 crosses and zeros game between two applications running on different ports or nodes. The instances must choose which player will play crosses and which will play zeros, take turns making moves, and report these moves to the other instance. The game will stop when a winner is determined or a tie is declared. The application should:
>
> 1. Be developed using a JVM language.
> 2. Use any technology of the developer’s choice.
> 3. Follow best practices for code formatting, testing, and documentation.
> 4. Handle broken and re-established connections between instances.
> 5. Provide a REST or HTML interface for retrieving the game state at any time.
> 6. Ensure both instances show the same game state or indicate an inconsistency.
> 7. Implement a delay to allow tracking of moves.
> 8. Make moves according to game rules.
> 9. Prevent invalid moves from another instance.
> 10. Optionally, another turn-based game can be implemented (e.g., Chess, Naval Combat).
> 11. The game’s algorithm can be random, but synchronization between instances is crucial.

## Technologies and Patterns Used
- **JVM Language**: Java
- **Framework**: Spring Boot (version 3.3.4)
- **Service Discovery**: Consul.
- **Load Balancing**: Dummy implementation included using the instances retrieved from Consul, easily extendable.
- **Design Patterns**:
  - **State Design Pattern**: Manages the different stages of the game.
  - **Producer-Consumer Pattern**: Distributes players to game rooms.
  - **Command Design Pattern**: Decouples invokers and targets for game actions, improving flexibility.

## Prerequisites
To run the application locally, ensure the following are installed:

- **Java 17** or higher
- **Maven 3.8** or higher

### Starting a Player Instance
Each player application instance should be started with custom Java arguments to define the username and port. Example:
```bash
-Dserver.port=8081 -Dplayer.username=player1
```

Similarly, for the second player, adjust the port and username as needed:
```bash
-Dserver.port=8082 -Dplayer.username=player2
```

Both players will then connect to the central Game Server, which handles orchestration and synchronization.

### Game Server
The Game Server coordinates the moves and ensures the game rules are followed. It can handle disconnections and restore the game state when the connection is re-established.

### Game Server heartbeats
The game server application sends heartbeats to a configured STOMP topic. Clients can subscribe to that topic and get useful information about the state of the server = game rooms, players, player status (online/offline) as well as the game field for each room.

## User Management
Currently, **user management** is a dummy implementation. No authentication or authorization is enforced. Future versions may include secure user authentication and session management.

## Future Extensions
The following improvements are planned for future releases:

1. **Persistent Game State**: Store the state of game rooms in persistent storage, allowing for recovery in case of server failure.
2. **Integration Testing**: Build a puppet Spring Boot application that connects to multiple player applications (in testing mode), monitors and validates messages between them and the server.
3. **Docker Support**: Scripts for building and running the application components in Docker containers, ensuring easy deployment and testing.

## How to Build and Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/tic-tac-toe.git
   ```
2. Navigate to the project directory:
   ```bash
   cd tic-tac-toe
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Start the Game Server:
   ```bash
   java -jar game-server.jar
   ```
5. Start Player 1 instance:
   ```bash
   java -Dserver.port=8081 -Dplayer.username=player1 -jar player-app.jar
   ```
6. Start Player 2 instance:
   ```bash
   java -Dserver.port=8082 -Dplayer.username=player2 -jar player-app.jar
   ```

## Testing and Code Quality
- Unit tests have been written to ensure the robustness of the game logic.
- **Mockito** is used for mocking components and simulating game logic.
- Future plans include **more extensive unit and integration testing** as outlined above.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request on the GitHub repository.

## License
This project is licensed under the IM Solutions License.

## Contact
For any questions or support, feel free to contact [your email address].

---

Enjoy playing Tic-Tac-Toe! 🎮
