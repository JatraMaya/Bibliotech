import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRegister } from '../hooks/useRegister';
import { useToastStore } from '../store/toastStore';
import { errorToMessage } from '../utils/utils';

export default function RegisterForm() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstname, setFistname] = useState('');
  const [lastname, setLastname] = useState('');

  const showToast = useToastStore((state) => state.show);

  const { mutate } = useRegister();

  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    mutate(
      {
        username,
        email,
        password,
        firstname,
        lastname: lastname || null,
      },
      {
        onSuccess: (data) => {
          showToast('success', data.message);
          navigate('/login');
        },

        onError: (error: any) => {
          const msg = errorToMessage(error?.message);
          showToast('error', msg);
        },
      },
    );
  };

  const handleLogin = () => {
    navigate('/login');
  };
  return (
    <div className="hero min-h-screen bg-base-200">
      <div className="hero-content w-full p-4">
        <div className="card w-full max-w-sm bg-base-100 shadow-xl">
          <div className="card-body">
            <h2 className="card-title justify-center">Register</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="form-control">
                <label className="floating-label">
                  <span>Username</span>
                  <input
                    type="text"
                    placeholder="Enter your username"
                    className="input input-bordered"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </label>
              </div>

              <div className="form-control">
                <label className="floating-label">
                  <span>Password</span>
                  <input
                    type="password"
                    placeholder="Enter your password"
                    className="input input-bordered"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </label>
              </div>

              <div className="form-control">
                <label className="floating-label">
                  <span>Email</span>
                  <input
                    type="text"
                    placeholder="Enter your email"
                    className="input input-bordered"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                  />
                </label>
              </div>

              <div className="form-control">
                <label className="floating-label">
                  <span>Firstname</span>
                  <input
                    type="text"
                    placeholder="Enter your firstname"
                    className="input input-bordered"
                    value={firstname}
                    onChange={(e) => setFistname(e.target.value)}
                    required
                  />
                </label>
              </div>

              <div className="form-control">
                <label className="floating-label">
                  <span>Lastname</span>
                  <input
                    type="text"
                    placeholder="Enter your lastname (optional)"
                    className="input input-bordered"
                    value={lastname}
                    onChange={(e) => setLastname(e.target.value)}
                  />
                </label>
              </div>

              <div className="form-control">
                <button type="submit" className="btn btn-primary btn-block">
                  Register
                </button>
              </div>
            </form>

            <div className="divider">OR</div>

            <div className="text-center">
              Already have an account?{' '}
              <a onClick={handleLogin} className="link link-primary">
                Login
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
