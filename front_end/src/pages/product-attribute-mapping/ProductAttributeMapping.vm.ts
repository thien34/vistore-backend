import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductProductAttributeMappingResponse } from '@/model/ProductProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { useState } from 'react'
import { getProductAttributeMappingColumns } from './ProductAttributeMappingColumns'

interface Search extends RequestParams {
    productId: number
}

function useProductAttributeMappingViewModel() {
    const [filter, setFilter] = useState<Search>({ productId: 2 })

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
        }))
    }

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

    // GET COLUMNS
    const columns = getProductAttributeMappingColumns()

    return { listResponse, isLoading, refetch, setFilter, filter, handleTableChange, columns }
}
export default useProductAttributeMappingViewModel
