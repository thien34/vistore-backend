import LayoutMain from '@admin/layouts/LayoutMain'
import ProductTag from '../pages/product_tag/ProductTag'

const routers = [
    {
        path: '',
        layout: 'main',
        element: <h1>Home Page</h1>,
    },
    {
        path: 'product-tag',
        layout: 'main',
        element: <ProductTag />,
        breadcrumbName: 'ProductTag',
        pageType: 'product-tag',
    },
]

const routesAdmin = [
    {
        path: '/admin',
        children: routers.map((item) => {
            let layout = <></>
            if (item.layout === 'main') layout = <LayoutMain />
            return {
                element: layout,
                children: [
                    {
                        path: item.path,
                        element: item.element,
                        breadcrumbName: item.breadcrumbName,
                    },
                ],
            }
        }),
    },
]
export default routesAdmin
