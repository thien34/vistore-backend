import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import ProviderAuth from './providers/ProviderAuth'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { Provider } from 'react-redux'
import store from './redux/store/productStore'

const queryClient = new QueryClient()
ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <Provider store={store}>
                <ProviderAuth>
                    <App />
                </ProviderAuth>
            </Provider>
        </QueryClientProvider>
    </React.StrictMode>,
)
