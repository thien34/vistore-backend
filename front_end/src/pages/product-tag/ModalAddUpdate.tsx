import { ProductTagRequest, ProductTagResponse } from '@/model/ProductTag'
import { Modal, Form, Input, Select, Button, Space } from 'antd'
import { Dispatch, SetStateAction, useEffect } from 'react'

const { Option } = Select

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    handleCreate: (productTag: ProductTagRequest) => void
    selectedTag: ProductTagResponse | null
    title: string
    setTitle: (title: string) => void
}

const layout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 18 },
}
const tailLayout = {
    wrapperCol: { offset: 16, span: 16 },
}

export default function ModalAddUpdate({
    isModalOpen,
    setIsModalOpen,
    handleCreate,
    selectedTag,
    title,
    setTitle,
}: Readonly<Props>) {
    const [form] = Form.useForm()

    useEffect(() => {
        const updateFormValues = () => {
            if (selectedTag && form) {
                form.setFieldsValue({
                    ...selectedTag,
                })
            }
        }

        updateFormValues()
        return () => form.resetFields()
    }, [selectedTag, form])

    const onFinish = (value: ProductTagRequest) => {
        handleCreate(value)
        form.resetFields()
        setTitle('Create Product Tag')
        setIsModalOpen(false)
    }

    const handleCancel = () => {
        form.resetFields()
        setTitle('Create Product Tag')
        setIsModalOpen(false)
    }

    return (
        <Modal closable={true} title={title} open={isModalOpen} onCancel={handleCancel} footer={null}>
            <Form {...layout} form={form} name='control-hooks' onFinish={onFinish}>
                <Form.Item hidden name='id'>
                    <Input />
                </Form.Item>
                <Form.Item
                    name='name'
                    label='Tag name'
                    rules={[{ required: true, message: 'The tag name is required.' }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    name='productId'
                    label='Product'
                    rules={[{ required: true, message: 'The Product is required.' }]}
                >
                    <Select placeholder='Select a option and change input text above' allowClear>
                        <Option value='1'>Product</Option>
                    </Select>
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
