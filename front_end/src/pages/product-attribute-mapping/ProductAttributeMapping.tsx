import { Button, Table } from 'antd'
import useProductAttributeMappingViewModel from './ProductAttributeMapping.vm'
import { Link } from 'react-router-dom'
import AppActions from '@/constants/AppActions '
import ManagerPath from '@/constants/ManagerPath '

export default function ProductAttributeMapping() {
    const { listResponse, filter, handleTableChange, columns } = useProductAttributeMappingViewModel()

    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6'>
            {listResponse && (
                <Table
                    rowKey='id'
                    bordered
                    columns={columns}
                    dataSource={listResponse.items}
                    pagination={{
                        current: filter.pageNo ?? 1,
                        pageSize: filter.pageSize ?? 6,
                        total: listResponse.totalPages * (filter.pageSize ?? 6),
                        onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                    }}
                />
            )}
            <div className='mt-5'>
                <Link to={`${ManagerPath.PRODUCT_ATTRIBUTE_MAPPING_ADD}?product-id=${filter.productId}`}>
                    <Button className='bg-[#475569] text-white' size='large'>
                        {AppActions.ADD}
                    </Button>
                </Link>
            </div>
        </div>
    )
}
