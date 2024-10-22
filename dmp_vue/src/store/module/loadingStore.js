import { defineStore } from 'pinia';

export const useLoadingStore = defineStore('loading', {
    state: () => ({
        loading: false,
        timeoutId: null,  // 添加一个 timeoutId 来处理定时器
    }),
    actions: {
        startLoading() {
            this.loading = true;

            // 清除之前的 timeout，如果存在
            if (this.timeoutId) {
                clearTimeout(this.timeoutId);
                this.timeoutId = null;
            }
        },
        stopLoading() {
            // 确保动画至少持续 500ms
            this.timeoutId = setTimeout(() => {
                this.loading = false;
                this.timeoutId = null;
            }, 500);  // 500ms 最短动画时间
        }
    }
});