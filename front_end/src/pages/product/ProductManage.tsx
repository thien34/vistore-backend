import { Table } from 'antd'
import useProductManageViewModel from './ProductManage.vm'
import ProductFilter from './ProductFilter'

export default function ProductManage() {
    const { dataSource, columns, rowSelection, handleFilterChange } = useProductManageViewModel()

    return (
        <>
            <ProductFilter onFilterChange={handleFilterChange} />
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <Table
                    rowKey='id'
                    bordered
                    rowSelection={rowSelection}
                    dataSource={dataSource}
                    columns={columns}
                    pagination={{ pageSize: 6 }}
                />
            </div>
        </>
    )
}
