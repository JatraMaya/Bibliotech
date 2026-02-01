import { useMutation } from '@tanstack/react-query';
import { useAuthStore } from '../store/authStore';
import { login as apiLogin, getUserData } from '../api/auth';
import { useNavigate } from 'react-router-dom';
import { useToastStore } from '../store/toastStore';
import type { LoginRequest } from '../types/authType';

export const useLogin = () => {
  const login = useAuthStore((state) => state.login);
  const navigate = useNavigate();
  const showToast = useToastStore((state) => state.show);

  return useMutation({
    mutationFn: async (data: LoginRequest) => {
      const loginRes = await apiLogin(data);
      const userInfo = await getUserData(loginRes.token);

      return {
        token: loginRes.token,
        user: userInfo,
        message: loginRes.message,
      };
    },
    onSuccess: (data) => {
      showToast('success', data.message);
      login(data.user, data.token);

      navigate(data.user.role === 'ADMIN' ? '/admin' : '/dashboard');
    },
    onError: (error: any) => {
      showToast('error', error.message);
    },
  });
};
