import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductProductAttributeMappingResponse } from '@/model/ProductProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { useCallback, useEffect, useMemo, useState } from 'react'
import { getProductAttributeMappingColumns } from './ProductAttributeMappingColumns'
import { useParams } from 'react-router-dom'

interface Search extends RequestParams {
    productId: number
}

function useProductAttributeMappingViewModel() {
    const { productId } = useParams<{ productId: string }>()
    const [filter, setFilter] = useState<Search>({ productId: Number(productId) || 0 })

    useEffect(() => {
        if (productId) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                productId: Number(productId),
            }))
        }
    }, [productId])

    // HANDLE TABLE CHANGE
    const handleTableChange = useCallback((pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
        }))
    }, [])

    // GET COLUMNS
    const columns = useMemo(getProductAttributeMappingColumns, [])

    // RETURN DATA
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ProductProductAttributeMappingResponse>(
        ProductAttributeMappingConfigs.resourceUrl,
        ProductAttributeMappingConfigs.resourceKey,
        filter,
    )

    return { listResponse, isLoading, refetch, setFilter, filter, handleTableChange, columns }
}
export default useProductAttributeMappingViewModel
