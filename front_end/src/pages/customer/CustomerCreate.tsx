import { Form, Input, Button, Radio, DatePicker, Checkbox, Select, Row, Col } from 'antd'
import { SaveOutlined } from '@ant-design/icons'
import 'tailwindcss/tailwind.css'
import useCustomerCreateViewModel from './CustomerCreate.vm'
import { GenderTypeEnum } from './GenderTypeEnum'

const { Option } = Select

export default function CustomerCreate() {
    const { handleFinish, onChange, value, customerRoles } = useCustomerCreateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Create a new Customer</h1>
            <Form layout='vertical' onFinish={handleFinish}>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            label='Email'
                            name='email'
                            rules={[
                                { type: 'email', message: 'Please enter a valid email.' },
                                { max: 100, message: 'Email cannot exceed 100 characters.' },
                            ]}
                        >
                            <Input maxLength={101} size='large' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            label='Password'
                            name='password'
                            rules={[{ min: 6, message: 'Password must be at least 6 characters.' }]}
                        >
                            <Input.Password size='large' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            label='First Name'
                            name='firstName'
                            rules={[
                                { required: true, message: 'First Name must not be empty.' },
                                { max: 100, message: 'Email cannot exceed 100 characters.' },
                            ]}
                        >
                            <Input maxLength={101} size='large' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            label='Last Name'
                            name='lastName'
                            rules={[
                                { required: true, message: 'Last Name must not be empty.' },
                                { max: 100, message: 'Email cannot exceed 100 characters.' },
                            ]}
                        >
                            <Input maxLength={101} size='large' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item initialValue={GenderTypeEnum.MALE} label='Gender' name='gender'>
                            <Radio.Group onChange={onChange} value={value}>
                                <Radio value={GenderTypeEnum.MALE}>Male</Radio>
                                <Radio value={GenderTypeEnum.FEMALE}>Female</Radio>
                                <Radio value={GenderTypeEnum.OTHER}>Other</Radio>
                            </Radio.Group>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label='Date of Birth' name='dob'>
                            <DatePicker size='large' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={24}>
                        <Form.Item
                            label='Customer Roles'
                            name='customerRoles'
                            rules={[{ required: true, message: 'At least one customer role must be selected.' }]}
                        >
                            <Select mode='multiple' placeholder='Select customer roles' size='large'>
                                {Array.isArray(customerRoles) && customerRoles.length > 0 ? (
                                    customerRoles.map((role) =>
                                        role.id ? (
                                            <Option key={role.id} value={role.id}>
                                                {role.name}
                                            </Option>
                                        ) : null,
                                    )
                                ) : (
                                    <Option disabled>No roles available</Option>
                                )}
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item name='active' valuePropName='checked'>
                            <Checkbox>Active</Checkbox>
                        </Form.Item>
                    </Col>
                </Row>

                <div className='flex space-x-2 mt-4'>
                    <Button
                        className='bg-[#374151] border-[#374151] text-white'
                        htmlType='submit'
                        icon={<SaveOutlined />}
                        size='large'
                    >
                        Save
                    </Button>
                </div>
            </Form>
        </div>
    )
}
