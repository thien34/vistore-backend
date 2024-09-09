import { MediaProdMapManage } from '../multimedia'
import { ProductAttributeMappingManage } from '../product-attribute-mapping'
import { ProdSpecAttrMappingManage } from '../productSpecificationAttributeMapping'
import ProductInfo from './ProductInfo'
import ProductPrice from './ProductPrice'
import ProductShipping from './ProductShipping'
import ProductInventory from './ProductInventory'
import ProductAction from './ProductAction'

export default function ProductUpdate() {
    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <ProductAction isUpdate={true} />
                <ProductInfo />
                <ProductPrice />
                <ProductShipping />
                <ProductInventory />
                <MediaProdMapManage></MediaProdMapManage>
                <ProductAttributeMappingManage></ProductAttributeMappingManage>
                <ProdSpecAttrMappingManage></ProdSpecAttrMappingManage>
            </div>
        </>
    )
}
