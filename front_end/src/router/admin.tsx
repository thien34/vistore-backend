import LayoutMain from '@/pages/layouts/LayoutMain'
import ManagerPath from '@/constants/ManagerPath'
import Home from '@/pages/home'
import ProductTagManage from '@/pages/product-tag/ProductTagManage'
import { ProductAttributeCreate, ProductAttributeManage, ProductAttributeUpdate } from '@/pages/product-attributes'
import { ManufactureCreate, ManufactureManage, ManufactureUpdate } from '@/pages/manufacturer'
import { ProductAttributeMappingCreate, ProductAttributeMappingUpdate } from '@/pages/product-attribute-mapping'
import { ProductManage, ProductUpdate } from '@/pages/product'
import { CategoryCreate, CategoryManage, CategoryUpdate } from '@/pages/category'
import {
    SpecificationAttributeGroupCreate,
    SpecificationAttributeGroupManage,
    SpecificationAttributeGroupUpdate,
} from '@/pages/specificationAttributeGroup'
import { SpecificationAttributeCreate, SpecificationAttributeUpdate } from '@/pages/specificationAttribute'
import {
    ProductSpecificationAttributeMappingCreate,
    ProductSpecificationAttributeMappingUpdate,
} from '@/pages/productSpecificationAttributeMapping'
import StockQuantityHistoryManage from '@/pages/stockQuantityHistory/StockQuantityHistoryManage'
import ProductCreate from '@/pages/product/ProductCreate'
import RelatedProductManage from '@/pages/related-products'
import { DiscountCreate, DiscountManage, DiscountUpdate } from '@/pages/discount'
import CustomerManage from '@/pages/customer/CustomerManage'
import { CustomerRoleCreate, CustomerRoleManage, CustomerRoleUpdate } from '@/pages/customer-roles'
import CustomerCreate from '@/pages/customer/CustomerCreate'
import { CustomerUpdate } from '@/pages/customer'
import { RetailSaleCreate } from '@/pages/retail-sale'
import AddressCreate from '@/pages/address/AddressCreate'
import AddressUpdate from '@/pages/address/AddressUpdate'

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
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_ADD,
        layout: 'main',
        element: <ProductAttributeCreate />,
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_UPDATE,
        layout: 'main',
        element: <ProductAttributeUpdate />,
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE,
        layout: 'main',
        element: <ProductAttributeManage />,
    },
    {
        path: ManagerPath.CATEGORY,
        layout: 'main',
        element: <CategoryManage />,
    },
    {
        path: ManagerPath.CATEGORY_ADD,
        layout: 'main',
        element: <CategoryCreate />,
    },
    {
        path: ManagerPath.CATEGORY_UPDATE,
        layout: 'main',
        element: <CategoryUpdate />,
    },
    {
        path: ManagerPath.MANUFACTURE,
        layout: 'main',
        element: <ManufactureManage />,
    },
    {
        path: ManagerPath.MANUFACTURE_ADD,
        layout: 'main',
        element: <ManufactureCreate />,
    },
    {
        path: ManagerPath.MANUFACTURE_UPDATE,
        layout: 'main',
        element: <ManufactureUpdate />,
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_MAPPING_ADD,
        layout: 'main',
        element: <ProductAttributeMappingCreate />,
    },
    {
        path: ManagerPath.PRODUCT_ATTRIBUTE_MAPPING_UPDATE,
        layout: 'main',
        element: <ProductAttributeMappingUpdate />,
    },
    {
        path: ManagerPath.STOCK_QUANTITY_HISTORY,
        layout: 'main',
        element: <StockQuantityHistoryManage />,
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_ADD,
        layout: 'main',
        element: <SpecificationAttributeGroupCreate />,
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_UPDATE,
        layout: 'main',
        element: <SpecificationAttributeGroupUpdate />,
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE,
        layout: 'main',
        element: <SpecificationAttributeGroupManage />,
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_UPDATE,
        layout: 'main',
        element: <SpecificationAttributeUpdate />,
    },
    {
        path: ManagerPath.SPECIFICATION_ATTRIBUTE_ADD,
        layout: 'main',
        element: <SpecificationAttributeCreate />,
    },
    {
        path: ManagerPath.PRODUCT,
        layout: 'main',
        element: <ProductManage />,
    },
    {
        path: ManagerPath.PRODUCT_EDIT,
        layout: 'main',
        element: <ProductUpdate />,
    },
    {
        path: ManagerPath.PRODUCT_CREATE,
        layout: 'main',
        element: <ProductCreate />,
    },
    {
        path: ManagerPath.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_ADD,
        layout: 'main',
        element: <ProductSpecificationAttributeMappingCreate />,
    },
    {
        path: ManagerPath.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATE,
        layout: 'main',
        element: <ProductSpecificationAttributeMappingUpdate />,
    },
    {
        path: ManagerPath.RELATED_PRODUCT,
        layout: 'main',
        element: <RelatedProductManage />,
    },
    {
        path: ManagerPath.DISCOUNT,
        layout: 'main',
        element: <DiscountManage />,
    },
    {
        path: ManagerPath.DISCOUNT_ADD,
        layout: 'main',
        element: <DiscountCreate />,
    },
    {
        path: ManagerPath.DISCOUNT_UPDATE,
        layout: 'main',
        element: <DiscountUpdate />,
    },
    {
        path: ManagerPath.CUSTOMER,
        layout: 'main',
        element: <CustomerManage />,
    },
    {
        path: ManagerPath.CUSTOMER_ADD,
        layout: 'main',
        element: <CustomerCreate />,
    },
    {
        path: ManagerPath.CUSTOMER_EDIT,
        layout: 'main',
        element: <CustomerUpdate />,
    },
    {
        path: ManagerPath.CUSTOMER_ROLE,
        layout: 'main',
        element: <CustomerRoleManage />,
    },
    {
        path: ManagerPath.CUSTOMER_ROLE_ADD,
        layout: 'main',
        element: <CustomerRoleCreate />,
    },
    {
        path: ManagerPath.CUSTOMER_ROLE_UPDATE,
        layout: 'main',
        element: <CustomerRoleUpdate />,
    },
    {
        path: ManagerPath.ADDRESS_ADD,
        layout: 'main',
        element: <AddressCreate />,
    },
    {
        path: ManagerPath.ADDRESS_UPDATE,
        layout: 'main',
        element: <AddressUpdate />,
    },
    {
        path: ManagerPath.RETAIL_SALE_CREATE,
        layout: 'main',
        element: <RetailSaleCreate />,
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
