import { updateMoneyDisplay } from "./moneyManager.js";
import { updateGrid, updateHeroes } from "./updateGame.js";

let LENGTH = 15;

function randomString(LENGTH) {
  const chars =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let result = "";
  for (let i = 0; i < LENGTH; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  return result;
}

function getClient() {
  let socket = new SockJS("http://localhost:8080/stomp-endpoint");
  let stompClient = Stomp.over(socket);
  return stompClient;
}

function connectClient(stompClient, onConnectCallback) {
  stompClient.connect({}, (res) => onConnect(stompClient, res), onError);
}

function onConnect(stompClient, frame) {
  console.log("Connected: " + frame);

  let id = new URLSearchParams(window.location.search).get("id");
  if (!id) {
    sendCreateGame(stompClient);
  } else {
    console.log("Existing game id found: " + id);
    window.id = id;
    sendGetGameStats(stompClient);
    stompClient.subscribe(`/topic/tile_placed/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      window.money = parseInt(payload["money"]);
      updateMoneyDisplay(payload["money"]);
    });

    stompClient.subscribe(`/topic/ai_changed/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      if (payload["result"] === false) {
        alert("AI change failed on server.");
      }
    });

    stompClient.subscribe(`/topic/game_launched/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      window.money = parseInt(payload["money"]);
      updateMoneyDisplay(payload["money"]);
      let heroes = payload["heroes"];
      let heroesData = JSON.parse(heroes);
      updateHeroes(heroesData);
      document.querySelector("#next-button").textContent = "Next Turn";
      window.gameLaunched = true;
    });

    stompClient.subscribe(`/topic/step_result/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      let heroes = payload["heroes"];
      let heroesData = JSON.parse(heroes);
      updateHeroes(heroesData);
    });
  }
}

function onError(error) {
  console.error("Connection error: " + error);
  alert("Could not connect to server. Please try again later.");
}

function sendCreateGame(stompClient) {
  let tmpId = randomString(LENGTH);
  let subscription = stompClient.subscribe(
    `/topic/get-id/${tmpId}`,
    function (message) {
      let payload = JSON.parse(message.body);
      let id = payload["id"];

      window.id = id;

      const urlParams = new URLSearchParams(window.location.search);
      urlParams.set("id", id);
      window.location.search = urlParams;
      subscription.unsubscribe();
    }
  );
  let message = {
    id: tmpId,
  };
  stompClient.send("/app/new_game", {}, JSON.stringify(message));
}

function sendGetGameStats(stompClient) {
  console.log("Existing game id found: " + window.id);
  let message = {
    id: window.id,
  };
  stompClient.send("/app/get_game_stats", {}, JSON.stringify(message));

  let subscription = stompClient.subscribe(
    `/topic/send_game_stats/${window.id}`,
    function (message) {
      let payload = JSON.parse(message.body);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      window.money = parseInt(payload["money"]);
      updateMoneyDisplay(payload["money"]);
      let heroes = payload["heroes"];
      let heroesData = JSON.parse(heroes);
      updateHeroes(heroesData);
      subscription.unsubscribe();
    }
  );
}

function sendAddElement(stompClient, elementType, x, y) {
  let message = {
    id: new URLSearchParams(window.location.search).get("id"),
    tile_type: elementType,
    col: x,
    row: y,
  };
  stompClient.send("/app/place_tile", {}, JSON.stringify(message));
}

function sendChangeAI(stompClient, newAI) {
  let message = {
    id: new URLSearchParams(window.location.search).get("id"),
    ai: newAI,
  };
  stompClient.send("/app/change_ai", {}, JSON.stringify(message));
}

function sendLaunchGame(stompClient) {
  let message = {
    id: new URLSearchParams(window.location.search).get("id"),
  };
  stompClient.send("/app/launch_game", {}, JSON.stringify(message));
}

function sendNextStep(stompClient) {
  let message = {
    id: new URLSearchParams(window.location.search).get("id"),
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
