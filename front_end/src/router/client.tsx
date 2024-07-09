import Home from '@/pages/home'
import LayoutMain from '@/pages/layouts/LayoutMain'

const routersPublic = [
    {
        path: '/',
        layout: 'main',
        element: <Home />,
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
