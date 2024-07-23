import useManufactureViewModel from "./Manufacture.vm"
import ManufactureSearch from "./ManufactureSearch"
import { Table } from 'antd'

export default function ManufactureManage() {
    const {
        rowSelection,
        columns,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    } = useManufactureViewModel()

    return (
        <>
            <ManufactureSearch search ={handleSearch} selectedRows={selectedRowKeys} handleDelete={handleDelete} />
            {isLoading && <p>Loading ...</p>}
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        rowSelection={rowSelection}
                        columns={columns}
                        dataSource={listResponse.items}
                        pagination={{
                            current: filter.page ?? 1,
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
