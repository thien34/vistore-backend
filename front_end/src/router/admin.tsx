import LayoutMain from '@/pages/layouts/LayoutMain'
import ManagerPath from '@/constants/ManagerPath '
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import CategoryManage from '@/pages/category/CategoryManage'
import CategoryCreate from '@/pages/category/CategoryCreate'
import CategoryUpdate from '@/pages/category/CategoryUpdate'
import Home from '@/pages/home'
import ProductAttributeCreate from '@/pages/productAttribute/ProductAttributeCreate.tsx'
import ProductAttributeSearch from '@/pages/productAttribute/ProductAttributeManage.tsx'
import ProductAttributeUpdate from '@/pages/productAttribute/ProductAttributeUpdate.tsx'
import ManufactureManage, { ManufactureCreate, ManufactureUpdate } from '@/pages/manufacturer'

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
        path: ManagerPath.PRODUCT_ATTRIBUTE_ADD,
        layout: 'main',
        element: <ProductAttributeCreate />,
        breadcrumbName: 'ProductAttribute',
        pageType: 'product-attribute-add',
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_UPDATE,
        layout: 'main',
        element: <ProductAttributeUpdate />,
        breadcrumbName: 'Update Category',
        pageType: 'category',
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE,
        layout: 'main',
        element: <ProductAttributeSearch />,
        breadcrumbName: 'ProductAttribute',
        pageType: 'product-attribute',
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
    {
        path: ManagerPath.MANUFACTURE,
        layout: 'main',
        element: <ManufactureManage />,
        breadcrumbName: 'Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.MANUFACTURE_ADD,
        layout: 'main',
        element: <ManufactureCreate />,
        breadcrumbName: 'Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.MANUFACTURE_UPDATE,
        layout: 'main',
        element: <ManufactureUpdate />,
        breadcrumbName: 'Manufacture',
        pageType: 'manufacture',
    },
]

const routesAdmin = [
    {
        path: '/admin',
        children: routers.map((item: { breadcrumbName: string; element: Element; path: string; layout: string }) => {
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
