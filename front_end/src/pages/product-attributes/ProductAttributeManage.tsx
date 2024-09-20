import { Table } from 'antd'
import ProductAttributeSearch from '@/pages/product-attributes/ProductAttributeSearch.tsx'
import useProductAttributeViewModel from './ProductAttribute.vm'

export default function ProductAttributeManage() {
    const {
        rowSelection,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        columns,
    } = useProductAttributeViewModel()

    return (
        <>
            <ProductAttributeSearch
                onSearch={handleSearch}
                selectedRowKeys={selectedRowKeys}
                handleDelete={handleDelete}
            />
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        rowSelection={rowSelection}
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
            </div>
        </>
    )
}
