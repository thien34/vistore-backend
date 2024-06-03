import Home from '@client/pages/home'
import LayoutMain from '@client/layouts/LayoutMain'
import Login from '@client/pages/login'

const routersPublic = [
    {
        path: '/',
        layout: 'main',
        element: <Home />,
    },
    {
        path: '/login',
        layout: 'main',
        element: <Login />,
    },
]

const routersPrivate = [
    {
        path: '/user',
        layout: 'main',
        element: <h1>User Page</h1>,
    },
    {
        path: '/',
        layout: 'main',
        element: <h1>Home private Page</h1>,
    },
]

const routersPrivateClient = routersPrivate.map((item) => {
    let layout = <></>
    if (item.layout === 'main') layout = <LayoutMain />
    return {
        element: layout,
        children: [
            {
                path: item.path,
                element: item.element,
            },
        ],
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
                element: item.element,
            },
        ],
    }
})

export { routersPublicClient, routersPrivateClient }
