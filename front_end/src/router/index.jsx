import { createBrowserRouter } from 'react-router-dom'
import { routersPrivateClient, routersPublicClient } from './client'
import routesAdmin from './admin'

export const routersPublic = createBrowserRouter(routersPublicClient)
export const routersPrivate = createBrowserRouter([...routesAdmin, ...routersPrivateClient])
