import { Form, Input, Button, Select } from 'antd';

export default function ProductAttributeCreate() {
    const [form] = Form.useForm()

    const handleFinish = (values: any) => {
        mutate(values, {
            onSuccess: () => {
                history.push('/admin/product-attributes');
            },
        });
    };

    return (
        <div className='p-6 bg-white rounded-lg shadow-md'>
            <h2 className='text-2xl mb-4'>Create Product Attribute</h2>
            <Form form={form} layout='vertical' onFinish={handleFinish}>
                <Form.Item name='name' label='Name' rules={[{ required: true, message: 'Please enter the name' }]}>
                    <Input />
                </Form.Item>
                <Form.Item
                    name='published'
                    label='Published'
                    rules={[{ required: true, message: 'Please select published status' }]}
                >
                    <Select options={[{ value: true, label: 'Yes' }, { value: false, label: 'No' }]} />
                </Form.Item>
                <Form.Item
                    name='displayOrder'
                    label='Display Order'
                    rules={[{ required: true, message: 'Please enter the display order' }]}
                >
                    <Input type='number' />
                </Form.Item>
                <Form.Item>
                    <Button type='primary' htmlType='submit' loading={isLoading}>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}
