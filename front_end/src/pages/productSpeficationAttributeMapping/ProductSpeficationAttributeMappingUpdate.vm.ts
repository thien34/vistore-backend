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

    // Fetch existing mapping
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

    useEffect(() => {}, [attributes])

    useEffect(() => {}, [attributeOptions])

    useEffect(() => {
        if (existingMapping) {
            const {
                specificationAttributeId,
                specificationAttributeOptionId,
                customValueJson,
                customValue,
                showOnProductPage,
                displayOrder,
            } = existingMapping

            const type = specificationAttributeOptionId === null ? 'CustomText' : 'Option'
            setAttributeType(type)

            let customText = ''
            let specificationAttributeName = ''

            if (type === 'CustomText') {
                if (customValueJson) {
                    try {
                        // Try to parse customValueJson as JSON
                        const parsedValue = JSON.parse(customValueJson)
                        customText = parsedValue.custom_value || ''
                        specificationAttributeName = parsedValue.spec_attribute_id
                            ? existingMapping.specificationAttributeName || ''
                            : ''
                    } catch (error) {
                        console.error('Error parsing customValueJson:', error)
                        // If parsing fails, assume customValueJson is plain text
                        customText = customValue || ''
                    }
                } else {
                    // Use plain text if customValueJson is not provided
                    customText = customValue || ''
                }
            }

            form.setFieldsValue({
                attribute: specificationAttributeId || undefined,
                attributeOption: specificationAttributeOptionId || undefined,
                customText,
                specificationAttributeName,
                showOnProductPage,
                displayOrder,
                attributeType: type,
            })

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
            form.setFieldsValue({ attributeOption: undefined, customText: '', specificationAttributeName: '' })
        } else if (value === 'Option') {
            form.setFieldsValue({ customText: '', specificationAttributeName: '' })
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
                        message.success('Deleted successfully')
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
                const selectedAttributeId = existingMapping?.specificationAttributeId || values.attribute
                if (!selectedAttributeId) {
                    throw new Error('Selected attribute is missing')
                }

                const customValue =
                    attributeType === 'Option'
                        ? JSON.stringify({
                              customValue:
                                  attributeOptions.find((option) => option.id === values.attributeOption)?.name || '',
                              spec_attribute_id: selectedAttributeId,
                          })
                        : attributeType === 'CustomText'
                          ? JSON.stringify({
                                custom_value: values.customText || '',
                                spec_attribute_id: selectedAttributeId,
                            })
                          : ''

                const payload: ProductSpecificationAttributeMappingRequest = {
                    productId: Number(productId),
                    specificationAttributeOptionId: values.attributeOption || null,
                    customValue: customValue,
                    showOnProductPage: values.showOnProductPage,
                    displayOrder: Number(values.displayOrder),
                    specificationAttributeId: selectedAttributeId,
                }

                // Debug payload to check values
                console.log('Payload for saving:', payload)

                // Ensure important fields are not empty
                if (!payload.specificationAttributeId) {
                    throw new Error('Attribute ID is required')
                }
                if (!payload.productId) {
                    throw new Error('Product ID is required')
                }

                // Send request to API
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
            .catch((info) => {
                console.log('Validate Failed:', info)
            })
    }

    const handleReload = async () => {
        console.log('Reloading attributes...')
        setIsSpinning(true)
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
