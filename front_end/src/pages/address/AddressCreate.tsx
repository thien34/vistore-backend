import { Button, Col, Form, Input, Row, Select } from 'antd'
import useAddressCreateViewModel from './AddressCreate.vm'
import { AddressTypeEnum } from './AddressTypeEnum'
import { SaveOutlined } from '@ant-design/icons'

export default function AddressCreate() {
    const { Option } = Select

    const { onFinish, provinces, filteredDistricts, filteredWards, onProvinceChange, onDistrictChange, form } =
        useAddressCreateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <h1 className='text-2xl font-bold mb-4'>Add a new address</h1>

            <Form layout='vertical' size='large' onFinish={onFinish} form={form}>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            label='First Name'
                            name='firstName'
                            rules={[{ required: true, message: 'Please input your first name!' }]}
                        >
                            <Input placeholder='Enter first name' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            label='Last Name'
                            name='lastName'
                            rules={[{ required: true, message: 'Please input your last name!' }]}
                        >
                            <Input placeholder='Enter last name' />
                        </Form.Item>
                    </Col>
                </Row>

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
                            <Input placeholder='Enter email' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label='Company' name='company'>
                            <Input placeholder='Enter company' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={8}>
                        <Form.Item
                            label='Province'
                            name='provinceId'
                            rules={[{ required: true, message: 'Please select a province!' }]}
                        >
                            <Select placeholder='Select province' onChange={onProvinceChange}>
                                {provinces?.map((province) => (
                                    <Option key={province.code} value={province.code}>
                                        {province.nameEn}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item
                            label='District'
                            name='districtId'
                            rules={[{ required: true, message: 'Please select a district!' }]}
                        >
                            <Select
                                placeholder='Select district'
                                onChange={onDistrictChange}
                                disabled={!filteredDistricts.length}
                            >
                                {filteredDistricts.map((district) => (
                                    <Option key={district.code} value={district.code}>
                                        {district.nameEn}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item
                            label='Ward'
                            name='wardId'
                            rules={[{ required: true, message: 'Please select a ward!' }]}
                        >
                            <Select placeholder='Select ward' disabled={!filteredWards.length}>
                                {filteredWards.map((ward) => (
                                    <Option key={ward.code} value={ward.code}>
                                        {ward.nameEn}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={24}>
                        <Form.Item
                            label='Address'
                            name='addressName'
                            rules={[{ required: true, message: 'Please input your address!' }]}
                        >
                            <Input placeholder='Enter address' />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            label='Phone Number'
                            name='phoneNumber'
                            rules={[{ required: true, message: 'Please input your phone number!' }]}
                        >
                            <Input placeholder='Enter phone number' />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            label='Address Type'
                            name='addressTypeId'
                            rules={[{ required: true, message: 'Please select an address type!' }]}
                        >
                            <Select placeholder='Select address type'>
                                <Option value={AddressTypeEnum.HOME}>Home</Option>
                                <Option value={AddressTypeEnum.BILLING}>Billing</Option>
                                <Option value={AddressTypeEnum.SHIPPING}>Shipping</Option>
                                <Option value={AddressTypeEnum.PICKUP}>Pickup</Option>
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                <Form.Item>
                    <Button
                        className='bg-[#374151] border-[#374151] text-white'
                        htmlType='submit'
                        icon={<SaveOutlined />}
                    >
                        Save
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}
