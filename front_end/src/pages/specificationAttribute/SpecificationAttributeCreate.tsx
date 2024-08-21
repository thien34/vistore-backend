import { Button, Form, Input, InputNumber, Select, Spin, message } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeCreateViewModel from '@/pages/specificationAttribute/SpecificationAttributeCreate.vm.ts'
import Alert from 'antd/es/alert/Alert'

const { Item } = Form
const { Option } = Select

const SpecificationAttributeCreate = () => {
    const [form] = Form.useForm()
    const viewModel = useSpecificationAttributeCreateViewModel(form)

    const { groups, loadingGroups, handleSubmit, showSaveConfirm, creating, setShouldRedirect } = viewModel

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40' style={{ maxWidth: 900, margin: '0 auto' }}>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 24 }}>
                <Button
                    type='primary'
                    icon={<SaveOutlined />}
                    onClick={showSaveConfirm}
                    loading={creating}
                    style={{ marginRight: 8 }}
                >
                    Save
                </Button>
                <Button
                    icon={<SaveOutlined />}
                    onClick={() => {
                        setShouldRedirect(false)
                        form.submit()
                    }}
                    loading={creating}
                >
                    Save and Continue Edit
                </Button>
            </div>
            <Alert
                message={<span> Create Specification Attribute</span>}
                type='info'
                showIcon
                style={{ marginBottom: 20 }}
            />
            <Form
                form={form}
                layout='vertical'
                style={{ marginTop: 24 }}
                onFinish={handleSubmit}
                onFinishFailed={() => {
                    message.error('Please fill in the required fields!')
                }}
            >
                <Form.Item
                    name='name'
                    label='Name'
                    tooltip='Set the name'
                    rules={[
                        { required: true, message: 'Please input the attribute name!' },
                        { max: 100, message: 'Name cannot exceed 100 characters!' },
                    ]}
                >
                    <Input maxLength={101} />
                </Form.Item>
                <Item name='group' label='Group'>
                    {loadingGroups ? (
                        <Spin />
                    ) : (
                        <Select placeholder='Select a group'>
                            {groups.map((group) => (
                                <Option key={group.id} value={group.id}>
                                    {group.name}
                                </Option>
                            ))}
                        </Select>
                    )}
                </Item>
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
                    <InputNumber defaultValue={0} type='number' />
                </Form.Item>
            </Form>
        </div>
    )
}

export default SpecificationAttributeCreate
