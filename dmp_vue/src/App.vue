<template>
  <div id="app">
    <!-- 全局加载动画 -->
    <div v-if="loadingStore.loading" class="loading-overlay">
      <div class="loading-spinner">
        <div class="outer-ring"></div>
        <div class="inner-ring"></div>
      </div>
      <div class="loading-text">加载中，请稍候...</div>
    </div>
    <router-view/>
  </div>
</template>

<script>
import {useLoadingStore} from '@/store/module/loadingStore.js';

export default {
  setup() {
    const loadingStore = useLoadingStore();
    return {loadingStore};
  }
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  z-index: 99999999;
}

.loading-spinner {
  position: relative;
  width: 80px;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.outer-ring, .inner-ring {
  position: absolute;
  border-radius: 50%;
  border: 4px solid transparent;
  animation: rotate 1.5s linear infinite;
}

.outer-ring {
  width: 80px;
  height: 80px;
  border-top-color: #3498db;
  border-left-color: #8e44ad;
  box-shadow: 0 0 8px rgba(52, 152, 219, 0.5);
}

.inner-ring {
  width: 50px;
  height: 50px;
  border-bottom-color: #e74c3c;
  border-right-color: #f1c40f;
  animation-duration: 1s;
  box-shadow: 0 0 6px rgba(231, 76, 60, 0.5);
}

.loading-text {
  margin-top: 20px;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  animation: fadeIn 1s ease-in-out infinite alternate;
}

@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0.7;
  }
  100% {
    opacity: 1;
  }
}
</style>