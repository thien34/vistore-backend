import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import ProviderAuth from './providers/ProviderAuth'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

const queryClient = new QueryClient()
ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <ProviderAuth>
                <App />
            </ProviderAuth>
        </QueryClientProvider>
    </React.StrictMode>,
)
