import { create } from 'zustand';

type ToastType = 'success' | 'error';

type ToastState = {
  open: boolean;
  type: ToastType;
  message: String;
  show: (type: ToastType, message: String) => void;
  hide: () => void;
};

export const useToastStore = create<ToastState>((set) => ({
  open: false,
  type: 'success',
  message: '',

  show: (type, message) => set({ open: true, type, message }),

  hide: () => set({ open: false, message: '' }),
}));
