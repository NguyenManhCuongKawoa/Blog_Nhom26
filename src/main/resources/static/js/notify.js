
let stompClient = null;
//const notifyArea = document.getElementById("notify");
//const notifyContent = notifyArea.querySelector(".notify-list");

function connect() {

  let socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
}


function onConnected() {
  stompClient.subscribe('/notify/public', onNotify);
}


function onError(error) {
  alert("Can't to connect to server socket'")
}

function onNotify(payload){
	 var notify = JSON.parse(payload.body);
	 
	 console.log(notify)
	 
}

connect()