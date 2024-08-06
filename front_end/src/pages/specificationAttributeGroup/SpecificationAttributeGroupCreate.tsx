import { Button, Form, Input } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeGroupCreateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupCreate.vm.ts'

const { Item } = Form

const SpecificationAttributeGroupCreate = () => {
    const { form, isLoading, showSaveConfirm, handleSave } = useSpecificationAttributeGroupCreateViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
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
                        onClick={showSaveConfirm} // Show confirmation modal before saving
                        loading={isLoading}
                        style={{ marginRight: 8 }}
                    >
                        Save
                    </Button>
                    <Button key='2' icon={<SaveOutlined />} onClick={() => handleSave(false)} loading={isLoading}>
                        Save and Continue Edit
                    </Button>
                </div>
            </div>
            <Form form={form} layout='vertical' style={{ marginTop: 24 }}>
                <Item name='name' label='Name' rules={[{ required: true, message: 'Please input the group name!' }]}>
                    <Input />
                </Item>
                <Item
                    name='displayOrder'
                    label='Display order'
                    initialValue={0}
                    rules={[{ required: true, message: 'Please input the group display order!' }]}
                >
                    <Input type='number' />
                </Item>
            </Form>
        </div>
    )
}

export default SpecificationAttributeGroupCreate
