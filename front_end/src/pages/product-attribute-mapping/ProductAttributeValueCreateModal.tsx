import { Checkbox, Form, Input, InputNumber, Modal } from 'antd'
import useProductAttributeValueCreateViewModel from './ProductAttributeValueCreate.vm'
import { ProductAttributeValueRequest } from '@/model/ProductProductAttributeMapping'

interface ProductAttributeValueCreateModalProps {
    open: boolean
    setOpen: (open: boolean) => void
    onAddValue: (newValue: ProductAttributeValueRequest) => void
    onUpdateValue: (newValue: ProductAttributeValueRequest) => void
    isAdd: boolean
    editingRecord: ProductAttributeValueRequest | null
}

export default function ProductAttributeValueCreateModal({
    open,
    setOpen,
    onAddValue,
    onUpdateValue,
    isAdd,
}: Readonly<ProductAttributeValueCreateModalProps>) {
    const { layout, form, onFinish, initialValue } = useProductAttributeValueCreateViewModel(
        onAddValue,
        onUpdateValue,
        setOpen,
        isAdd,
    )

    return (
        <Modal
            title={isAdd ? 'Add new value' : 'Update product attribute value'}
            centered
            open={open}
            onOk={() => form.submit()}
            onCancel={() => setOpen(false)}
            width={650}
        >
            <Form {...layout} form={form} size='large' onFinish={onFinish} initialValues={initialValue}>
                <Form.Item label='Name' name='name' rules={[{ required: true, message: 'Please input the name!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item
                    label='Price adjustment'
                    name='priceAdjustment'
                    rules={[{ type: 'number', message: 'Please input a valid number!' }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item
                    label='Price adjustment. Use percentage'
                    name='priceAdjustmentPercentage'
                    valuePropName='checked'
                >
                    <Checkbox />
                </Form.Item>
                <Form.Item
                    label='Weight adjustment'
                    name='weightAdjustment'
                    rules={[{ type: 'number', message: 'Please input a valid number!' }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item
                    label='Cost'
                    name='cost'
                    rules={[{ type: 'number', message: 'Please input a valid number!' }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item label='Is pre-selected' name='isPreSelected' valuePropName='checked'>
                    <Checkbox />
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    rules={[{ type: 'number', message: 'Please input a valid number!' }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item label='Pictures' name='productAttributeValuePictureRequests'>
                    <InputNumber />
                </Form.Item>
            </Form>
        </Modal>
    )
}
