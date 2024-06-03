import { RouterProvider } from 'react-router-dom'
import { routersPrivate, routersPublic } from './router'
import useAuth from './hooks/useAuth'

function App() {
    const { user } = useAuth()
    const routers = user ? routersPrivate : routersPublic
    return <RouterProvider router={routers}></RouterProvider>
}

export default App
