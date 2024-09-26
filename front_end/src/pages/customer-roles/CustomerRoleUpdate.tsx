import { Form, Input, Button, Checkbox, Row, Col, Spin } from 'antd'
import { DeleteOutlined, InfoCircleOutlined } from '@ant-design/icons'
import 'tailwindcss/tailwind.css'
import useCustomerRoleUpdateViewModel from './CustomerRoleUpdate.vm'

export default function CustomerRoleUpdate() {
    const {
        form,
        isLoading,
        handleSave,
        handleSaveAndContinue,
        isSystemRole,
        handleValuesChange,
        handleDeleteCustomerRole,
    } = useCustomerRoleUpdateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Update Customer Role</h1>

            {isLoading ? (
                <Spin />
            ) : (
                <Form form={form} onValuesChange={handleValuesChange} layout='vertical' onFinish={handleSave}>
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
                                <Input size='large' value={isSystemRole ? 'Yes' : 'No'} readOnly />
                            </Form.Item>
                        </Col>
                    </Row>

                    <div className='flex space-x-2 mt-4'>
                        <Button type='primary' htmlType='submit' size='large'>
                            Save
                        </Button>
                        <Button
                            type='default'
                            size='large'
                            onClick={() => handleSaveAndContinue(form.getFieldsValue())}
                        >
                            Save and Continue Edit
                        </Button>
                        <Button
                            onClick={handleDeleteCustomerRole}
                            size='large'
                            danger
                            type='primary'
                            icon={<DeleteOutlined />}
                        >
                            Delete
                        </Button>
                    </div>
                </Form>
            )}
        </div>
    )
}
