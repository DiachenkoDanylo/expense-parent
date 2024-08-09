let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // Subscribe to the public topic
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });

        // Subscribe to the user's private queue
        stompClient.subscribe('/user/queue/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}



function sendMessage() {
    const username = document.getElementById('username').value;
    const message = document.getElementById('messageInput').value;
    const messageInput= document.getElementById('messageInput');
    const sendTo = document.getElementById('sendTo').value;
    messageInput.value='';
    stompClient.send("/app/sendMessage", {}, JSON.stringify({'sendFrom': username, 'message': message, 'sendTo': sendTo}));

}

function showMessage(message) {
    const messages = document.getElementById('messages');
    const username = document.getElementById('username').value;
    const messageDiv = document.createElement('div');
    messageDiv.className='message';
    const messageElement = document.createElement('div');

    if (message.sendFrom === username) {
        messageElement.className='chat_bubble_container outgoing';
    } else {
        messageElement.className='chat_bubble_container incoming';
    }
    messageElement.innerHTML = `<div class="chat_bubble">${message.sendFrom}: ${message.message}</div>`;
    messageDiv.appendChild(messageElement)
    messages.appendChild(messageDiv);
    const container = document.getElementById('messages');
    container.scrollTop = container.scrollHeight;
}

function assign(){
    const ticketId = document.getElementById('ticketId').value;
    const username = document.getElementById('username').value;
    stompClient.send("/app/assign",{}, JSON.stringify({'assignTo': username, 'ticketId':ticketId}));
}

connect();