import { Button, Form, Input, InputNumber, Select } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeCreateViewModel from '@/pages/specificationAttribute/SpecificationAttributeCreate.vm.ts'
import SpecificationAttributeConfigs from './SpecificationAttributeConfigs'

const SpecificationAttributeCreate = () => {
    const { layout, groups, handleSubmit, handleSave, form } = useSpecificationAttributeCreateViewModel()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>{SpecificationAttributeConfigs.addTitle}</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='default-btn-color' size='large' icon={<SaveOutlined />} onClick={handleSave}>
                        {SpecificationAttributeConfigs.btnSave}
                    </Button>
                </div>
            </div>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <Form
                    {...layout}
                    form={form}
                    initialValues={{ displayOrder: 0 }}
                    layout='horizontal'
                    size='large'
                    onFinish={handleSubmit}
                >
                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[
                            { required: true, message: 'Please input the attribute name!' },
                            { max: 100, message: 'Name cannot exceed 100 characters!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label='Display order'
                        name='displayOrder'
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
                    <Form.Item name='group' label='Group'>
                        <Select placeholder='Select a group'>
                            {groups.map((group) => (
                                <Select.Option key={group.id} value={group.id}>
                                    {group.name}
                                </Select.Option>
                            ))}
                        </Select>
                    </Form.Item>
                </Form>
            </div>
        </>
    )
}

export default SpecificationAttributeCreate
