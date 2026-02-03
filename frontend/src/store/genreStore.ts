import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type Genre = {
  id: number;
  name: string;
};

interface GenreState {
  genres: Genre[];
  setGenres: (genres: Genre[]) => void;
  addGenre: (genre: Genre) => void;
}

export const genreStore = create<GenreState>()(
  persist(
    (set) => ({
      genres: [],

      setGenres: (genres) => set({ genres }),

      addGenre: (genre) =>
        set((state) => ({
          genres: [...state.genres, genre],
        })),
    }),
    {
      name: 'genre-storage',
    },
  ),
);
