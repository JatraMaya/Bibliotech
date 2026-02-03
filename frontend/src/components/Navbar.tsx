import { useQueryClient } from '@tanstack/react-query';
import { useAuthStore } from '../store/authStore';
import { useNavigate } from 'react-router-dom';
import Avatar from './Avatar';

export default function Navbar() {
  const logout = useAuthStore((state) => state.logout);
  const user = useAuthStore((state) => state.user);
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    queryClient.clear();
    navigate('/login', { replace: true });
  };

  const handleToProfile = () => {
    navigate('/profile');
  };

  const handleToDashboard = () => {
    navigate('/dashboard');
  };

  function navigateBook() {
    navigate('/book', { replace: true });
  }

  return (
    <div className="navbar bg-base-100 shadow-sm">
      <div className="navbar-start">
        <div className="dropdown">
          <div tabIndex={0} role="button" className="btn btn-ghost lg:hidden">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              {' '}
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M4 6h16M4 12h8m-8 6h16"
              />{' '}
            </svg>
          </div>
          <ul
            tabIndex={-1}
            className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow"
          >
            <li>
              <a onClick={navigateBook}>Add Book Collection</a>
            </li>
            <li>
              <a>Add Book Review</a>
            </li>
          </ul>
        </div>
        <a className="btn btn-ghost text-xl" onClick={handleToDashboard}>
          Bibliotech
        </a>
      </div>
      <div className="navbar-center hidden lg:flex">
        <ul className="menu menu-horizontal px-1">
          <li>
            <a onClick={navigateBook}>Add Book Collection</a>
          </li>
          <li>
            <a>Add Book review</a>
          </li>
        </ul>
      </div>

      {/* Avatar Image */}
      <div className="navbar-end mr-3">
        <div className="dropdown dropdown-end z-50">
          <div
            tabIndex={0}
            role="button"
            className="btn btn-ghost btn-circle avatar w-10"
          >
            <Avatar fullname={user?.fullname} avatarImg={user?.avatarUrl} />
          </div>
          <ul
            tabIndex={-1}
            className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 p-2 shadow"
          >
            <li>
              <a onClick={handleToProfile} className="justify-end">
                Profile
              </a>
            </li>
            <li>
              <a onClick={handleLogout} className="justify-end">
                Logout
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
