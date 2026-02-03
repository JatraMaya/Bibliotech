import { errorWithStatusCode, getApiPath } from '../utils/utils';

interface Genre {
  name: string;
}

interface GenreFull extends Genre {
  id: number;
}

interface GetAllGenre {
  totalItems: number;
  totalPages: number;
  status: string;
  data: GenreFull[];
  currentPage: number;
}

const API_URL = getApiPath();

export const addGenre = async (
  data: Genre,
  token: string,
): Promise<{ status: string; message: string }> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  if (!data.name || data.name === null) {
    errorWithStatusCode('Genre name is reauired', 400);
  }
  const response = await fetch(`${API_URL}/genre`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(result.message, response.status);
  }

  return result;
};

export const getAllGenre = async (
  page: number = 0,
  size: number = 10,
  token: string,
): Promise<GetAllGenre> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  const response = await fetch(`${API_URL}/genre?page=${page}&size=${size}`, {
    headers: { Authorization: `Bearer ${token}` },
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(result.message, response.status);
  }

  return result;
};
