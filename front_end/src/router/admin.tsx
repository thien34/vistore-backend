import LayoutMain from '@/pages/layouts/LayoutMain'
import ManagerPath from '@/constants/ManagerPath '
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import CategoryManage from '@/pages/category/CategoryManage'
import CategoryCreate from '@/pages/category/CategoryCreate'
import CategoryUpdate from '@/pages/category/CategoryUpdate'
import Home from '@/pages/home'

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
        path: ManagerPath.CATEGORY,
        layout: 'main',
        element: <CategoryManage />,
        breadcrumbName: 'Category',
        pageType: 'category',
    },
    {
        path: ManagerPath.CATEGORY_ADD,
        layout: 'main',
        element: <CategoryCreate />,
        breadcrumbName: 'Add Category',
        pageType: 'category',
    },
    {
        path: ManagerPath.CATEGORY_UPDATE,
        layout: 'main',
        element: <CategoryUpdate />,
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
