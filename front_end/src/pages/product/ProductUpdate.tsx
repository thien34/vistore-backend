import { MediaProdMapManage } from '../multimedia'
import { ProductAttributeMappingManage } from '../product-attribute-mapping'
import { ProdSpecAttrMappingManage } from '../productSpecificationAttributeMapping'

export default function ProductUpdate() {
    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <MediaProdMapManage></MediaProdMapManage>
                <ProductAttributeMappingManage></ProductAttributeMappingManage>
                <ProdSpecAttrMappingManage></ProdSpecAttrMappingManage>
            </div>
        </>
    )
}
