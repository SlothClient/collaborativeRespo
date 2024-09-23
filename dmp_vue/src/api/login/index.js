import {request} from '@/utils/request.js'

export const login = (loginForm) => {
    return request.post("user/login", loginForm)
}

export const logout = () => {
    return request.get("/user/logout")
}