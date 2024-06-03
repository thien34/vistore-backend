import { createBrowserRouter } from 'react-router-dom'
import { routersPrivateClient, routersPublicClient } from './client/router'
import routesAdmin from './admin/router'

export const routersPublic = createBrowserRouter(routersPublicClient)
export const routersPrivate = createBrowserRouter([...routesAdmin, ...routersPrivateClient])
