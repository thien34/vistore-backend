import React, { createContext } from 'react'

type ProviderAuthContextType = {
    user: null | { username: string }
    setUser: React.Dispatch<React.SetStateAction<null | { username: string }>>
}

export const ProviderAuthContext = createContext<ProviderAuthContextType>({
    user: null,
    setUser: () => {},
})
type ProviderAuthProps = {
    children: JSX.Element
}
const ProviderAuth: React.FC<ProviderAuthProps> = ({ children }) => {
    const [user, setUser] = React.useState<null | { username: string }>({ username: '' })
    return <ProviderAuthContext.Provider value={{ user, setUser }}>{children}</ProviderAuthContext.Provider>
}

export default ProviderAuth
