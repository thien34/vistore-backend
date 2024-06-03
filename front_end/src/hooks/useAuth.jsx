import { ProviderAuthContext } from '@/providers/ProviderAuth'
import { useContext } from 'react'

const useAuth = () => {
    return useContext(ProviderAuthContext)
}

export default useAuth
