import React, { useEffect, useState } from 'react'
import { Button, Table } from 'antd'
import type { TableColumnsType, TableProps } from 'antd'
import { useCategories } from '@/admin/hooks/category.hook'
import { CategoriesResponse, CategoryFilter } from '@/admin/types/Category'
import { EditOutlined } from '@ant-design/icons'
import CategorySearch from './CategorySearch'

type TableRowSelection<T> = TableProps<T>['rowSelection']

const columns: TableColumnsType<CategoriesResponse> = [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.length - b.name.length,
    },
    {
        width: '25%',
        title: 'Published',
        dataIndex: 'published',
        key: 'published',
        render: (published) => (published ? 'Yes' : 'No'),
    },
    {
        width: '25%',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: '10%',
        align: 'center',
        title: 'Action',
        key: 'action',
        render: () => (
            <Button
                className='bg-[#374151] border-[#374151] text-white'
                icon={<EditOutlined />}
                // onClick={() => handleEdit(record)}
            >
                Edit
            </Button>
        ),
    },
]
export default function Category() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<CategoryFilter>({
        name: '',
        pageNo: 1,
        pageSize: 6,
        published: undefined,
    })
    const { data, isLoading, error } = useCategories({ ...filter })

    useEffect(() => {
        if (error) {
            console.error('Error fetching categories:', error)
        }
    }, [error])

    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        console.log('selectedRowKeys changed: ', newSelectedRowKeys)
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<CategoriesResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
        }))
    }

    const handleSearch = (newFilter: { name: string; published: boolean | undefined }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            ...newFilter,
            pageNo: 1,
        }))
    }

    return (
        <div>
            <CategorySearch onSearch={handleSearch} />
            {isLoading && <p>Loading ...</p>}
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {data && (
                    <Table
                        rowSelection={rowSelection}
                        bordered
                        columns={columns}
                        dataSource={data.data.items}
                        rowKey='id'
                        pagination={{
                            pageSize: filter.pageSize,
                            total: data.data.totalPage * 6,
                            current: filter.pageNo,
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                        }}
                    />
                )}
            </div>
        </div>
    )
}
