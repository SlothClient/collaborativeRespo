// useLogin.ts
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

interface LoginRules {
  uName: Array<{ required: boolean; message: string; trigger: string }>;
  uPwd: Array<{ required: boolean; message: string; trigger: string }>;
}

interface UseLoginReturn {
  loginInfo: any;
  loginForm: any;
  rules: LoginRules;
  login: () => Promise<void>;
}

const useLogin = (): UseLoginReturn => {
  const router = useRouter();
  const loginForm = ref(null);
  const loginInfo = ref({
    uName: '',
    uPwd: '',
  });

  const rules: LoginRules = {
    uName: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 11, message: '长度在 3 到 11 个字符', trigger: 'blur' },
    ],
    uPwd: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' },
    ],
  };

  const login = async () => {
    loginForm.value.validate(async (valid) => {
      if (valid) {
        try {
          const response = await axios.get("http://localhost:8889/equip/login", {
            // 参数和后端对应，不能随便取
            params: { uname: loginInfo.value.uName, upwd: loginInfo.value.uPwd },
          });
          if (response.data.status) {
            ElMessage.success('登录成功');
            sessionStorage.setItem('user', loginInfo.value.uName);
            router.push("/layout");
          } else {
            ElMessage.error(response.data.msg);
          }
        } catch (error) {
          ElMessage.error('登录失败，请重试');
          console.error('登录失败:', error);
        }
      } else {
        ElMessage.error('请按照规范输入！');
      }
    });
  };

  return { loginInfo, loginForm, rules, login };
};

export default useLogin;