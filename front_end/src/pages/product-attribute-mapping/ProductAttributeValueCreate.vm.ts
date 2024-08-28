import { ProductAttributeValueRequest } from '@/model/ProductProductAttributeMapping'
import { useForm } from 'antd/es/form/Form'
import { useState } from 'react'

function useProductAttributeValueCreateViewModel(
    onAddValue: (newValue: ProductAttributeValueRequest) => void,
    onUpdateValue: (newValue: ProductAttributeValueRequest) => void,
    setOpen: (open: boolean) => void,
    isAddMode: boolean,
    editingRecord: ProductAttributeValueRequest | null,
) {
    const [selectedUnit, setSelectedUnit] = useState<string>()

    const handleSelectChange = (value: string) => {
        setSelectedUnit(value)
    }

    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 12 },
    }

    const initialValue: ProductAttributeValueRequest = {
        id: null,
        name: '',
        colorSquaresRgb: '',
        priceAdjustment: 0,
        priceAdjustmentPercentage: false,
        weightAdjustment: 0,
        cost: 0,
        isPreSelected: false,
        displayOrder: 0,
        productAttributeValuePictureRequests: [],
    }

    const [form] = useForm<ProductAttributeValueRequest>()

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: ProductAttributeValueRequest) => {
        values.priceAdjustmentPercentage = selectedUnit == 'true'
        if (isAddMode) {
            onAddValue(values)
        } else {
            values.id = editingRecord?.id
            onUpdateValue(values)
        }
        form.resetFields()
        setOpen(false)
    }
    return { layout, form, onFinish, initialValue, handleSelectChange, selectedUnit, setSelectedUnit }
}
export default useProductAttributeValueCreateViewModel
