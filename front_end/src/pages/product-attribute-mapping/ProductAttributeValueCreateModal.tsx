import { Checkbox, Form, Input, InputNumber, Modal, Select } from 'antd'
import useProductAttributeValueCreateViewModel from './ProductAttributeValueCreate.vm'
import { ProductAttributeValueRequest } from '@/model/ProductProductAttributeMapping'
import { useEffect } from 'react'

interface ProductAttributeValueCreateModalProps {
    open: boolean
    setOpen: (open: boolean) => void
    onAddValue: (newValue: ProductAttributeValueRequest) => void
    openUpdateValue: (newValue: ProductAttributeValueRequest) => void
    isAdd: boolean
    editingRecord: ProductAttributeValueRequest | null
}

export default function ProductAttributeValueCreateModal({
    open,
    setOpen,
    onAddValue,
    openUpdateValue,
    isAdd,
    editingRecord,
}: Readonly<ProductAttributeValueCreateModalProps>) {
    const { layout, form, onFinish, initialValue, handleSelectChange, selectedUnit, setSelectedUnit } =
        useProductAttributeValueCreateViewModel(onAddValue, openUpdateValue, setOpen, isAdd, editingRecord)

    useEffect(() => {
        if (open) {
            if (editingRecord) {
                form.setFieldsValue(editingRecord)
                setSelectedUnit(editingRecord.priceAdjustmentPercentage ? 'true' : 'false')
            }
            if (isAdd) {
                form.resetFields()
                setSelectedUnit('false')
            }
        }
    }, [open, editingRecord, form, isAdd, setSelectedUnit])

    const selectAfter = (
        <Select
            defaultValue='false'
            value={selectedUnit}
            onChange={(value) => {
                handleSelectChange(value)
            }}
        >
            <Select.Option value='false'>&#36;</Select.Option>
            <Select.Option value='true'>&#37;</Select.Option>
        </Select>
    )

    return (
        <Modal
            title={isAdd ? 'Add new value' : 'Update product attribute value'}
            centered
            open={open}
            onOk={() => form.submit()}
            onCancel={() => setOpen(false)}
            width={700}
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
                    <InputNumber addonAfter={selectAfter} />
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
