import PropTypes from 'prop-types'
import { createContext, useState } from 'react'
export const ProviderAuthContext = createContext({
    user: null,
    setUser: () => {}
})

const ProviderAuth = ({ children }) => {
    const [user, setUser] = useState(null)
    return <ProviderAuthContext.Provider value={{ user, setUser }}>{children}</ProviderAuthContext.Provider>
}

export default ProviderAuth

ProviderAuth.propTypes = {
    children: PropTypes.node
}
