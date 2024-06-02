import { Outlet } from 'react-router-dom'

const routers = [
    {
        path: '',
        layout: 'main',
        element: <h1>Home Page</h1>
    }
]

const routesAdmin = [
    {
        path: '/admin',
        children: routers.map((item) => {
            let layout = <></>
            if (item.layout === 'main')
                layout = (
                    <div>
                        <h1>Admin layout</h1>
                        <Outlet />
                    </div>
                )
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
    }
]
export default routesAdmin
