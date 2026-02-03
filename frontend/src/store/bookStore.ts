import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { BookCollection } from '../types/BookType';

interface BookState {
  books: BookCollection[];
  setBooks: (books: BookCollection[]) => void;
  addBook: (book: BookCollection) => void;
  updateBook: (id: number, book: Partial<BookCollection>) => void;
  deleteBook: (id: number) => void;
  getBookById: (id: number) => BookCollection | undefined;
}

export const useBookStore = create<BookState>()(
  persist(
    (set, get) => ({
      books: [],

      setBooks: (books) => set({ books }),

      addBook: (book) =>
        set((state) => ({
          books: [...state.books, book],
        })),

      updateBook: (id, updatedBook) =>
        set((state) => ({
          books: state.books.map((book) =>
            book.id === id ? { ...book, ...updatedBook } : book,
          ),
        })),

      deleteBook: (id) =>
        set((state) => ({
          books: state.books.filter((book) => book.id !== id),
        })),

      getBookById: (id) => get().books.find((book) => book.id === id),
    }),
    {
      name: 'book-storage',
    },
  ),
);
