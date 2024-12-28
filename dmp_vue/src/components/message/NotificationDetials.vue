<template>
  <div v-if="currentChat" class="chat-detail">
    <!-- 聊天头部 -->
    <div class="chat-header" v-if="currentChat.messages">
      <el-avatar :src="`http://localhost:8080`+currentChat.chatUserWithAvatar " size="default"/>
      <h3>{{ currentChat.chatUserWith }}</h3>
    </div>

    <!-- 聊天消息区域 -->
    <div class="chat-messages" ref="chatMessages">
      <div>
        <div
            v-for="(message, index) in currentChat.messages"
            :key="index"
            :class="['message-container', { 'self': isSelf(message) }]"
        >
          <el-avatar
              :src="isSelf(message) ? userStore.user.avatar : `http://localhost:8080`+currentChat.chatUserWithAvatar"
              size="small"
          />
          <div class="message-content">
            <div class="message-text" :class= "['message-text', { 'self': isSelf(message) }]" >{{ message.text }}</div>
            <div class="message-time">{{ message.time }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 聊天输入框 -->
    <div class="chat-input">
      <el-input
          v-model="newMessage"
          placeholder="输入消息..."
          @keyup.enter="sendMessage"
      >
        <template #append>
          <el-button type="success" @click="sendMessage">发送</el-button>
        </template>
      </el-input>
    </div>
  </div>
  <div class="chat-detail" v-else><el-empty><p>请选择一个聊天对象</p></el-empty></div>
</template>


<script setup>
import {ref, computed, nextTick, onMounted, onBeforeUnmount, watch} from 'vue';
import {useMessageStore} from "@/store/module/messageStore.js";
import {useUserStore} from "@/store/module/userStore.js";
import moment from "moment";
import {initializeWebSocket, webSocketSendMessage} from "@/utils/webSocket.js";

const messageStore = useMessageStore();
const userStore = useUserStore();

const currentChat = computed(() => messageStore.currentChat);


const newMessage = ref('');
const chatMessages = ref(null);
const userId = userStore.user.id;

const isSelf =(message) =>{
  return message.sender === userId || message.from === userId;
}

const scrollToBottom = () => {
  nextTick(() => {
    if (chatMessages.value) {
      chatMessages.value.scrollTop = chatMessages.value.scrollHeight;
    }
  });
};

const sendMessage = () => {
  if (newMessage.value.trim()) {
    const message = {
      from: userId,
      to: currentChat.value.chatUserWithId,
      text: newMessage.value,
      time: moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
    }
    console.log(message)
    webSocketSendMessage(message);

    messageStore.addMessage(message);
    newMessage.value = '';
    scrollToBottom();
  }
};

// 监听 WebSocket 发出的滚动事件
const emit = (event) => {
  if (event === 'scrollToBottom') {
    scrollToBottom();
  }
};

// 当组件挂载后，监听 WebSocket 的消息
onMounted(() => {
  // 这里初始化 WebSocket，并传递 emit 函数
  initializeWebSocket(userId, emit);
});

</script>

<style scoped>
.chat-detail {
  width: 70%;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.chat-header {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.chat-header h3 {
  margin-left: 10px;
  font-size: 1.2em;
}

.chat-messages {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
  margin-bottom: 10px;
}

.message-container {
  display: flex;
  margin-bottom: 15px;
}

.message-container.self {
  flex-direction: row-reverse;
}

.message-content {
  margin: 0 10px;
  max-width: 70%;
}

.message-text {
  background-color: #fff;
  padding: 10px 15px;
  border-radius: 18px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: inline-block;
}
.message-text.self{
  margin-left: 60px;
}


.message-container.self .message-text {
  background-color: #dcf8c6;
}

.message-time {
  font-size: 0.8em;
  color: #888;
  margin-top: 5px;
  text-align: left;
}

.chat-input {
  margin-top: auto;
}
</style>