import { StockQuantityHistoryResponse } from '@/model/StockQuantityHistory'
import { RequestParams } from '@/utils/FetchUtils'
import { useState } from 'react'
import StockQuantityHistoryConfigs from './StockQuantityHistoryConfig'
import useGetAllApi from '@/hooks/use-get-all-api'
import getStockQuantityColumns from './StockQuantityColumns'

interface Search extends RequestParams {
    page?: number
}

export default function useStockQuantityHistoryViewModel() {
    const [filter, setFilter] = useState<Search>({})
    // GET COLUMNS
    const columns = getStockQuantityColumns()
    // RETURN DATA
    const { data: listResponse, isLoading } = useGetAllApi<StockQuantityHistoryResponse>(
        StockQuantityHistoryConfigs.resourceUrl,
        StockQuantityHistoryConfigs.resourceKey,
        filter,
    )

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            page: pagination.current,
        }))
    }

    // HANDLE SEARCH
    const handleSearch = (newFilter: { productId: string }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            productId: newFilter.productId,
        }))
    }

    return {
        columns,
        filter,
        handleTableChange,
        handleSearch,
        listResponse,
        isLoading,
    }
}
