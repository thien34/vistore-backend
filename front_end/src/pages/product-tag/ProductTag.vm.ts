import { useState } from 'react'
import ProductTagConfigs from './ProductTagConfigs'
import { useQueryClient } from '@tanstack/react-query'
import { RequestParams } from '@/utils/FetchUtils'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import { ProductTagResponse, ProductTagUpdateRequest } from '@/model/ProductTag'
import useGetAllApi from '@/hooks/use-get-all-api'
import getProductTagColumns from './ProductTagColumns'
import useUpdateApi from '@/hooks/use-update-api'

interface Search extends RequestParams {
    cate?: string
}

function useProductTagCreateViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [selectedTag, setSelectedTag] = useState<ProductTagResponse | null>(null)
    const [filter, setFilter] = useState<Search>({})
    const queryClient = useQueryClient()

    const deleteApi = useDeleteByIdsApi<number>(ProductTagConfigs.resourceUrl, ProductTagConfigs.resourceKey)
    const { mutate: updateProductTag } = useUpdateApi<ProductTagUpdateRequest>(
        ProductTagConfigs.resourceUrl,
        ProductTagConfigs.resourceKey,
        selectedTag?.id ?? 0,
    )

    const handleSearch = (term: string) => {
        if (term !== filter?.name) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                name: term,
                pageNo: 0,
            }))
        }
    }

    const handleCreate = async (productTag: ProductTagUpdateRequest) => {
        updateProductTag(productTag, {
            onSuccess: () => {
                queryClient.invalidateQueries({ queryKey: [ProductTagConfigs.resourceKey, 'getAll'] })
            },
        })
        setIsModalOpen(true)
    }

    const handleDelete = async () => {
        const selectedIds = selectedRowKeys.map((key) => Number(key))
        deleteApi.mutate(selectedIds, {
            onSuccess: () => {
                queryClient.invalidateQueries({ queryKey: [ProductTagConfigs.resourceKey, 'getAll'] })
            },
        })
    }

    const onSelectChange = async (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }
    const rowSelection = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    const handleEdit = (data: ProductTagResponse) => {
        setSelectedTag(data)
        setIsModalOpen(true)
    }

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current - 1,
            pageSize: pagination.pageSize,
        }))
    }

    const columns = getProductTagColumns(handleEdit)

    const { data: listResponse } = useGetAllApi<ProductTagResponse>(
        ProductTagConfigs.resourceUrl,
        ProductTagConfigs.resourceKey,
        filter,
    )

    return {
        selectedRowKeys,
        handleSearch,
        handleCreate,
        handleDelete,
        rowSelection,
        isModalOpen,
        selectedTag,
        setIsModalOpen,
        columns,
        filter,
        listResponse,
        handleTableChange,
    }
}

export default useProductTagCreateViewModel
