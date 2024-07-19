import { TableRowSelection } from 'antd/es/table/interface'
// import  {getProductAttributeColumns} from './ProductAttributeColumns'
import { useState } from 'react'
import { RequestParams } from '@/utils/FetchUtils'
import useGetAllApi from '@/hooks/use-get-all-api'
import ProductAttributeConfigs from './ProductAttributeConfigs'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'

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
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        filter,
    )

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
            pageNo: 0,
        }))
    }

    return {
        rowSelection,
        // columns,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    }
}
export default useProductAttributeViewModel
