import { Form, Input, Button, Row, Col, Checkbox, DatePicker, Radio, Select } from 'antd'
import { DeleteOutlined, SaveOutlined } from '@ant-design/icons'
import useCustomerUpdateViewModel from './CustomerUpdate.vm'

export default function CustomerUpdate() {
    const { form, onFinish, onChange, value, customerRoles, handleDelete } = useCustomerUpdateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Update Customer</h1>
            <Form layout='vertical' form={form} onFinish={onFinish}>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            label='Email'
                            name='email'
                            rules={[{ required: true, message: 'Email must not be empty.' }]}
                        >
                            <Input size='large' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label='Password' name='password'>
                            <Input.Password size='large' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label='First Name' name='firstName'>
                            <Input size='large' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label='Last Name' name='lastName'>
                            <Input size='large' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label='Gender' name='gender'>
                            <Radio.Group onChange={onChange} value={value}>
                                <Radio value='MALE'>Male</Radio>
                                <Radio value='FEMALE'>Female</Radio>
                                <Radio value='OTHER'>Other</Radio>
                            </Radio.Group>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label='Date of Birth' name='dateOfBirth'>
                            <DatePicker size='large' format='YYYY-MM-DD' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label='Phone' name='phone'>
                            <Input size='large' />
                        </Form.Item>
                    </Col>

                    <Col span={12}>
                        <Form.Item label='Role' name='customerRoles'>
                            <Select
                                size='large'
                                mode='multiple'
                                placeholder='Select customer roles'
                                options={customerRoles.map((role) => ({
                                    label: role.name,
                                    value: role.id,
                                }))}
                            ></Select>
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label='Active' name='active' valuePropName='checked'>
                            <Checkbox>Active</Checkbox>
                        </Form.Item>
                    </Col>
                </Row>

                <div className='flex space-x-2 mt-4'>
                    <Button type='primary' htmlType='submit' icon={<SaveOutlined />} size='large'>
                        Save
                    </Button>
                    <Button
                        size='large'
                        type='default'
                        danger
                        icon={<DeleteOutlined />}
                        onClick={handleDelete}
                        className='ml-10'
                    >
                        Delete
                    </Button>
                </div>
            </Form>
        </div>
    )
}
