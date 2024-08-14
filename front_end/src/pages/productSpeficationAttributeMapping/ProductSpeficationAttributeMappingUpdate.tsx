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

    const {
        attributeType,
        attributeOptions,
        attributes,
        handleAttributeChange,
        handleAttributeTypeChange,
        handleDelete,
        handleSave,
        isLoadingMapping,
        handleReload,
        isSpinning,
    } = useProductSpecificationAttributeMappingUpdateViewModel(form)

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Spin spinning={isLoadingMapping || isSpinning}>
                <Form form={form} layout='vertical'>
                    <Title level={3}>Edit Product Specification Attribute Mapping</Title>

                    {attributeType !== 'CustomText' && (
                        <Form.Item
                            name='attribute'
                            label='Attribute'
                            rules={[{ required: true, message: 'Please select an attribute' }]}
                        >
                            <Select disabled onChange={handleAttributeChange}>
                                {attributes.map((attr) => (
                                    <Option key={attr.id} value={attr.id}>
                                        {attr.name}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                    )}

                    <Form.Item
                        name='attributeType'
                        label='Attribute Type'
                        rules={[{ required: true, message: 'Please select an attribute type' }]}
                    >
                        <Select disabled onChange={handleAttributeTypeChange}>
                            <Option value='Option'>Option</Option>
                            <Option value='CustomText'>Custom Text</Option>
                        </Select>
                    </Form.Item>

                    {attributeType === 'Option' && (
                        <Form.Item
                            name='attributeOption'
                            label='Attribute Option'
                            rules={[{ required: true, message: 'Please select an attribute option' }]}
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
                        <>
                            <Form.Item
                                name='customText'
                                label='Custom Text'
                                rules={[{ required: true, message: 'Please enter custom text' }]}
                            >
                                <Input.TextArea />
                            </Form.Item>
                            <Form.Item
                                name='specificationAttributeName'
                                label='Specification Attribute Name'
                                rules={[{ required: true, message: 'Please enter the specification attribute name' }]}
                            >
                                <Input disabled />
                            </Form.Item>
                        </>
                    )}

                    <Form.Item name='showOnProductPage' valuePropName='checked'>
                        <Checkbox>Show on product page</Checkbox>
                    </Form.Item>

                    <Form.Item
                        name='displayOrder'
                        label='Display Order'
                        rules={[{ required: true, message: 'Please enter a display order' }]}
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
                        <Button
                            type='primary'
                            onClick={() => handleSave(() => form.resetFields())}
                            style={{ marginLeft: '10px' }}
                        >
                            Save and Continue Edit
                        </Button>
                        <Button
                            type='default'
                            onClick={() =>
                                navigate(`/admin/products/product-spec-attribute-mapping/productId/${productId}`)
                            }
                            style={{ marginLeft: '10px' }}
                        >
                            Cancel
                        </Button>
                        <Button
                            type='default'
                            onClick={handleReload}
                            icon={<ReloadOutlined />}
                            style={{ marginLeft: '10px' }}
                        >
                            Reload Attributes
                        </Button>
                        <Button
                            type='default'
                            danger
                            icon={<DeleteOutlined />}
                            onClick={handleDelete}
                            style={{ marginLeft: '10px' }}
                        >
                            Delete
                        </Button>
                    </Form.Item>
                </Form>
            </Spin>
        </div>
    )
}

export default ProductSpecificationAttributeMappingUpdate
