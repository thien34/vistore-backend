import { Table, Button, Collapse, Tabs } from 'antd'
import { Link } from 'react-router-dom'
import useProductUpdateViewModel from '@/pages/product/ProductUpdate.vm'
import ProductAttributeMappingConfigs from '../product-attribute-mapping/ProductAttributeMappingConfigs'
import ProductAttributeMapping from '../product-attribute-mapping/ProductAttributeMapping'
import ProductAtbCombinationsManage from '../product-attribute-combinations'

const { Panel } = Collapse

export default function ProductUpdate() {
    const { listResponse, isLoading, filter, handleTableChange, columns } = useProductUpdateViewModel()

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
                <Collapse defaultActiveKey={['1']} className='mb-6'>
                    <Panel header='Product Specification Attributes' key='1'>
                        {listResponse && (
                            <Table
                                rowKey='id'
                                dataSource={listResponse?.items}
                                columns={columns}
                                bordered
                                pagination={{
                                    current: filter.pageNo ?? 1,
                                    pageSize: filter.pageSize ?? 6,
                                    total: listResponse.totalPages * (filter.pageSize ?? 6),
                                    onChange: (page, pageSize) =>
                                        handleTableChange({ current: page, pageSize: pageSize }),
                                }}
                                loading={isLoading}
                            />
                        )}

                        <Link to={`/admin/product/product-spec-attribute-add/?productId=${filter.productId}`}>
                            <Button type='primary'>+ Add attribute</Button>
                        </Link>
                    </Panel>
                </Collapse>
            </div>
        </>
    )
}
