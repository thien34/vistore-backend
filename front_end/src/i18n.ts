import i18n, { Resource } from 'i18next'
import { initReactI18next } from 'react-i18next'
import HttpApi from 'i18next-http-backend'

interface TranslationResource {
    resourceName: string
    resourceValue: string
}

const mapApiDataToI18nFormat = (data: TranslationResource[]): Resource => {
    const result: Resource = {
        vi: {
            translation: {},
        },
        en: {
            translation: {},
        },
    }

    data.forEach((item: TranslationResource) => {
        ;(result.vi.translation as Record<string, string>)[item.resourceName] = item.resourceValue
        ;(result.en.translation as Record<string, string>)[item.resourceName] = item.resourceValue
    })

    return result
}

export const loadLocaleResources = async (language: string): Promise<Resource | null> => {
    try {
        const response = await fetch(`http://localhost:8080/locale_string_resources?languageName=${language}`)
        if (!response.ok) {
            throw new Error(`Failed to load resources for ${language}`)
        }
        const data = await response.json()
        console.log(`Loaded resources for ${language}:`, data)
        return mapApiDataToI18nFormat(data.data)
    } catch (error) {
        console.error(`Error loading resources for ${language}:`, error)
        return null
    }
}

const initializeI18n = async () => {
    const resources = await loadLocaleResources('en')
    if (resources) {
        i18n.use(HttpApi)
            .use(initReactI18next)
            .init({
                resources,
                lng: 'vi',
                fallbackLng: 'vi',
                interpolation: {
                    escapeValue: false,
                },
            })
    } else {
        console.error('Language err.')
    }
}

initializeI18n()

export const changeLanguage = async (language: string) => {
    const resources = await loadLocaleResources(language)
    if (resources && resources[language] && resources[language].translation) {
        i18n.addResourceBundle(language, 'translation', resources[language].translation, true, true)
        i18n.changeLanguage(language)
        console.log(`Language changed to ${language}`)
    } else {
        console.error(`Failed to change language to ${language}`)
    }
}

export default i18n
