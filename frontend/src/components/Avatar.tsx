import { useMemo } from 'react';
import { fullnameToInitial } from '../utils/utils';

type AvatarProp = {
  fullname?: string;
  avatarImg?: string | null;
};

export default function Avatar({ fullname, avatarImg }: AvatarProp) {
  const initials = useMemo(() => {
    if (!fullname) return '?';

    return fullnameToInitial(fullname);
  }, [fullname]);

  if (avatarImg) {
    return (
      <div className="w-8 rounded-full">
        <img alt="User Avatar Profile" src={avatarImg} />
      </div>
    );
  }

  return (
    <div className="avatar avatar-placeholder">
      <div className="bg-neutral text-neutral-content w-8 rounded-full">
        <span>{initials}</span>
      </div>
    </div>
  );
}
