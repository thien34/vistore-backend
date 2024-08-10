import { ProductAttributeValueRequest } from '@/model/ProductProductAttributeMapping'
import { useForm } from 'antd/es/form/Form'

function useProductAttributeValueCreateViewModel(
    onAddValue: (newValue: ProductAttributeValueRequest) => void,
    onUpdateValue: (newValue: ProductAttributeValueRequest) => void,
    setOpen: (open: boolean) => void,
    isAddMode: boolean,
) {
    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 12 },
    }

    const initialValue: ProductAttributeValueRequest = {
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
        if (isAddMode) {
            onAddValue(values)
        } else {
            onUpdateValue(values)
        }
        form.resetFields()
        setOpen(false)
    }
    return { layout, form, onFinish, initialValue }
}
export default useProductAttributeValueCreateViewModel
