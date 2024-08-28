import { Form, Modal } from 'antd'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import useCreateApi from '@/hooks/use-create-api.ts'
import { ProductAttributeRequest } from '@/model/ProductAttribute.ts'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import ProductAttributeConfigs from './ProductAttributeConfigs'

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
    const [isOpenConfirm, setOpenConfirm] = useState(false)
    const [loading, setLoading] = useState(false)
    const [current, setCurrent] = useState(1)

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id ?? 0))
        return arr.length === 0 ? 0 : maxId + 1
    }

    const handleAddValue = () => {
        if (!newValue.name || newValue.name.length > 50) {
            console.error('Name is required and cannot exceed 50 characters')
            return
        }

        if (isEdit) {
            const index = values.findIndex((item) => item.id === newValue.id)
            if (index > -1) {
                const updatedValues = [...values]
                updatedValues[index] = newValue
                setValues(updatedValues)
                console.log('Value updated successfully')
            }
        } else {
            const newId = getNewId(values)
            const newValueWithId = { ...newValue, id: newId }
            setValues([...values, newValueWithId])
            console.log('Value added successfully')
        }

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
            setIsEditPriceAdjustment: 0,
        })
        setIsModalOpen(false)
        setIsEdit(false)
    }
    const handleCancelModal = () => {
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
            setIsEditPriceAdjustment: 0,
        })
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

    const handleFinish = async (formValues: { name?: string; description?: string }) => {
        setLoading(true)
        setOpenConfirm(false)
        try {
            if (!formValues.name || formValues.name.length > 50) {
                console.error('Name is required and cannot exceed 50 characters')
                setLoading(false)
                return
            }
            form.resetFields()
            setValues([])
            const data = {
                name: formValues.name,
                description: formValues.description || '',
                values: values,
            }
            await onFinish(data)
            navigate('/admin/product-attributes', { state: { reload: true } })
        } catch (error) {
            console.error('Error submitting form:', error)
        } finally {
            setLoading(false)
        }
    }

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const onFinish = async (values: ProductAttributeRequest) => {
        try {
            console.log(values)
            createProductAttribute(values, {
                onSuccess: (data: string) => {
                    console.log('data tra ve thanh cong: ', data)
                    navigate(-1)
                },
                onError: (error: { message: string }) => {
                    console.log('data tra ve loi: ', error)
                    console.error(error.message)
                },
            })
        } catch (error) {
            console.error('Error in onFinish:', error)
        }
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
        form,
        formAdd,
        setNewValue,
        handleInputChange,
        handleCancelModal,
    }
}

export default useProductAttributeCreate
