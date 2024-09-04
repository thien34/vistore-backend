import { useNavigate, useParams } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { Form } from 'antd'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import ProductAttributeConfigs from './ProductAttributeConfigs.ts'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'

function useProductAttributeUpdate() {
    const NUMERIC_REGEX = /^\d{0,12}(\.\d{0,2})?$/
    const DISPLAY_ORDER_REGEX = /^\d{0,8}(\.\d{0,2})?$/
    const navigate = useNavigate()
    const { id } = useParams<{ id: string }>()
    const [current, setCurrent] = useState(1)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const { mutate: updateProductAttribute, isPending } = useUpdateApi<ProductAttributeResponse>(
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
    const [loading, setLoading] = useState(false)

    const { data: productAttributeResponse, isLoading } = useGetByIdApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )

    useEffect(() => {
        if (productAttributeResponse) {
            form.setFieldsValue({
                name: productAttributeResponse.name,
                description: productAttributeResponse.description,
            })
            setValues(productAttributeResponse.values)
        }
    }, [form, productAttributeResponse])

    const onFinish = async (values: ProductAttributeResponse) => {
        updateProductAttribute({ id: Number(id), ...values }, { onSuccess: () => navigate(-1) })
    }

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id))
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
            setNewValue({
                id: getNewId(values),
                name: '',
                priceAdjustment: 0,
                priceAdjustmentUsePercentage: false,
                weightAdjustment: 0,
                cost: 0,
                isPreSelected: false,
                displayOrder: 0,
                productAttribute: 0,
            })
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
        setNewValue({
            id: getNewId(values),
            name: '',
            priceAdjustment: 0,
            priceAdjustmentUsePercentage: false,
            weightAdjustment: 0,
            cost: 0,
            isPreSelected: false,
            displayOrder: 0,
            productAttribute: 0,
        })
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
        setLoading(true)
        try {
            const sanitizedValues = values.map((val) => ({
                ...val,
                priceAdjustment: parseFloat(val.priceAdjustment.toString().replace(/[^\d.-]/g, '')) || 0,
                weightAdjustment: parseFloat(val.weightAdjustment.toString().replace(/[^\d.-]/g, '')) || 0,
                cost: parseFloat(val.cost.toString().replace(/[^\d.-]/g, '')) || 0,
                displayOrder: parseFloat(val.displayOrder.toString().replace(/[^\d.-]/g, '')) || 0,
            }))

            const data = {
                name: formValues.name,
                description: formValues.description,
                values: sanitizedValues,
            }

            await onFinish(data)
        } catch (error) {
            console.error('Error submitting form:', error)
            console.error('Failed to submit form')
        } finally {
            setLoading(false)
        }
    }

    const handleInputChange =
        (field: keyof PredefinedProductAttributeValueRequest) => (e: React.ChangeEvent<HTMLInputElement>) => {
            const value = e.target.value
            if (NUMERIC_REGEX.test(value)) {
                setNewValue({
                    ...newValue,
                    [field]: value ? parseFloat(value) : 0,
                })
            }
        }

    const handleInputDisplayOrder =
        (field: keyof PredefinedProductAttributeValueRequest) => (e: React.ChangeEvent<HTMLInputElement>) => {
            const value = e.target.value
            if (DISPLAY_ORDER_REGEX.test(value)) {
                setNewValue({
                    ...newValue,
                    [field]: value ? parseFloat(value) : 0,
                })
            }
        }

    return {
        onFinish,
        productAttributeResponse,
        isLoading,
        isPending,
        handlePageChange,
        current,
        form,
        formAdd,
        values,
        setValues,
        isEdit,
        setIsEdit,
        newValue,
        setNewValue,
        loading,
        setLoading,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        handleFinish,
        NUMERIC_REGEX,
        handleInputChange,
        handleInputDisplayOrder,
        isModalOpen,
        setIsModalOpen,
        handleModalCancel,
    }
}

export default useProductAttributeUpdate
