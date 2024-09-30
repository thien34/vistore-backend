import { useEffect, useState } from 'react'
import { TableRowSelection } from 'antd/es/table/interface'
import { useQueryClient } from '@tanstack/react-query'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import useGetAllApi from '@/hooks/use-get-all-api'
import useUpdateApi from '@/hooks/use-update-api'
import useCreateApi from '@/hooks/use-create-api'
import { RelatedProductRequest, RelatedProductResponse } from '@/model/RelatedProduct'
import { RequestParams } from '@/utils/FetchUtils'
import RelatedProductConfigs from './RelatedProductCofigs'
import getRelatedProductColumns from './RelatedProductColumns'
import { useParams } from 'react-router-dom'

interface Search extends RequestParams {
    productId?: string
    page?: number
}
export default function useRelatedProductViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [isModalAddOpen, setIsModalAddOpen] = useState(false)
    const [title, setTitle] = useState(RelatedProductConfigs.updateTitle)
    const [selectedRecord, setSelectedRecord] = useState<RelatedProductResponse | null>(null)
    const [filter, setFilter] = useState<Search>({})
    const [idrecord, setIdRecord] = useState(3)
    const [lstRelatedProduct, setLstRelatedProduct] = useState<RelatedProductRequest[]>([])
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        RelatedProductConfigs.resourceUrl,
        RelatedProductConfigs.resourceKey,
    )
    const { mutate: updateApi } = useUpdateApi<RelatedProductRequest>(
        RelatedProductConfigs.resourceUrl,
        RelatedProductConfigs.resourceKey,
        idrecord,
    )
    const { mutate: createApi } = useCreateApi<RelatedProductRequest[]>(RelatedProductConfigs.resourceUrl)
    const { productId: productIdParam } = useParams() // Lấy productId từ URL
    const [productId, setProductId] = useState<string | null>('1')
    const queryClient = useQueryClient()
    // SET TITLE
    useEffect(() => {
        if (productIdParam) {
            setProductId(productIdParam)
            setFilter({ ...filter, productId: productIdParam }) // Update filter with productId
            queryClient.invalidateQueries({ queryKey: [RelatedProductConfigs.resourceKey, 'getAll'] }) // Invalidate previous queries
        }
        document.title = 'Related Products - Vítore'
    }, [productIdParam, queryClient, filter])
    // HANDLE EDIT
    const handleEdit = (data: RelatedProductResponse) => {
        setIdRecord(data.id)
        setSelectedRecord(data)
        setTitle(RelatedProductConfigs.updateTitle)
        setIsModalOpen(true)
    }
    //handle create
    const handleCreate = () => {
        showModalAdd()
    }
    const handleCreateFromModal = (relatedProducts: RelatedProductRequest[]) => {
        if (relatedProducts.length > 0) {
            createApi(relatedProducts, {
                onSuccess: () => {
                    queryClient.invalidateQueries({
                        queryKey: [RelatedProductConfigs.resourceKey, 'getAll'],
                    })
                    setIsModalAddOpen(false)
                    setLstRelatedProduct([])
                },
            })
        }
    }
    // GET COLUMNS
    const columns = getRelatedProductColumns(handleEdit)

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }
    // HANDLE Update
    const handleUpdate = async (relatedProduct: RelatedProductRequest) => {
        updateApi(relatedProduct, {
            onSuccess: () => {
                // Invalidate the query for related products after successful creation
                queryClient.invalidateQueries({ queryKey: [RelatedProductConfigs.resourceKey, 'getAll'] })
            },
        })
        // Optionally open the modal or handle other state updates after the API call
        setIsModalOpen(true)
    }
    const rowSelection: TableRowSelection<RelatedProductResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    // RETURN DATA
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<RelatedProductResponse>(
        RelatedProductConfigs.resourceUrl,
        RelatedProductConfigs.resourceKey,
        filter,
    )
    //SHOW MODAL UPDATE

    const showModalUpdate = () => {
        setIsModalOpen(true)
    }
    //SHOW MODAL Add
    const showModalAdd = () => {
        setIsModalAddOpen(true)
    }
    // HANDLE DELETE
    const handleDelete = async () => {
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch()
                setSelectedRowKeys([])
            },
        })
    }
    // HANDLE SEARCH
    const handleSearch = (filter: { productId: string }) => {
        setFilter({ ...filter, productId: filter.productId })
    }
    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            page: pagination.current,
        }))
    }

    return {
        // Table data and interactions
        columns,
        rowSelection,
        listResponse,
        isLoading,
        handleTableChange,

        // Modals and record editing
        isModalOpen,
        setIsModalOpen,
        showModalUpdate,
        isModalAddOpen,
        setIsModalAddOpen,
        showModalAdd,
        title,
        setTitle,
        selectedRecord,
        setSelectedRecord,
        handleUpdate,
        handleEdit,
        handleCreate,

        // Actions and selections
        selectedRowKeys,
        handleDelete,
        handleCreateFromModal,
        lstRelatedProduct,

        // Search and filtering
        productId,
        filter,
        handleSearch,
    }
}
