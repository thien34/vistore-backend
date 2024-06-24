import { PlusOutlined } from '@ant-design/icons'
import {
    Button,
    Card,
    Checkbox,
    CheckboxProps,
    Col,
    Form,
    GetProp,
    Image,
    Input,
    InputNumber,
    Row,
    Select,
    Upload,
    UploadFile,
    UploadProps,
} from 'antd'
import { useState } from 'react'

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
}
const validateMessages = {
    required: '${label} is required!',
    types: {
        email: '${label} is not a valid email!',
        number: '${label} is not a valid number!',
    },
    number: {
        range: '${label} must be between ${min} and ${max}',
    },
}
const onFinish = (values: string) => {
    console.log(values)
}
const getBase64 = (file: FileType): Promise<string> =>
    new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result as string)
        reader.onerror = (error) => reject(error)
    })

const onChange: CheckboxProps['onChange'] = (e) => {
    console.log(`checked = ${e.target.checked}`)
}
type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
export default function CategoryCreateUpdate() {
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj as FileType)
        }

        setPreviewImage(file.url || (file.preview as string))
        setPreviewOpen(true)
    }

    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => setFileList(newFileList)

    const uploadButton = (
        <button style={{ border: 0, background: 'none' }} type='button'>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </button>
    )
    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
            <Form {...layout} name='nest-messages' onFinish={onFinish} validateMessages={validateMessages}>
                <Row gutter={[24, 8]}>
                    <Col span={12}>
                        <Card className='min-h-full' size='small' title='Category info'>
                            <Form.Item name={['category', 'name']} label='Name' rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name={['category', 'description']} label='Description'>
                                <Input.TextArea />
                            </Form.Item>
                            <Form.Item name={['category', 'parentCategoryId']} label='Parent category'>
                                <Select
                                    showSearch
                                    placeholder='[none]'
                                    filterOption={(input, option) =>
                                        (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
                                    }
                                    options={[
                                        { value: '1', label: 'Jack' },
                                        { value: '2', label: 'Lucy' },
                                        { value: '3', label: 'Tom' },
                                    ]}
                                />
                            </Form.Item>
                            <Form.Item name={['category', 'pictureId']} label='Picture'>
                                <Upload
                                    action='https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload'
                                    listType='picture-card'
                                    fileList={fileList}
                                    onPreview={handlePreview}
                                    onChange={handleChange}
                                >
                                    {fileList.length >= 8 ? null : uploadButton}
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
                            </Form.Item>
                        </Card>
                    </Col>
                    <Col span={12}>
                        <Card className='min-h-full' size='small' title='Display'>
                            <Form.Item name={['category', 'published']} label='Published'>
                                <Checkbox onChange={onChange} checked />
                            </Form.Item>
                            <Form.Item name={['category', 'showOnHomePage']} label='Show on home page'>
                                <Checkbox onChange={onChange} />
                            </Form.Item>
                            <Form.Item name={['category', 'includeInTopMenu']} label='Include in top menu'>
                                <Checkbox onChange={onChange} checked />
                            </Form.Item>
                            <Form.Item name={['category', 'pageSize']} label='Page size'>
                                <InputNumber defaultValue={6} />
                            </Form.Item>
                            <Form.Item name={['category', 'priceRangeFiltering']} label='Price range filtering'>
                                <Checkbox onChange={onChange} />
                            </Form.Item>
                            <Form.Item name={['category', 'displayOrder']} label='Display order'>
                                <InputNumber defaultValue={0} />
                            </Form.Item>
                        </Card>
                    </Col>
                </Row>

                <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                    <Button type='primary' htmlType='submit'>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}
