import { create } from 'zustand';
import type { AuthState } from '../types/authType';
import { persist } from 'zustand/middleware';
import type { User } from '../types/UserType';

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      setSession: (user, token) => set({ user, token, isAuthenticated: true }),
      logout: () => set({ user: null, token: null, isAuthenticated: false }),
      update: (partial: Partial<User>) =>
        set((state) => ({
          user: state?.user ? { ...state.user, ...partial } : null,
        })),
    }),
    {
      name: 'auth-storage',
    },
  ),
);
