import useCreateApi from '@/hooks/use-create-api'
import {
    ProductAttributeValueRequest,
    ProductProductAttributeMappingRequest,
} from '@/model/ProductProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import { useNavigate } from 'react-router-dom'
import { getProductAttributeValueColumns } from './ProductAttributeMappingColumns'
import { ProductAttributeNameResponse } from '@/model/ProductAttribute'
import ProductAttributeConfigs from '@/pages/product-attributes/ProductAttributeConfigs'
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
    const { mutate: createProductAttributeMappingApi } = useCreateApi<ProductProductAttributeMappingRequest, string>(
        ProductAttributeMappingConfigs.resourceUrl,
    )
    const productAttributeName = useGetApi<ProductAttributeNameResponse[]>(
        `${ProductAttributeConfigs.resourceUrl}/list-name`,
        ProductAttributeConfigs.resourceKey,
        {},
    ).data

    const [editingRecord, setEditingRecord] = useState<ProductAttributeValueRequest | null>(null)

    // HANDLE DELETE VALUE ATTRIBUTE
    const handleDeleteValue = (name: string) => {
        setDataSource((prev) => prev.filter((item) => item.name !== name))
    }

    // HANDLE OPEN MODAL
    const handleOpenModal = (isAdd: boolean) => {
        setIsAddMode(isAdd)
        setOpen(true)
        form.resetFields()
    }

    // INITIAL VALUE
    const initialValue: ProductProductAttributeMappingRequest = {
        productId: 0,
        productAttributeId: 0,
        textPrompt: '',
        isRequired: false,
        attributeControlTypeId: 0,
        displayOrder: 0,
        productAttributeValueRequests: dataSource,
    }

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: ProductProductAttributeMappingRequest) => {
        const requestData: ProductProductAttributeMappingRequest = {
            ...values,
            productAttributeValueRequests: dataSource,
            productId: 1,
        }
        console.log(requestData)

        createProductAttributeMappingApi(requestData, { onSuccess: () => navigation(-1) })
    }

    // HANDLE ADD
    const handleAdd = () => {
        form.submit()
    }

    // GET COLUMNS
    const columns = getProductAttributeValueColumns(handleDeleteValue, () => handleOpenModal(false))

    // HANDLE ADD VALUE ATTRIBUTE
    const handleAddValue = (newValue: ProductAttributeValueRequest) => {
        setDataSource([...dataSource, newValue])
    }

    // HANDLE UPDATE VALUE ATTRIBUTE
    const handleUpdateValue = (updatedValue: ProductAttributeValueRequest | null) => {
        if (updatedValue) {
            setEditingRecord(updatedValue)
            setIsAddMode(false)
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
        handleUpdateValue,
        isAddMode,
        editingRecord,
    }
}
export default useProductAttributeMappingCreateViewModel
