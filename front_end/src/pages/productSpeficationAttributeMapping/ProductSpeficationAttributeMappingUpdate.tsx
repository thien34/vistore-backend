import { Form, Input, Select, Checkbox, Button, Typography, Spin } from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
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

    console.log('Current form values:', form.getFieldsValue())

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
                    <Select onChange={handleAttributeTypeChange} value={attributeType}>
                        <Option value='Option'>Option</Option>
                        <Option value='CustomText'>Custom Text</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label='Attribute'
                    name='attribute'
                    tooltip='Select the attribute'
                    rules={[{ required: true, message: 'Please select an attribute!' }]}
                >
                    <Select onChange={handleAttributeChange} value={form.getFieldValue('attribute')}>
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
                        rules={[
                            { required: attributeType === 'Option', message: 'Please select an attribute option!' },
                        ]}
                    >
                        <Select disabled={attributeType !== 'Option'}>
                            {attributeOptions.map((option) => (
                                <Option key={option.id} value={option.id}>
                                    <div dangerouslySetInnerHTML={{ __html: option.name }} />
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
                        rules={[{ required: attributeType === 'CustomText', message: 'Please enter custom text!' }]}
                    >
                        <Input.TextArea
                            rows={4}
                            value={attributeType === 'CustomText' ? form.getFieldValue('customText') : ''}
                        />
                    </Form.Item>
                )}

                <Form.Item name='showOnProductPage' valuePropName='checked'>
                    <Checkbox disabled={attributeType !== 'CustomText' && attributeType !== 'Option'}>
                        Show on product page
                    </Checkbox>
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
