import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductSpecificationAttributeMappingByProductResponse } from '@/model/ProductSpecificationAttributeMapping'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingConfigs'
import { RequestParams } from '@/utils/FetchUtils'
import { useEffect, useState } from 'react'
import { getProductSpecificationAttributeMappingColumns } from './ProductSpecificationAttributeMappingColumns'
import { useNavigate, useParams } from 'react-router-dom'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import { Form, message, Modal } from 'antd'
import { ProductVideoMappingRequest, ProductVideoMappingResponse } from '@/model/ProductVideoMapping'
import ProductVideoMappingConfigs from './ProductVideoMappingConfigs'
import useCreateApi from '@/hooks/use-create-api'
import { getProductVideoMappingColumns } from './ProductVideoMappingColumns'
import useUpdateApi from '@/hooks/use-update-api'

interface Search extends RequestParams {
    productId: number
}

function useProductUpdateViewModel() {
    const { productId } = useParams<{ productId: string; idVideo: string }>()
    const [filter, setFilter] = useState<Search>({ productId: Number(productId) })
    const navigate = useNavigate()
    const [data, setData] = useState<ProductVideoMappingResponse[]>([])
    const [form] = Form.useForm()
    const [isEditModalVisible, setEditModalVisible] = useState(false)
    const [editRecord, setEditRecord] = useState<ProductVideoMappingResponse | null>(null)

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

    const {
        data: listMultimediaResponse,
        isLoading: isLoadingMultimedia,
        refetch: refetchMultimedia,
    } = useGetAllApi<ProductVideoMappingResponse>(
        ProductVideoMappingConfigs.resourceUrl,
        ProductVideoMappingConfigs.resourceKey,
        filter,
    )

    const { mutate: createProductVideoMappingApi } = useCreateApi<ProductVideoMappingRequest, string>(
        ProductVideoMappingConfigs.resourceUrl,
    )

    const addProductVideoMapping = (values: Omit<ProductVideoMappingRequest, 'productId'>) => {
        createProductVideoMappingApi(
            { ...values, productId: Number(productId) },
            {
                onSuccess: () => {
                    message.success('Product video added successfully')
                    refetchMultimedia()
                    form.resetFields()
                },
                onError: () => {
                    message.error('Failed to add product video')
                },
            },
        )
    }

    const deleteApi = useDeleteByIdApi(
        ProductSpecificationAttributeMappingConfigs.resourceUrl,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
    )

    const deleteProductVideo = useDeleteByIdApi(
        ProductVideoMappingConfigs.resourceUrl,
        ProductVideoMappingConfigs.resourceKey,
    )

    const { mutate: updateProductVideo } = useUpdateApi<ProductVideoMappingRequest, string>(
        ProductVideoMappingConfigs.resourceUrl,
        ProductVideoMappingConfigs.resourceKey,
        Number(editRecord?.id || '0'),
    )

    const handleEdit = (record: ProductSpecificationAttributeMappingByProductResponse) => {
        navigate(`/admin/products/product-spec-attribute-mapping/edit/${filter.productId}/${record.id}`)
    }

    useEffect(() => {
        if (listMultimediaResponse?.items) {
            setData(listMultimediaResponse.items)
        }
    }, [listMultimediaResponse])

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

    const onEditProductVideo = (record: ProductVideoMappingResponse) => {
        setEditRecord(record)
        console.log(record)
        setEditModalVisible(true)
    }
    useEffect(() => {
        if (editRecord) {
            form.setFieldsValue({
                videoUrl: editRecord.videoUrl,
                displayOrder: editRecord.displayOrder,
            })
        }
    }, [editRecord, form])

    const onDeleteProductVideo = (id: number) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteProductVideo.mutate(id, {
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

    const columnsProductVideo = getProductVideoMappingColumns(onEditProductVideo, onDeleteProductVideo)

    const handleCancelEdit = () => {
        setEditModalVisible(false)
    }

    const handleUpdateProductVideo = (values: ProductVideoMappingRequest) => {
        if (!editRecord || !editRecord.id) {
            message.error('No record selected for update.')
            return
        }

        const updatedValues = {
            ...values,
            productId: Number(productId),
            id: editRecord.id,
        }
        updateProductVideo(updatedValues, {
            onSuccess: () => {
                message.success('Product video updated successfully')
                refetchMultimedia()
                setEditModalVisible(false)
            },
            onError: () => {
                message.error('Failed to update product video')
            },
        })
    }

    return {
        listResponse,
        isLoading,
        refetch,
        setFilter,
        filter,
        handleTableChange,
        columns,
        addProductVideoMapping,
        data,
        columnsProductVideo,
        form,
        isEditModalVisible,
        editRecord,
        handleCancelEdit,
        listMultimediaResponse,
        isLoadingMultimedia,
        handleUpdateProductVideo,
        refetchMultimedia,
    }
}

export default useProductUpdateViewModel
