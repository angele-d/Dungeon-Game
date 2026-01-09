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
    console.log("Nope");
  } else {
    window.id = id;
    stompClient.subscribe(`/topic/leaderboard/${id}`, function (message) {
      let payload = JSON.parse(message.body);
      let leaderboard = payload["result"];
      let parsedLeaderboard;
      if (leaderboard != "]") {
        parsedLeaderboard = JSON.parse(leaderboard);
      } else {
        parsedLeaderboard = [];
      }
      parsedLeaderboard
        .sort((score, id, remaining) => parseInt(score))
        .reverse();
      updateLeaderboard(parsedLeaderboard);
    });
    sendGetLeaderboard(stompClient);
  }
}

function sendGetLeaderboard(stompClient) {
  stompClient.send(
    "/app/get_leaderboard",
    {},
    JSON.stringify({
      id: window.id,
    })
  );
}

function onError(error) {
  console.error("Connection error:", error);
}

function updateLeaderboard(gameList) {
  let list = document.querySelector("#leaderboard-list");
  let url = window.location.pathname;
  url = url.replace("/leaderboard.html", "/game.html");

  gameList.forEach((game, i) => {
    const newElement = document.createElement("tr");
    newElement.classList.add("leaderboard-entry");
    newElement.innerHTML = `
    <th class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">${
      i + 1
    }</th>
    <td class="px-6 py-4 filename"><a href="${url}?load=${game[1]}">${
      "Game " + game[1]
    }</a></td>
    <td class="px-6 py-4">${game[0]}</td>
    <td class="px-6 py-4">$${game[2]}</td>
  `;
    list.appendChild(newElement);
  });
}

document.addEventListener("DOMContentLoaded", () => {
  let stompClient = getClient();
  connectClient(stompClient);
});
