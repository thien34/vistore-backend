import { RouterProvider } from 'react-router-dom'
import { routersPrivate, routersPublic } from './routers'
import useAuth from './providers/useAuth'

function App() {
    const { user } = useAuth()
    const routers = user ? routersPrivate : routersPublic
    return <RouterProvider router={routers} />
}

export default App
