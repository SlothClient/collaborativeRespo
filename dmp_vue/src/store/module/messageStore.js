import {defineStore} from "pinia";
import {ref, computed} from "vue";
import {fetchChatHistory, markMessageAsRead} from "@/api/websocket/index.js";
import {ElNotification} from "element-plus";


export const useMessageStore = defineStore('message', () => {
    const chatHistory = ref([]);

    const activeCategory = ref('System');
    const currentChat = ref(null);

    const unreadCountTotal = ref(0);  // 未读消息数

    const unreadMessages = ref({})

    const currentChatSetNull = () =>{
        currentChat.value = null
    }

    const initiative = () => {
        chatHistory.value.forEach((chat) => {
            unreadMessages.value[chat.chatUserWithId] = chat.unreadCountTotal
            unreadCountTotal.value += chat.unreadCountTotal
        })
        console.log(unreadCountTotal.value )
    }

    // 设置当前用户总未读消息数量
    const setUnreadCount = (count) => {
        unreadCountTotal.value = count;
    }

    // 设置当前登入用户与其他用户之间的消息未读数量
    const setUnreadMessages = (otherUserId, count) => {
        unreadMessages.value = {
            ...unreadMessages.value,
            [otherUserId]: count
        };
    }

    // 清除当前与登入用户的未读消息
    const clearUnreadMessages = async (otherUserId) => {
        if (unreadMessages.value[otherUserId]) {
            await markMessageAsRead(otherUserId);
            unreadCountTotal.value -= unreadMessages.value[otherUserId];
            unreadMessages.value[otherUserId] = 0;
        }
    };

    // 新消息到达，更新未读数量
    const incrementUnreadMessages = (otherUserId) => {
        if (!unreadMessages[otherUserId]) {
            unreadMessages[otherUserId] = 0;
        }
        unreadMessages.value[otherUserId]++;
        unreadCountTotal.value++;
    }

    const setActiveCategory = (category) => {
        activeCategory.value = category;
    };

    const getChatHistory = async () => {
        if (chatHistory.value.length === 0) {
            try {
                const res = await fetchChatHistory();
                if (res.data.flag) {
                    chatHistory.value = res.data.data;
                    initiative();

                } else {
                    throw new Error(res.data.data);
                }
            } catch (error) {
                ElNotification({
                    type: "error",
                    message: error.message || "获取历史聊天记录失败"
                });
            }
            console.log(chatHistory.value)
        }
    };

    const setCurrentChat = async (selectChat) => {
        const chat = chatHistory.value.find(chat => chat.chatUserId === selectChat.chatUserId && chat.chatUserWithId === selectChat.chatUserWithId);
        console.log(chat)
        if (chat) {
            currentChat.value = chat;
            await clearUnreadMessages(selectChat.chatUserWithId)
        } else {
            console.error("没找到对应聊天");
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

    const clearMessage = () => {
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
        clearMessage,
        unreadCountTotal,
        setUnreadCount,
        setUnreadMessages,
        clearUnreadMessages,
        incrementUnreadMessages,
        unreadMessages,
        currentChatSetNull
    };
}, {
    // persist: true
});