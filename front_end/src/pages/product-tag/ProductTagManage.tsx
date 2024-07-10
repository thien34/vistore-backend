import { Empty, Table } from 'antd'
import ModalAddUpdate from './ModalAddUpdate'
import useProductTagCreateViewModel from './ProductTag.vm'
import { ProductTagSearch } from './ProductTagSearch'

export default function ProductTagManage() {
    const {
        selectedRowKeys,
        handleSearch,
        handleCreate,
        handleDelete,
        rowSelection,
        showModal,
        isModalOpen,
        selectedTag,
        title,
        setTitle,
        setIsModalOpen,
        columns,
        filter,
        listResponse,
        handleTableChange,
    } = useProductTagCreateViewModel()

    return (
        <div>
            <ProductTagSearch
                selectedRowKeys={selectedRowKeys}
                onSearch={handleSearch}
                showModal={showModal}
                handleDelete={handleDelete}
            />
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {listResponse?.items ? (
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
                ) : (
                    <Empty />
                )}
            </div>
            <ModalAddUpdate
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                handleCreate={handleCreate}
                selectedTag={selectedTag}
                title={title}
                setTitle={setTitle}
            />
        </div>
    )
}
