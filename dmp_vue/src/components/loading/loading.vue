<script setup>
import {useLoadingStore} from "@/store/module/loadingStore.js";

const loadingStore = useLoadingStore();
</script>

<template>
  <div v-if="loadingStore.loading" class="loading-overlay">
    <div class="loading-container">
      <div class="loading-ripple">
        <div></div>
        <div></div>
      </div>
      <div class="loading-text">
        <span>加</span><span>载</span><span>中</span><span>，</span><span>请</span><span>稍</span><span>后</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(3px) saturate(100%);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}


.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  transition: transform 0.3s ease;
}

.loading-container:hover {
  transform: scale(1.05);
}


.loading-ripple {
  position: relative;
  width: 64px;
  height: 64px;
}

.loading-ripple div {
  position: absolute;
  border: 3px solid #3498db;
  border-radius: 50%;
  animation: ripple 1.5s cubic-bezier(0, 0.2, 0.8, 1) infinite;
  opacity: 1;
}

.loading-ripple div:nth-child(2) {
  animation-delay: -0.5s;
}

.loading-text {
  font-family: 'Arial', sans-serif;
  font-size: 16px;
  color: #333;
  letter-spacing: 2px;
}

.loading-text span {
  display: inline-block;
  animation: bounce 1.5s infinite;
  text-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
  font-weight: 500;
}


.loading-text span:nth-child(2) {
  animation-delay: 0.1s;
}

.loading-text span:nth-child(3) {
  animation-delay: 0.2s;
}

.loading-text span:nth-child(4) {
  animation-delay: 0.3s;
}

.loading-text span:nth-child(5) {
  animation-delay: 0.4s;
}

.loading-text span:nth-child(6) {
  animation-delay: 0.5s;
}

.loading-text span:nth-child(7) {
  animation-delay: 0.6s;
}

@keyframes ripple {
  0% {
    top: 28px;
    left: 28px;
    width: 0;
    height: 0;
    opacity: 1;
  }
  100% {
    top: -1px;
    left: -1px;
    width: 58px;
    height: 58px;
    opacity: 0;
  }
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
    color: #3498db;
  }
}

</style>