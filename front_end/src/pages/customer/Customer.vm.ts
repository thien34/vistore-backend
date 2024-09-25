import { useState } from 'react'
import getCustomerColumns from './CustomerColumns'
import { CustomerResponse } from '@/model/Customer'
import CustomerConfigs from './CustomerConfigs'
import useGetAllApi from '@/hooks/use-get-all-api'
import { RequestParams } from '@/utils/FetchUtils'
import dayjs from 'dayjs'
import { Filter } from './CustomerSearch'

interface Search extends RequestParams {
    email?: string
    firstName?: string
    lastName?: string
    registrationDateFrom?: string
    registrationDateTo?: string
    customerRoles?: string[]
    pageNo?: number
    pageSize?: number
}

export default function useCustomerViewModel() {
    const columns = getCustomerColumns()
    const [filter, setFilter] = useState<Search>({})
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])

    // Handle table change
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    const {
        data: dataResources,
        isLoading,
        refetch,
    } = useGetAllApi<CustomerResponse>(CustomerConfigs.resourceUrl, CustomerConfigs.resourceKey, filter)

    const handleSearch = (searchFilter: Filter) => {
        console.log('Search filter:', searchFilter)

        const searchParams: Search = {
            email: searchFilter.email || undefined,
            firstName: searchFilter.firstName || undefined,
            lastName: searchFilter.lastName || undefined,
            registrationDateFrom: searchFilter.registrationDateFrom
                ? dayjs(searchFilter.registrationDateFrom).toISOString()
                : undefined,
            registrationDateTo: searchFilter.registrationDateTo
                ? dayjs(searchFilter.registrationDateTo).toISOString()
                : undefined,
            customerRoles:
                searchFilter.customerRoles && searchFilter.customerRoles.length > 0
                    ? searchFilter.customerRoles
                    : undefined,
            pageNo: 1,
            pageSize: 6,
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
        selectedRowKeys,
        setSelectedRowKeys,
        filter,
        handleSearch,
    }
}
