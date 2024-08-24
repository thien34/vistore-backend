import { useState } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductSpecificationAttributeMappingByProductResponse } from '@/model/ProductSpecificationAttributeMapping'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { getProductSpecificationAttributeMappingColumns } from './ProductSpecificationAttributeMappingColumns'

interface Search extends RequestParams {
    productId: number
}

function useProductAttributeMappingViewModel() {
    const [filter, setFilter] = useState<Search>({ productId: 31 })
    const [isSpinning, setIsSpinning] = useState(false)

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ProductSpecificationAttributeMappingByProductResponse>(
        ProductSpecificationAttributeMappingConfigs.resourceGetByProductId,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        filter,
    )

    const handleEdit = (record: ProductSpecificationAttributeMappingByProductResponse) => {
        // Handle edit logic here
    }

    const handleDelete = (id: number) => {
        // Handle delete logic here
    }

    const columns = getProductSpecificationAttributeMappingColumns(handleEdit, handleDelete)

    const handleReload = async () => {
        setIsSpinning(true)
        try {
            await refetch()
        } catch (error) {
            console.error('Failed to reload data:', error)
        } finally {
            setIsSpinning(false)
        }
    }

    return { listResponse, isLoading, refetch, setFilter, filter, handleTableChange, columns, handleReload, isSpinning }
}

export default useProductAttributeMappingViewModel
