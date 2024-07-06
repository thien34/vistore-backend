import LayoutMain from '@admin/layouts/LayoutMain'
import ManagerPath from '@/constants/ManagerPath '
import Home from '@/admin/pages/home'
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import CategoryCreateUpdate from '@/pages/category/CategoryCreateUpdate'
import CategoryManage from '@/pages/category/CategoryManage'

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
