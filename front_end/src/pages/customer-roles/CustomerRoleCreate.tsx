import { Form, Input, Button, Checkbox, Row, Col } from 'antd'
import { InfoCircleOutlined } from '@ant-design/icons'
import 'tailwindcss/tailwind.css'
import useCustomerRoleCreateViewModel from './CustomerRoleCreate.vm'
import { CustomerRoleRequest } from '@/model/CustomerRole'

export default function CustomerRoleCreate() {
    const { form, onFinish } = useCustomerRoleCreateViewModel()

    const handleSaveAndContinue = (values: CustomerRoleRequest) => {
        onFinish(values, false)
    }

    const handleSave = (values: CustomerRoleRequest) => {
        onFinish(values, true)
    }

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Add a new customer role</h1>

            <Form form={form} layout='vertical' onFinish={handleSave}>
                <Row gutter={16} align='middle'>
                    <Col flex='1'>
                        <Form.Item
                            label='Role Name'
                            name='name'
                            rules={[
                                { required: true, message: 'Please input the role name!' },
                                { max: 100, message: 'Role name cannot exceed 100 characters!' },
                            ]}
                            tooltip={{ title: 'Enter the customer role name', icon: <InfoCircleOutlined /> }}
                        >
                            <Input maxLength={101} size='large' />
                        </Form.Item>
                    </Col>

                    <Col>
                        <Form.Item name='active' valuePropName='checked' label='Active'>
                            <Checkbox />
                        </Form.Item>
                    </Col>

                    <Col>
                        <Form.Item label='Is System Role' tooltip='Indicate if this is a system role'>
                            <Input size='large' value='No' readOnly />
                        </Form.Item>
                    </Col>
                </Row>

                <div className='flex space-x-2 mt-4'>
                    <Button type='primary' htmlType='submit' size='large'>
                        Save
                    </Button>
                    <Button type='default' size='large' onClick={() => handleSaveAndContinue(form.getFieldsValue())}>
                        Save and Continue Edit
                    </Button>
                </div>
            </Form>
        </div>
    )
}
