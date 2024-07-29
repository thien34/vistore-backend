import React, { useEffect, useState } from 'react';
import { Button, Form, Input, Select, Spin, Modal, message } from 'antd';
import { SaveOutlined } from '@ant-design/icons';
import useCreateApi from '@/hooks/use-create-api';
import { useNavigate } from 'react-router-dom';
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts';
import useGetAllApi from '@/hooks/use-get-all-api.ts';
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts';

const { Item } = Form;
const { Option } = Select;

export default function SpecificationAttributeCreate() {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const [groups, setGroups] = useState([]);
    const [loadingGroups, setLoadingGroups] = useState(true);
    const [shouldRedirect, setShouldRedirect] = useState(false);
    const { mutate, isLoading: creating } = useCreateApi(SpecificationAttributeConfigs.resourceUrl);

    // Fetch groups data
    const { data, error } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
    );

    useEffect(() => {
        if (data) {
            setGroups(data.items);
            setLoadingGroups(false);
        }
        if (error) {
            message.error(`Error loading groups: ${error.message}`);
            setLoadingGroups(false);
        }
    }, [data, error]);

    // Form submit handler
    const handleSubmit = (values) => {
        const requestBody = {
            name: values.name,
            specificationAttributeGroupId: values.group,
            displayOrder: Number(values.displayOrder),
        };

        mutate(requestBody, {
            onSuccess: () => {
                message.success('Created successfully');
                if (shouldRedirect) {
                    navigate('/admin/specification-attributes');
                } else {
                    form.resetFields();
                }
            },
            onError: (error) => {
                message.error(`Error creating attribute: ${error.message}`);
            },
        });
    };

    const showSaveConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Please confirm if you want to save the changes.',
            onOk: () => {
                setShouldRedirect(true);
                form.submit();
            },
        });
    };

    return (
        <div style={{ padding: 24 }}>
            {/* Buttons */}
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 24 }}>
                <Button
                    type='primary'
                    icon={<SaveOutlined />}
                    onClick={showSaveConfirm} // Show confirmation modal before saving
                    loading={creating}
                    style={{ marginRight: 8 }}
                >
                    Save
                </Button>
                <Button
                    icon={<SaveOutlined />}
                    onClick={() => {
                        setShouldRedirect(false);
                        form.submit();
                    }} // Submit form and show success message without redirect
                    loading={creating}
                >
                    Save and Continue Edit
                </Button>
            </div>
            {/* Form */}
            <h2>Attribute Info</h2>
            <Form
                form={form}
                layout='vertical'
                style={{ marginTop: 24 }}
                onFinish={handleSubmit} // Save without redirect
                onFinishFailed={() => {
                    message.error('Please fill in the required fields!');
                }}
            >
                <Item
                    name='name'
                    label='Name'
                    rules={[{ required: true, message: 'Please input the attribute name!' }]}
                >
                    <Input />
                </Item>
                <Item name='group' label='Group'>
                    {loadingGroups ? (
                        <Spin />
                    ) : (
                        <Select placeholder='Select a group'>
                            {groups.map((group) => (
                                <Option key={group.id} value={group.id}>
                                    {group.name}
                                </Option>
                            ))}
                        </Select>
                    )}
                </Item>
                <Item name='displayOrder' label='Display Order' initialValue={0}>
                    <Input type='number' />
                </Item>
            </Form>
        </div>
    );
}
