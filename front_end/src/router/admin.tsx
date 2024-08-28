import LayoutMain from '@/pages/layouts/LayoutMain'
import ManagerPath from '@/constants/ManagerPath '
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import CategoryManage from '@/pages/category/CategoryManage'
import CategoryCreate from '@/pages/category/CategoryCreate'
import CategoryUpdate from '@/pages/category/CategoryUpdate'
import Home from '@/pages/home'
import ProductAttributeSearch from '@/pages/productAttribute/ProductAttributeManage.tsx'
import ProductAttributeUpdate from '@/pages/productAttribute/ProductAttributeUpdate.tsx'
import ManufactureManage, { ManufactureCreate, ManufactureUpdate } from '@/pages/manufacturer'
import ProductAttributeMappingCreate from '@/pages/product-attribute-mapping/ProductAttributeMappingCreate'
import SpecificationAttributeGroupManage from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupManage.tsx'
import SpecificationAttributeManage from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupManage.tsx'
import SpecificationAttributeGroupCreate from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupCreate.tsx'
import SpecificationAttributeCreate from '@/pages/specificationAttribute/SpecificationAttributeCreate.tsx'
import SpecificationAttributeUpdate from '@/pages/specificationAttribute/SpecificationAttributeUpdate.tsx'
import SpecificationAttributeGroupUpdate from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupUpdate.tsx'
import ProductManage from '@/pages/product'
import ProductSpecificationAttributeMappingCreate from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingCreate'
import ProductSpecificationAttributeMappingUpdate from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingUpdate'
import StockQuantityHistoryManage from '@/pages/stockQuantityHistory/StockQuantityHistoryManage'
import ProductAttribute from '@/pages/productAttribute/ProductAttributeCreate'
import ProductUpdate from '@/pages/product/ProductUpdate'
import { ProductAttributeMappingUpdate } from '@/pages/product-attribute-mapping'

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
        element: <ProductAttribute />,
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
        breadcrumbName: 'Add Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.MANUFACTURE_UPDATE,
        layout: 'main',
        element: <ManufactureUpdate />,
        breadcrumbName: 'Update Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_MAPPING_ADD,
        layout: 'main',
        element: <ProductAttributeMappingCreate />,
        breadcrumbName: 'Add Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_MAPPING_UPDATE,
        layout: 'main',
        element: <ProductAttributeMappingUpdate />,
        breadcrumbName: 'Add Manufacture',
        pageType: 'manufacture',
    },
    {
        path: ManagerPath.STOCK_QUANTITY_HISTORY,
        layout: 'main',
        element: <StockQuantityHistoryManage />,
        breadcrumbName: 'StockQuantityHistory',
        pageType: 'StockQuantityHistory',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP,
        layout: 'main',
        element: <SpecificationAttributeGroupManage />,
        breadcrumbName: 'Specification Attribute Group',
        pageType: 'specification-attribute-group',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_ADD,
        layout: 'main',
        element: <SpecificationAttributeGroupCreate />,
        breadcrumbName: 'Specification Attribute Group Add',
        pageType: 'specification-attribute-group',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_UPDATE,
        layout: 'main',
        element: <SpecificationAttributeGroupUpdate />,
        breadcrumbName: 'Specification Attribute Group Update',
        pageType: 'specification-attribute-group',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE,
        layout: 'main',
        element: <SpecificationAttributeManage />,
        breadcrumbName: 'Specification Attribute',
        pageType: 'specification-attribute',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_UPDATE,
        layout: 'main',
        element: <SpecificationAttributeUpdate />,
        breadcrumbName: 'Specification Attribute Update',
        pageType: 'specification-attribute',
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_ADD,
        layout: 'main',
        element: <SpecificationAttributeCreate />,
        breadcrumbName: 'Specification Attribute Create',
        pageType: 'specification-attribute',
    },
    {
        path: ManagerPath.PRODUCT,
        layout: 'main',
        element: <ProductManage />,
        breadcrumbName: 'Product Manage',
        pageType: 'product',
    },
    {
        path: ManagerPath.PRODUCT_UPDATE_SPECIFICATION_ATTRIBUTE_MAPPING,
        layout: 'main',
        element: <ProductUpdate />,
        breadcrumbName: 'Product Update',
        pageType: 'product',
    },
    {
        path: ManagerPath.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_ADD,
        layout: 'main',
        element: <ProductSpecificationAttributeMappingCreate />,
        breadcrumbName: 'Product Specification Attribute Mapping Create',
        pageType: 'product_specification_attribute_mapping',
    },
    {
        path: ManagerPath.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATE,
        layout: 'main',
        element: <ProductSpecificationAttributeMappingUpdate />,
        breadcrumbName: 'Product Specification Attribute Mapping Update',
        pageType: 'product_specification_attribute_mapping',
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
                    },
                ],
            }
        }),
    },
]
export default routesAdmin
