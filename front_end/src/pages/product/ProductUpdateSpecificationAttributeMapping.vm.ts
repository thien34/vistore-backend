import { useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpeficationAttributeMapping/ProductSpecificationAttributeMappingConfigs.ts'
import { useState } from 'react'

export default function useProductUpdateSpecificationAttributeMappingViewModel() {
    const { id: productId } = useParams<{ id: string }>()
    const [isSpinning, setIsSpinning] = useState(false)
    const {
        data: apiData,
        isSuccess,
        error,
        refetch: refetchProductAttributeMappings,
    } = useGetByIdApi(
        ProductSpecificationAttributeMappingConfigs.resourceGetByProductId,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        Number(productId),
    )

    const handleReload = async () => {
        setIsSpinning(true)
        try {
            await new Promise((resolve) => setTimeout(resolve, 2000))
            await refetchProductAttributeMappings()
        } catch (error) {
            console.error('Failed to reload data:', error)
        } finally {
            setIsSpinning(false)
        }
    }

    if (error) {
        return false
    }

    const dataSource = isSuccess && apiData && apiData.items ? apiData.items : []

    const tableData = dataSource.map((mapping) => {
        let attributeName = mapping.specificationAttributeName
        if (mapping.specificationAttributeOptionId === null && mapping.specificationAttributeInfo) {
            // Nếu không có optionId, lấy tên từ JSON
            try {
                const info = JSON.parse(mapping.specificationAttributeInfo)
                attributeName = info.name
            } catch (e) {
                console.error('Error parsing specificationAttributeInfo:', e)
                attributeName = 'Unknown'
            }
        }

        return {
            key: mapping.id,
            attributeName,
            optionName: mapping.customValue,
            showOnProductPage: mapping.showOnProductPage,
            displayOrder: mapping.displayOrder,
        }
    })

    return {
        handleReload,
        tableData,
        productId,
        isSpinning,
    }
}
