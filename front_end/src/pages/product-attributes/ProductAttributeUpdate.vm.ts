import { useNavigate, useParams } from 'react-router-dom'
import { useState } from 'react'
import { Form } from 'antd'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import ProductAttributeConfigs from './ProductAttributeConfigs.ts'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import { ProductAttributeRequest, ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { getProductAttributeValueColumns } from './ProductAttributeColumns.tsx'

function useProductAttributeUpdate() {
    const navigate = useNavigate()
    const { id } = useParams<{ id: string }>()
    const [current, setCurrent] = useState(1)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const { mutate: updateProductAttribute } = useUpdateApi<ProductAttributeRequest>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )
    const [form] = Form.useForm()
    const [formAdd] = Form.useForm()
    const [values, setValues] = useState<PredefinedProductAttributeValueRequest[]>([])
    const [isEdit, setIsEdit] = useState(false)
    const [newValue, setNewValue] = useState<PredefinedProductAttributeValueRequest>({
        id: 1,
        productAttribute: 0,
        name: '',
        priceAdjustment: 0,
        priceAdjustmentUsePercentage: false,
        weightAdjustment: 0,
        cost: 0,
        isPreSelected: false,
        displayOrder: 0,
    })

    const layout = {
        labelCol: { span: 5 },
        wrapperCol: { span: 13 },
    }

    const { data: productAttributeResponse } = useGetByIdApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id ?? 0))
        return arr.length === 0 ? 0 : maxId + 1
    }

    const handleAddValue = () => {
        if (isEdit) {
            formAdd.resetFields()
            const index = values.findIndex((item) => item.id === newValue.id)
            const newArr = values.filter((item) => item.id !== newValue.id)
            newArr.splice(index, 0, {
                ...newValue,
                priceAdjustment: newValue.priceAdjustment || 0,
                weightAdjustment: newValue.weightAdjustment || 0,
                cost: newValue.cost || 0,
                displayOrder: newValue.displayOrder || 0,
            })
            setValues(newArr)
            setIsEdit(false)
        } else {
            formAdd.resetFields()
            setValues([
                ...values,
                {
                    ...newValue,
                    id: getNewId(values),
                    priceAdjustment: newValue.priceAdjustment || 0,
                    weightAdjustment: newValue.weightAdjustment || 0,
                    cost: newValue.cost || 0,
                    displayOrder: newValue.displayOrder || 0,
                },
            ])
        }
        setIsModalOpen(false)
    }
    const handleModalCancel = () => {
        formAdd.resetFields()
        setIsModalOpen(false)
    }

    const handleRemoveValue = (value: PredefinedProductAttributeValueRequest) => {
        setValues((prevValues) => prevValues.filter((v) => v.id !== value.id))
    }

    const handleEditValue = (value: PredefinedProductAttributeValueRequest) => {
        setIsEdit(true)
        formAdd.setFieldsValue(value)
        setNewValue(value)
        setIsModalOpen(true)
    }

    const handleFinish = async (formValues: { name: string; description: string }) => {
        try {
            const sanitizedValues = values.map((val) => ({
                ...val,
                priceAdjustment: val.priceAdjustment || 0,
                weightAdjustment: val.weightAdjustment || 0,
                cost: val.cost || 0,
                displayOrder: val.displayOrder || 0,
            }))

            const data = {
                name: formValues.name,
                description: formValues.description,
                values: sanitizedValues,
            }

            await updateProductAttribute({ ...data }, { onSuccess: () => navigate(-1) })
        } catch (error) {
            console.error('Failed to submit form')
        }
    }

    const columns = getProductAttributeValueColumns(handleEditValue, handleRemoveValue)

    return {
        productAttributeResponse,
        current,
        handlePageChange,
        form,
        formAdd,
        values,
        setValues,
        newValue,
        setNewValue,
        isModalOpen,
        setIsModalOpen,
        handleAddValue,
        handleFinish,
        handleModalCancel,
        columns,
        layout,
    }
}

export default useProductAttributeUpdate
