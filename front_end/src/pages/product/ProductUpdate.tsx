import { Table, Button, Collapse, Tabs } from 'antd'
import { EditOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductUpdateViewModel from '@/pages/product/ProductUpdate.vm'
import ProductAttributeMappingConfigs from '../product-attribute-mapping/ProductAttributeMappingConfigs'
import ProductAttributeMapping from '../product-attribute-mapping/ProductAttributeMapping'
import ProductAtbCombinationsManage from '../product-attribute-combinations'

const { Panel } = Collapse

const ProductUpdate = () => {
    const { tableData, productId } = useProductUpdateViewModel()

    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <Collapse
                    collapsible='header'
                    defaultActiveKey={['1']}
                    className='mb-6'
                    items={[
                        {
                            key: '1',
                            label: ProductAttributeMappingConfigs.resourceKey,
                            children: (
                                <Tabs
                                    defaultActiveKey='1'
                                    type='card'
                                    size='middle'
                                    items={[
                                        {
                                            key: '1',
                                            label: 'Attributes',
                                            children: <ProductAttributeMapping />,
                                        },
                                        {
                                            key: '2',
                                            label: 'Attribute combinations',
                                            children: <ProductAtbCombinationsManage />,
                                        },
                                    ]}
                                />
                            ),
                        },
                    ]}
                />
                <Collapse defaultActiveKey={['2']} className='mb-6'>
                    <Panel header='Product Specification Attributes' key='1'>
                        <Table rowKey='id' bordered dataSource={tableData} pagination={false}>
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

                        <Link to={`/admin/product/product-spec-attribute/add/${productId}`}>
                            <Button type='primary' style={{ marginTop: '20px' }}>
                                + Add attribute
                            </Button>
                        </Link>
                        <Button style={{ margin: 10 }} icon={<ReloadOutlined />}></Button>
                    </Panel>
                </Collapse>
            </div>
        </>
    )
}

export default ProductUpdate
