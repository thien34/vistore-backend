import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductSpecificationAttributeMappingByProductResponse } from '@/model/ProductSpecificationAttributeMapping'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { useEffect, useState } from 'react'
import { getProductSpecificationAttributeMappingColumns } from './ProductSpecificationAttributeMappingColumns'
import { useNavigate, useParams } from 'react-router-dom'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import { message, Modal } from 'antd'
interface Search extends RequestParams {
    productId: number
}
function useProductUpdateViewModel() {
    const { productId } = useParams<{ productId: string }>()
    const [filter, setFilter] = useState<Search>({ productId: parseInt(productId || '0', 10) })
    const navigate = useNavigate()

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ProductSpecificationAttributeMappingByProductResponse>(
        ProductSpecificationAttributeMappingConfigs.resourceGetByProductId,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        filter,
    )
    const deleteApi = useDeleteByIdApi(
        ProductSpecificationAttributeMappingConfigs.resourceUrl,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
    )

    const handleEdit = (record: ProductSpecificationAttributeMappingByProductResponse) => {
        navigate(`/admin/products/product-spec-attribute-mapping/edit/${filter.productId}/${record.id}`)
    }

    const handleDelete = (id: number) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteApi.mutate(parseInt(id.toString()), {
                    onSuccess: () => {
                        message.success('Deleted successfully')
                        navigate(`/admin/products/${productId}`)
                    },
                    onError: () => {
                        message.error('Delete failed!')
                    },
                })
            },
        })
    }

    useEffect(() => {
        if (productId) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                productId: parseInt(productId, 10),
            }))
        }
    }, [productId])

    const columns = getProductSpecificationAttributeMappingColumns(handleEdit, handleDelete)

    return { listResponse, isLoading, refetch, setFilter, filter, handleTableChange, columns }
}
export default useProductUpdateViewModel
