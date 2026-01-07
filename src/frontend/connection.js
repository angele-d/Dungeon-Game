import { updateGrid, updateHeroes } from "./updateGame.js";

function getClient() {
  //let socket = new WebSocket("http://localhost:8080/stomp-endpoint");
  let socket = new SockJS("http://localhost:8080/stomp-endpoint");
  let stompClient = Stomp.over(socket);
  return stompClient;
}

function connectClient(stompClient, onConnectCallback) {
  stompClient.connect({}, (res) => onConnect(stompClient, res), onError);
}

function onConnect(stompClient, frame) {
  console.log("Connected: " + frame);

  stompClient.subscribe("/topic/tile_placed", function (message) {
    console.log("Received message: " + message.body);
    let payload = JSON.parse(message.body);
    let msg = payload["message"];
    updateGrid(msg["grid"]);
  });

  stompClient.subscribe("/topic/ai_changed", function (message) {
    console.log("Received message: " + message.body);
    let payload = JSON.parse(message.body);
    let msg = payload["message"];
    if (msg["result"] === false) {
      alert("AI change failed on server.");
    }
  });

  stompClient.subscribe("/topic/game_launched", function (message) {
    console.log("Received message: " + message.body);
    let payload = JSON.parse(message.body);
    let msg = payload["message"];
    updateGrid(msg["grid"]);
    updateHeroes(msg["heroes"]);
  });

  stompClient.subscribe("/topic/step_result", function (message) {
    console.log("Received message: " + message.body);
    let payload = JSON.parse(message.body);
    let msg = payload["message"];
    updateGrid(msg["grid"]);
    updateHeroes(msg["heroes"]);
  });
}

function onError(error) {
  console.error("Connection error: " + error);
  alert("Could not connect to server. Please try again later.");
}

function sendAddElement(stompClient, id, elementType, x, y) {
  let message = {
    id: id,
    tile_type: elementType,
    col: x,
    row: y,
  };
  console.log(
    "Sending message: " + JSON.stringify(message) + " " + x + " " + y
  );
  stompClient.send("/app/place_tile", {}, JSON.stringify(message));
}

function sendChangeAI(stompClient, id, newAI) {
  let message = {
    id: id,
    ai: newAI,
  };
  stompClient.send("/app/change_ai", {}, JSON.stringify(message));
}

function sendLaunchGame(stompClient, id) {
  let message = {
    id: id,
  };
  stompClient.send("/app/launch_game", {}, JSON.stringify(message));
}

function sendNextStep(stompClient, id) {
  let message = {
    id: id,
  };
  stompClient.send("/app/next_step", {}, JSON.stringify(message));
}

export {
  getClient,
  connectClient,
  sendAddElement,
  sendChangeAI,
  sendLaunchGame,
  sendNextStep,
};
