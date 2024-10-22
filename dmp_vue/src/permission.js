import { router } from "@/router/index.js";
import { useUserStore } from "@/store/module/user.js";
import { usePermissionStore } from "@/store/module/permission.js";
import { getToken, removeToken } from "@/utils/token.js";
import { useLoadingStore } from "@/store/module/loadingStore.js";

const whiteList = ["/login", '/loginTest'];

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const permissionStore = usePermissionStore();
    const hasToken = getToken();
    const loadingStore = useLoadingStore();

    loadingStore.startLoading(); // 开始加载动画

    if (hasToken) {
        if (to.path === '/login') {
            next({ path: '/home' });
        } else {
            if (userStore.user.roles.length === 0) {
                try {
                    // 获取用户信息
                    await userStore.GetUserInfo();
                    // 生成路由
                    await permissionStore.generateRoutes();
                    next({ ...to, replace: true });
                } catch (error) {
                    // 出错时重置 token 并跳转到登录页
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

router.afterEach(() => {
    const loadingStore = useLoadingStore();
    loadingStore.stopLoading(); // 停止加载动画
});