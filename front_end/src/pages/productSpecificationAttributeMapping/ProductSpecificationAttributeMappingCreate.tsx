import { Form, Input, Select, Checkbox, Button, Typography, InputNumber } from 'antd'
import { useNavigate, useSearchParams } from 'react-router-dom'
import useProductSpecificationAttributeMappingCreateViewModel from '@/pages/productSpecificationAttributeMapping/ProductSpecificationAttributeMappingCreate.vm'

const { Title } = Typography
const { Option, OptGroup } = Select

const ProductSpecificationAttributeMappingCreate = () => {
    const [form] = Form.useForm()
    const params = useSearchParams()
    const productId = new URLSearchParams(params[0]).get('productId')
    const navigate = useNavigate()

    const viewModel = useProductSpecificationAttributeMappingCreateViewModel(form, productId, navigate)

    const {
        attributeType,
        setAttributeType,
        selectedAttributeId,
        attributeOptions,
        groupedAttributes,
        isLoading,
        handleAttributeChange,
        handleSave,
    } = viewModel

    if (isLoading) return <p>Loading...</p>

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Title level={4}>Add a new product specification attribute</Title>
            <Form
                form={form}
                layout='vertical'
                initialValues={{
                    attributeType: 'Option',
                    attribute: selectedAttributeId,
                    attributeOption: attributeOptions.length > 0 ? attributeOptions[0]?.id : undefined,
                    showOnProductPage: true,
                    displayOrder: 0,
                    productId,
                }}
                style={{ marginTop: '16px' }}
            >
                <Form.Item name='productId' noStyle>
                    <Input type='hidden' value={Number(productId ?? 0)} />
                </Form.Item>
                <Form.Item
                    label='Attribute type'
                    name='attributeType'
                    rules={[{ required: true, message: 'Please select an attribute type!' }]}
                >
                    <Select onChange={(value) => setAttributeType(value)}>
                        <Option value='Option'>Option</Option>
                        <Option value='CustomText'>Custom Text</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    label='Attribute'
                    name='attribute'
                    rules={[{ required: true, message: 'Please select an attribute!' }]}
                >
                    <Select onChange={handleAttributeChange} dropdownStyle={{ maxHeight: 300, overflow: 'auto' }}>
                        {groupedAttributes &&
                            Array.from(groupedAttributes.entries()).map(([groupName, attrs]) => (
                                <OptGroup key={groupName} label={groupName}>
                                    {attrs.map((attr) => (
                                        <Option key={attr.id} value={attr.id}>
                                            {attr.name}
                                        </Option>
                                    ))}
                                </OptGroup>
                            ))}
                        {(!groupedAttributes || groupedAttributes.size === 0) && (
                            <Option value=''>No attributes available</Option>
                        )}
                    </Select>
                </Form.Item>
                {attributeType === 'Option' && (
                    <Form.Item
                        label='Attribute option'
                        name='attributeOption'
                        rules={[{ required: true, message: 'Please select an attribute option!' }]}
                    >
                        <Select dropdownStyle={{ maxHeight: 300, overflow: 'auto' }}>
                            {attributeOptions.length > 0 ? (
                                attributeOptions.map((option) => (
                                    <Option key={option.id} value={option.id}>
                                        {option.name}
                                    </Option>
                                ))
                            ) : (
                                <Option value=''>No options available</Option>
                            )}
                        </Select>
                    </Form.Item>
                )}

                {attributeType === 'CustomText' && (
                    <Form.Item
                        label='Custom Text'
                        name='customText'
                        tooltip='Enter custom text'
                        rules={[
                            { required: true, message: 'Please enter custom text!' },
                            { max: 500, message: 'Custom text cannot exceed 500 characters!' },
                        ]}
                    >
                        <Input.TextArea
                            rows={4}
                            maxLength={500}
                            style={{
                                width: '100%',
                                boxSizing: 'border-box',
                                wordBreak: 'break-word',
                                overflow: 'hidden',
                                whiteSpace: 'pre-wrap',
                            }}
                        />
                    </Form.Item>
                )}
                <Form.Item name='showOnProductPage' valuePropName='checked'>
                    <Checkbox>Show on product page</Checkbox>
                </Form.Item>
                <Form.Item
                    label='Display order'
                    name='displayOrder'
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
                    <InputNumber defaultValue={0} type='number' />
                </Form.Item>

                <Form.Item>
                    <Button type='primary' onClick={handleSave}>
                        Save
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default ProductSpecificationAttributeMappingCreate
