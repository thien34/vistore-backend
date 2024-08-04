import useUpdateApi from '@/hooks/use-update-api'
import { useNavigate, useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import ProductAttributeConfigs from './ProductAttributeConfigs.ts'
import { useState } from 'react'
import { Form, message } from 'antd'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'

function useProductAttributeUpdate() {
    const NUMERIC_REGEX = /^\d{0,12}(\.\d{0,2})?$/
    const DISPLAY_ORDER_REGEX = /^\d{0,8}(\.\d{0,2})?$/
    const navigation = useNavigate()
    const { id } = useParams<{ id: string }>()
    const [current, setCurrent] = useState(1)
    const { mutate: updateProductAttribute, isPending } = useUpdateApi<ProductAttributeResponse, string>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )
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
    })
    const [isOpenConfirm, setOpenConfirm] = useState(false)
    const [loading, setLoading] = useState(false)

    const { data: productAttributeResponse, isLoading } = useGetByIdApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )

    // HANDLER FUNCTION ON FINISH FORM
    const onFinish = async (values: ProductAttributeResponse) => {
        updateProductAttribute({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
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
            setIsModalOpen(false)
            setIsEdit(false)
        } else {
            formAdd.resetFields()
            newValue.id = getNewId(values)
            setValues([
                ...values,
                {
                    ...newValue,
                    priceAdjustment: newValue.priceAdjustment || 0,
                    weightAdjustment: newValue.weightAdjustment || 0,
                    cost: newValue.cost || 0,
                    displayOrder: newValue.displayOrder || 0,
                },
            ])
            setNewValue({
                id: getNewId(values),
                name: '',
                priceAdjustment: 0,
                priceAdjustmentUsePercentage: false,
                weightAdjustment: 0,
                cost: 0,
                isPreSelected: false,
                displayOrder: 0,
            })
            setIsModalOpen(false)
        }
    }

    const handleRemoveValue = (value: PredefinedProductAttributeValueRequest) => {
        setValues(values.filter((v) => v.id !== value.id))
    }

    const handleEditValue = (value: PredefinedProductAttributeValueRequest) => {
        setIsModalOpen(true)
        formAdd.setFieldsValue(value)
        setNewValue(value)
        setIsEdit(true)
    }
    const gradientStyleEdit = {
        background: 'linear-gradient(to right, #4facfe, #00f2fe)',
        border: 'none',
        color: 'white',
    }
    const gradientStyleSave = {
        background: 'linear-gradient(to right, #34C759, #8BC34A)',
        border: 'none',
        color: 'white',
    }

    const gradientStyleRemove = {
        background: 'linear-gradient(to right, #ff6a6a, #ff0000)',
        border: 'none',
        color: 'white',
    }

    const handleFinish = async (formValues: { name: string; description: string }) => {
        setLoading(true)
        setOpenConfirm(false)
        try {
            form.resetFields()
            setValues([])

            const data = {
                name: formValues.name,
                description: formValues.description,
                values: values.map((val) => ({
                    ...val,
                    priceAdjustment: val.priceAdjustment || 0,
                    weightAdjustment: val.weightAdjustment || 0,
                    cost: val.cost || 0,
                    displayOrder: val.displayOrder || 0,
                })),
            }

            console.log('data: ', data)
            await onFinish(data)
        } catch (error) {
            console.error('Error submitting form:', error)
            message.error('Failed to submit form')
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
        isModalOpen,
        setIsModalOpen,
        isEdit,
        setIsEdit,
        newValue,
        setNewValue,
        isOpenConfirm,
        setOpenConfirm,
        loading,
        setLoading,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        gradientStyleEdit,
        gradientStyleSave,
        gradientStyleRemove,
        handleFinish,
        NUMERIC_REGEX,
        handleInputChange,
        handleInputDisplayOrder,
    }
}
export default useProductAttributeUpdate
