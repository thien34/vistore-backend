import { Collapse, Table } from "antd";
import useRelatedProductViewModel from "./RelatedProduct.vm";
import RelatedProductAction from "./RelatedProductActionBar";
import ModalUpdate from "./ModalUpdate";
import ModalAdd from "./ModalAdd";

const { Panel } = Collapse
export default function RelatedProductManage() {
    const {
        columns,
        rowSelection,
        listResponse,
        isLoading,
        selectedRowKeys,
        title,
        setTitle,
        setIsModalOpen,
        selectedRecord,
        handleDelete,
        handleUpdate,
        filter,
        handleCreate,
        handleCreateFromModal,
        isModalAddOpen,
        setIsModalAddOpen,
        isModalOpen,
        productId,
        handleSearch,
        handleTableChange,
    } = useRelatedProductViewModel()
    return (
        <>  
            <Collapse defaultActiveKey={['1']} className='mb-6'>
            <Panel header='Related Products' key='1'>
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
                            pageSize: 6,
                            total: listResponse.totalPages * 6,
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                        }}
                    />
                )}
                <RelatedProductAction handleCreate={handleCreate} onSearch={handleSearch} handleDelete={handleDelete} selectedRows={selectedRowKeys}/>
                <ModalUpdate
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                handleUpdate={handleUpdate}
                title={title}
                setTitle={setTitle}
                selectedRecord={selectedRecord}
                />
                <ModalAdd
                    isModalAddOpen={isModalAddOpen}
                    setIsModalAddOpen={setIsModalAddOpen}
                    handleCreateFromModal={handleCreateFromModal}
                    productId={parseInt(productId ?? "1")}
                    title={title}
                    setTitle={setTitle}
                />
            </div>
            </Panel>
        </Collapse>
        </>
    )
}