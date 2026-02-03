import type { AuthorTypeBase } from '../types/authorType';
import type { ReadingStatus } from '../types/BookType';
import { errorWithStatusCode, getApiPath } from '../utils/utils';

type Author = {
  id: number;
  name: string;
  authorPictureUrl: string | null;
};

type AddAuthorResponse = {
  author: Author;
  status: string;
  message: string;
};

type AddBook = {
  title: string;
  authorsId: string;
  genresId: string;
  cover?: File;
};

type Genre = {
  id: number;
  name: string;
};

type Tag = {
  id: number;
  name: string;
};

type Book = {
  authors: Author[];
  genres: Genre[];
  tags: Tag[];
  title: string;
  coverUrl: string | null;
};

type AddBookResponse = {
  book: Book;
};

type GetCollectionResponse = {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  status: string;
  data: {
    bookTitle: string;
    comment: string;
    coverUrl: string;
    id: number;
    readingStatus: ReadingStatus;
  }[];
};

const API_URL = getApiPath();

export const addAuthor = async (
  data: AuthorTypeBase,
  token: string,
): Promise<AddAuthorResponse> => {
  const formData = new FormData();

  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  if (!data.name) {
    errorWithStatusCode('AUthor name is required', 400);
  }

  formData.append('name', data.name);

  if (data.profilePic) {
    formData.append('profilePic', data.profilePic);
  }

  const response = await fetch(`${API_URL}/book`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(
      result.message,
      response.status,
      'Failed to add authore',
    );
  }

  return result;
};

export const addBook = async (
  data: AddBook,
  token: string,
): Promise<AddBookResponse> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }
  const formData = new FormData();

  formData.append('title', data.title);
  formData.append('authorsId', data.authorsId);
  formData.append('genresId', data.genresId);

  if (data.cover) {
    formData.append('cover', data.cover);
  }

  const response = await fetch(`${API_URL}/book`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(result.message, response.status, 'Failed to add book');
  }

  return result;
};

export const addToCollection = async (
  data: { bookId: number; readingStatus: ReadingStatus },
  token: string,
): Promise<{ status: string; message: string }> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  const response = await fetch(`${API_URL}/user/book`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(
      result.message,
      response.status,
      'Failed to add book to collection',
    );
  }

  return result;
};

export const updateRedingStatus = async (
  bookId: number,
  status: string,
  token: string,
): Promise<{ status: string; message: string }> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  const response = await fetch(
    `${API_URL}/user/book/${bookId}/status?status=${status}`,
    {
      method: 'PUT',
      headers: { Authorization: `Bearer ${token}` },
    },
  );

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(
      result.message,
      response.status,
      'Failed to update reading status',
    );
  }

  return result;
};

export const getCollection = async (
  page: number = 0,
  size: number = 10,
  token: string,
): Promise<GetCollectionResponse> => {
  if (!token) {
    errorWithStatusCode('Unauthenticated', 401);
  }

  const response = await fetch(
    `${API_URL}/user/book/all?page=${page}&size=${size}`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  const result = await response.json();

  if (!response.ok) {
    errorWithStatusCode(
      result.message,
      response.status,
      'Failed to get book collection',
    );
  }
  return result;
};
