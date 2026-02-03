export interface User {
  email: string;
  username: string;
  fullname: string;
  avatarUrl: string | null;
  bio?: string | null;
  role?: string;
}

export interface AddUserProfile {
  bio: string;
}

export interface EditUserProfile {
  firstname?: string | null;
  lastname?: string | null;
  bio?: string | null;
}
