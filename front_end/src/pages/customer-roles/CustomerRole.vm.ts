import { useState, useEffect } from 'react'
import getCustomerRoleColumns from './CustomerRoleColumns'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import { RequestParams } from '@/utils/FetchUtils'
import useGetAllApi from '@/hooks/use-get-all-api'
import CustomerRoleConfigs from './CustomerRoleConfigs'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'

interface Search extends RequestParams {}

function useCustomerRoleViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({ pageNo: 1, pageSize: 6 })
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        CustomerRoleConfigs.resourceUrl,
        CustomerRoleConfigs.resourceKey,
    )

    useEffect(() => {
        document.title = 'Customer Roles - Vítore'
    }, [])

    const columns = getCustomerRoleColumns()
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<CustomerRoleResponse>(CustomerRoleConfigs.resourceUrl, CustomerRoleConfigs.resourceKey, filter)

    // Handle delete
    const handleDelete = async () => {
        const currentPage = filter.pageNo || 1
        const currentSize = listResponse?.items.length || 0
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                let newPage = currentPage
                if (currentSize === selectedRowKeys.length && currentPage > 1) {
                    newPage = currentPage - 1
                }
                setFilter((prevFilter) => ({
                    ...prevFilter,
                    pageNo: newPage,
                }))
                refetch()
                setSelectedRowKeys([])
            },
        })
    }

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    return {
        columns,
        handleTableChange,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    }
}

export default useCustomerRoleViewModel
