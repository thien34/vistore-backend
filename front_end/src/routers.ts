import { createBrowserRouter } from 'react-router-dom'
import routesAdmin from './router/admin'
import { routersPrivateClient, routersPublicClient } from './router/client'

export const routersPublic = createBrowserRouter(routersPublicClient)
export const routersPrivate = createBrowserRouter([...routesAdmin, ...routersPrivateClient])
