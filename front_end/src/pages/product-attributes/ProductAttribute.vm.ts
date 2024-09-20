import { TableRowSelection } from 'antd/es/table/interface'
import React, { useState } from 'react'
import { RequestParams } from '@/utils/FetchUtils'
import useGetAllApi from '@/hooks/use-get-all-api'
import ProductAttributeConfigs from './ProductAttributeConfigs'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { getProductAttributeColumns } from './ProductAttributeColumns'
interface Search extends RequestParams {
    published?: boolean
}

function useProductAttributeViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({})

    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
    )

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<ProductAttributeResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    // RETURN DATA
    const { data: listResponse, refetch } = useGetAllApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        filter,
    )

    // HANDLE DELETE
    const handleDelete = async () => {
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch().then(() => {
                    setSelectedRowKeys([])
                    setFilter((prevFilter) => ({ ...prevFilter, pageNo: 1 }))
                })
            },
        })
    }

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
        }))
    }

    // HANDLE SEARCH
    const handleSearch = (newFilter: { name: string }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            ...newFilter,
            pageNo: 1,
        }))
    }

    const columns = getProductAttributeColumns()

    return {
        rowSelection,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        columns,
    }
}
export default useProductAttributeViewModel
