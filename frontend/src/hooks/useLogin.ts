import { useMutation } from '@tanstack/react-query';
import { useAuthStore } from '../store/authStore';

import type { LoginRequest } from '../types/authType';

import { login as apiLogin, getUserData } from '../api/auth';
import { useNavigate } from 'react-router-dom';

export const useLogin = () => {
  const login = useAuthStore((state) => state.login);
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async (data: LoginRequest) => {
      const loginRes = await apiLogin(data);
      const userInfo = await getUserData(loginRes.token);

      return { token: loginRes.token, user: userInfo };
    },
    onSuccess: (data) => {
      console.log('RESPONSE FROM API: ', data.user);
      login(data.user, data.token);

      if (data.user.role == 'ADMIN') {
        console.log("NAVIGATE TO ADMIN")
        navigate('/admin');
      } else {
        console.log("NAVIGATE TO DASHBOARD")
        navigate('/dashboard');
      }
    },
  });
};
