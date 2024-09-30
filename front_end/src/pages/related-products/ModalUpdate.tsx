import { RelatedProductRequest, RelatedProductResponse } from '@/model/RelatedProduct'
import { Modal, Form, Input, Button, Space, InputNumber } from 'antd'
import { Dispatch, SetStateAction, useEffect } from 'react'

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    handleUpdate: (relatedProduct: RelatedProductRequest) => void
    selectedRecord: RelatedProductResponse | null
    title: string
    setTitle: (title: string) => void
}

const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 18 },
}
const tailLayout = {
    wrapperCol: { offset: 16, span: 16 },
}

export default function ModalUpdate({
    isModalOpen,
    setIsModalOpen,
    handleUpdate,
    selectedRecord,
    title,
    setTitle,
}: Readonly<Props>) {
    const [form] = Form.useForm()

    useEffect(() => {
        const updateFormValues = () => {
            if (selectedRecord && form) {
                form.setFieldsValue({
                    ...selectedRecord,
                })
            }
        }

        updateFormValues()
        return () => form.resetFields()
    }, [selectedRecord, form])

    const onFinish = (value: RelatedProductRequest) => {
        console.log(value.id)
        handleUpdate(value)
        form.resetFields()
        setTitle('Update Related Product')
        setIsModalOpen(false)
    }

    const handleCancel = () => {
        form.resetFields()
        setTitle('Update Related Product')
        setIsModalOpen(false)
    }

    return (
        <Modal closable={true} title={title} open={isModalOpen} onCancel={handleCancel} footer={null}>
            <Form {...layout} form={form} name='control-related-product-update' onFinish={onFinish}>
                <Form.Item hidden name='id'>
                    <Input />
                </Form.Item>
                <Form.Item hidden name='product1Id'>
                    <Input />
                </Form.Item>
                <Form.Item hidden name='product2Id'>
                    <Input />
                </Form.Item>
                <Form.Item name='nameProduct2' label='Product'>
                    <Input disabled />
                </Form.Item>
                <Form.Item
                    name='displayOrder'
                    label='Display order'
                    rules={[{ required: true, min: 0, max: 100, type: 'number' }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item {...tailLayout}>
                    <Space>
                        <Button type='primary' htmlType='submit'>
                            Submit
                        </Button>
                        <Button htmlType='button' onClick={handleCancel}>
                            Cancel
                        </Button>
                    </Space>
                </Form.Item>
            </Form>
        </Modal>
    )
}
