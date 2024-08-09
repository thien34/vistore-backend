import { Form, Input, Select, Checkbox, Button, Typography, Spin } from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
import { Editor } from '@tinymce/tinymce-react'
import { ReloadOutlined } from '@ant-design/icons'
import useProductSpecificationAttributeMappingCreateViewModel from '@/pages/productSpeficationAttributeMapping/ProductSpeficationAttributeMappingCreate.vm.ts'
import React from 'react'

const { Title } = Typography
const { Option, OptGroup } = Select

const ProductSpecificationAttributeMappingCreate = () => {
    const [form] = Form.useForm()
    const { productId } = useParams<{ productId: string }>()
    const navigate = useNavigate()

    const viewModel = useProductSpecificationAttributeMappingCreateViewModel(form, productId, navigate)

    const {
        attributeType,
        setAttributeType,
        customHtml,
        setCustomHtml,
        selectedAttributeId,
        attributeOptions,
        groupedAttributes,
        isLoading,
        error,
        handleReload,
        handleAttributeChange,
        handleSave,
        handleSaveAndContinue,
        isSpinning,
    } = viewModel

    if (isLoading) return <p>Loading...</p>
    if (error) return <p>Error fetching attributes: {error.message}</p>

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Title level={4}>Add a new product specification attribute</Title>
            <a href='/product/details'>Back to product details</a>
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
                    <Input type='hidden' value={productId} />
                </Form.Item>
                <Form.Item
                    label='Attribute type'
                    name='attributeType'
                    tooltip='Select the attribute type'
                    rules={[{ required: true, message: 'Please select an attribute type!' }]}
                >
                    <Select onChange={(value) => setAttributeType(value)}>
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
                    <Select
                        onChange={handleAttributeChange}
                        dropdownStyle={{ maxHeight: 300, overflow: 'auto' }} // Thêm thuộc tính này để tạo thanh cuộn
                    >
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
                        {!groupedAttributes?.size && <Option value=''>No attributes available</Option>}
                    </Select>
                </Form.Item>

                {attributeType === 'Option' && (
                    <Form.Item
                        label='Attribute option'
                        name='attributeOption'
                        tooltip='Select the attribute option'
                        rules={[{ required: true, message: 'Please select an attribute option!' }]}
                    >
                        <Select
                            dropdownStyle={{ maxHeight: 300, overflow: 'auto' }} // Thêm thuộc tính này để tạo thanh cuộn
                        >
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
                    <Button type='primary' onClick={handleSave}>
                        Save
                    </Button>
                    <Button type='default' onClick={handleSaveAndContinue} style={{ marginLeft: '8px' }}>
                        Save and Continue Edit
                    </Button>
                    <Button style={{ margin: 10 }} onClick={handleReload} icon={<ReloadOutlined />}></Button>
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

export default ProductSpecificationAttributeMappingCreate
