import { useNavigate } from 'react-router-dom';
import { type BookCollection } from '../types/BookType';
import BookCard from './BookCard';

type BookCollectionProp = {
  books: BookCollection[];
};

export default function BooksCollection({ books }: BookCollectionProp) {
  const navigate = useNavigate();
  function navigateBook() {
    navigate('/book', { replace: true });
  }
  return (
    <div className="container mx-auto px-4 py-8">
      {books.length == 0 ? (
        <div className="flex flex-col items-center justify-center min-h-[60vh] text-center">
          <svg
            className="w-24 h-24 text-base-300 mb-4"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
            />
          </svg>
          <h3 className="text-2xl font-bold mb-2">No books yet</h3>
          <p className="text-base-content/60 mb-6">
            Start building your reading list
          </p>
          <button className="btn btn-primary" onClick={navigateBook}>
            Add Your First Book
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {books?.map((book) => (
            <BookCard key={book.bookTitle} book={book} />
          ))}
        </div>
      )}
    </div>
  );
}
