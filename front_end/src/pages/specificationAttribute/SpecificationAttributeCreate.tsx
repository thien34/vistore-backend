import { Button, Form, Input, Select, Spin, message } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeCreateViewModel from '@/pages/specificationAttribute/SpecificationAttributeCreate.vm.ts'

const { Item } = Form
const { Option } = Select

const SpecificationAttributeCreate = () => {
    const [form] = Form.useForm()
    const viewModel = useSpecificationAttributeCreateViewModel(form)

    const { groups, loadingGroups, handleSubmit, showSaveConfirm, creating, setShouldRedirect } = viewModel

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
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
            <h2>Attribute Info</h2>
            <Form
                form={form}
                layout='vertical'
                style={{ marginTop: 24 }}
                onFinish={handleSubmit}
                onFinishFailed={() => {
                    message.error('Please fill in the required fields!')
                }}
            >
                <Item
                    name='name'
                    label='Name'
                    rules={[{ required: true, message: 'Please input the attribute name!' }]}
                >
                    <Input />
                </Item>
                <Item name='group' label='Group'>
                    {loadingGroups ? (
                        <Spin />
                    ) : (
                        <Select placeholder='Select a group'>
                            {groups.map((group: { id: number; name: string }) => (
                                <Option key={group.id} value={group.id}>
                                    {group.name}
                                </Option>
                            ))}
                        </Select>
                    )}
                </Item>
                <Item name='displayOrder' label='Display Order' initialValue={0}>
                    <Input type='number' />
                </Item>
            </Form>
        </div>
    )
}

export default SpecificationAttributeCreate
