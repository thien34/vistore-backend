import ProductSpecificationAttributeMappingCreate from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingCreate'
import ProductUpdateSpecificationAttributeMapping from '@/pages/product/ProductUpdateSpecificationAttributeMapping.tsx'
import useSpecificationAttributeManageViewModel from '@/pages/specificationAttribute/SpecificationAttribute.vm.ts'

const ProductSpecificationManager = () => {
    const { handleAttributeTypeChange, attributeType } = useSpecificationAttributeManageViewModel

    return (
        <div>
            <ProductSpecificationAttributeMappingCreate onAttributeTypeChange={handleAttributeTypeChange} />
            <ProductUpdateSpecificationAttributeMapping attributeType={attributeType} />
        </div>
    )
}

export default ProductSpecificationManager
