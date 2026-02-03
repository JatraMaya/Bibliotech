import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import LoginForm from '../components/LoginForm';
import RegisterForm from '../components/RegisterForm';
import Dashboard from '../components/Dashboard';
import Profile from '../components/Profile';
import AddbookPage from '../components/AddBookPage';

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}

export default function AppRoutes() {
  const { isAuthenticated, user } = useAuthStore();

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/login"
          element={
            isAuthenticated ? (
              <Navigate
                to={user?.role === 'ADMIN' ? '/admin' : '/dashboard'}
                replace
              />
            ) : (
              <LoginForm />
            )
          }
        />

        <Route
          path="/register"
          element={
            isAuthenticated ? (
              <Navigate
                to={user?.role === 'ADMIN' ? '/admin' : '/dashboard'}
                replace
              />
            ) : (
              <RegisterForm />
            )
          }
        />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />

        <Route
          path="/book"
          element={
            <ProtectedRoute>
              <AddbookPage />
            </ProtectedRoute>
          }
        />

        <Route path="/" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
