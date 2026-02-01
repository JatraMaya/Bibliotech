import { useEffect } from 'react';
import { useToastStore } from '../store/toastStore';

export function Toast() {
  const open = useToastStore((s) => s.open);
  const type = useToastStore((s) => s.type);
  const message = useToastStore((s) => s.message);
  const hide = useToastStore.getState().hide; // 👈 stable access

  useEffect(() => {
    if (!open) return;

    const timer = setTimeout(() => {
      hide();
    }, 3000);

    return () => {
      clearTimeout(timer);
    };
  }, [open]); // 👈 ONLY depend on open

  if (!open) return null;

  return (
    <div className="toast toast-top toast-center">
      <div
        className={`alert cursor-pointer ${
          type === 'success' ? 'alert-success' : 'alert-error'
        }`}
        onClick={hide}
      >
        {message}
      </div>
    </div>
  );
}
