

## Table of Contents
## Project Description
Simple RestApi Client App that connects and list messages. Made out of Kotlin and JetPackCompose.
The `NanoWebsocketClient` is responsible for establishing a WebSocket connection with the server, sending and receiving messages, and handling connection status.

## Technologies Used
UI made with JetPack Compose.

## Installation
To Build Application
- Java 17
- Android Gradle Plugin Version: 8.0.1
- Gradle Version: 8.0
  Configure your Android Studio (Flamingo).

## Usage
1. Create an instance of `NanoWebsocketClient` by calling `getInstance()`.

2. Connect to the WebSocket server by calling `connect()`.

3. Send messages to the server using `sendMessage(message: String)` or `sendDbMessage(message: DbMessage)`.

4. Receive messages from the server by subscribing to the WebSocket connection using `observeWebSocketConnection()`.

5. Disconnect from the WebSocket server by calling `disconnect()`.

6. Retry connection on failure by calling `retryConnection()`.

**Attention**: Before connecting to the Api, you need to start the Service (CommSvc - Service App from Autotrac).

### Example

val websocketClient = NanoWebsocketClient.getInstance()
websocketClient.connect()

// Send a message
websocketClient.sendMessage("Hello, server!")

// Receive messages
websocketClient.observeWebSocketConnection()
    .subscribe { isConnected ->
        if (isConnected) {
            // WebSocket connection is established
            // Start sending requests or handle received messages
        } else {
            // WebSocket connection is closed or failed
            // Handle the connection failure
        }
    }
## API Endpoints
### Properties

- `serverUrl`: The URL of the WebSocket server.

- `webSocketClient`: The WebSocket client instance.

- `gson`: An instance of the Gson library for JSON serialization/deserialization.

- `TAG`: A constant representing the log tag for logging purposes.

- `MAX_RETRIES`: The maximum number of connection retries.

- `RETRY_DELAY_MS`: The delay between connection retries.

- `client`: An instance of the OkHttpClient for making HTTP requests.

- `packageName`: The package name of the application.

- `CONNECTION_CHECK_INTERVAL_MS`: The interval for checking the WebSocket connection status.

### Constants

- `REQ_MESSAGE_LIST`: Represents the request type for retrieving the message list.

- `REQ_MESSAGE_COUNT`: Represents the request type for retrieving the message count.

- `REQ_MESSAGE_DELETE`: Represents the request type for deleting a message.

## Security
 Must authenticate every HTTP Request. 
The first request will be to endpoint (/auth), trusting the certificate and sending the headers:
- package-name: The package name of the application
- password: The password generated for authentication
It will return a token to be added on the additional request with the specific endpoint and parameters on the queries.

## Documentation in HTML
[Api Documentation](app/build/dokka/API_html/index.html)

## Contributing
N/A

## License
OpenSource