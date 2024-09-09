import { ProductSpecificationAttributeMappingByProductResponse } from '@/model/ProductSpecificationAttributeMapping'
import { getProductSpecificationAttributeMappingColumns } from './ProductSpecificationAttributeMappingColumns'
import { useNavigate, useParams } from 'react-router-dom'
import { Modal } from 'antd'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import ProductSpecificationAttributeMappingConfigs from './ProductSpecificationAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { useEffect, useState } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api'

export default function useProdSpecAttrMappingManage() {
    interface Search extends RequestParams {
        productId: number
    }
    const navigate = useNavigate()
    const { productId } = useParams<{ productId: string }>()
    const [filter, setFilter] = useState<Search>({ productId: Number(productId) })

    useEffect(() => {
        if (productId) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                productId: Number(productId),
            }))
        }
    }, [productId])

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
                deleteApi.mutate(id, {
                    onSuccess: () => {
                        navigate(`/admin/products/${productId}`)
                    },
                })
                deleteApi.mutate(Number(id.toString()))
            },
        })
    }

    const columns = getProductSpecificationAttributeMappingColumns(handleEdit, handleDelete)

    const { data: listResponse, isLoading } = useGetAllApi<ProductSpecificationAttributeMappingByProductResponse>(
        ProductSpecificationAttributeMappingConfigs.resourceGetByProductId,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        filter,
    )

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    return { columns, listResponse, isLoading, filter, handleTableChange }
}
