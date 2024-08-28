import useStockQuantityHistoryViewModel from './StockQuantity.vm'
import { Table } from 'antd'
import StockQuantityHistorySearch from './StockQuantityHistorySearch'
export default function StockQuantityHistoryManage() {
    const { columns, filter, handleTableChange, handleSearch, listResponse, isLoading } =
        useStockQuantityHistoryViewModel()

    return (
        <>
            <StockQuantityHistorySearch onSearch={handleSearch} />
            {isLoading && <p>Loading ...</p>}
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
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
