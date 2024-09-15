import { Form, Modal } from 'antd'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import useCreateApi from '@/hooks/use-create-api.ts'
import { ProductAttributeRequest } from '@/model/ProductAttribute.ts'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import ProductAttributeConfigs from './ProductAttributeConfigs'
import { getProductAttributeValueColumns } from './ProductAttributeColumns'

function useProductAttributeCreate() {
    const { mutate: createProductAttribute } = useCreateApi<ProductAttributeRequest>(
        ProductAttributeConfigs.resourceUrl,
    )
    const navigate = useNavigate()
    const [form] = Form.useForm()
    const [formAdd] = Form.useForm()
    const [values, setValues] = useState<PredefinedProductAttributeValueRequest[]>([])
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [isEdit, setIsEdit] = useState(false)
    const [newValue, setNewValue] = useState<PredefinedProductAttributeValueRequest>({
        id: 1,
        name: '',
        priceAdjustment: 0,
        priceAdjustmentUsePercentage: false,
        weightAdjustment: 0,
        cost: 0,
        isPreSelected: false,
        displayOrder: 0,
        productAttribute: 0,
        setIsEditPriceAdjustment: 0,
    })
    const [current, setCurrent] = useState(1)

    const layout = {
        labelCol: { span: 5 },
        wrapperCol: { span: 13 },
    }

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id ?? 0))
        return arr.length === 0 ? 0 : maxId + 1
    }

    const handleAddValue = () => {
        if (isEdit) {
            const index = values.findIndex((item) => item.id === newValue.id)
            if (index > -1) {
                const updatedValues = [...values]
                updatedValues[index] = newValue
                setValues(updatedValues)
            }
        } else {
            const newId = getNewId(values)
            const newValueWithId = { ...newValue, id: newId }
            setValues([...values, newValueWithId])
        }
        formAdd.resetFields()
        setIsModalOpen(false)
        setIsEdit(false)
    }
    const handleCancelModal = () => {
        formAdd.resetFields()
        setIsModalOpen(false)
        setIsEdit(false)
    }

    const handleRemoveValue = (value: PredefinedProductAttributeValueRequest) => {
        Modal.confirm({
            title: 'Confirm Delete',
            content: 'Are you sure you want to delete this value?',
            okText: 'Yes',
            cancelText: 'No',
            onOk: () => {
                setValues(values.filter((v) => v.id !== value.id))
            },
        })
    }

    const handleEditValue = (value: PredefinedProductAttributeValueRequest) => {
        setIsModalOpen(true)
        formAdd.setFieldsValue(value)
        setNewValue(value)
        setIsEdit(true)
    }

    const handleFinish = async (formValues: { name: string; description: string }) => {
        form.resetFields()
        setValues([])
        const data: ProductAttributeRequest = {
            name: formValues.name,
            description: formValues.description || '',
            values: values,
        }
        createProductAttribute(data, {
            onSuccess: () => {
                navigate(-1)
            },
        })
    }

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const columns = getProductAttributeValueColumns(handleEditValue, handleRemoveValue)

    return {
        handlePageChange,
        handleFinish,
        handleAddValue,
        isModalOpen,
        handleCancelModal,
        setIsModalOpen,
        values,
        newValue,
        columns,
        current,
        form,
        formAdd,
        setNewValue,
        layout,
    }
}

export default useProductAttributeCreate
