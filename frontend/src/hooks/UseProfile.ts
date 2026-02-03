import { useMutation } from '@tanstack/react-query';
import { useAuthStore } from '../store/authStore';
import type { AddUserProfile, EditUserProfile } from '../types/UserType';
import { addProfile, editProfile } from '../api/user';
import { getUserData } from '../api/auth';
import { useToastStore } from '../store/toastStore';

export const useAddProfile = () => {
  const token = useAuthStore((state) => state.token);
  const setSession = useAuthStore((state) => state.setSession);
  const showToast = useToastStore((state) => state.show);

  return useMutation({
    mutationFn: async (payload: AddUserProfile) => {
      if (!token) {
        throw Error('Not authenticated');
      }
      return addProfile(payload, token);
    },
    onSuccess: async (data) => {
      if (!token) {
        throw Error('Not authenticated');
      }
      showToast('success', data.message);
      const userData = await getUserData(token);
      setSession(userData, token);
    },
    onError: (error: any) => {
      showToast('error', error.message);
    },
  });
};

export const useEditProfile = () => {
  const token = useAuthStore((state) => state.token);
  const setSession = useAuthStore((state) => state.setSession);
  const showToast = useToastStore((state) => state.show);

  return useMutation({
    mutationFn: async (payload: EditUserProfile) => {
      if (!token) {
        throw Error('Not authenticated');
      }
      return editProfile(payload, token);
    },
    onSuccess: async () => {
      if (!token) {
        throw Error('Not authenticated');
      }
      const userData = await getUserData(token);
      setSession(userData, token);
    },
    onError: (error: any) => {
      showToast('error', error.message);
    },
  });
};
