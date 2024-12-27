<template>
  <div class="chat-list">
    <el-scrollbar>
      <el-card v-for="(chat, index) in chatList" :key="index" class="chat-card" @click="selectChat(chat)">
        <div class="chat-content">
          <div class="avatar">
            <el-avatar :src="`http://localhost:8080`+chat.chatUserWithAvatar" />
          </div>
          <div class="chat-info">
            <div class="chat-header">
              <span class="chat-title">{{ chat.chatUserWith }}</span>
              <el-badge :value="currentUserUnreadMessageCounts[chat.chatUserWithId]"
                :hidden="currentUserUnreadMessageCounts[chat.chatUserWithId] === 0" class="notification" />
            </div>

            <div class="chat-preview" v-html="getMessageText(chat)"></div>
            <span class="chat-time">{{ chat.messages.length === 0 ? "无消息" : chat.messages[chat.messages.length - 1].time
              }}</span>
          </div>
        </div>
      </el-card>
    </el-scrollbar>
  </div>
</template>

<script setup>
import {useMessageStore} from "@/store/module/messageStore.js";
import {computed, ref, watch} from "vue";

const messageStore = useMessageStore();
const chatList = computed(() => {
  return messageStore.filteredChats
});

const getMessageText = (chat) => {
  if (chat.messages.length === 0) return "无消息";
  console.log(chat.messages[chat.messages.length - 1].text)
  return chat.messages[chat.messages.length - 1].text;
};

const selectChat = (chat) => {
  messageStore.setCurrentChat(chat);

};

const currentUserUnreadMessageCounts = ref(messageStore.unreadMessages)

watch(()=>messageStore.unreadMessages,(newVal) =>currentUserUnreadMessageCounts.value = newVal)
</script>

<style scoped>
.chat-list {
  width: 40%;
  height: 100%;
  background-color: #f8f8f8;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.chat-card {
  margin: 8px;
  padding: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 8px;
  width: 230px;
}

.chat-card:hover {
  transform: translateY(-2px);
  background-color: #f0f2f5;
}

.chat-content {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.avatar {
  flex-shrink: 0;
}

.chat-info {
  flex: 1;
  min-width: 0;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}

.chat-title {
  font-weight: 600;
  font-size: 16px;
  color: #333;
}

.chat-preview {
  color: #666;
  font-size: 14px;
  margin: 4px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.chat-time {
  font-size: 12px;
  color: #999;
}

.notification {
  margin-left: auto;
}

.el-badge__content {
  background-color: #ff4d4f;
  color: white;
  font-size: 12px;
  border: none;
  padding: 0 6px;
  height: 18px;
  line-height: 18px;
  border-radius: 9px;
}
</style>