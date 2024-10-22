import {request} from "@/utils/request.js";

export const fetchChatHistory = ()=>{
    return request.post('message/getChatHistory');
}