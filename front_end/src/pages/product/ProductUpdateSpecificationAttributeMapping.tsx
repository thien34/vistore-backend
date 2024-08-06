import React from 'react'
import { Table, Button, Pagination, Typography, Divider, Spin } from 'antd'
import { EditOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductUpdateSpecificationAttributeMappingViewModel from '@/pages/product/ProductUpdateSpecificationAttributeMapping.vm.ts'

const { Paragraph } = Typography
const { Title } = Typography

const ProductUpdateSpecificationAttributeMapping = () => {
    const { handleReload, tableData, productId, isSpinning } = useProductUpdateSpecificationAttributeMappingViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Title level={4}>Specification attributes</Title>
            <div style={{ margin: 20, padding: 20, backgroundColor: '#f5f5f5', borderRadius: 8 }}>
                <Divider orientation='left'>Product Specification Attribute</Divider>
                <Paragraph style={{ fontSize: 16, lineHeight: 1.5, color: '#333' }}>
                    Specification attributes are product features i.e, screen size, number of USB-ports, visible on
                    product details page. Specification attributes can be used for filtering products on the category
                    details page. Unlike product attributes, specification attributes are used for information purposes
                    only. You can add attribute for your product using existing list of attributes, or if you need to
                    create a new one go to Catalog {'>'} Attributes {'>'} Specification attributes.
                </Paragraph>
            </div>
            <Table dataSource={tableData} pagination={false} bordered>
                <Table.Column title='Attribute' dataIndex='attributeName' key='attributeName' />
                <Table.Column title='Value' dataIndex='optionName' key='optionName' />
                <Table.Column title='Attribute Type' dataIndex='attributeType' key='attributeType' />
                <Table.Column
                    title='Show on product page'
                    dataIndex='showOnProductPage'
                    key='showOnProductPage'
                    render={(showOnProductPage) => (showOnProductPage ? '✔' : '✘')}
                />
                <Table.Column title='Display order' dataIndex='displayOrder' key='displayOrder' />
                <Table.Column
                    title='Edit'
                    key='edit'
                    render={(record) => (
                        <Link to={`/admin/products/product-spec-attribute-mapping/edit/${productId}/${record.key}`}>
                            <Button type='primary' icon={<EditOutlined />}>
                                Edit
                            </Button>
                        </Link>
                    )}
                />
            </Table>
            {isSpinning && (
                <div className='flex justify-center items-center h-full w-full fixed top-0 left-0 bg-[#fff] bg-opacity-50 z-10'>
                    <Spin size='large' />
                </div>
            )}
            <Pagination defaultCurrent={1} total={tableData.length} style={{ marginTop: '20px' }} />
            <Link to={`/admin/product/product-spec-attribute/add/${productId}`}>
                <Button type='primary' style={{ marginTop: '20px' }}>
                    + Add attribute
                </Button>
            </Link>
            <Button style={{ margin: 10 }} onClick={handleReload} icon={<ReloadOutlined />}></Button>
        </div>
    )
}

export default ProductUpdateSpecificationAttributeMapping
