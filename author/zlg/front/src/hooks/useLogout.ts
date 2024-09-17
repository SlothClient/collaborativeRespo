// useLogout.ts
import { useRouter } from 'vue-router';

const useLogout = () => {
  const router = useRouter();

  const logout = () => {
    sessionStorage.removeItem('user');
    router.replace('/login');
  };

  return { logout };
};

export default useLogout;