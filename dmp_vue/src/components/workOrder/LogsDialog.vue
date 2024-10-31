<template>
    <div>
        <transition name="drawer-fade">
            <div class="drawer" v-if="isVisible">
                <div class="drawer-header">
                    <h3>Logs</h3>
                    <button class="close-btn" @click="closeDrawer">✖</button>
                </div>
                <div class="drawer-content">
                    <table>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Name</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(log, index) in gridData" :key="index">
                                <td>{{ log.date }}</td>
                                <td>{{ log.name }}</td>
                                <td>{{ log.address }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </transition>
        <transition name="overlay-fade">
            <div class="overlay" v-if="isVisible" @click="closeDrawer"></div>
        </transition>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';

const props = defineProps({
    logsDrawerVisible: {
        type: Boolean,
        default: false,
        required: true
    },
    selectedOrder: {
        type: Object,
        default: () => ({}),
        required: true
    }
});

const emit = defineEmits(['update:logsDrawerVisible']);

const isVisible = ref(props.logsDrawerVisible);
const gridData = ref([]);

// 通过 props 更新 gridData
watch(() => props.selectedOrder, (newValue) => {
    gridData.value = newValue.logs || []; // 假设 selectedOrder 中有 logs 属性
});

// 计算属性以保持 isVisible 的同步
watch(() => props.logsDrawerVisible, (newValue) => {
    isVisible.value = newValue;
});

const closeDrawer = () => {
    isVisible.value = false;
    emit('update:logsDrawerVisible', false);
};
</script>

<style scoped>
.drawer {
    position: fixed;
    top: 0;
    right: 0;
    height: 100%;
    width: 300px;
    background-color: white;
    box-shadow: -2px 0 5px rgba(0, 0, 0, 0.3);
    z-index: 1000;
    transition: transform 0.3s ease;
    transform: translateX(0);
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
}

.drawer-header {
    display: flex;
    justify-content: space-between;
    padding: 16px;
    border-bottom: 1px solid #eaeaea;
}

.close-btn {
    background: none;
    border: none;
    font-size: 18px;
    cursor: pointer;
}

.drawer-content {
    padding: 16px;
}

.overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    z-index: 500;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #eaeaea;
}

th {
    background-color: #f2f2f2;
}

/* 添加过渡动画 */
.drawer-fade-enter-active,
.drawer-fade-leave-active {
    transition: transform 0.3s ease;
}

.drawer-fade-enter {
    transform: translateX(100%);
}

.drawer-fade-leave-to {
    transform: translateX(100%);
}

.overlay-fade-enter-active,
.overlay-fade-leave-active {
    transition: opacity 0.3s ease;
}

.overlay-fade-enter {
    opacity: 0;
}

.overlay-fade-leave-to {
    opacity: 0;
}
</style>