import { Table } from 'antd'
import CategorySearch from '@/pages/category/CategorySearch'
import useCategoryViewModel from './Category.vm'

export default function CategoryManage() {
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
    } = useCategoryViewModel()

    return (
        <>
            <CategorySearch onSearch={handleSearch} selectedRowKeys={selectedRowKeys} handleDelete={handleDelete} />
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
