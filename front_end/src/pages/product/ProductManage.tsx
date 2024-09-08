import { Table } from 'antd'
import useProductManageViewModel from './ProductManage.vm'
import ProductActionManage from './ProductActionManage'

export default function ProductManage() {
    const { dataSource, handleRowClick, columns, rowSelection } = useProductManageViewModel()

    return (
        <>
            <ProductActionManage />
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <Table
                    rowKey='id'
                    bordered
                    rowSelection={rowSelection}
                    dataSource={dataSource}
                    columns={columns}
                    pagination={{ pageSize: 6 }}
                    onRow={(record) => {
                        return {
                            onClick: () => handleRowClick(record),
                        }
                    }}
                ></Table>
            </div>
        </>
    )
}
