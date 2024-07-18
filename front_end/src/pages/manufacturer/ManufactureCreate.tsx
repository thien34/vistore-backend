import { PlusOutlined } from "@ant-design/icons"
import useManufactureCreateViewModel from "./ManufactureCreate.vm"
import { Button, Card, Checkbox, Col, Form, Image, Input, InputNumber, Row, Upload } from 'antd'

export default function ManufactureCreate() {
    const {
        form,
        layout,
        onFinish,
        isPending,
        fileList,
        handleChange,
        handlePreview,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
        initialManufacture,
    } = useManufactureCreateViewModel()

    const uploadButton = (
        <button style={{ border: 0, background: 'none' }} type='button'>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </button>
    )

    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6'>
            <Form {...layout} name='nest-messages' form={form} onFinish={onFinish} initialValues={initialManufacture}>
                <Row gutter={[24, 8]}>
                    <Col span={12}>
                        <Card className='min-h-full' size='small' title='Category info'>
                            <Form.Item   name='name' label='Name' rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name='description' label='Description'>
                                <Input.TextArea />
                            </Form.Item>
                            <Form.Item name='pictureId' label='Picture'>
                                <div>
                                    <Upload
                                        listType='picture-card'
                                        fileList={fileList}
                                        onPreview={handlePreview}
                                        onChange={handleChange}
                                    >
                                        {fileList.length >= 2 ? null : uploadButton}
                                    </Upload>
                                    {previewImage && (
                                        <Image
                                            wrapperStyle={{ display: 'none' }}
                                            preview={{
                                                visible: previewOpen,
                                                onVisibleChange: (visible) => setPreviewOpen(visible),
                                                afterOpenChange: (visible) => !visible && setPreviewImage(''),
                                            }}
                                            src={previewImage}
                                        />
                                    )}
                                </div>
                            </Form.Item>
                        </Card>
                    </Col>
                    <Col span={12}>
                        <Card className='min-h-full' size='small' title='Display'>
                            <Form.Item name='published' label='Published' valuePropName='checked'>
                                <Checkbox />
                            </Form.Item>
                            <Form.Item
                                name='pageSize'
                                label='Page size'
                                rules={[{ required: true, min: 1, type: 'number' }]}
                            >
                                <InputNumber />
                            </Form.Item>
                            <Form.Item name='priceRangeFiltering' label='Price range filtering' valuePropName='checked'>
                                <Checkbox />
                            </Form.Item>
                            <Form.Item
                                name='displayOrder'
                                label='Display order'
                                rules={[{ required: true, min: 0, type: 'number' }]}
                            >
                                <InputNumber />
                            </Form.Item>
                        </Card>
                    </Col>
                </Row>
                <Form.Item className='flex justify-end pt-5 items-center' wrapperCol={{ ...layout.wrapperCol }}>
                    <Button type='primary' htmlType='submit' size='large' loading={isPending}>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}