// useVerifyUser.ts
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const useVerifyUser = () => {
  const router = useRouter();
  const currentUser = sessionStorage.getItem('user') || '';

  onMounted(() => {
    console.log("Header:mounted");

    if (!currentUser) {
      router.push('/login');
    }
  });
  return { currentUser };
};

export default useVerifyUser;