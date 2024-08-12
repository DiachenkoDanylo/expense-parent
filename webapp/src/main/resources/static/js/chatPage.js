let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // requestMessageList();
        console.log('Connected: ' + frame);


        // Subscribe to the user's CHATS REQUEST
        stompClient.subscribe('/user/queue/helpTicket', function (message) {
            showTicket(JSON.parse(message.body));
        });
    });
}

function getRequestForChats(){
    const username = document.getElementById('username').value;
    stompClient.send("/app/request", {}, JSON.stringify({'sendFrom': username, 'message': 'REQUEST_CHAT', 'sendTo': ''}));
}

function openNewChat() {
    const username = document.getElementById('username').value;
    stompClient.send("/app/request", {}, JSON.stringify({'sendFrom': username, 'message': 'NEW_CHAT', 'sendTo': ''}));
    window.location.reload();
}

function showTicket(message) {
    console.log(message)
    const tickets = document.getElementById('layout');
    const messageDiv = document.createElement('div');
    messageDiv.className='expense-card'
    const messageElement = document.createElement('div');
    messageElement.innerHTML = `<div class="expense-description">
        <a href="/chat/${message.id}">${message.id}</a>
        <p>Solved: ${message.solved}</p>
    </div>`;
    messageDiv.appendChild(messageElement);
    tickets.appendChild(messageDiv);
}

connect();
setTimeout(getRequestForChats, 500);

