import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import ProviderAuth from './providers/ProviderAuth.jsx'

const queryClient = new QueryClient()
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ProviderAuth>
            <QueryClientProvider client={queryClient}>
                <App />
            </QueryClientProvider>
        </ProviderAuth>
    </React.StrictMode>
)
