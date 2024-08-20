import { Table, Button, Pagination, Spin, Collapse } from 'antd'
import { EditOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductUpdateSpecificationAttributeMappingViewModel from '@/pages/product/ProductUpdateSpecificationAttributeMapping.vm.ts'

const { Panel } = Collapse

const ProductUpdateSpecificationAttributeMapping = () => {
    const { handleReload, tableData, productId, isSpinning } = useProductUpdateSpecificationAttributeMappingViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Collapse defaultActiveKey={['1']} expandIconPosition='left'>
                <Panel header='Product Specification Attributes' key='1'>
                    <Table dataSource={tableData} pagination={false} bordered>
                        <Table.Column title='Attribute' dataIndex='attributeName' key='attributeName' />
                        <Table.Column title='Value' dataIndex='optionName' key='optionName' />
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
                                <Link
                                    to={`/admin/products/product-spec-attribute-mapping/edit/${productId}/${record.key}`}
                                >
                                    <Button type='primary' icon={<EditOutlined />}>
                                        Edit
                                    </Button>
                                </Link>
                            )}
                        />
                    </Table>

                    <Pagination defaultCurrent={1} total={tableData.length} style={{ marginTop: '20px' }} />
                    <Link to={`/admin/product/product-spec-attribute/add/${productId}`}>
                        <Button type='primary' style={{ marginTop: '20px' }}>
                            + Add attribute
                        </Button>
                    </Link>
                    <Button style={{ margin: 10 }} onClick={handleReload} icon={<ReloadOutlined />}></Button>
                </Panel>
            </Collapse>

            {isSpinning && (
                <div className='flex justify-center items-center h-full w-full fixed top-0 left-0 bg-[#fff] bg-opacity-50 z-10'>
                    <Spin size='large' />
                </div>
            )}
        </div>
    )
}

export default ProductUpdateSpecificationAttributeMapping
