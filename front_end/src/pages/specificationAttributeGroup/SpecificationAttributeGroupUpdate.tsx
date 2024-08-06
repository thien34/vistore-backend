import { Button, Form, Input, InputNumber } from 'antd'
import useSpecificationAttributeGroupUpdateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupUpdate.vm.ts'

function SpecificationAttributeGroupUpdate() {
    const { setIsSaveAndContinue, isLoading, form, onFinish, onFinishFailed, handleDelete } =
        useSpecificationAttributeGroupUpdateViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Form
                form={form}
                layout='vertical'
                name='edit_specification'
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
            >
                <Form.Item label='Name' name='name' rules={[{ required: true, message: 'Please input the name!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    rules={[{ required: true, message: 'Please input the display order!' }]}
                >
                    <InputNumber min={0} />
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
