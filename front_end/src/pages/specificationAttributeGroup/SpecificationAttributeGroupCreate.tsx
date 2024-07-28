import React from 'react';
import { Button, Form, Input } from 'antd';
import { SaveOutlined } from '@ant-design/icons';

const { Item } = Form;

const SpecificationAttributeGroupCreate = () => {
    const [form] = Form.useForm();

    const handleSave = () => {
        form.validateFields()
            .then((values) => {
                console.log('Success:', values);
                // Implement your save logic here
            })
            .catch((errorInfo) => {
                console.log('Failed:', errorInfo);
            });
    };

    return (
        <div style={{ padding: 24 }}>
            <div className="site-page-header" style={{ marginBottom: 24, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h1 style={{ margin: 0 }}>Add a new specification attribute group</h1>
                <div>
                    <Button key="1" type="primary" icon={<SaveOutlined />} onClick={handleSave} style={{ marginRight: 8 }}>
                        Save
                    </Button>
                    <Button key="2" icon={<SaveOutlined />} onClick={handleSave}>
                        Save and Continue Edit
                    </Button>
                </div>
            </div>
            <Form form={form} layout="vertical" style={{ marginTop: 24 }}>
                <Item
                    name="name"
                    label="Name"
                    rules={[{ required: true, message: 'Please input the group name!' }]}
                >
                    <Input />
                </Item>
                <Item
                    name="displayOrder"
                    label="Display order"
                    rules={[{ required: true, message: 'Please input the display order!' }]}
                    initialValue={0}
                >
                    <Input type="number" />
                </Item>
            </Form>
        </div>
    );
};

export default SpecificationAttributeGroupCreate;
