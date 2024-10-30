import { defineStore } from 'pinia';

export const useLoadingStore = defineStore('loading', {
    state: () => ({
        loading: false,
        timeoutId: null,
    }),
    actions: {
        //开始
        startLoading() {
            this.loading = true;
            if (this.timeoutId) {
                clearTimeout(this.timeoutId);
                this.timeoutId = null;
            }
        },
        //结束
        stopLoading() {
            this.timeoutId = setTimeout(() => {
                this.loading = false;
                this.timeoutId = null;
            }, 500);
        }
    }
});