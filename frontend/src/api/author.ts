import type { AuthorTypeBase, CreateAuthorResponse } from '../types/authorType';
import { errorWithStatusCode, getApiPath } from '../utils/utils';

const API_URL = getApiPath();
export const addAuthor = async (
  data: AuthorTypeBase,
  token: string,
): Promise<CreateAuthorResponse> => {
  const formData = new FormData();

  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  if (!data.name || data.name === null) {
    errorWithStatusCode('Author name is required', 400);
  }

  if (data.profilePic) {
    formData.append('profilePic', data.profilePic);
  }

  const response = await fetch(`${API_URL}/author`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(result.message, response.status);
  }

  return result;
};

export const deleteAuthor = async (
  authorId: number,
  token: string,
): Promise<{ status: string; message: string }> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  const response = await fetch(`${API_URL}/admin/author/${authorId}`, {
    method: 'DELETE',
    headers: { Authorization: `Bearer ${token}` },
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(result.message, response.status);
  }

  return result;
};
