import ProductTagService from '@/admin/apis/ProductTagService'
import { ProductTagRequest, ProductTagResponse, ProductTagResponseWithPage } from '@/admin/types/ProductTag'
import { Button, Empty, Table, TableColumnsType, theme } from 'antd'
import { useEffect, useState, useCallback } from 'react'
import ModalAddAndUpdate from './ModalAddAndUpdate'
import { ProductTagSearch } from './ProductTagSearch'
import { EditOutlined } from '@ant-design/icons'

export default function ProductTag() {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken()
    const [tags, setTags] = useState<ProductTagResponseWithPage['data']['items']>([])
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [totalPage, setTotalPage] = useState<number>(0)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [selectedTag, setSelectedTag] = useState<ProductTagResponse | null>(null)
    const [title, setTitle] = useState('Create Product Tag')
    const [filter, setFilter] = useState({
        name: '',
        pageNo: 0,
        pageSize: 6,
        sortBy: 'id',
    })

    const fetchTags = useCallback(async () => {
        try {
            const response = await ProductTagService.getAll(filter)

            const tagsWithKey = response.data.data.items.map((item) => ({
                ...item,
                key: item.id.toString(),
            }))
            if (tagsWithKey.length === 0) {
                setSelectedRowKeys([])
            }
            setTags(tagsWithKey)
            setTotalPage(response.data.data.totalPage + 5)
        } catch (error) {
            console.error('Error fetching product tags:', error)
        }
    }, [filter])

    useEffect(() => {
        fetchTags()
    }, [fetchTags])

    const handleSearch = (term: string) => {
        if (term !== filter.name) {
            setFilter((prevFilter) => ({
                ...prevFilter,
                name: term,
                pageNo: 0,
            }))
        }
    }

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current - 1,
            pageSize: pagination.pageSize,
        }))
    }

    const handleCreate = async (productTag: ProductTagRequest) => {
        setIsModalOpen(true)

        const response = await ProductTagService.create(productTag)
        if (response.status === 200) {
            fetchTags()
        }
    }

    const handleEdit = (data: ProductTagResponse) => {
        setSelectedTag(data)
        setTitle('Update Product Tag')
        setIsModalOpen(true)
    }

    const handleDelete = async () => {
        const selectedIds = selectedRowKeys.map((key) => Number(key))

        const response = await ProductTagService.delete(selectedIds)
        if (response.status === 200) {
            fetchTags()
        }
    }

    const onSelectChange = async (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }
    const rowSelection = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    const showModal = () => {
        setIsModalOpen(true)
    }

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
            render: (_, record) => (
                <Button
                    style={{ backgroundColor: '#374151', borderColor: '#374151', color: '#ffffff' }}
                    icon={<EditOutlined />}
                    onClick={() => handleEdit(record)}
                >
                    Edit
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
                {tags.length > 0 ? (
                    <Table
                        style={{ marginBottom: 15, marginTop: 25 }}
                        rowSelection={rowSelection}
                        bordered
                        columns={columns}
                        dataSource={tags}
                        pagination={{
                            current: filter.pageNo + 1,
                            pageSize: filter.pageSize,
                            total: totalPage,
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize }),
                        }}
                    />
                ) : (
                    <Empty />
                )}
            </div>
            <ModalAddAndUpdate
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
