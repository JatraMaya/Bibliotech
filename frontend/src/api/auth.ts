import type { LoginRequest, LoginResponse } from '../types/authType';
import type { User } from '../types/UserType';

const API_URL = import.meta.env.VITE_API_URL;

export const login = async (data: LoginRequest): Promise<LoginResponse> => {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error('Login failed');
  }

  return response.json();
};

export const getUserData = async (token: string): Promise<User> => {
  const response = await fetch(`${API_URL}/user/me`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to get user info');
  }

  return response.json();
};
