import {request} from "@/utils/request.js";

export const fetchChatHistory = ()=>{
    return request.post('message/getChatHistory');
}

export const markMessageAsRead = (otherUserId) => {
    return request.put(`message/read/${otherUserId}`);
};

export const notifySuperior = (message,roleName) =>{
    return request.get(`/message/notify?message=${message}&roleName=${roleName}`)
}
