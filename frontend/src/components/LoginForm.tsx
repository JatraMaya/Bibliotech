import React, { useState } from 'react';
import { useLogin } from '../hooks/useLogin';
import { useNavigate } from 'react-router-dom';

export default function LoginForm() {
  const { mutate: loginMutation, isPending, isError } = useLogin();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    loginMutation({ username, password });
  };

  const handleRegister = () => {
    navigate('/register');
  };

  return (
    <div className="hero min-h-screen bg-base-200">
      <div className="hero-content w-full p-4">
        <div className="card w-full max-w-sm bg-base-100 shadow-xl">
          <div className="card-body">
            <h2 className="card-title justify-center">Welcome Back</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="form-control">
                <label className="label">
                  <span className="label-text">Email</span>
                </label>
                <input
                  type="text"
                  placeholder="Enter your username"
                  className="input input-bordered"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>

              <div className="form-control">
                <label className="label">
                  <span className="label-text">Password</span>
                </label>
                <input
                  type="password"
                  placeholder="Enter your password"
                  className="input input-bordered"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
                <label className="label">
                  <a href="#" className="label-text-alt link link-hover">
                    Forgot password?
                  </a>
                </label>
              </div>

              <div className="form-control">
                <button type="submit" className="btn btn-primary btn-block">
                  Login
                </button>
              </div>
            </form>

            <div className="divider">OR</div>

            <div className="text-center">
              Don't have an account?{' '}
              <a onClick={handleRegister} className="link link-primary">
                Sign up
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
