import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Form, message, Modal, UploadProps } from 'antd'
import useGetAllApi from '@/hooks/use-get-all-api'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import ProductVideoMappingConfigs from '../video/ProductVideoMappingConfigs'
import { ProductVideoMappingRequest, ProductVideoMappingResponse } from '@/model/ProductVideoMapping'
import { getProductVideoMappingColumns } from '../video/ProductVideoMappingColumns'
import { RequestParams } from '@/utils/FetchUtils'
import { RcFile } from 'antd/lib/upload/interface'
import { RuleObject } from 'antd/es/form'
import { getProductPictureMappingColumns } from '../picture/ProductPictureMappingColumns'
import { ProductPictureMappingRequest, ProductPictureMappingResponse } from '@/model/ProductPictureMapping'
import ProductPictureMappingConfigs from '../picture/ProductPictureMappingConfigs'

interface Search extends RequestParams {
    productId: number
}
function useMediaProdMapManage() {
    const { productId } = useParams<{ productId: string }>()
    const [filter, setFilter] = useState<Search>({ productId: Number(productId) })
    const [data, setData] = useState<ProductVideoMappingResponse[]>([])
    const [form] = Form.useForm()
    const [editForm] = Form.useForm()
    const [isEditModalVisible, setEditModalVisible] = useState(false)
    const [editRecord, setEditRecord] = useState<ProductVideoMappingResponse | null>(null)
    const [isUploadMode, setIsUploadMode] = useState(false)
    const [uploading, setUploading] = useState(false)
    const [loading, setLoading] = useState(false)
    const [fileList, setFileList] = useState<RcFile[]>([])
    const [isEditPicture, setIsEditPicture] = useState(false)
    const propsUpload: UploadProps = {
        name: 'file',
        beforeUpload: (file: RcFile) => {
            setFileList([file])
            return false
        },
        onRemove: (file) => {
            setFileList((prevFileList) => prevFileList.filter((item) => item.uid !== file.uid))
        },
        fileList,
    }

    const {
        data: listMultimediaResponse,
        isLoading: isLoadingMultimedia,
        refetch: refetchMultimedia,
    } = useGetAllApi<ProductVideoMappingResponse>(
        ProductVideoMappingConfigs.resourceUrl,
        ProductVideoMappingConfigs.resourceKey,
        filter,
    )
    const {
        data: listPictureResponse,
        isLoading: isLoadingPicture,
        refetch: refetchPicture,
    } = useGetAllApi<ProductPictureMappingResponse>(
        ProductPictureMappingConfigs.resourceUrl,
        ProductPictureMappingConfigs.resourceKey,
        filter,
    )
    const isCloudinaryUrl = (url: string) => {
        const cloudinaryPattern = /https?:\/\/res\.cloudinary\.com\//
        return cloudinaryPattern.test(url)
    }
    const validateURL = (_: RuleObject, value: string) => {
        const urlPattern = new RegExp(
            '^(https?:\\/\\/)' +
                '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.?)+[a-z]{2,}|' +
                '((\\d{1,3}\\.){3}\\d{1,3}))' +
                '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' +
                '(\\?[;&a-z\\d%_.~+=-]*)?' +
                '(\\#[-a-z\\d_]*)?$',
            'i',
        )
        return !value || urlPattern.test(value)
            ? Promise.resolve()
            : Promise.reject(new Error('Please enter a valid URL!'))
    }

    const deleteProductVideo = useDeleteByIdApi(
        ProductVideoMappingConfigs.resourceUrl,
        ProductVideoMappingConfigs.resourceKey,
    )
    const deleteProductPicture = useDeleteByIdApi(
        ProductPictureMappingConfigs.resourceUrl,
        ProductPictureMappingConfigs.resourceKey,
    )

    const handleSwitchChange = (checked: boolean) => {
        setIsUploadMode(checked)
        form.resetFields()
    }

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }

    const handleAddUrlVideo = (values: ProductVideoMappingRequest) => {
        if (!productId) {
            return
        }
        const formData = new FormData()
        formData.append('isUpload', 'false')
        formData.append('productId', productId || '')
        formData.append('displayOrder', values.displayOrder?.toString() || '0')
        formData.append('videoUrl', values.videoUrl || '')
        fetch(ProductVideoMappingConfigs.resourceUrl, {
            method: 'POST',
            body: formData,
        })
            .then((response) => response.json())
            .then(() => {
                message.success('Product video added successfully')
                refetchMultimedia()
                form.resetFields()
            })
            .catch(() => {
                message.error('Failed to add product video')
            })
    }

    const handleAddUploadVideo = (values: ProductVideoMappingRequest) => {
        if (fileList.length === 0) {
            message.error('Please upload a video file!')
            return
        }
        setUploading(true)
        setLoading(true)
        const formData = new FormData()
        formData.append('isUpload', 'true')
        formData.append('productId', productId || '')
        formData.append('displayOrder', values.displayOrder?.toString() || '0')
        formData.append('videoFile', fileList[0])
        fetch(ProductVideoMappingConfigs.resourceUrl, {
            method: 'POST',
            body: formData,
        })
            .then((response) => response.json())
            .then(() => {
                message.success('Product video added successfully')
                setFileList([])
                refetchMultimedia()
                form.resetFields()
            })
            .catch(() => {
                message.error('Failed to add product video')
            })
            .finally(() => {
                setUploading(false)
                setLoading(false)
            })
    }
    useEffect(() => {
        if (isUploadMode && editRecord) {
            const fileName = editRecord.videoUrl?.split('/').pop() || 'product_video.mp4'
            const fileList = [
                {
                    uid: '-1',
                    name: fileName,
                    url: editRecord.videoUrl,
                    status: 'done',
                },
                // eslint-disable-next-line @typescript-eslint/no-explicit-any
            ] as any
            setFileList(fileList)
        } else {
            setFileList([])
        }
    }, [isUploadMode, editRecord])

    const addProductVideoMapping = (values: Omit<ProductVideoMappingRequest, 'productId'>) => {
        if (values.inputMethod === 'url') {
            handleAddUrlVideo(values as ProductVideoMappingRequest)
        } else if (values.inputMethod === 'upload') {
            handleAddUploadVideo(values as ProductVideoMappingRequest)
        } else {
            message.error('Invalid input method selected.')
        }
    }

    const handleUpdateProductVideo = (values: ProductVideoMappingRequest) => {
        if (isUploadMode && fileList.length === 0) {
            message.error('Please upload a video file!')
            return
        }
        const updatedValues = new FormData()

        updatedValues.append('productId', productId || '')
        updatedValues.append('displayOrder', values.displayOrder?.toString() || '0')

        if (isUploadMode) {
            updatedValues.append('isUpload', 'true')
            if (isUploadMode && fileList.length > 0) {
                updatedValues.append('videoFile', fileList[0])
            }
        } else {
            updatedValues.append('isUpload', 'false')
            updatedValues.append('videoUrl', values.videoUrl || '')
        }
        setLoading(true)
        fetch(`${ProductVideoMappingConfigs.resourceUrl}/${editRecord?.id}`, {
            method: 'PUT',
            body: updatedValues,
        })
            .then((response) => response.json())
            .then(() => {
                message.success('Product video updated successfully')
                refetchMultimedia()
                setEditModalVisible(false)
            })
            .catch(() => {
                message.error('Failed to update product video')
            })
            .finally(() => {
                setLoading(false)
            })
    }

    const onEditProductVideo = (record: ProductVideoMappingResponse) => {
        console.log(record)
        const isCloudinary = isCloudinaryUrl(record.videoUrl ?? '')

        setIsUploadMode(isCloudinary)

        editForm.resetFields()

        if (isCloudinary) {
            editForm.setFieldsValue({
                videoFile: record.videoUrl,
                displayOrder: record.displayOrder,
            })
        } else {
            editForm.setFieldsValue({
                videoUrl: record.videoUrl,
                displayOrder: record.displayOrder,
            })
        }
        setEditRecord(record)
        setEditModalVisible(true)
    }

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
                        if (listMultimediaResponse?.items.length === 1 && filter.pageNo && filter.pageNo > 1) {
                            setFilter((prev) => ({
                                ...prev,
                                pageNo: (prev.pageNo ?? 1) - 1,
                            }))
                        } else {
                            refetchMultimedia()
                        }
                        message.success('Deleted successfully')
                    },
                    onError: () => {
                        message.error('Delete failed!')
                    },
                })
            },
        })
    }

    const handleCancelEdit = () => {
        form.resetFields()
        setEditModalVisible(false)
    }

    useEffect(() => {
        if (listMultimediaResponse?.items) {
            setData(listMultimediaResponse.items)
        }
    }, [listMultimediaResponse])

    useEffect(() => {
        if (productId) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                productId: Number(productId),
            }))
        }
    }, [productId])

    useEffect(() => {
        if (editRecord) {
            editForm.setFieldsValue({
                videoUrl: editRecord.videoUrl,
                displayOrder: editRecord.displayOrder,
            })
        }
    }, [editRecord, editForm])
    const onDeleteProductPicture = (id: number) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteProductPicture.mutate(id, {
                    onSuccess: () => {
                        if (listPictureResponse?.items.length === 1 && filter.pageNo && filter.pageNo > 1) {
                            setFilter((prev) => ({
                                ...prev,
                                pageNo: (prev.pageNo ?? 1) - 1,
                            }))
                        } else {
                            refetchPicture()
                        }
                        message.success('Deleted successfully')
                    },
                    onError: () => {
                        message.error('Delete failed!')
                    },
                })
            },
        })
    }
    const handleAddUploadPicture = (values: ProductPictureMappingRequest) => {
        if (fileList.length === 0) {
            message.error('Please upload an image file!')
            return
        }

        const formData = new FormData()
        formData.append('productId', productId || '')
        formData.append('displayOrder', values.displayOrder?.toString() || '0')
        fileList.forEach((file) => {
            formData.append('files', file)
        })

        fetch(ProductPictureMappingConfigs.resourceUrl, {
            method: 'POST',
            body: formData,
        })
            .then((response) => response.json())
            .then(() => {
                message.success('Product pictures added successfully')
                setFileList([])
                refetchPicture()
                form.resetFields()
            })
            .catch(() => {
                message.error('Failed to add product pictures')
            })
    }

    const propsUploadPicture: UploadProps = {
        name: 'files',
        multiple: true,
        beforeUpload: (file: RcFile) => {
            setFileList((prevFileList) => [...prevFileList, file])
            return false
        },
        onRemove: (file) => {
            setFileList((prevFileList) => prevFileList.filter((item) => item.uid !== file.uid))
        },
        fileList,
    }

    const onEditProductPicture = (record: ProductPictureMappingResponse) => {
        setEditRecord(record)
        form.setFieldsValue({ displayOrder: record.displayOrder })
        setIsEditPicture(true)
    }
    const handleUpdateProductPicture = (values: ProductPictureMappingRequest) => {
        if (!editRecord) return

        const updatedValues = new FormData()
        updatedValues.append('displayOrder', values.displayOrder?.toString() || '0')

        setLoading(true)

        fetch(`${ProductPictureMappingConfigs.resourceUrl}/${editRecord.id}`, {
            method: 'PUT',
            body: updatedValues,
        })
            .then((response) => response.json())
            .then(() => {
                refetchPicture()
                setIsEditPicture(false)
            })
            .finally(() => {
                setLoading(false)
            })
    }

    const columnsProductVideo = getProductVideoMappingColumns(onEditProductVideo, onDeleteProductVideo)
    const columnsProductPicture = getProductPictureMappingColumns(onEditProductPicture, onDeleteProductPicture)

    return {
        listPictureResponse,
        refetchPicture,
        isLoadingPicture,
        propsUploadPicture,
        handleAddUploadPicture,
        filter,
        handleTableChange,
        columnsProductPicture,
        addProductVideoMapping,
        handleUpdateProductPicture,
        data,
        columnsProductVideo,
        form,
        editForm,
        isEditModalVisible,
        editRecord,
        uploading,
        fileList,
        setIsEditPicture,
        isEditPicture,
        handleCancelEdit,
        listMultimediaResponse,
        isLoadingMultimedia,
        handleUpdateProductVideo,
        refetchMultimedia,
        handleAddUrlVideo,
        isUploadMode,
        handleSwitchChange,
        handleAddUploadVideo,
        propsUpload,
        loading,
        setLoading,
        validateURL,
    }
}

export default useMediaProdMapManage
