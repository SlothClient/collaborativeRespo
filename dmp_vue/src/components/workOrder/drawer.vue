<template>
    <div class="drawer-mask" v-if="visible" @click="closeDrawer">
        <div class="drawer-content" 
             :style="{ width: drawerWidth }"
             :class="[`theme-${theme}`, { 'closing': isClosing }, { 'theme-switching': isThemeSwitching }]" 
             @click.stop>
            <div class="drawer-header">
                <div class="default-header">
                    <div class="header-title">
                        <slot name="drawer-title">
                            <h3>默认标题</h3>
                        </slot>
                    </div>
                    <div class="theme-switcher">
                        <div class="theme-options">
                            <div 
                                v-for="option in themeOptions" 
                                :key="option.value"
                                :class="['theme-option', { active: theme === option.value }]"
                                @click="switchTheme(option.value)"
                            >
                                <span class="theme-icon" :class="option.value"></span>
                                {{ option.label }}
                            </div>
                        </div>
                    </div>
                    <button class="close-btn" @click="closeDrawer">
                        <svg class="close-icon" viewBox="0 0 1024 1024" width="16" height="16">
                            <path d="M512 456.310154L94.247385 38.557538a39.542154 39.542154 0 0 0-55.689847 0 39.542154 39.542154 0 0 0 0 55.689847L456.310154 512 38.557538 929.752615a39.542154 39.542154 0 0 0 55.689847 55.689847L512 567.689846l417.752615 417.752616c15.163077 15.163077 40.526769 15.163077 55.689847 0s15.163077-40.526769 0-55.689847L567.689846 512 985.442462 94.247385a39.542154 39.542154 0 0 0-55.689847-55.689847L512 456.310154z"/>
                        </svg>
                    </button>
                </div>
            </div>
            <div class="drawer-body">
                <div class="default-content">
                    <slot name="content">
                        <div class="content-card">
                            <div class="card-title">默认卡片</div>
                            <div class="card-content">请通过 content 插槽自定义内容</div>
                        </div>
                    </slot>
                </div>
            </div>
        </div>
    </div>
    <div class="mask" v-show="visible" :class="{ 'mask-closing': isClosing }" @click="handleMaskClick"></div>
</template>
<script lang="ts" setup>
import { ref, computed, watch } from 'vue';
const props = defineProps({
    visible: {
        type: Boolean,
        default: false,
    },
    theme: {
        type: String,
        default: 'classic',  // classic | lively | business
        validator: (value: string) => ['classic', 'lively', 'business'].includes(value)
    },
    mask: {
        type: Boolean,
        default: true
    },
    maskClosable: {
        type: Boolean,
        default: true
    },
    width: {
        type: [String, Number],
        default: '30%'  // 默认宽度
    }
});
const emit = defineEmits(['update:visible', 'update:theme']);
const visible = computed({
    get: () => props.visible,
    set: (val) => {
        emit('update:visible', val);
    },
})
const isClosing = ref(false);
const isThemeSwitching = ref(false);


const closeDrawer = () => {
    isClosing.value = true;
    setTimeout(() => {
        isClosing.value = false;
        emit('update:visible', false);
    }, 300);
};
const handleMaskClick = () => {
    if (props.maskClosable) {
        closeDrawer();
    }
}

// 主题选项
const themeOptions = [
    { label: '经典', value: 'classic' },
    { label: '灵动', value: 'lively' },
    { label: '商务', value: 'business' }
];

// 切换主题
const switchTheme = (newTheme: string) => {
    emit('update:theme', newTheme);
};

// 计算最终的宽度值
const drawerWidth = computed(() => {
    if (typeof props.width === 'number') {
        return `${props.width}px`;
    }
    return props.width;
});
</script>
<!-- 
解除 scoped 限制才能将drawer组件中的content-card样式暴露出去，虽然不是很好的方案，暂时只能这样
要么就只能写成单独的样式文件分别在drawer组件和父组件中导入了 
-->
<style>
.drawer-mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1000;
    animation: fadeIn 0.3s ease-out;
}

.drawer-content {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 380px;
    height: 100%;
    background-color: #f8f9fa;
    box-shadow: -4px 0 12px rgba(0, 0, 0, 0.15);
    animation: none;
}

.drawer-content.closing {
    animation: slideOutRight 0.3s linear;
}

@keyframes slideInRight {
    from {
        transform: translate3d(100%, 0, 0);
    }
    to {
        transform: translate3d(0, 0, 0);
    }
}

@keyframes slideOutRight {
    from {
        transform: translate3d(0, 0, 0);
    }
    to {
        transform: translate3d(100%, 0, 0);
    }
}

.close-btn {
    border: none;
    background: transparent;
    cursor: pointer;
    padding: 8px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.2s cubic-bezier(0.23, 1, 0.32, 1);
}

.close-btn:hover {
    background-color: #f1f3f5;
}

.close-icon {
    fill: #868e96;
    transition: fill 0.2s;
}

.close-btn:hover .close-icon {
    fill: #495057;
}

.default-header {
    display: flex;
    align-items: center;
    padding: 16px 24px;
    gap: 16px;
}

.header-title {
    min-width: 120px;
    flex-shrink: 0;
}

.header-title h3 {
    margin: 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.theme-switcher {
    flex: 1;
    min-width: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 12px;
}

.close-btn {
    flex-shrink: 0;
}

.drawer-body {
    height: calc(100% - 55px);
    padding: 24px;
    padding-bottom: 40px;
    overflow-y: auto;
    background: #f8f9fa;
}

.default-content {
    display: flex;
    flex-direction: column;
    gap: 16px;
    padding-bottom: 40px;
}

.content-card {
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    transition: all 0.3s cubic-bezier(0.23, 1, 0.32, 1);
    border: 1px solid #e9ecef;
    overflow: hidden;
}

.content-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
    border-color: #dee2e6;
}

.card-title {
    padding: 16px 20px;
    font-weight: 500;
    color: #1a1a1a;
    border-bottom: 1px dashed #e9ecef;
    background: #f8f9fa;
    font-size: 15px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.card-title::before {
    content: '';
    display: inline-block;
    width: 4px;
    height: 16px;
    background: #4a90e2;
    border-radius: 2px;
    margin-right: 12px;
}

.card-content {
    padding: 20px;
    color: #4a5568;
    line-height: 1.6;
    background: #ffffff;
    font-size: 14px;
    position: relative;
}

.card-content::before {
    content: '';
    position: absolute;
    top: 0;
    left: 20px;
    right: 20px;
    height: 1px;
    background: linear-gradient(to right, transparent, #e9ecef, transparent);
}

.content-card:hover .card-title {
    background: #f1f5f9;
}

.content-card:hover .card-title::before {
    background: #2779bd;
}

.drawer-body::-webkit-scrollbar {
    width: 6px;
}

.drawer-body::-webkit-scrollbar-thumb {
    background-color: #adb5bd;
    border-radius: 3px;
    transition: background-color 0.3s;
}

.drawer-body::-webkit-scrollbar-thumb:hover {
    background-color: #868e96;
}

.drawer-body::-webkit-scrollbar-track {
    background-color: #f8f9fa;
}

.content-card:last-child {
    margin-bottom: 8px;
}

.theme-classic {
    background: #f8f9fa;
}

.theme-classic .default-header {
    background: #ffffff;
    border-bottom: 1px solid #e9ecef;
    padding: 20px 24px;
}

.theme-classic .content-card {
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    border: 1px solid #e9ecef;
    overflow: hidden;
    transition: all 0.3s cubic-bezier(0.23, 1, 0.32, 1);
}

.theme-classic .content-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
    border-color: #dee2e6;
}

.theme-classic .card-title {
    padding: 16px 20px;
    font-weight: 500;
    color: #1a1a1a;
    border-bottom: 1px dashed #e9ecef;
    background: #f8f9fa;
    font-size: 15px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.theme-classic .card-title::before {
    content: '';
    display: inline-block;
    width: 4px;
    height: 16px;
    background: #4a90e2;
    border-radius: 2px;
    margin-right: 12px;
}

.theme-classic .card-content {
    padding: 20px;
    color: #4a5568;
    line-height: 1.6;
    background: #ffffff;
    font-size: 14px;
    position: relative;
}

.theme-classic .card-content::before {
    content: '';
    position: absolute;
    top: 0;
    left: 20px;
    right: 20px;
    height: 1px;
    background: linear-gradient(to right, transparent, #e9ecef, transparent);
}

.theme-classic .content-card:hover .card-title {
    background: #f1f5f9;
}

.theme-classic .content-card:hover .card-title::before {
    background: #2779bd;
}

.theme-lively {
    background: #f5f8ff;
    border-radius: 24px 0 0 24px;
    margin: 12px 0;
    height: calc(100% - 24px);
    overflow: hidden;
    border-left: 2px solid rgba(99, 102, 241, 0.3);
    box-shadow: -8px 0 20px rgba(99, 102, 241, 0.08);
}

.theme-lively .default-header {
    background: linear-gradient(135deg, #ffffff, #f5f8ff);
    border-bottom: 2px solid #818cf8;
    border-radius: 24px 0 16px 16px;
    padding: 20px 24px;
}

.theme-lively .default-header h3 {
    color: #4f46e5;
    font-size: 18px;
    background: linear-gradient(45deg, #4f46e5, #818cf8);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    letter-spacing: 0.5px;
}

.theme-lively .drawer-body {
    background: linear-gradient(180deg, #f5f8ff, #ffffff);
    padding: 24px;
    padding-bottom: 40px;
}

.theme-lively .content-card {
    background: #ffffff;
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(99, 102, 241, 0.08);
    border: 2px solid #e0e7ff;
    position: relative;
    overflow: hidden;
}

.theme-lively .content-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 4px;
    /* background: linear-gradient(90deg, #4f46e5, #818cf8); */
    opacity: 0;
    transition: opacity 0.3s;
}

.theme-lively .content-card:hover::before {
    opacity: 1;
}

.theme-lively .card-title {
    background: #eef2ff;
    color: #4338ca;
    font-weight: 600;
    border-bottom: 2px dashed #c7d2fe;
    padding: 16px 20px;
    position: relative;
    display: flex;
    align-items: center;
}

.theme-lively .card-title::before {
    content: '';
    width: 4px;
    height: 18px;
    background: linear-gradient(180deg, #4f46e5, #818cf8);
    border-radius: 4px;
    margin-right: 12px;
    transition: transform 0.3s;
}

.theme-lively .card-content {
    padding: 20px;
    color: #4b5563;
    line-height: 1.6;
    background: #ffffff;
    position: relative;
}

.theme-lively .card-content::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 20px;
    right: 20px;
    height: 1px;
    background: linear-gradient(90deg, 
        transparent,
        rgba(99, 102, 241, 0.2),
        transparent
    );
}

.theme-lively .content-card:hover {
    border-color: #818cf8;
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(99, 102, 241, 0.12);
}

.theme-lively .close-btn {
    border-radius: 12px;
    transition: all 0.3s;
}

.theme-lively .close-btn:hover {
    background-color: rgba(99, 102, 241, 0.1);
    transform: rotate(90deg);
}

.theme-lively .close-icon {
    fill: #6366f1;
}

.theme-lively .drawer-body::-webkit-scrollbar {
    width: 8px;
}

.theme-lively .drawer-body::-webkit-scrollbar-thumb {
    background: linear-gradient(180deg, #4f46e5, #818cf8);
    border-radius: 4px;
}

.theme-lively .drawer-body::-webkit-scrollbar-track {
    background: rgba(99, 102, 241, 0.1);
    border-radius: 4px;
}

.theme-lively + .drawer-mask {
    background: linear-gradient(135deg, 
        rgba(79, 70, 229, 0.2),
        rgba(99, 102, 241, 0.1)
    );
    backdrop-filter: blur(4px);
}

.theme-lively .card-content::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: radial-gradient(
        circle at top right,
        rgba(99, 102, 241, 0.03),
        transparent 70%
    );
    pointer-events: none;
}

.theme-lively .content-card {
    transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
}

@keyframes slideInRight {
    from {
        transform: translate3d(100%, 0, 0) scale(0.96);
        opacity: 0;
    }
    to {
        transform: translate3d(0, 0, 0) scale(1);
        opacity: 1;
    }
}

@keyframes slideOutRight {
    from {
        transform: translate3d(0, 0, 0) scale(1);
        opacity: 1;
    }
    to {
        transform: translate3d(100%, 0, 0) scale(0.96);
        opacity: 0;
    }
}

.theme-business {
    background: linear-gradient(135deg, #1a1f2d, #2d364d);
    box-shadow: -8px 0 24px rgba(0, 0, 0, 0.2);
}

.theme-business .default-header {
    background: linear-gradient(90deg, rgba(255, 255, 255, 0.03), rgba(255, 255, 255, 0.08));
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    padding: 20px 24px;
}

.theme-business .default-header h3 {
    color: #fff;
    font-size: 18px;
    font-weight: 500;
    letter-spacing: 0.5px;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    background: linear-gradient(90deg, #fff, #e2e8f0);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
}

.theme-business .drawer-body {
    background: linear-gradient(180deg, 
        rgba(255, 255, 255, 0.02),
        rgba(255, 255, 255, 0.01)
    );
    padding: 24px;
    padding-bottom: 40px;
}

.theme-business .content-card {
    background: rgba(255, 255, 255, 0.03);
    border-radius: 12px;
    border: 1px solid rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(8px);
    position: relative;
    overflow: hidden;
    transition: all 0.3s ease;
}

.theme-business .content-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 1px;
    background: linear-gradient(90deg,
        transparent,
        rgba(255, 255, 255, 0.1),
        transparent
    );
}

.theme-business .card-title {
    background: rgba(255, 255, 255, 0.05);
    color: #fff;
    padding: 20px;
    font-weight: 500;
    letter-spacing: 0.5px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    display: flex;
    align-items: center;
    position: relative;
}

.theme-business .card-title::before {
    content: '';
    width: 4px;
    height: 18px;
    background: linear-gradient(180deg, #3b82f6, #60a5fa);
    border-radius: 2px;
    margin-right: 12px;
}

.theme-business .card-content {
    color: rgba(255, 255, 255, 0.85);
    background: transparent;
    padding: 20px;
    line-height: 1.6;
    font-size: 14px;
    position: relative;
}

.theme-business .content-card:hover {
    transform: translateY(-2px);
    background: rgba(255, 255, 255, 0.05);
    border-color: rgba(255, 255, 255, 0.15);
    box-shadow: 
        0 8px 24px rgba(0, 0, 0, 0.2),
        0 2px 4px rgba(59, 130, 246, 0.1);
}

.theme-business .content-card:hover .card-title {
    background: rgba(255, 255, 255, 0.08);
}

.theme-business .content-card::after {
    content: '';
    position: absolute;
    top: 0;
    right: 0;
    width: 100px;
    height: 100px;
    background: radial-gradient(
        circle at top right,
        rgba(59, 130, 246, 0.1),
        transparent 70%
    );
    pointer-events: none;
}

.theme-business .close-btn {
    border-radius: 8px;
    background: rgba(255, 255, 255, 0.05);
}

.theme-business .close-btn:hover {
    background: rgba(255, 255, 255, 0.1);
}

.theme-business .close-icon {
    fill: rgba(255, 255, 255, 0.8);
}

.theme-business .drawer-body::-webkit-scrollbar {
    width: 6px;
}

.theme-business .drawer-body::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 3px;
}

.theme-business .drawer-body::-webkit-scrollbar-thumb:hover {
    background: rgba(255, 255, 255, 0.3);
}

.theme-business .drawer-body::-webkit-scrollbar-track {
    background: rgba(255, 255, 255, 0.05);
}

.theme-business + .drawer-mask {
    background: rgba(0, 0, 0, 0.7);
    backdrop-filter: blur(4px);
}

@keyframes businessSlideIn {
    from {
        transform: translate3d(100%, 0, 0);
        opacity: 0;
    }
    to {
        transform: translate3d(0, 0, 0);
        opacity: 1;
    }
}

.theme-business {
    animation: businessSlideIn 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

@keyframes neonPulse {
    0% {
        box-shadow: 0 0 5px rgba(59, 130, 246, 0.2),
                    0 0 10px rgba(59, 130, 246, 0.2),
                    0 0 15px rgba(59, 130, 246, 0.2);
    }
    50% {
        box-shadow: 0 0 10px rgba(59, 130, 246, 0.3),
                    0 0 20px rgba(59, 130, 246, 0.3),
                    0 0 30px rgba(59, 130, 246, 0.3);
    }
    100% {
        box-shadow: 0 0 5px rgba(59, 130, 246, 0.2),
                    0 0 10px rgba(59, 130, 246, 0.2),
                    0 0 15px rgba(59, 130, 246, 0.2);
    }
}

.theme-business .content-card:hover {
    animation: neonPulse 2s infinite;
}

.theme-classic + .drawer-mask {
    background-color: rgba(0, 0, 0, 0.45);
}

.theme-lively + .drawer-mask {
    background-color: rgba(0, 150, 255, 0.2);
}

.theme-business + .drawer-mask {
    background-color: rgba(0, 0, 0, 0.65);
}

.theme-classic .drawer-body::-webkit-scrollbar-thumb {
    background-color: #d9d9d9;
}

.theme-lively .drawer-body::-webkit-scrollbar-thumb {
    background-color: #91caff;
}

.theme-business .drawer-body::-webkit-scrollbar-thumb {
    background: linear-gradient(45deg, #1677ff, #36cfc9);
}

.mask {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.25);
    z-index: 999;
    animation: fadeIn 0.3s ease-out;
}

.mask-closing {
    animation: fadeOut 0.3s ease-out;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}

@keyframes fadeOut {
    from {
        opacity: 1;
    }
    to {
        opacity: 0;
    }
}

.theme-switching {
    animation: none !important;
    transition: background-color 0.3s ease;
}

.drawer-content {
    animation: none;
}

.drawer-content:not(.theme-switching) {
    animation: slideInRight 0.3s cubic-bezier(0.23, 1, 0.32, 1);
}

.drawer-content.closing:not(.theme-switching) {
    animation: slideOutRight 0.3s linear;
}

.theme-switcher {
    margin: 0 20px;
    position: relative;
}

.theme-options {
    display: flex;
    background: rgba(0, 0, 0, 0.04);
    border-radius: 16px;
    padding: 2px;
    position: relative;
    transition: all 0.3s ease;
    transform: scale(0.9);
}

.theme-option {
    padding: 4px 12px;
    font-size: 12px;
    cursor: pointer;
    border-radius: 14px;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    position: relative;
    display: flex;
    align-items: center;
    gap: 4px;
    user-select: none;
    white-space: nowrap;
    height: 24px;
}

.theme-icon {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    display: inline-block;
    position: relative;
}

.theme-icon.classic {
    background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    border: 1px solid #dee2e6;
}

.theme-icon.lively {
    background: linear-gradient(135deg, #60a5fa 0%, #3b82f6 100%);
    box-shadow: 0 2px 4px rgba(59, 130, 246, 0.3);
}

.theme-icon.business {
    background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%);
    box-shadow: 0 2px 4px rgba(15, 23, 42, 0.3);
}

/* 经典主题样式 */
.theme-classic .theme-options {
    background: rgba(0, 0, 0, 0.04);
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.05);
}

.theme-classic .theme-option {
    color: #666;
}

.theme-classic .theme-option.active {
    background: #fff;
    color: #1a1a1a;
    font-weight: 500;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

/* 灵动主题样式 */
.theme-lively .theme-options {
    background: rgba(99, 102, 241, 0.08);
    box-shadow: inset 0 1px 2px rgba(99, 102, 241, 0.1);
}

.theme-lively .theme-option {
    color: #6366f1;
}

.theme-lively .theme-option.active {
    background: #fff;
    color: #4f46e5;
    font-weight: 500;
    box-shadow: 0 2px 4px rgba(99, 102, 241, 0.2);
}

/* 商务主题样式 */
.theme-business .theme-options {
    background: rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(8px);
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.2);
}

.theme-business .theme-option {
    color: rgba(255, 255, 255, 0.7);
}

.theme-business .theme-option.active {
    background: rgba(255, 255, 255, 0.15);
    color: #fff;
    font-weight: 500;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    backdrop-filter: blur(4px);
}

/* 悬停效果 */
.theme-option:hover:not(.active) {
    background: rgba(255, 255, 255, 0.08);
}

.theme-classic .theme-option:hover:not(.active) {
    background: rgba(0, 0, 0, 0.02);
}

.theme-lively .theme-option:hover:not(.active) {
    background: rgba(99, 102, 241, 0.06);
}

.theme-business .theme-option:hover:not(.active) {
    background: rgba(255, 255, 255, 0.06);
}

/* 点击效果 */
.theme-option:active {
    transform: scale(0.95);
}

/* 过渡动画 */
.theme-option {
    transform-origin: center;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.theme-option.active {
    transform-origin: center;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>