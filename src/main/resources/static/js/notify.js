
let stompClient = null;
const notifyArea = document.getElementById("notify-area");

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
	 /*
			 	{
		    "id": 5,
		    "postId": 13,
		    "senderId": 9,
		    "senderName": "Baby baby123",
		    "receiver": 6,
		    "shortContent": "hay quá",
		    "createdAt": "2022-05-28"
		}
	  */
	  let html = notifyArea.innerHTML;
	  
	  let notifyContent = `<li>
			<a style="padding: 3px 20px">
				<div class="" style="display:flex; ">
					<div class="col-lg-2" style="display: flex; justify-content: center; align-items: center;">
						<p><span style="    font-size: 26px;
							    padding: 8px;
							    border-radius: 50%; color: #807f7f;
							    background: #ccc;" class="glyphicon glyphicon-bell"></span ></p>
					</div>
					<div class="col-lg-10" style="padding-left: 0px; padding-right: 10px; width: 100%;">
						<div style="word-wrap: break-word; white-space: normal; display: flex; flex-direction: column; justify-content: center; ">
							<p style="margin: 0px; font-size:20px; font-weight: 600;   color: #737373;">Có người comment bài viết của bạn </p>
							<p style="margin: 0px; font-size: 16px;  color: #000;">${notify.shortContent} </p>
							<p style="margin: 0px;text-align: right; color: #000;">${notify.createdAt}</p >
						</div>
					</div>
				</div>
			</a>
		</li>`
	  
	  notifyArea.innerHTML = notifyContent + html
	 
}

connect()