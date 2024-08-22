import { useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingConfigs'

export default function useProductUpdateViewModel() {
    const { id: productId } = useParams<{ id: string }>()
    const {
        data: apiData,
        isSuccess,
        error,
    } = useGetByIdApi(
        ProductSpecificationAttributeMappingConfigs.resourceGetByProductId,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        Number(productId),
    )

    if (error) {
        return false
    }

    const dataSource = isSuccess && apiData && apiData.items ? apiData.items : []

    const tableData = dataSource.map((mapping) => {
        const attributeName = mapping.specificationAttributeName
        let optionName = mapping.specificationAttributeOptionName

        // If specificationAttributeOptionId is null, handle customValue
        if (mapping.specificationAttributeOptionId === null && mapping.customValue) {
            try {
                const parsedValue = JSON.parse(mapping.customValue)
                optionName = parsedValue.custom_value || mapping.customValue
            } catch (e) {
                console.error('Error parsing customValue:', e)
                optionName = mapping.customValue // Fallback to raw customValue if parsing fails
            }
        } else {
            optionName = mapping.specificationAttributeOptionName || mapping.customValue
        }

        return {
            key: mapping.id,
            attributeName,
            optionName,
            showOnProductPage: mapping.showOnProductPage,
            displayOrder: mapping.displayOrder,
        }
    })

    return {
        tableData,
        productId,
    }
}
