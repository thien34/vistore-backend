import { ProductTagResponse, ProductTagUpdateRequest } from '@/model/ProductTag'
import { Modal, Form, Input, Button, Space } from 'antd'
import { Dispatch, SetStateAction, useEffect } from 'react'
import ProductTagConfigs from './ProductTagConfigs'

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    handleCreate: (productTag: ProductTagUpdateRequest) => void
    selectedTag: ProductTagResponse | null
}

const layout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 18 },
}
const tailLayout = {
    wrapperCol: { offset: 16, span: 16 },
}

export default function ModalAddUpdate({ isModalOpen, setIsModalOpen, handleCreate, selectedTag }: Readonly<Props>) {
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

    const onFinish = (value: ProductTagUpdateRequest) => {
        handleCreate(value)
        form.resetFields()
        setIsModalOpen(false)
    }

    const handleCancel = () => {
        setIsModalOpen(false)
    }

    return (
        <Modal
            closable={true}
            title={ProductTagConfigs.updateTitle}
            open={isModalOpen}
            onCancel={handleCancel}
            footer={null}
        >
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
