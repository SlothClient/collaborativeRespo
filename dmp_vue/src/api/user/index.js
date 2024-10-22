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

export const addUserInfoList = (user) => {
    return request.post("/addUserInfoList", user)
}

export const deleteUserInfo = (user) => {
    return request.post("/deleteUserInfo",user)
}

export const updateUserInfoList = (newUser) => {
    return request.post("/updateUserInfo", newUser)
}

export const getRole = () => {
    return request.post("/role/getRole")
}

export const getUserInfoList = (useRsq) => {
    return request.post("/getUserInfoList", useRsq)
}


export const updateAvatar = (file) =>{
    return request.post("/updateAvatar", file, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}

