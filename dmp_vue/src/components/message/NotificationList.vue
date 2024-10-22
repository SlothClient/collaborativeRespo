<template>
  <div class="chat-list">
    <el-scrollbar>
      <el-card
          v-for="(chat, index) in chatHistory"
          :key="index"
          class="chat-card"
          @click="selectChat(chat)"
      >
        <div class="chat-content">
          <div class="avatar"><el-avatar :src="`http://localhost:8080`+chat.chatUserWithAvatar " /></div>
          <div class="chat-info">
            <p class="chat-title">{{ chat.chatUserWith }}</p>
            <p class="chat-preview">{{
                chat.messages.length === 0 ? "无消息" : chat.messages[chat.messages.length - 1].text
              }}</p>
            <small>{{ chat.messages.length === 0 ? "无消息" : chat.messages[chat.messages.length - 1].time }}</small>
          </div>
        </div>
      </el-card>
    </el-scrollbar>
  </div>
</template>

<script setup>
import {useMessageStore} from "@/store/module/message.js";
import {computed} from "vue";

const messageStore = useMessageStore();
const chatHistory = computed(() => {
  return messageStore.chatHistory
});

const selectChat = (chat) => {
  messageStore.setCurrentChat(chat);
};
</script>

<style scoped>
.chat-list {
  width: 30%;
  height: 100%;
  background-color: #f8f8f8;
}

.chat-card {
  margin-bottom: 10px;
  padding: 0;
  cursor: pointer;
  transition: transform 0.2s;
}

.chat-card:hover {
  transform: scale(1.02);
}

.chat-content {
  display: flex;
  align-items: center;
}

.chat-info {
  margin-left: 20px;
}

.chat-title {
  font-weight: bold;
  margin-bottom: 5px;
}

.chat-preview {
  color: #666;
  font-size: 0.9em;
  margin-bottom: 5px;
}
.avatar{
  height: 50px;
  width: 50px;
}
</style>