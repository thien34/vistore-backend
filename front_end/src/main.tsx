import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import ProviderAuth from './providers/ProviderAuth'
import { I18nextProvider } from 'react-i18next'
import i18n from './i18n'
import { LanguageProvider } from '@/providers/LanguageContext'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <I18nextProvider i18n={i18n}>
            <LanguageProvider>
                <ProviderAuth>
                    <App />
                </ProviderAuth>
            </LanguageProvider>
        </I18nextProvider>
    </React.StrictMode>,
)
