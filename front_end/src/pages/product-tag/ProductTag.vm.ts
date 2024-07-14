import useCreateApi from '@/hooks/use-create-api'
import { useState } from 'react'
import ProductTagConfigs from './ProductTagConfigs'
import { useQueryClient } from '@tanstack/react-query'
import { RequestParams } from '@/utils/FetchUtils'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import { ProductTagRequest, ProductTagResponse } from '@/model/ProductTag'
import useGetAllApi from '@/hooks/use-get-all-api'
import getProductTagColumns from './ProductTagColumns'

interface Search extends RequestParams {
    cate?: string
}

function useProductTagCreateViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [selectedTag, setSelectedTag] = useState<ProductTagResponse | null>(null)
    const [title, setTitle] = useState(ProductTagConfigs.createTitle)
    const [filter, setFilter] = useState<Search>({})
    const queryClient = useQueryClient()

    const createApi = useCreateApi<ProductTagRequest, string>(ProductTagConfigs.resourceUrl)
    const deleteApi = useDeleteByIdsApi<number>(ProductTagConfigs.resourceUrl, ProductTagConfigs.resourceKey)

    const handleSearch = (term: string) => {
        if (term !== filter?.name) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                name: term,
                pageNo: 0,
            }))
        }
    }

    const handleCreate = async (productTag: ProductTagRequest) => {
        createApi.mutate(productTag, {
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

    const showModal = () => {
        setIsModalOpen(true)
    }

    const handleEdit = (data: ProductTagResponse) => {
        setSelectedTag(data)
        setTitle(ProductTagConfigs.updateTitle)
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
        showModal,
        isModalOpen,
        selectedTag,
        title,
        setTitle,
        setIsModalOpen,
        columns,
        filter,
        listResponse,
        handleTableChange,
    }
}

export default useProductTagCreateViewModel
