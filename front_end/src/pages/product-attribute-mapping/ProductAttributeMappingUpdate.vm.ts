import useGetByIdApi from '@/hooks/use-get-by-id-api'
import {
    ProductAttributeValueRequest,
    ProductProductAttributeMappingDetailResponse,
    ProductProductAttributeMappingRequest,
} from '@/model/ProductProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import { useNavigate, useParams } from 'react-router-dom'
import { useForm } from 'antd/es/form/Form'
import useUpdateApi from '@/hooks/use-update-api'
import useCreateApi from '@/hooks/use-create-api'
import { useState } from 'react'

enum InputType {
    DROPDOWN = 'dropdown',
    RADIO_BUTTON = 'radio_button',
    COLOR_SQUARES = 'color_squares',
}

function getNumberFromEnum(value: string): number {
    switch (value) {
        case InputType.DROPDOWN:
            return 0
        case InputType.RADIO_BUTTON:
            return 1
        case InputType.COLOR_SQUARES:
            return 2
        default:
            throw new Error('Invalid enum value')
    }
}

function useProductAttributeMappingUpdateViewModel() {
    const layout = {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 },
    }
    const { id } = useParams<{ id: string }>()
    const [form] = useForm<ProductProductAttributeMappingRequest>()
    const navigation = useNavigate()
    const [idProdAttrValue, setIdProdAttrValue] = useState<number>(0)

    const { data: prodAttrMapResponse } = useGetByIdApi<ProductProductAttributeMappingDetailResponse>(
        ProductAttributeMappingConfigs.resourceUrl,
        ProductAttributeMappingConfigs.resourceKey,
        Number(id),
    )

    const { mutate: createProdAttrValueApi } = useCreateApi<ProductAttributeValueRequest>(
        ProductAttributeMappingConfigs.resourceUrlValue,
    )

    const [productAttributeValues, setProductAttributeValues] = useState<ProductAttributeValueRequest[]>(
        prodAttrMapResponse?.productAttributeValueResponses.map((response) => ({
            ...response,
            productAttributeValuePictureRequests: [],
        })) || [],
    )

    const { mutate: updateProductAttributeMappingApi } = useUpdateApi<ProductProductAttributeMappingRequest>(
        ProductAttributeMappingConfigs.resourceUrl,
        ProductAttributeMappingConfigs.resourceKey,
        Number(id),
    )

    const { mutate: updateProdAttrValueApi } = useUpdateApi<ProductAttributeValueRequest>(
        ProductAttributeMappingConfigs.resourceUrlValue,
        ProductAttributeMappingConfigs.resourceUrlValue,
        idProdAttrValue,
    )

    // HANDLE ADD
    const handleUpdate = () => {
        form.submit()
    }

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: ProductProductAttributeMappingRequest) => {
        const requestData: ProductProductAttributeMappingRequest = {
            ...values,
            productAttributeValueRequests: productAttributeValues,
            productId: prodAttrMapResponse?.productId || 0,
            id: Number(id),
        }
        updateProductAttributeMappingApi(requestData, { onSuccess: () => navigation(-1) })
    }

    const handleAddValue = (newValue: ProductAttributeValueRequest) => {
        newValue.productAttributeMappingId = Number(id)
        createProdAttrValueApi(newValue, {
            onSuccess: () => {
                setProductAttributeValues((prevValues) => [...prevValues, newValue])
            },
        })
    }

    const handleUpdateValue = (updatedValue: ProductAttributeValueRequest) => {
        setIdProdAttrValue(updatedValue.id || 0)
        updatedValue.productAttributeMappingId = Number(id)
        updateProdAttrValueApi(updatedValue, {
            onSuccess: () => {
                setProductAttributeValues((prevValues) =>
                    prevValues.map((value) => (value.id === updatedValue.id ? updatedValue : value)),
                )
            },
        })
    }

    return {
        prodAttrMapResponse,
        getNumberFromEnum,
        productAttributeValues,
        setProductAttributeValues,
        form,
        layout,
        onFinish,
        handleUpdate,
        handleAddValue,
        handleUpdateValue,
    }
}
// PRODUCT_ATTRIBUTE_MAPPING_UPDATE
export default useProductAttributeMappingUpdateViewModel
