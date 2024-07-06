import { TableRowSelection } from 'antd/es/table/interface'
import getCategoryColumns from './CategoryColumns'
import { CategoriesResponse } from '@/model/Category'
import { useState } from 'react'
import { RequestParams } from '@/utils/FetchUtils'
import useGetAllApi from '@/hooks/use-get-all-api'
import CategoryConfigs from './CategoryConfigs'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'

interface Search extends RequestParams {
    published?: boolean
}

function useCategoryViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({})
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(CategoryConfigs.resourceUrl, CategoryConfigs.resourceKey)

    // GET COLUMNS
    const columns = getCategoryColumns()

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<CategoriesResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    // RETURN DATA
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<CategoriesResponse>(CategoryConfigs.resourceUrl, CategoryConfigs.resourceKey, filter)

    // HANDLE DELETE
    const handleDelete = async () => {
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch()
                setSelectedRowKeys([])
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
    const handleSearch = (newFilter: { name: string; published: boolean | undefined }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            ...newFilter,
            pageNo: 1,
        }))
    }

    return {
        rowSelection,
        columns,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    }
}
export default useCategoryViewModel
