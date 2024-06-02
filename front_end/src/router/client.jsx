import LayoutMain from '@/layouts/client/LayoutMain'
import Home from '@/pages/client/home'
import Login from '@/pages/client/login'

const routersPublic = [
    {
        path: '/',
        layout: 'main',
        element: <Home />
    },
    {
        path: '/login',
        layout: 'main',
        element: <Login />
    }
]

const routersPrivate = [
    {
        path: '/user',
        layout: 'main',
        element: <h1>User Page</h1>
    },
    {
        path: '/',
        layout: 'main',
        element: <h1>Home private Page</h1>
    }
]

const routersPrivateClient = routersPrivate.map((item) => {
    let layout = <></>
    if (item.layout === 'main') layout = <LayoutMain />
    return {
        element: layout,
        children: [
            {
                path: item.path,
                element: item.element
            }
        ]
    }
})

const routersPublicClient = routersPublic.map((item) => {
    let layout = <></>
    if (item.layout === 'main') layout = <LayoutMain />
    return {
        element: layout,
        children: [
            {
                path: item.path,
                element: item.element
            }
        ]
    }
})

export { routersPublicClient, routersPrivateClient }
