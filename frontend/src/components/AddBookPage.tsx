import { useState } from 'react';
import Navbar from './Navbar';

export default function AddbookPage() {
  const [title, setTitle] = useState('');

  return (
    <>
      <Navbar />
      <div className="hero min-h-screen bg-base-200">
        <div className="hero-content w-full p-4">
          <div className="card w-full max-w-sm bg-base-100 shadow-xl">
            <div className="card-body">
              <form className="space-y-4">
                <div className="form-control">
                  <label className="floating-label">
                    <span>Book TItle</span>
                    <input
                      type="text"
                      placeholder="Enter the book title"
                      className="input input-bordered"
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      required
                    />
                  </label>
                </div>

                <div className="form-control">
                  <label className="floating-label">
                    <span>Authors</span>
                    <input
                      type="text"
                      placeholder="Enter Author(s)"
                      className="input input-bordered"
                      required
                    />
                  </label>
                </div>

                <div className="form-control">
                  <label className="floating-label">
                    <span>Genres</span>
                    <input
                      type="text"
                      placeholder="Enter Genre(s)"
                      className="input input-bordered"
                      required
                    />
                  </label>
                </div>

                <div className="form-control">
                  <label className="floating-label">
                    <span>Book Tag</span>
                    <input
                      type="text"
                      placeholder="Enter Tag(s) (optional)"
                      className="input input-bordered"
                      required
                    />
                  </label>
                </div>

                <div className="form-control">
                  <label className="floating-label">
                    <span>Book Cover (Optional)</span>
                    <input
                      type="file"
                      className="file-input input-bordered"
                      required
                    />
                  </label>
                </div>

                <div className="form-control">
                  <button type="submit" className="btn btn-primary btn-block">
                    Add book to collection
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
