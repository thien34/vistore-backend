import ProductAttributeConfigs from './ProductAttributeConfigs'
import useCreateApi from '@/hooks/use-create-api.ts'
import { ProductAttributeRequest } from '@/model/ProductAttribute.ts'
import { Form, message } from 'antd'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'

function useProductAttributeCreate() {
    const { mutate: createProductAttribute } = useCreateApi<ProductAttributeRequest, string>(
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
    })
    const [isOpenConfirm, setOpenConfirm] = useState(false)
    const [loading, setLoading] = useState(false)
    const [current, setCurrent] = useState(1)
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

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id))
        return arr.length === 0 ? 0 : maxId + 1
    }

    const handleAddValue = () => {
        if (!newValue.name || newValue.name.length > 50) {
            message.error('Name is required and cannot exceed 50 characters')
            return
        }
        if (isEdit) {
            formAdd.resetFields()
            const index = values.findIndex((item) => item.id === newValue.id)
            const newArr = values.filter((item) => item.id !== newValue.id)
            newArr.splice(index, 0, newValue)
            setValues(newArr)
            setIsModalOpen(false)
            setIsEdit(false)
        } else {
            formAdd.resetFields()
            newValue.id = getNewId(values)
            setValues([...values, newValue])
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

    const handleFinish = async (formValues: { name: string; description: string }) => {
        setLoading(true)
        setOpenConfirm(false)
        try {
            if (formValues.name.length > 50) {
                message.error('Name cannot exceed 50 characters')
                setLoading(false)
                return
            }
            form.resetFields()
            setValues([])
            const data = {
                name: formValues.name,
                description: formValues.description,
                values: values,
            }

            console.log('data: ', data)
            await onFinish(data)
            navigate('/admin/product-attributes')
        } catch (error) {
            console.error('Error submitting form:', error)
            message.error('Failed to submit form')
        } finally {
            setLoading(false)
        }
    }

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const onFinish = async (values: ProductAttributeRequest) => {
        console.log(values)
        createProductAttribute(values, {
            onSuccess: (data: string) => {
                console.log('data tra ve thanh cong: ', data)
                message.success(data)
                navigate(-1)
            },
            onError: (error: { message: string }) => {
                console.log('data tra ve loi: ', error)
                message.error(error.message)
            },
        })
    }
    const createNumericRegex = (maxLength: number, decimalPlaces: number) => {
        return new RegExp(`^\\d{0,${maxLength}}(\\.\\d{0,${decimalPlaces}})?$`)
    }

    const handleInputChange =
        (field: keyof PredefinedProductAttributeValueRequest, maxLength: number, decimalPlaces: number) =>
        (e: React.ChangeEvent<HTMLInputElement>) => {
            const value = e.target.value
            const regex = createNumericRegex(maxLength, decimalPlaces)
            if (regex.test(value)) {
                setNewValue({
                    ...newValue,
                    [field]: value ? parseFloat(value) : 0,
                })
            }
        }

    return {
        onFinish,
        handlePageChange,
        handleFinish,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        isModalOpen,
        setIsModalOpen,
        values,
        newValue,
        isOpenConfirm,
        setOpenConfirm,
        loading,
        current,
        gradientStyleEdit,
        gradientStyleSave,
        gradientStyleRemove,
        form,
        formAdd,
        setNewValue,
        setIsEdit,
        handleInputChange,
    }
}

export default useProductAttributeCreate
