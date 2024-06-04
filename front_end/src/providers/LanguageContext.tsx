import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'
import { changeLanguage as changeI18nLanguage } from '@/i18n'
import { useTranslation } from 'react-i18next'

interface LanguageContextProps {
    language: string
    changeLanguage: (language: string) => void
}

const LanguageContext = createContext<LanguageContextProps | undefined>(undefined)

interface LanguageProviderProps {
    children: ReactNode
}

export const LanguageProvider: React.FC<LanguageProviderProps> = ({ children }) => {
    const { i18n } = useTranslation()
    const [language, setLanguage] = useState<string>(i18n.language)

    const changeLanguage = async (newLanguage: string) => {
        await changeI18nLanguage(newLanguage)
        setLanguage(newLanguage)
    }

    useEffect(() => {
        const handleLanguageChanged = (lng: string) => {
            setLanguage(lng)
        }

        i18n.on('languageChanged', handleLanguageChanged)

        return () => {
            i18n.off('languageChanged', handleLanguageChanged)
        }
    }, [i18n])

    return <LanguageContext.Provider value={{ language, changeLanguage }}>{children}</LanguageContext.Provider>
}

export const useLanguage = (): LanguageContextProps => {
    const context = useContext(LanguageContext)
    if (!context) {
        throw new Error('Language err')
    }
    return context
}
