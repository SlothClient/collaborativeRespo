// 图片类型声明，用于解决图片引入报错问题
declare module '*.jpg' {
  const value: string;
  export default value;
} 