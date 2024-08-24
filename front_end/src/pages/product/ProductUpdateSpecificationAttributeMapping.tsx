import { Table, Button, Spin, Collapse } from 'antd'
import { ReloadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductAttributeMappingViewModel from './ProductUpdateSpecificationAttributeMapping.vm'
const { Panel } = Collapse

export default function ProductUpdateSpecificationAttributeMapping() {
    const { listResponse, isLoading, handleReload, filter, handleTableChange, columns, isSpinning } =
        useProductAttributeMappingViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Collapse defaultActiveKey={['1']} expandIconPosition='left'>
                <Panel header='Product Specification Attributes' key='1'>
                    <Table
                        dataSource={listResponse?.items || []}
                        columns={columns}
                        pagination={{
                            current: filter.pageNo ?? 1,
                            pageSize: filter.pageSize ?? 6,
                            total: listResponse?.totalPages * (filter.pageSize ?? 6) || 0, // Sử dụng giá trị mặc định nếu listResponse hoặc totalPages không tồn tại
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                        }}
                        loading={isLoading}
                        bordered
                    />

                    <Link to={`/admin/product/product-spec-attribute/add/${filter.productId}`}>
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
