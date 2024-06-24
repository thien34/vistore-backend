import LayoutMain from '@admin/layouts/LayoutMain'
import ProductTag from '../pages/product_tag/ProductTag'
import Home from '../pages/home'
import Category from '../pages/category/Category'
import CategoryCreateUpdate from '../pages/category/CategoryCreateUpdate'

const routers = [
    {
        path: '',
        layout: 'main',
        element: <Home />,
    },
    {
        path: 'product-tag',
        layout: 'main',
        element: <ProductTag />,
        breadcrumbName: 'ProductTag',
        pageType: 'product-tag',
    },
    {
        path: 'category',
        layout: 'main',
        element: <Category />,
        breadcrumbName: 'Category',
        pageType: 'category',
    },
    {
        path: 'category/add',
        layout: 'main',
        element: <CategoryCreateUpdate />,
        breadcrumbName: 'Add Category',
        pageType: 'category',
    },
    {
        path: 'category/:id/update',
        layout: 'main',
        element: <CategoryCreateUpdate />,
        breadcrumbName: 'Update Category',
        pageType: 'category',
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
