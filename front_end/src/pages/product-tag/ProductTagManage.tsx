import useGetAllApi from '@/hooks/use-get-all-api'
import ProductTagConfigs from './ProductTagConfigs'
import { Button, Empty, Table, TableColumnsType, theme } from 'antd'
import { EditOutlined } from '@ant-design/icons'
import ModalAddUpdate from './ModalAddUpdate'
import useProductTagCreateViewModel from './ProductTag.vm'
import { ProductTagSearch } from './ProductTagSearch'
import { ProductTagResponse } from '@/model/ProductTag'
import AppActions from '@/constants/AppActions '

export default function ProductTagManage() {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken()

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
        handleEdit,
        filter,
        handleTableChange,
    } = useProductTagCreateViewModel()

    const { data: listResponse } = useGetAllApi<ProductTagResponse>(
        ProductTagConfigs.resourceUrl,
        ProductTagConfigs.resourceKey,
        filter,
    )

    const columns: TableColumnsType<ProductTagResponse> = [
        {
            title: 'Tag name',
            dataIndex: 'name',
            key: 'name',
            sorter: (a, b) => a.name.length - b.name.length,
        },
        {
            title: 'Tagged products',
            dataIndex: 'productId',
            width: 190,
            key: 'productId',
            sorter: (a, b) => a.productId - b.productId,
        },
        {
            width: 180,
            title: 'Action',
            key: 'action',
            render: (_, _record) => (
                <Button
                    style={{ backgroundColor: '#374151', borderColor: '#374151', color: '#ffffff' }}
                    icon={<EditOutlined />}
                    onClick={() => handleEdit(_record)}
                >
                    {AppActions.EDIT}
                </Button>
            ),
        },
    ]

    return (
        <div>
            <ProductTagSearch
                selectedRowKeys={selectedRowKeys}
                onSearch={handleSearch}
                showModal={showModal}
                handleDelete={handleDelete}
            />
            <div
                style={{
                    padding: 24,
                    minHeight: 330,
                    background: colorBgContainer,
                    borderRadius: borderRadiusLG,
                }}
            >
                {listResponse?.items ? (
                    <Table
                        rowKey='id'
                        style={{ marginBottom: 15, marginTop: 25 }}
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
