import { useCategories, useCategory, useCreateCategory, useUpdateCategory } from '@/admin/hooks/category.hook'
import { useCreatePictures } from '@/admin/hooks/picture.hook'
import { CategoriesResponse, CategoryParentResponse, CategoryRequest } from '@/admin/types/Category'
import { PlusOutlined } from '@ant-design/icons'
import {
    Button,
    Card,
    Checkbox,
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
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
}
const validateMessages = {
    required: '${label} is required!',
    number: {
        range: '${label} must be between ${min} and ${max}',
    },
}

const getBase64 = (file: FileType): Promise<string> =>
    new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result as string)
        reader.onerror = (error) => reject(new Error(error.total.toString()))
    })

function getCategoryFullName(category: CategoriesResponse | CategoryParentResponse): string {
    return category.categoryParent
        ? `${getCategoryFullName(category.categoryParent)} >> ${category.name}`
        : category.name
}

type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]

export default function CategoryCreateUpdate() {
    const { id } = useParams<{ id: string }>()
    const isUpdateMode = Boolean(id)
    const [form] = Form.useForm()
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const { mutate: createCategory, isPending } = useCreateCategory()
    const { mutate: updateCategory } = useUpdateCategory()
    const categoryResponse = useCategory(Number(id))
    const navigation = useNavigate()
    const { mutateAsync: createPictures } = useCreatePictures()
    const { data } = useCategories({
        name: '',
        pageNo: 1,
        pageSize: 6,
        published: undefined,
    })

    useEffect(() => {
        if (isUpdateMode && categoryResponse.data) {
            form.setFieldsValue({
                ...categoryResponse.data,
            })
        }
    }, [isUpdateMode, categoryResponse, form])

    const onFinish = async (values: CategoryRequest) => {
        if (fileList.length) {
            try {
                const result = await createPictures({ images: [fileList[0].originFileObj as File] })
                values.pictureId = result.data[0]
            } catch (error) {
                console.error(error)
            }
        }
        if (isUpdateMode) {
            updateCategory({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
        } else {
            createCategory(values, { onSuccess: () => navigation(-1) })
        }
        console.log('Received values:', values)
    }

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj as FileType)
        }
        setPreviewImage(file.url ?? (file.preview as string))
        setPreviewOpen(true)
    }

    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        setFileList(newFileList)
    }

    const uploadButton = (
        <button style={{ border: 0, background: 'none' }} type='button'>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </button>
    )

    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6'>
            <Form
                {...layout}
                name='nest-messages'
                form={form}
                onFinish={onFinish}
                validateMessages={validateMessages}
                initialValues={categoryResponse.data}
            >
                <Row gutter={[24, 8]}>
                    <Col span={12}>
                        <Card className='min-h-full' size='small' title='Category info'>
                            <Form.Item name='name' label='Name' rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name='description' label='Description'>
                                <Input.TextArea />
                            </Form.Item>
                            <Form.Item name='categoryParentId' label='Parent category'>
                                <Select
                                    showSearch
                                    placeholder='[none]'
                                    filterOption={(input, option) =>
                                        (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
                                    }
                                    options={[
                                        { label: '[None]', value: null },
                                        ...(data?.data.items.map((item) => ({
                                            label: getCategoryFullName(item),
                                            value: item.id,
                                        })) ?? []),
                                    ]}
                                />
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
                            <Form.Item name='showOnHomePage' label='Show on home page' valuePropName='checked'>
                                <Checkbox />
                            </Form.Item>
                            <Form.Item name='includeInTopMenu' label='Include in top menu' valuePropName='checked'>
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
