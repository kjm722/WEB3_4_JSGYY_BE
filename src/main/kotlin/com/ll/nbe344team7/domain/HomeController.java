package com.ll.nbe344team7.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String homeView() {
        return """
                <!doctype html>
                       <html lang="ko">
                       <head>
                           <meta charset="utf-8">
                           <meta name="viewport" content="width=device-width, initial-scale=1">
                           <title>STOMP</title>
                           <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
                           <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
                           <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
                           <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>
                           <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
                           <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
                
                           <script type="text/javascript">
                               let stompClient; // stompClient 변수를 전역으로 설정
                               let subscriptions = {}; // 구독 정보를 저장할 객체
                
                               function connectWebSocket() {
                                   const url = $('#websocketUrl').val(); // 웹소켓 URL 가져오기
                
                                   // websocket URL을 사용하여 stompClient 초기화
                                   stompClient = Stomp.client(url);
                                   stompClient.connect({} , stompConnectHandler, stompErrorHandler);
                                   console.log(stompClient);
                               }
                               
                               function disconnectWebSocket() {
                                   if (stompClient !== null && stompClient.connected) {
                                       stompClient.disconnect(function () {
                                           console.log("Disconnected from WebSocket");
                                       });
                
                                       stompClient = null; // WebSocket 객체 초기화
                                   } else {
                                       console.warn("WebSocket is not connected or already disconnected.");
                                   }
                               }
                
                               function stompConnectHandler() {
                                   console.log('connected!');
                               }
                
                               function stompErrorHandler(e) {
                                   console.error('stomp connect error - ', e);
                               }
                
                               function subscribeToPath(path) {
                                   const subscription = stompClient.subscribe(path, (data) => {
                                       displayMessage(data.body); // 메시지 수신 시 displayMessage 호출
                                   });
                
                                   // 구독 정보를 저장
                                   subscriptions[path] = subscription;
                               }
                
                               function unsubscribeFromPath(path) {
                                   if (subscriptions[path]) {
                                       subscriptions[path].unsubscribe(); // 구독 해제
                                       delete subscriptions[path]; // 구독 정보 삭제
                                       console.log(`Unsubscribed from ${path}`);
                                   }
                               }
                
                               function displayMessage(messageData) {
                                   const messageBox = $('#messageBox');
                                   // 수신된 메시지에서 message만 추출하여 추가
                                   messageBox.append(`<div class="alert alert-info">${messageData}</div>`);
                                   messageBox.scrollTop(messageBox[0].scrollHeight);
                               }
                
                               $(function () {
                                   // 연결 버튼 클릭 시 웹소켓 연결
                                   $('#connectBtn').click(connectWebSocket);
                                    
                                   // 구독 추가 버튼 클릭 시
                                   $('#addSubscriptionBtn').click(function () {
                                       const subscriptionCount = $('#subscriptionList .subscription-form').length; // 현재 구독 수
                                       const subscriptionForm = `
                                           <div class="mb-3 input-group subscription-form" id="subscription-${subscriptionCount}" style="width: 500px;">
                                               <input type="text" class="form-control" placeholder="SUB PATH" id="path-${subscriptionCount}" />
                                               <button class="btn btn-primary subscribeBtn">SUB</button>
                                               <button class="btn btn-danger unsubscribeBtn" style="display: none;">UNSUB</button>
                                           </div>`;
                                       $('#subscriptionList').append(subscriptionForm);
                                   });
                
                                   // 구독 버튼 클릭 시
                                   $(document).on('click', '.subscribeBtn', function () {
                                       const inputField = $(this).siblings('input');
                                       const path = inputField.val();
                                       subscribeToPath(path);
                                       inputField.prop('disabled', true); // 입력 필드 비활성화
                                       $(this).prop('disabled', true).hide(); // 구독 버튼 비활성화 및 숨김
                                       $(this).siblings('.unsubscribeBtn').show(); // 구독 해제 버튼 표시
                                   });
                
                                   // 구독 해제 버튼 클릭 시
                                   $(document).on('click', '.unsubscribeBtn', function () {
                                       const inputField = $(this).siblings('input');
                                       const path = inputField.val();
                                       unsubscribeFromPath(path); // 구독 해제 함수 호출
                                       inputField.prop('disabled', false); // 입력 필드 재활성화
                                       $(this).siblings('.subscribeBtn').prop('disabled', false).show(); // 구독 버튼 재활성화
                                       $(this).hide(); // 구독 해제 버튼 숨김
                                   });
                
                                   // 메시지 전송 버튼 클릭 시
                                   $('#sendBtn').click(function () {
                                        console.log("여기")
                                       const destinationPath = $('#destinationPath').val(); // 대상 경로 가져오기X
                                       const messageJson = $('#message').val(); // JSON 형태의 메시지 가져오기
                
                                       try {
                                           const message = JSON.parse(messageJson); // JSON으로 변환
                                           stompClient.send(destinationPath, {}, JSON.stringify(message)); // 메시지 발행
                                       } catch (error) {
                                           alert('유효한 JSON을 입력하세요!'); // JSON 오류 처리
                                       }
                                   });
                               });
                
                               function login() {
                                   const data = {
                                       username: document.getElementById('username').value,
                                       password: document.getElementById('password').value
                                   };
                
                                   fetch('/api/auth/login', {
                                       method: 'POST',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       },
                                       body: JSON.stringify(data)
                                   }).then(response => {
                                       // 로그인 성공/실패 처리
                                   });
                               }
                           </script>
                       </head>
                
                       <body>
                           <div class="container">
                                <form id="loginForm">
                                    <input type="text" id="username"/>
                                    <input type="password" id="password"/>
                                    <button type="button" onclick="login()">login</button>
                                </form>
                
                               <h1>WebSocket CONNECT</h1>
                               <div class="mb-3 input-group" style="width: 500px;">
                                   <input type="text" id="websocketUrl" class="form-control" value="ws://localhost:8080/ws"/>
                                   <button id="connectBtn" class="btn btn-primary">CONN</button>
                                   <button id="disconnectBtn" onclick="disconnectWebSocket()" class="btn btn-primary">DISCONN</button>
                               </div>
                
                               <h2>SUBSCRIBE</h2>
                               <div id="subscriptionList"></div>
                               <div class="input-group mb-3">
                                   <button id="addSubscriptionBtn" class="btn btn-secondary">ADD</button>
                               </div>
                
                               <h2>SEND MESSAGE</h2>
                               <div class="mb-3">
                                   <label for="destinationPath" class="form-label">DESTINATION PATH:</label>
                                   <input type="text" id="destinationPath" class="form-control" placeholder="/pub/send/message"/>
                               </div>
                               <div class="mb-3">
                                   <label for="message" class="form-label">MESSAGE(JSON):</label>
                                   <textarea id="message" class="form-control">{"content": "메세지", "roomId": "1"}</textarea>
                               </div>
                               <button id="sendBtn" class="btn btn-success">SEND</button>
                              \s
                               <h2 class="mt-4">MESSAGES</h2>
                               <div id="messageBox" class="border p-3" style="height: 200px; overflow-y: auto;"></div>
                           </div>
                       </body>
                       </html>
                """;
    }
}
