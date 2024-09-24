import {request} from "@/utils/request.js";

export const getUserInfo = () => {
    return request.get("user/getUserInfo")
}


export const getUserMenu = () => {
    return request.get("user/getUserMenu")
}

export const logout = () => {
    return request.get("user/logout")
}

