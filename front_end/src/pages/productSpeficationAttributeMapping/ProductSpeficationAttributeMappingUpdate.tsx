import { Form, Input, Select, Checkbox, Button, Typography, Spin } from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
import { Editor } from '@tinymce/tinymce-react'
import { DeleteOutlined, ReloadOutlined } from '@ant-design/icons'
import useProductSpecificationAttributeMappingUpdateViewModel from '@/pages/productSpeficationAttributeMapping/ProductSpeficationAttributeMappingUpdate.vm.ts'

const { Title } = Typography
const { Option } = Select

const ProductSpecificationAttributeMappingUpdate = () => {
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const { productId } = useParams<{ productId: string }>()

    const viewModel = useProductSpecificationAttributeMappingUpdateViewModel(form)

    const {
        attributeType,
        customHtml,
        setCustomHtml,
        attributeOptions,
        attributes,
        isLoadingAttributes,
        errorAttributes,
        isLoadingMapping,
        errorMapping,
        handleAttributeChange,
        handleAttributeTypeChange,
        handleDelete,
        handleSave,
        handleReload,
        isSpinning,
    } = viewModel

    if (isLoadingAttributes || isLoadingMapping) return <p>Loading...</p>
    if (errorAttributes) return <p>Error fetching attributes: {errorAttributes.message}</p>
    if (errorMapping) return <p>Error fetching mapping: {errorMapping.message}</p>

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Title level={4}>Update Product Specification Attribute Mapping</Title>
            <Form
                form={form}
                layout='vertical'
                initialValues={{
                    attributeType: 'Option',
                    showOnProductPage: true,
                    displayOrder: 0,
                    productId,
                }}
                style={{ marginTop: '16px' }}
            >
                <Form.Item name='productId' noStyle>
                    <Input type='hidden' value={productId} />
                </Form.Item>
                <Form.Item
                    label='Attribute type'
                    name='attributeType'
                    tooltip='Select the attribute type'
                    rules={[{ required: true, message: 'Please select an attribute type!' }]}
                >
                    <Select disabled onChange={handleAttributeTypeChange} value={attributeType}>
                        <Option value='Option'>Option</Option>
                        <Option value='CustomText'>Custom Text</Option>
                        <Option value='CustomHtml'>Custom HTML Text</Option>
                        <Option value='HyperLink'>HyperLink</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label='Attribute'
                    name='attribute'
                    tooltip='Select the attribute'
                    rules={[{ required: true, message: 'Please select an attribute!' }]}
                >
                    <Select disabled onChange={handleAttributeChange}>
                        {attributes.map((attr) => (
                            <Option key={attr.id} value={attr.id}>
                                {attr.name}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                {attributeType === 'Option' && (
                    <Form.Item
                        label='Attribute option'
                        name='attributeOption'
                        tooltip='Select the attribute option'
                        rules={[{ required: true, message: 'Please select an attribute option!' }]}
                    >
                        <Select>
                            {attributeOptions.map((option) => (
                                <Option key={option.id} value={option.id}>
                                    {option.name}
                                </Option>
                            ))}
                        </Select>
                    </Form.Item>
                )}

                {attributeType === 'CustomText' && (
                    <Form.Item
                        label='Custom Text'
                        name='customText'
                        tooltip='Enter custom text'
                        rules={[{ required: true, message: 'Please enter custom text!' }]}
                    >
                        <Input.TextArea rows={4} />
                    </Form.Item>
                )}

                {attributeType === 'CustomHtml' && (
                    <Form.Item
                        label='Custom HTML Text'
                        name='customHtml'
                        tooltip='Enter custom HTML text'
                        rules={[{ required: true, message: 'Please enter custom HTML text!' }]}
                    >
                        <Editor
                            apiKey='bkjd3qy86up5pl6yvop38mueuvzz7gqxa85mnjox5oejos5g'
                            value={customHtml}
                            init={{
                                plugins: 'link image code',
                                toolbar: 'undo redo | link image | code',
                            }}
                            onEditorChange={(content) => setCustomHtml(content)}
                        />
                    </Form.Item>
                )}

                {attributeType === 'HyperLink' && (
                    <Form.Item
                        label='HyperLink URL'
                        name='hyperlink'
                        tooltip='Enter hyperlink URL'
                        rules={[{ required: true, message: 'Please enter a hyperlink URL!' }]}
                    >
                        <Input />
                    </Form.Item>
                )}
                <Form.Item name='showOnProductPage' valuePropName='checked'>
                    <Checkbox>Show on product page</Checkbox>
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    tooltip='Set the display order'
                    rules={[{ required: true, message: 'Please enter the display order!' }]}
                >
                    <Input type='number' />
                </Form.Item>
                <Form.Item>
                    <Button
                        type='primary'
                        onClick={() =>
                            handleSave(() =>
                                navigate(`/admin/products/product-spec-attribute-mapping/productId/${productId}`),
                            )
                        }
                    >
                        Save
                    </Button>
                    <Button type='default' style={{ margin: '10px' }} onClick={() => handleSave(() => {})}>
                        Save and Continue Edit
                    </Button>
                    <Button danger icon={<DeleteOutlined />} onClick={handleDelete}>
                        Delete
                    </Button>
                    <Button style={{ margin: 10 }} onClick={handleReload} icon={<ReloadOutlined />} />
                </Form.Item>
            </Form>
            {isSpinning && (
                <div className='flex justify-center items-center h-full w-full fixed top-0 left-0 bg-[#fff] bg-opacity-50 z-10'>
                    <Spin size='large' />
                </div>
            )}
        </div>
    )
}

export default ProductSpecificationAttributeMappingUpdate
