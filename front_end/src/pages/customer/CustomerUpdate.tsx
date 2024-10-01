import { Form, Input, Button, Row, Col, Checkbox, DatePicker, Radio, Select, Collapse, Table } from 'antd'
import { DeleteOutlined, PlusOutlined, SaveOutlined } from '@ant-design/icons'
import useCustomerUpdateViewModel from './CustomerUpdate.vm'
import AddressConfigs from '../address/AddressConfigs'

export default function CustomerUpdate() {
    const { Panel } = Collapse
    const {
        form,
        onFinish,
        onChange,
        value,
        customerRoles,
        handleDelete,
        columns,
        filter,
        handleTableChange,
        handleAddAddress,
        addressesResponse,
        rowSelection,
        isLoading,
        handleDeleteAddress,
    } = useCustomerUpdateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Update Customer</h1>
            <Form layout='vertical' form={form} onFinish={onFinish}>
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
                            <Input size='large' />
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
                        <Form.Item
                            label='Role'
                            name='customerRoles'
                            rules={[{ required: true, message: 'At least one customer role must be selected.' }]}
                        >
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
                    <Button
                        className='bg-[#374151] border-[#374151] text-white'
                        htmlType='submit'
                        icon={<SaveOutlined />}
                        size='large'
                    >
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
            <Collapse className='mt-4' defaultActiveKey={['1']}>
                <Panel header='Addresses' key='1'>
                    {addressesResponse && (
                        <Table
                            rowKey='id'
                            bordered
                            columns={columns}
                            rowSelection={rowSelection}
                            loading={isLoading}
                            dataSource={addressesResponse.items}
                            scroll={{ x: 650 }}
                            pagination={{
                                current: filter.pageNo ?? 1,
                                pageSize: filter.pageSize ?? 6,
                                total: addressesResponse.totalPages * (filter.pageSize ?? 6),
                                onChange: (page, pageSize) => handleTableChange({ current: page, pageSize }),
                            }}
                        />
                    )}
                    <Button
                        className='bg-[#374151] border-[#374151] text-white mt-4'
                        icon={<PlusOutlined />}
                        size='large'
                        onClick={handleAddAddress}
                    >
                        {AddressConfigs.createTitle}
                    </Button>
                    <Button
                        danger
                        icon={<DeleteOutlined />}
                        size='large'
                        className='mt-4 mx-2'
                        onClick={handleDeleteAddress}
                    >
                        Delete
                    </Button>
                </Panel>
            </Collapse>
        </div>
    )
}
