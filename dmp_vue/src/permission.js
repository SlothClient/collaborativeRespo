import {router} from "@/router/index.js";
import {useUserStore} from "@/store/module/user.js"
import {usePermissionStore} from "@/store/module/permission.js";
import {getToken, removeToken} from "@/utils/token.js";

const whiteList = ["/login"];
router.beforeEach(async (to, from, next) => {

    const userStore = useUserStore();
    const permissionStore = usePermissionStore();
    const hasToken = getToken();

    if (hasToken) {
        if (to.path === '/login') {
            next({path: 'dashboard/home'});
        } else {
            if (userStore.user.roles.length === 0) {
                try {
                    // 获取用户信息
                    await userStore.GetUserInfo();

                    // 重新生成路由
                    const res = await permissionStore.generateRoutes();
                    console.log(res)
                    next({...to, replace: true});
                } catch (error) {
                    // 出错时重置token并跳转到登录页
                    await removeToken();
                    next(`/login?redirect=${to.path}`);
                    console.error(error);
                }
            } else {

                next();

            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        } else {
            next(`/login?redirect=${to.path}`);
        }
    }
});