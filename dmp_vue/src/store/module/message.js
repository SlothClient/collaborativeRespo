import {defineStore} from "pinia";
import {ref, computed} from "vue";
import {fetchChatHistory} from "@/api/websocket/index.js";
import {ElNotification} from "element-plus";

export const useMessageStore = defineStore('message', () => {
    const chatHistory = ref([]);

    const activeCategory = ref('system');
    const currentChat = ref(null);

    // Actions and methods
    const setActiveCategory = (category) => {
        activeCategory.value = category;
    };

    const getChatHistory = async () => {
        if (chatHistory.value.length === 0) {
            try {
                const res = await fetchChatHistory();
                if (res.data.flag) {
                    chatHistory.value = res.data.data;
                } else {
                    throw new Error(res.data.data);
                }
            } catch (error) {
                ElNotification({
                    type: "error",
                    message: error.message || "Failed to fetch chat history"
                });
            }
            console.log(chatHistory.value)
        }
    };

    const setCurrentChat = (selectChat) => {
        const chat = chatHistory.value.find(chat => chat.chatUserId === selectChat.chatUserId && chat.chatUserWithId === selectChat.chatUserWithId);
        console.log(chat)
        if (chat) {
            currentChat.value = chat;
            console.log(currentChat.value)
        } else {
            console.error("Chat not found");
        }
    };

    const addMessage = (message) => {
        // 找到合适的聊天记录，匹配 message.from 和 message.to 对应的 chatUserId 和 chatUserWithId
        const chat = chatHistory.value.find(chat =>
            (chat.chatUserId === message.from && chat.chatUserWithId === message.to) ||
            (chat.chatUserId === message.to && chat.chatUserWithId === message.from)
        );

        if (chat) {
            // 将消息添加到找到的聊天记录中
            chat.messages.push(message);
        } else {
            // 如果找不到对应的聊天记录，可以选择创建一个新的对话
            console.error("对应的聊天记录未找到");
        }
    };

    const filteredChats = computed(() => {
        return chatHistory.value.filter(chat => chat.category === activeCategory.value);
    });

    const clearMessage = ()=>{
       chatHistory.value = []
        activeCategory.value = 'system';
        currentChat.value = null
    }
    return {
        chatHistory,
        currentChat,
        setCurrentChat,
        setActiveCategory,
        activeCategory,
        addMessage,
        getChatHistory,
        filteredChats,
        clearMessage
    };
}, {
    // persist: true
});