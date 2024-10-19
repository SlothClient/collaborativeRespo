import mitt from "mitt";
// emitter绑定触发事件
const emitter = mitt();
// // 测试功能
// // 绑定事件
// emitter.on('orderSelected',()=>{
//     console.log('订单已选中')
// })
// // 触发事件
// emitter.emit('orderSelected')
export default emitter;