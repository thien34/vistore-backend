import LayoutMain from '@admin/layouts/LayoutMain'
import Home from '../pages/home'
import Category from '../pages/category/Category'
import CategoryCreateUpdate from '../pages/category/CategoryCreateUpdate'
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import ManagerPath from '@/constants/ManagerPath '

const routers = [
    {
        path: '',
        layout: 'main',
        element: <Home />,
    },
    {
        path: ManagerPath.PRODUCT_TAG,
        layout: 'main',
        element: <ProductTagManage />,
        breadcrumbName: 'ProductTag',
        pageType: 'product-tag',
    },
    {
        path: ManagerPath.PRODUCT_TAG,
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
