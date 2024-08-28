import useCreateApi from '@/hooks/use-create-api'
import {
    ProductAttributeValueRequest,
    ProductProductAttributeMappingRequest,
} from '@/model/ProductProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { getProductAttributeValueColumns } from './ProductAttributeMappingColumns'
import { ProductAttributeNameResponse } from '@/model/ProductAttribute'
import ProductAttributeConfigs from '../productAttribute/ProductAttributeConfigs'
import useGetApi from '@/hooks/use-get-api'
import { useForm } from 'antd/es/form/Form'
import { useState } from 'react'

function useProductAttributeMappingCreateViewModel() {
    const layout = {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 },
    }

    const [form] = useForm<ProductProductAttributeMappingRequest>()
    const [dataSource, setDataSource] = useState<ProductAttributeValueRequest[]>([])
    const [open, setOpen] = useState(false)
    const [isAddMode, setIsAddMode] = useState(true)
    const navigation = useNavigate()
    const { mutate: createProductAttributeMappingApi } = useCreateApi<ProductProductAttributeMappingRequest>(
        ProductAttributeMappingConfigs.resourceUrl,
    )
    const productAttributeName = useGetApi<ProductAttributeNameResponse[]>(
        `${ProductAttributeConfigs.resourceUrl}/list-name`,
        ProductAttributeConfigs.resourceKey,
        {},
    ).data

    const params = useSearchParams()
    const productId = new URLSearchParams(params[0]).get('product-id')

    const [editingRecord, setEditingRecord] = useState<ProductAttributeValueRequest | null>(null)

    // HANDLE DELETE VALUE ATTRIBUTE
    const handleDeleteValue = (name: string) => {
        setDataSource((prev) => prev.filter((item) => item.name !== name))
    }

    // HANDLE UPDATE VALUE ATTRIBUTE
    const handleOpenModalUpdateValue = (updatedRecord: ProductAttributeValueRequest | null) => {
        if (updatedRecord) {
            setEditingRecord(updatedRecord)
            setIsAddMode(false)
            setOpen(true)
        }
    }

    // HANDLE OPEN MODAL
    const handleOpenModal = () => {
        setIsAddMode(true)
        setOpen(true)
    }

    // INITIAL VALUE
    const initialValue: ProductProductAttributeMappingRequest = {
        productId: Number(productId ?? 0),
        productAttributeId: 0,
        textPrompt: '',
        isRequired: false,
        attributeControlTypeId: 0,
        displayOrder: 0,
        productAttributeValueRequests: dataSource,
    }

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: ProductProductAttributeMappingRequest) => {
        dataSource.forEach((item) => {
            item.id = null
        })
        const requestData: ProductProductAttributeMappingRequest = {
            ...values,
            productAttributeValueRequests: dataSource,
            productId: Number(productId ?? 0),
        }
        createProductAttributeMappingApi(requestData, { onSuccess: () => navigation(-1) })
    }

    // HANDLE ADD
    const handleAdd = () => {
        form.submit()
    }

    // GET COLUMNS
    const columns = getProductAttributeValueColumns(handleDeleteValue, handleOpenModalUpdateValue)

    // HANDLE ADD VALUE ATTRIBUTE
    const handleAddValue = (newValue: ProductAttributeValueRequest) => {
        newValue.id = dataSource.length + 1
        setDataSource([...dataSource, newValue])
    }

    // HANDLE UPDATE VALUE ATTRIBUTE
    const handleUpdateValue = (newValue: ProductAttributeValueRequest | null) => {
        if (newValue) {
            setDataSource((prev) => prev.map((item) => (item.id === newValue.id ? newValue : item)))
        }
    }

    return {
        layout,
        onFinish,
        columns,
        productAttributeName,
        initialValue,
        handleAdd,
        form,
        open,
        setOpen,
        handleOpenModal,
        dataSource,
        setDataSource,
        handleAddValue,
        handleDeleteValue,
        isAddMode,
        editingRecord,
        handleUpdateValue,
    }
}
export default useProductAttributeMappingCreateViewModel
