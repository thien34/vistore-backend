import { Table } from 'antd'
import ModalAddUpdate from './ModalUpdate'
import useProductTagCreateViewModel from './ProductTag.vm'
import { ProductTagSearch } from './ProductTagSearch'

export default function ProductTagManage() {
    const {
        selectedRowKeys,
        handleSearch,
        handleCreate,
        handleDelete,
        rowSelection,
        isModalOpen,
        selectedTag,
        setIsModalOpen,
        columns,
        filter,
        listResponse,
        handleTableChange,
    } = useProductTagCreateViewModel()

    return (
        <div>
            <ProductTagSearch selectedRowKeys={selectedRowKeys} onSearch={handleSearch} handleDelete={handleDelete} />
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        rowSelection={rowSelection}
                        columns={columns}
                        dataSource={listResponse.items}
                        pagination={{
                            current: (filter.pageNo ?? 0) + 1,
                            pageSize: filter.pageSize ?? 6,
                            total: listResponse.totalPages * (filter.pageSize ?? 6),
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize }),
                        }}
                    />
                )}
            </div>
            <ModalAddUpdate
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                handleCreate={handleCreate}
                selectedTag={selectedTag}
            />
        </div>
    )
}
