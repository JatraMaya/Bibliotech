import { useGetBookCollection } from '../hooks/useGetBookCollection';
import { ReadingStatus } from '../types/BookType';
import BooksCollection from './BookCollection';
import Navbar from './Navbar';

export default function Dashboard() {
  const { data, isLoading, isError, error } = useGetBookCollection(0, 10);
  if (isLoading) {
    return (
      <>
        <Navbar />
        <div className="flex justify-center items-center min-h-[60vh]">
          <span className="loading loading-spinner loading-lg"></span>
        </div>
      </>
    );
  }

  if (isError) {
    return (
      <>
        <Navbar />
        <div className="flex justify-center items-center min-h-[60vh]">
          <div className="alert alert-error">
            <span>{error.message}</span>
          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <BooksCollection books={data?.data || []} />
    </>
  );
}
