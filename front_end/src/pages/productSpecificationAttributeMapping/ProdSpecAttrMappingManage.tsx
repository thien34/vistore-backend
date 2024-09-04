import { Button, Collapse, Table } from 'antd'
import { Link } from 'react-router-dom'
import useProdSpecAttrMappingManage from './ProdSpecAttrMappingManage.vm'

export default function ProdSpecAttrMappingManage() {
    const { columns, listResponse, isLoading, filter, handleTableChange } = useProdSpecAttrMappingManage()

    return (
        <>
            <Collapse
                collapsible='header'
                defaultActiveKey={['1']}
                className='mb-6'
                items={[
                    {
                        key: '1',
                        label: 'Product Specification Attributes',
                        children: (
                            <>
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
                            </>
                        ),
                    },
                ]}
            ></Collapse>
        </>
    )
}
