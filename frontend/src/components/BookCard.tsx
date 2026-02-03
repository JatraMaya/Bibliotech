import type { BookCollection } from '../types/BookType';

type BookProps = {
  book: BookCollection;
};

export default function BookCard({ book }: BookProps) {
  const defaultImgSrc = 'https://placehold.net/book-600x600.png';

  const statusBadgeMap: Record<BookCollection['readingStatus'], string> = {
    COMPLETED: 'badge-success',
    READING: 'badge-info',
    DROPPED: 'badge-error',
    WISHLIST: 'badge-warning',
  };

  return (
    <div className="card bg-base-100 shadow-sm">
      <figure className="relative h-[500px] overflow-hidden">
        <span
          className={`badge ${statusBadgeMap[book.readingStatus]} absolute top-2 right-2 z-10`}
        >
          {book.readingStatus}
        </span>
        <img
          src={book.coverUrl ?? defaultImgSrc}
          alt={`Book cover of ${book.bookTitle}`}
          className="w-full h-full object-cover"
        />
      </figure>
      <div className="card-body">
        <h2 className="card-title">{book.bookTitle}</h2>
        {book.comment && <p className="text-sm opacity-80">{book.comment}</p>}
        <div className="card-actions justify-end">
          <button className="btn btn-primary btn-sm">Buy Now</button>
        </div>
      </div>
    </div>
  );
}
