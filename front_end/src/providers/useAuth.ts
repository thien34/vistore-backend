import { useContext } from 'react'
import { ProviderAuthContext } from './ProviderAuth'

const useAuth = () => {
    const { user, setUser } = useContext(ProviderAuthContext)
    return { user, setUser }
}

export default useAuth
