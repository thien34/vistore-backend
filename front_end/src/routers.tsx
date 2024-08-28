import { createBrowserRouter } from 'react-router-dom'
import routesAdmin from './router/admin'
import { routersPrivateClient, routersPublicClient } from './router/client'
import { NotFound } from './pages/layouts/NotFound'

export const routersPublic = createBrowserRouter(routersPublicClient)
export const routersPrivate = createBrowserRouter([
    {
        errorElement: <NotFound />,
        children: [...routesAdmin, ...routersPrivateClient],
    },
])
