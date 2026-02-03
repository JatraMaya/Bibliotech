export interface AuthorTypeBase {
  name: string;
  profilePic?: File;
}

export interface AuthorFull extends Omit<AuthorTypeBase, 'profilePic'> {
  id: number;
  authorPictureUrl: string;
}

export interface CreateAuthorResponse {
  author: AuthorFull;
  status: string;
  message: string;
}
