import React from 'react'
import { Button, Form, Input, Select } from 'antd'
import { SaveOutlined } from '@ant-design/icons'

const { Item } = Form
const { Option } = Select

export default function SpecificationAttributeCreate() {
    const [form] = Form.useForm()

    return (
        <div style={{ padding: 24 }}>
            {/* Nút Save và Save and Continue Edit */}
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 24 }}>
                <Button type="primary" icon={<SaveOutlined />} style={{ marginRight: 8 }}>
                    Save
                </Button>
                <Button icon={<SaveOutlined />}>
                    Save and Continue Edit
                </Button>
            </div>
            {/* Form điền thông tin */}
            <h2>Attribute Info</h2>
            <Form form={form} layout="vertical" style={{ marginTop: 24 }}>
                <Item
                    name="name"
                    label="Name"
                    rules={[{ required: true, message: 'Please input the attribute name!' }]}
                >
                    <Input />
                </Item>
                <Item name="group" label="Group" rules={[{ required: true, message: 'Please select a group!' }]}>
                    <Select placeholder='Select a group'>
                        <Option value='group1'>Group 1</Option>
                        <Option value='group2'>Group 2</Option>
                    </Select>
                </Item>
                <Item
                    name='displayOrder'
                    label='Display Order'
                    rules={[{ required: true, message: 'Please input the display order!' }]}
                    initialValue={0}
                >
                    <Input type='number' />
                </Item>
            </Form>
        </div>
    )
}
