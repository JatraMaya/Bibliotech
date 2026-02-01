import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import AppRoutes from './routes/AppRoutes';
import { Toast } from './components/Toast';

const queryClient = new QueryClient();

function App() {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <Toast />
        <AppRoutes />
      </QueryClientProvider>
    </>
  );
}

export default App;
