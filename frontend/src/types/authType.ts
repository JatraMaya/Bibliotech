import type { User } from './UserType';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  status: string;
  message: string;
  token: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  firstname: string;
  lastname: string | null;
}

export interface RegisterResponse {
  status: string;
  message: string;
  user: {
    email: string;
    fullname: string;
    username: string;
  };
}

export interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  setSession: (user: User, token: string) => void;
  logout: () => void;
}
