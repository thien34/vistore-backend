import React from 'react';
import { Button, Form, Input, Modal, message } from 'antd';
import { SaveOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import useCreateApi from '@/hooks/use-create-api.ts';
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts';

const { Item } = Form;

const SpecificationAttributeGroupCreate = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate(); // Initialize useNavigate hook

    // Initialize the create API hook with the appropriate URL
    const { mutate: createSpecificationAttributeGroup, isLoading } = useCreateApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
    );

    const handleSave = (shouldRedirect: boolean) => {
        form.validateFields()
            .then((values) => {
                createSpecificationAttributeGroup(values, {
                    onSuccess: () => {
                        message.success('Specification attribute group created successfully');
                        form.resetFields();
                        if (shouldRedirect) {
                            navigate('/admin/specification-attribute-groups');
                        }
                    },
                    onError: (error) => {
                        message.error(`Failed to create specification attribute group: ${error.message}`);
                    },
                });
            })
            .catch((errorInfo) => {
                console.log('Validation Failed:', errorInfo);
            });
    };

    const showSaveConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Please confirm if you want to save the changes.',
            onOk: () => handleSave(true),
            onCancel() {
                console.log('Cancel');
            },
        });
    };

    return (
        <div style={{ padding: 24 }}>
            <div
                className='site-page-header'
                style={{ marginBottom: 24, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
            >
                <h1 style={{ margin: 0 }}>Add a new specification attribute group</h1>
                <div>
                    <Button
                        key='1'
                        type='primary'
                        icon={<SaveOutlined />}
                        onClick={showSaveConfirm} // Show confirmation modal before saving
                        loading={isLoading}
                        style={{ marginRight: 8 }}
                    >
                        Save
                    </Button>
                    <Button
                        key='2'
                        icon={<SaveOutlined />}
                        onClick={() => handleSave(false)} // Save and show success message without redirect
                        loading={isLoading}
                    >
                        Save and Continue Edit
                    </Button>
                </div>
            </div>
            <Form form={form} layout='vertical' style={{ marginTop: 24 }}>
                <Item name='name' label='Name' rules={[{ required: true, message: 'Please input the group name!' }]}>
                    <Input />
                </Item>
                <Item
                    name='displayOrder'
                    label='Display order'
                    initialValue={0}
                >
                    <Input type='number' />
                </Item>
            </Form>
        </div>
    );
};

export default SpecificationAttributeGroupCreate;
