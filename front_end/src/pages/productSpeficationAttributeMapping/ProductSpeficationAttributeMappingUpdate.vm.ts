import { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api.ts'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpeficationAttributeMapping/ProductSpecificationAttributeMappingConfigs.ts'
import {
    ProductSpecificationAttributeMappingRequest,
    ProductSpecificationAttributeMappingResponse,
} from '@/model/ProductSpecificationAttributeMapping.ts'
import { SpecificationAttributeOptionResponse } from '@/model/SpecificationAttributeOption.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'
import { message, Modal } from 'antd'
const useProductSpecificationAttributeMappingUpdateViewModel = (form) => {
    const [attributeType, setAttributeType] = useState('Option')
    const [isSpinning, setIsSpinning] = useState(false)
    const [attributeOptions, setAttributeOptions] = useState<SpecificationAttributeOptionResponse[]>([])
    const [attributes, setAttributes] = useState<SpecificationAttributeResponse[]>([])
    const { productId, id } = useParams<{ productId: string; id: string }>()
    const navigate = useNavigate()

    // Fetch attributes
    const {
        data: listAttribute,
        isLoading: isLoadingAttributes,
        error: errorAttributes,
        refetch: refetchAttributes,
    } = useGetAllApi<SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    const mappingId = id ? parseInt(id, 10) : null
    const {
        data: existingMapping,
        isLoading: isLoadingMapping,
        error: errorMapping,
    } = useGetByIdApi<ProductSpecificationAttributeMappingResponse>(
        ProductSpecificationAttributeMappingConfigs.resourceUrl,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        mappingId || 0,
    )

    const updateMutation = useUpdateApi<
        ProductSpecificationAttributeMappingRequest,
        ProductSpecificationAttributeMappingRequest
    >(
        ProductSpecificationAttributeMappingConfigs.resourceUrl,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
        mappingId || 0,
    )

    const deleteApi = useDeleteByIdApi(
        ProductSpecificationAttributeMappingConfigs.resourceUrl,
        ProductSpecificationAttributeMappingConfigs.resourceKey,
    )

    useEffect(() => {
        if (listAttribute?.items) {
            const attrs = listAttribute.items
            const filteredAttrs = attrs.filter((attr) => attr.listOptions.length > 0) // Filter attributes with no options
            setAttributes(filteredAttrs)
            if (filteredAttrs.length > 0) {
                const defaultAttributeId = filteredAttrs[0].id
                setAttributeOptions(filteredAttrs[0].listOptions || [])
                form.setFieldsValue({
                    attribute: defaultAttributeId,
                    attributeOption: filteredAttrs[0].listOptions?.[0]?.id,
                })
            }
        }
    }, [listAttribute, form])

    useEffect(() => {
        if (existingMapping) {
            const {
                specificationAttributeId,
                specificationAttributeOptionId,
                customValue,
                showOnProductPage,
                displayOrder,
                specificationAttributeInfo,
            } = existingMapping

            const parsedInfo = specificationAttributeInfo ? JSON.parse(specificationAttributeInfo) : null

            const type = specificationAttributeOptionId === null ? 'CustomText' : 'Option'
            setAttributeType(type)

            // Nếu specificationAttributeOptionId là null, sử dụng name từ specificationAttributeInfo
            let attributeName = specificationAttributeOptionId === null ? (parsedInfo ? parsedInfo.name : '') : ''
            if (specificationAttributeOptionId !== null) {
                const selectedOption = attributes.find((attr) =>
                    attr.listOptions.some((option) => option.id === specificationAttributeOptionId),
                )
                if (selectedOption) {
                    attributeName = selectedOption.listOptions.find(
                        (option) => option.id === specificationAttributeOptionId,
                    ).name
                }
            }

            // Đặt giá trị vào form
            form.setFieldsValue({
                attribute: specificationAttributeId || undefined,
                attributeOption: specificationAttributeOptionId || undefined,
                customText: type === 'CustomText' ? customValue : '',
                showOnProductPage,
                displayOrder,
                attributeType: type,
                attributeName, // Đặt attributeName
            })

            // Cập nhật danh sách tùy chọn thuộc tính nếu có
            const selectedAttribute = attributes.find((attr) => attr.id === specificationAttributeId)
            if (selectedAttribute) {
                setAttributeOptions(selectedAttribute.listOptions || [])
            }
        }
    }, [existingMapping, attributes, form])

    const handleAttributeChange = (value: number) => {
        const selectedAttribute = attributes.find((attr) => attr.id === value)
        if (selectedAttribute) {
            if (selectedAttribute.listOptions.length === 0) {
                setAttributeOptions([])
                form.setFieldsValue({ attributeOption: undefined })
            } else {
                setAttributeOptions(selectedAttribute.listOptions || [])
                form.setFieldsValue({ attributeOption: selectedAttribute.listOptions[0]?.id })
            }
        } else {
            setAttributeOptions([])
            form.setFieldsValue({ attributeOption: undefined })
        }
    }

    const handleAttributeTypeChange = (value: string) => {
        setAttributeType(value)
        if (value === 'CustomText') {
            form.setFieldsValue({ attributeOption: undefined })
        }
    }

    const handleDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteApi.mutate(parseInt(id, 10), {
                    onSuccess: () => {
                        navigate(`/admin/products/product-spec-attribute-mapping/productId/${productId}`)
                    },
                    onError: () => {
                        message.error('Delete failed!')
                    },
                })
            },
        })
    }

    const handleSave = (onSuccess: () => void) => {
        form.validateFields()
            .then((values) => {
                const customValue =
                    attributeType === 'Option'
                        ? attributeOptions.find((option) => option.id === values.attributeOption)?.name || ''
                        : attributeType === 'CustomText'
                          ? values.customText
                          : ''

                const payload: ProductSpecificationAttributeMappingRequest = {
                    productId: Number(productId),
                    specificationAttributeOptionId: values.attributeOption || null,
                    customValue: customValue,
                    showOnProductPage: values.showOnProductPage,
                    displayOrder: Number(values.displayOrder),
                    specificationAttributeId: values.attribute,
                }

                updateMutation.mutate(payload, {
                    onSuccess: () => {
                        message.success('Product specification attribute mapping updated successfully')
                        onSuccess()
                    },
                    onError: (error) => {
                        message.error(`Error updating mapping: ${error.message}`)
                    },
                })
            })
            .catch((info: string) => {
                console.log('Validate Failed:', info)
            })
    }

    const handleReload = async () => {
        setIsSpinning(true) // Show spinner
        await new Promise((resolve) => setTimeout(resolve, 2000))
        refetchAttributes()
        setIsSpinning(false)
    }

    return {
        attributeType,
        setAttributeType,
        attributeOptions,
        setAttributeOptions,
        attributes,
        setAttributes,
        listAttribute,
        isLoadingAttributes,
        errorAttributes,
        isLoadingMapping,
        errorMapping,
        handleAttributeChange,
        handleAttributeTypeChange,
        handleDelete,
        handleSave,
        refetchAttributes,
        handleReload,
        isSpinning,
    }
}

export default useProductSpecificationAttributeMappingUpdateViewModel
