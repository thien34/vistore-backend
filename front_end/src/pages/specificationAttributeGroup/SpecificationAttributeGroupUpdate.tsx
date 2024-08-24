import { Button, Form, Input, InputNumber } from 'antd'
import useSpecificationAttributeGroupUpdateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupUpdate.vm.ts'

function SpecificationAttributeGroupUpdate() {
    const { setIsSaveAndContinue, isLoading, form, onFinish, onFinishFailed, handleDelete } =
        useSpecificationAttributeGroupUpdateViewModel()

    return (
        <div
            className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'
            style={{ maxWidth: 900, margin: '0 auto', marginTop: 30 }}
        >
            <Form
                form={form}
                layout='vertical'
                name='edit_specification'
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                initialValues={{ displayOrder: 0 }}
            >
                <Form.Item
                    name='name'
                    label='Name'
                    tooltip='Set the name'
                    rules={[
                        { required: true, message: 'Please input the group name!' },
                        { max: 100, message: 'Name cannot exceed 100 characters!' },
                    ]}
                >
                    <Input maxLength={101} />
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    tooltip='Set the display order'
                    rules={[
                        { required: true, message: 'Please enter the display order!' },
                        {
                            type: 'number',
                            min: 0,
                            max: 2000000,
                            message: 'Display order must be between 0 and 2,000,000!',
                        },
                    ]}
                >
                    <InputNumber type='number' />
                </Form.Item>
                <div
                    style={{
                        display: 'flex',
                        justifyContent: 'flex-end',
                        alignItems: 'center',
                        marginTop: 16,
                    }}
                >
                    <Button
                        type='primary'
                        htmlType='submit'
                        loading={isLoading}
                        onClick={() => setIsSaveAndContinue(false)}
                    >
                        Save
                    </Button>
                    <Button
                        type='default'
                        htmlType='button'
                        style={{ marginLeft: 8 }}
                        onClick={() => {
                            setIsSaveAndContinue(true)
                            form.submit()
                        }}
                    >
                        Save and Continue Edit
                    </Button>
                    <Button type='primary' danger style={{ marginLeft: 8 }} onClick={handleDelete}>
                        Delete
                    </Button>
                </div>
            </Form>
        </div>
    )
}

export default SpecificationAttributeGroupUpdate
