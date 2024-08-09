import { useState, useEffect } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api'
import useCreateApi from '@/hooks/use-create-api'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpeficationAttributeMapping/ProductSpecificationAttributeMappingConfigs.ts'
import { SpecificationAttributeOptionResponse } from '@/model/SpecificationAttributeOption.ts'
import { ProductSpecificationAttributeMappingRequest } from '@/model/ProductSpecificationAttributeMapping.ts'
import { FormInstance, message } from 'antd'
import { NavigateFunction } from 'react-router-dom'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'

const useProductSpecificationAttributeMappingCreateViewModel = (
    form: FormInstance,
    productId: string | undefined,
    navigate: NavigateFunction,
) => {
    const [attributeType, setAttributeType] = useState('Option')
    const [customHtml, setCustomHtml] = useState('')
    const [selectedAttributeId, setSelectedAttributeId] = useState<number | null>(null)
    const [attributeOptions, setAttributeOptions] = useState<SpecificationAttributeOptionResponse[]>([])
    const [attributes, setAttributes] = useState<SpecificationAttributeResponse[]>([])
    const [groupedAttributes, setGroupedAttributes] = useState<Map<string, SpecificationAttributeResponse[]>>()
    const [isSpinning, setIsSpinning] = useState(false)
    const {
        data: listAttribute,
        isLoading,
        error,
        refetch: refetchAttributes,
    } = useGetAllApi(SpecificationAttributeConfigs.resourceUrl, SpecificationAttributeConfigs.resourceKey)

    useEffect(() => {
        if (listAttribute?.items) {
            const attrs = listAttribute.items as SpecificationAttributeResponse[]
            const filteredAttrs = attrs.filter((attr) => (attr.listOptions || []).length > 0)
            setAttributes(filteredAttrs)

            const groups = new Map<string, SpecificationAttributeResponse[]>()
            filteredAttrs.forEach((attr) => {
                const groupName = attr.specificationAttributeGroupName || 'Ungrouped'
                if (!groups.has(groupName)) {
                    groups.set(groupName, [])
                }
                groups.get(groupName)!.push(attr)
            })
            setGroupedAttributes(groups)

            if (filteredAttrs.length > 0) {
                const defaultAttributeId = filteredAttrs[0].id
                setSelectedAttributeId(defaultAttributeId)
                setAttributeOptions(filteredAttrs[0].listOptions || [])
                form.setFieldsValue({
                    attribute: defaultAttributeId,
                    attributeOption: filteredAttrs[0].listOptions?.[0]?.id,
                })
            }
        }
    }, [listAttribute, form])

    const handleAttributeChange = (value: number) => {
        setSelectedAttributeId(value)
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

    const createMutation = useCreateApi(ProductSpecificationAttributeMappingConfigs.resourceUrl)

    const handleSave = () => {
        form.validateFields()
            .then((values) => {
                const customValue =
                    attributeType === 'Option'
                        ? attributeOptions.find((option) => option.id === values.attributeOption)?.name || ''
                        : attributeType === 'CustomText'
                          ? values.customText
                          : attributeType === 'CustomHtml'
                            ? customHtml
                            : attributeType === 'HyperLink'
                              ? values.hyperlink
                              : ''

                const payload: ProductSpecificationAttributeMappingRequest = {
                    productId: Number(productId),
                    specificationAttributeOptionId: values.attributeOption || null,
                    customValue: customValue,
                    showOnProductPage: values.showOnProductPage,
                    displayOrder: Number(values.displayOrder),
                    specificationAttributeId: selectedAttributeId,
                    attributeType: attributeType,
                }

                createMutation.mutate(payload, {
                    onSuccess: () => {
                        message.success('Product specification created successfully')
                        navigate(`/admin/products/product-spec-attribute-mapping/productId/${productId}`)
                    },
                    onError: (error) => {
                        message.error(`Error creating mapping: ${error.message}`)
                    },
                })
            })
            .catch((info) => {
                console.log('Validate Failed:', info)
            })
    }

    const handleSaveAndContinue = () => {
        form.validateFields()
            .then((values) => {
                const customValue =
                    attributeType === 'Option'
                        ? attributeOptions.find((option) => option.id === values.attributeOption)?.name || ''
                        : attributeType === 'CustomText'
                          ? values.customText
                          : attributeType === 'CustomHtml'
                            ? customHtml
                            : attributeType === 'HyperLink'
                              ? values.hyperlink
                              : ''

                const payload: ProductSpecificationAttributeMappingRequest = {
                    productId: Number(productId),
                    specificationAttributeOptionId: values.attributeOption || null,
                    customValue: customValue,
                    showOnProductPage: values.showOnProductPage,
                    displayOrder: Number(values.displayOrder),
                    specificationAttributeId: selectedAttributeId ?? undefined,
                    attributeType: attributeType,
                }

                createMutation.mutate(payload, {
                    onSuccess: () => {
                        message.success('Product specification created successfully')
                    },
                    onError: (error) => {
                        console.error('Error creating mapping:', error.message)
                    },
                })
            })
            .catch((info: string) => {
                console.log('Validate Failed:', info)
            })
    }
    const handleReload = async () => {

        setIsSpinning(true)
        try {
            await new Promise((resolve) => setTimeout(resolve, 2000))
            await refetchAttributes()
        } catch (error) {
            console.error('Failed to reload data:', error)
        } finally {
            setIsSpinning(false)
        }
    }
    return {
        attributeType,
        setAttributeType,
        customHtml,
        setCustomHtml,
        selectedAttributeId,
        setSelectedAttributeId,
        attributeOptions,
        setAttributeOptions,
        attributes,
        setAttributes,
        groupedAttributes,
        listAttribute,
        isLoading,
        error,
        refetchAttributes,
        handleReload,
        handleAttributeChange,
        handleSave,
        handleSaveAndContinue,
        isSpinning,
    }
}

export default useProductSpecificationAttributeMappingCreateViewModel
