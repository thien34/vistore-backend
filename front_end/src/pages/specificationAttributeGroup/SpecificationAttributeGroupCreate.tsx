import { Button, Col, Form, Input, InputNumber, Row } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import useSpecificationAttributeGroupCreateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupCreate.vm.ts'
import SpecificationAttributeGroupConfigs from './SpecificationAttributeGroupConfigs'

const SpecificationAttributeGroupCreate = () => {
    const { form, handleSave, layout } = useSpecificationAttributeGroupCreateViewModel()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>{SpecificationAttributeGroupConfigs.addTitle}</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='default-btn-color' size='large' icon={<SaveOutlined />} onClick={handleSave}>
                        {SpecificationAttributeGroupConfigs.btnSave}
                    </Button>
                </div>
            </div>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <Form initialValues={{ displayOrder: 0 }} {...layout} form={form} layout='vertical' size='large'>
                    <Row>
                        <Col span={12}>
                            <Form.Item
                                name='name'
                                label='Name'
                                rules={[
                                    { required: true, message: 'Please input the group name!' },
                                    { max: 100, message: 'Name cannot exceed 100 characters!' },
                                ]}
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
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
                                <InputNumber className='w-36' type='number' />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </div>
        </>
    )
}

export default SpecificationAttributeGroupCreate
