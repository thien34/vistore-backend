import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import ProviderAuth from './providers/ProviderAuth'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <ProviderAuth>
            <App />
        </ProviderAuth>
    </React.StrictMode>,
)
