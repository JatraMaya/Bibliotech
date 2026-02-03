export interface BookCollection {
  bookTitle: string;
  comment: string;
  readingStatus: ReadingStatus;
  id: number;
  coverUrl?: string | null;
}

export const ReadingStatus = {
  COMPLETED: 'COMPLETED',
  DROPPED: 'DROPPED',
  READING: 'READING',
  WISHLIST: 'WISHLIST',
} as const;

export type ReadingStatus = (typeof ReadingStatus)[keyof typeof ReadingStatus];
