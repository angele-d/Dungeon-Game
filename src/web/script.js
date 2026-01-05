var stompClient = null;
var socket = new SockJS("http://localhost:8080/stomp-endpoint");
stompClient = Stomp.over(socket);

function connect() {
  updateStatus("Opening Web Socket...", "orange");
  stompClient.connect({}, onConnect, onError);
}

function onConnect(frame) {
  updateStatus("Connected !", "green");

  stompClient.subscribe("/topic/messages", function (message) {
    var payload = JSON.parse(message.body);
    document.querySelector("#commandInput").value = payload.message;
  });
}

connect();

function buttonOnClick() {
  stompClient.send(
    "/app/test",
    {},
    JSON.stringify({ message: document.querySelector("#commandInput").value })
  );
}

function onError(error) {
  updateStatus("Connection failed", "red");
  console.error("STOMP error", error);
}

function updateStatus(text, color) {
  var el = document.getElementById("status");
  if (!el) return;
  el.textContent = text;
  if (color) el.style.color = color;
}
