import { updateMoneyDisplay } from "./moneyManager.js";
import { updateGrid, updateHeroes, updateScore } from "./updateGame.js";

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
  let loadId = new URLSearchParams(window.location.search).get("load");
  if (!id && !loadId) {
    sendCreateGame(stompClient);
  } else if (!id) {
    sendCopyGame(stompClient, loadId);
  } else {
    window.id = id;
    sendGetGameStats(stompClient);
    sendIsSimulationRunning(stompClient);
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
      if (payload["result"] == "false") {
        alert("AI change failed on server.");
      } else {
        document.querySelector("#ai-menu").value = window.ai;
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
      let heroesData;
      if (heroes) {
        heroesData = JSON.parse(heroes);
      } else {
        heroesData = [];
      }
      updateHeroes(heroesData);
      document.querySelector("#next-button").textContent = "Next Turn";
      window.gameLaunched = true;
      document.querySelector("#editor-panel").classList.add("gray-out");
      document.querySelector("#editor-panel").scrollTop = 0;
    });

    stompClient.subscribe(`/topic/step_result/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      let heroes = payload["heroes"];
      let heroesData = JSON.parse(heroes);
      updateHeroes(heroesData);
      let score = parseInt(payload["score"]);
      updateScore(score);
    });
  }
}

function onError(error) {
  console.error("Connection error: " + error);
  alert("Could not connect to server. Please try again later.");
}

function sendCheckSimulationReady(stompClient) {
  let message = {
    id: window.id,
  };
  stompClient.send("/app/is_simulation_ready", {}, JSON.stringify(message));

  let subscription = stompClient.subscribe(
    `/topic/simulation_ready/${window.id}`,
    function (message) {
      let payload = JSON.parse(message.body);
      if (payload["result"] === "true") {
        sendLaunchGame(stompClient);
      } else {
        alert("Simulation is not ready to be launched.");
      }
      subscription.unsubscribe();
    }
  );
}

function sendCreateGame(stompClient) {
  window.gameLaunched = false;
  let tmpId = randomString(LENGTH);
  let subscription = stompClient.subscribe(
    `/topic/get_id/${tmpId}`,
    function (message) {
      let payload = JSON.parse(message.body);
      let id = payload["id"];

      window.id = id;

      stompClient.subscribe(`/topic/ai_changed/${id}`, function (message) {
        let payload = JSON.parse(message.body);
        if (payload["result"] == "false") {
          alert("AI change failed on server.");
        } else {
          document.querySelector("#ai-menu").value = window.ai;
        }
      });
      sendChangeAI(stompClient, "BFS"); // Default AI

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

function sendCopyGame(stompClient, loadId) {
  window.gameLaunched = false;
  let tmpId = randomString(LENGTH);
  let subscription = stompClient.subscribe(
    `/topic/get_id/${tmpId}`,
    function (message) {
      let payload = JSON.parse(message.body);
      let id = payload["id"];

      window.id = id;

      stompClient.subscribe(`/topic/ai_changed/${id}`, function (message) {
        let payload = JSON.parse(message.body);
        if (payload["result"] == "false") {
          alert("AI change failed on server.");
        } else {
          document.querySelector("#ai-menu").value = window.ai;
        }
      });
      sendChangeAI(stompClient, "BFS"); // Default AI

      const urlParams = new URLSearchParams(window.location.search);
      urlParams.delete("load");
      urlParams.set("id", id);
      window.location.search = urlParams;
      subscription.unsubscribe();
    }
  );
  let message = {
    id: tmpId,
    copy_id: loadId,
  };
  stompClient.send("/app/copy_game", {}, JSON.stringify(message));
}

function sendGetGameStats(stompClient) {
  let message = {
    id: window.id,
  };
  stompClient.send("/app/get_game_stats", {}, JSON.stringify(message));

  let subscription = stompClient.subscribe(
    `/topic/send_game_stats/${window.id}`,
    function (message) {
      let payload = JSON.parse(message.body);
      console.log("Received game stats:", payload);
      let grid = payload["grid"];
      let gridData = JSON.parse(grid);
      updateGrid(gridData);
      window.money = parseInt(payload["money"]);
      updateMoneyDisplay(payload["money"]);
      let heroes = payload["heroes"];
      let heroesData = JSON.parse(heroes);
      updateHeroes(heroesData);
      let score = parseInt(payload["score"]);
      updateScore(score);
      subscription.unsubscribe();
    }
  );
}

function sendIsSimulationRunning(stompClient) {
  let message = {
    id: window.id,
  };

  let subscription = stompClient.subscribe(
    `/topic/is_simulation_running/${window.id}`,
    function (message) {
      let payload = JSON.parse(message.body);
      if (payload["result"] == "true") {
        window.gameLaunched = true;
        document.querySelector("#next-button").textContent = "Next Turn";
        document.querySelector("#editor-panel").classList.add("gray-out");
        document.querySelector("#editor-panel").scrollTop = 0;
      }
      subscription.unsubscribe();
    }
  );

  stompClient.send("/app/is_simulation_running", {}, JSON.stringify(message));
}

function sendAddElement(stompClient, elementType, x, y) {
  let message = {
    id: window.id,
    tile_type: elementType,
    col: x,
    row: y,
  };
  stompClient.send("/app/place_tile", {}, JSON.stringify(message));
}

function sendChangeAI(stompClient, newAI) {
  let message = {
    id: window.id,
    ai: newAI,
  };
  stompClient.send("/app/change_ai", {}, JSON.stringify(message));
}

function sendLaunchGame(stompClient) {
  let message = {
    id: window.id,
  };
  stompClient.send("/app/launch_game", {}, JSON.stringify(message));
}

function sendNextStepIfPossible(stompClient) {
  let message = {
    id: window.id,
  };

  let subscription = stompClient.subscribe(
    `/topic/wave_terminated/${window.id}`,
    function (message) {
      let payload = JSON.parse(message.body);
      if (payload["result"] == "false") {
        let subscription2 = stompClient.subscribe(
          `/topic/game_terminated/${window.id}`,
          function (message) {
            let payload = JSON.parse(message.body);
            if (payload["result"] == "false") {
              // If game is not terminated
              sendNextStep(stompClient);
            } else {
              // If the game is terminated => go to leaderboard
              const url = new URL(window.location.href);

              url.pathname = url.pathname.replace(
                "game.html",
                "leaderboard.html"
              );

              url.searchParams.set("id", window.id);

              window.location.href = url.toString();
            }
            subscription2.unsubscribe();
          }
        );
        message = {
          id: window.id,
        };
        stompClient.send(
          "/app/is_game_terminated",
          {},
          JSON.stringify(message)
        );
      } else {
        message = {
          id: window.id,
        };
        stompClient.send("/app/next_step", {}, JSON.stringify(message));
      }
      subscription.unsubscribe();
    }
  );
  stompClient.send("/app/is_wave_terminated", {}, JSON.stringify(message));
}

function sendNextStep(stompClient) {
  let message = {
    id: window.id,
  };
  stompClient.send("/app/next_step", {}, JSON.stringify(message));
}

export {
  getClient,
  connectClient,
  sendAddElement,
  sendChangeAI,
  sendCheckSimulationReady,
  sendNextStepIfPossible,
};
