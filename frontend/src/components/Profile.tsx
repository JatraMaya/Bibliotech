import { useMemo } from 'react';
import { useAuthStore } from '../store/authStore';
import Navbar from './Navbar';
import { fullnameToInitial } from '../utils/utils';
import EditProfileForm from './EditProfileForm';

export default function Profile() {
  const user = useAuthStore((state) => state.user);

  const initials = useMemo(() => {
    if (!user?.fullname) return '?';

    return fullnameToInitial(user?.fullname);
  }, [user?.fullname]);
  return (
    <>
      <Navbar />
      <div className="hero min-h-screen bg-base-200">
        <div className="hero-content flex-col lg:flex-row">
          <figure className="avatar">
            <div className="w-32 md:w-48 lg:w-64 rounded-full">
              {user?.avatarUrl ? (
                <img src={user.avatarUrl} alt={user.fullname} />
              ) : (
                <div className="bg-neutral text-neutral-content flex items-center justify-center w-full h-full text-4xl md:text-6xl">
                  {initials}
                </div>
              )}
            </div>
          </figure>

          <div className="text-center lg:text-left">
            <h1 className="text-3xl md:text-5xl font-bold">{user?.fullname}</h1>

            <div className="stats stats-vertical lg:stats-horizontal shadow mt-6">
              <div className="stat">
                <div className="stat-title">Email</div>
                <div className="stat-value text-sm md:text-base">
                  {user?.email}
                </div>
              </div>
            </div>

            {user?.bio && (
              <div className="mt-6">
                <p className="text-base md:text-lg">{user.bio}</p>
              </div>
            )}

            <div className="mt-6">
              <button
                className="btn btn-primary"
                onClick={() =>
                  (
                    document.getElementById('my_modal_1') as HTMLDialogElement
                  )?.showModal()
                }
              >
                Edit Profile
              </button>
            </div>
          </div>
        </div>
      </div>

      <dialog id="my_modal_1" className="modal">
        <div className="modal-box w-full max-w-sm">
          <h3 className="card-title justify-center mb-4">Edit Profile</h3>

          <EditProfileForm />
        </div>
        <form method="dialog" className="modal-backdrop">
          <button>close</button>
        </form>
      </dialog>
    </>
  );
}
