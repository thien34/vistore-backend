import { Button, Form, Input, InputNumber } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeGroupCreateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupCreate.vm.ts'

const SpecificationAttributeGroupCreate = () => {
    const { form, showSaveConfirm, handleSave } = useSpecificationAttributeGroupCreateViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40' style={{ maxWidth: 900, margin: '0 auto' }}>
            <div
                className='site-page-header'
                style={{ marginBottom: 24, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
            >
                <h1 style={{ margin: 0 }}>Add a new specification attribute group</h1>
                <div>
                    <Button
                        key='1'
                        type='primary'
                        icon={<SaveOutlined />}
                        onClick={showSaveConfirm}
                        style={{ marginRight: 8 }}
                    >
                        Save
                    </Button>
                    <Button key='2' icon={<SaveOutlined />} onClick={() => handleSave(false)}>
                        Save and Continue Edit
                    </Button>
                </div>
            </div>
            <Form initialValues={{ displayOrder: 0 }} form={form} layout='vertical' style={{ marginTop: 30 }}>
                <Form.Item
                    name='name'
                    label='Name'
                    tooltip='Set the name'
                    rules={[
                        { required: true, message: 'Please input the group name!' },
                        { max: 100, message: 'Name cannot exceed 100 characters!' },
                    ]}
                >
                    <Input maxLength={101} />
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    tooltip='Set the display order'
                    rules={[
                        { required: true, message: 'Please enter the display order!' },
                        {
                            type: 'number',
                            min: 0,
                            max: 2000000,
                            message: 'Display order must be between 0 and 2,000,000!',
                        },
                    ]}
                >
                    <InputNumber type='number' />
                </Form.Item>
            </Form>
        </div>
    )
}

export default SpecificationAttributeGroupCreate
