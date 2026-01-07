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

  stompClient.subscribe("/topic/new_game", function (message) {
    console.log("Received message: " + message.body);
    let payload = JSON.parse(message.body);
  });

  sendCreateGame(stompClient);

  stompClient.subscribe("/topic/tile_placed", function (message) {
    let payload = JSON.parse(message.body);
    let grid = payload["grid"];
    let gridData = JSON.parse(grid);
    updateGrid(gridData);
  });

  stompClient.subscribe("/topic/ai_changed", function (message) {
    let payload = JSON.parse(message.body);
    if (payload["result"] === false) {
      alert("AI change failed on server.");
    }
  });

  stompClient.subscribe("/topic/game_launched", function (message) {
    let payload = JSON.parse(message.body);
    let grid = payload["grid"];
    let gridData = JSON.parse(grid);
    updateGrid(gridData);
    let heroes = payload["heroes"];
    let heroesData = JSON.parse(heroes);
    updateHeroes(heroesData);
    document.querySelector("#next-button").textContent = "Next Turn";
    window.gameLaunched = true;
  });

  stompClient.subscribe("/topic/step_result", function (message) {
    let payload = JSON.parse(message.body);
    let grid = payload["grid"];
    let gridData = JSON.parse(grid);
    updateGrid(gridData);
    let heroes = payload["heroes"];
    let heroesData = JSON.parse(heroes);
    updateHeroes(heroesData);
  });
}

function onError(error) {
  console.error("Connection error: " + error);
  alert("Could not connect to server. Please try again later.");
}

function sendCreateGame(stompClient) {
  let message = {};
  stompClient.send("/app/new_game", {}, JSON.stringify(message));
}

function sendAddElement(stompClient, id, elementType, x, y) {
  let message = {
    id: id,
    tile_type: elementType,
    col: x,
    row: y,
  };
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
