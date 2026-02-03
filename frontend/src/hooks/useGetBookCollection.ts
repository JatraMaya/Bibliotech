import { useQuery } from '@tanstack/react-query';
import { useAuthStore } from '../store/authStore';
import { useBookStore } from '../store/bookStore';
import { getCollection } from '../api/book';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const useGetBookCollection = (page: number = 0, size: number = 10) => {
  const token = useAuthStore((s) => s.token);
  const { setBooks } = useBookStore();
  const navigate = useNavigate();

  if (!token) {
    throw Error('User unauthenticated');
  }

  const query = useQuery({
    queryKey: ['bookCollection', page, size],
    queryFn: () => getCollection(page, size, token),
    enabled: !!token,
  });

  useEffect(() => {
    if (query.isError) {
      const statusCode = (query.error as any).statusCode;
      if (statusCode === 401) {
        navigate('/login', { replace: true });
      }
    }
    if (query.data) {
      setBooks(query.data.data);
    }
  }, [query.data, setBooks]);

  return query;
};
