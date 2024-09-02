import { useState, useEffect } from 'react'
import useCreateApi from '@/hooks/use-create-api'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import ProductSpecificationAttributeMappingConfigs from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingConfigs'
import { SpecificationAttributeOptionResponse } from '@/model/SpecificationAttributeOption.ts'
import { ProductSpecificationAttributeMappingRequest } from '@/model/ProductSpecificationAttributeMapping.ts'
import { FormInstance } from 'antd'
import { NavigateFunction } from 'react-router-dom'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'
import useGetApi from '@/hooks/use-get-api'

const useProductSpecificationAttributeMappingCreateViewModel = (
    form: FormInstance,
    productId: string | null,
    navigate: NavigateFunction,
) => {
    const [attributeType, setAttributeType] = useState('Option')
    const [selectedAttributeId, setSelectedAttributeId] = useState<number | null>(null)
    const [attributeOptions, setAttributeOptions] = useState<SpecificationAttributeOptionResponse[]>([])
    const [attributes, setAttributes] = useState<SpecificationAttributeResponse[]>([])
    const [groupedAttributes, setGroupedAttributes] = useState<Map<string, SpecificationAttributeResponse[]>>()
    const {
        data: listAttribute,
        isLoading,
        refetch: refetchAttributes,
    } = useGetApi(`${SpecificationAttributeConfigs.resourceUrl}/list-name`, SpecificationAttributeConfigs.resourceKey)

    useEffect(() => {
        if (listAttribute) {
            const attrs = listAttribute as SpecificationAttributeResponse[]
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
        form.validateFields().then((values) => {
            const value =
                attributeType === 'CustomText'
                    ? JSON.stringify({
                          custom_value: values.customText,
                          spec_attribute_id: selectedAttributeId,
                      })
                    : ''
            const payload: ProductSpecificationAttributeMappingRequest = {
                productId: Number(productId),
                specificationAttributeOptionId: values.attributeOption || null,
                customValue: value,
                showOnProductPage: values.showOnProductPage,
                displayOrder: Number(values.displayOrder),
            }

            createMutation.mutate(payload, {
                onSuccess: () => {
                    navigate(`/admin/products/${productId}`)
                },
            })
        })
    }

    return {
        attributeType,
        setAttributeType,
        selectedAttributeId,
        setSelectedAttributeId,
        attributeOptions,
        setAttributeOptions,
        attributes,
        setAttributes,
        groupedAttributes,
        listAttribute,
        isLoading,
        refetchAttributes,
        handleAttributeChange,
        handleSave,
    }
}

export default useProductSpecificationAttributeMappingCreateViewModel
