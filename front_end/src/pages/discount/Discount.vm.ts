import getDiscountColumns from './DiscountColumns'
import { DiscountResponse } from '@/model/Discount'
import DiscountConfigs from './DiscountConfigs'
import useGetAllApi from '@/hooks/use-get-all-api'
import { RequestParams } from '@/utils/FetchUtils'
import { useState } from 'react'
import { TableRowSelection } from 'antd/es/table/interface'
import { Filter } from './DiscountSearch'
import dayjs from 'dayjs'

interface Search extends RequestParams {
    startDate?: string
    endDate?: string
    discountTypeId?: string
    couponCode?: string
    name?: string
    isActive?: boolean
}

export default function useDiscountViewModel() {
    const columns = getDiscountColumns()
    const [filter, setFilter] = useState<Search>({})
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    // RETURN DATA
    const {
        data: dataResources,
        isLoading,
        refetch,
    } = useGetAllApi<DiscountResponse>(DiscountConfigs.resourceUrl, DiscountConfigs.resourceKey, filter)

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<DiscountResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    const handleSearch = (searchFilter: Filter) => {
        console.log('Search filter:', searchFilter)

        const searchParams: Search = {
            startDate: searchFilter.startDate ? dayjs(searchFilter.startDate).toISOString() : undefined,
            endDate: searchFilter.endDate ? dayjs(searchFilter.endDate).toISOString() : undefined,
            discountTypeId: searchFilter.discountTypeId !== 'All' ? searchFilter.discountTypeId : undefined,
            couponCode: searchFilter.couponCode || undefined,
            name: searchFilter.discountName || undefined,
            isActive: searchFilter.isActive !== 'All' ? searchFilter.isActive === 'true' : undefined,
        }

        setFilter(searchParams)
        refetch()
    }

    return {
        columns,
        handleTableChange,
        refetch,
        dataResources,
        isLoading,
        rowSelection,
        filter,
        handleSearch,
    }
}
