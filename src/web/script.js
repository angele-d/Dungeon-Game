var stompClient = null;

function connect() {
    var socket = new SockJS('http://localhost:8080/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        // On successful connection
        document.getElementById("status").innerHTML = "Connected !";
        document.getElementById("status").style.color = "green";
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/messages', function(message) {
            showGameUpdate(JSON.parse(message.body));
        });

        stompClient.send("/src/test", {}, JSON.stringify({ message: 'Hello Server!' }));
    });
}
