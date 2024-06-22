import React, { useEffect, useState } from 'react'
import { Button, Table } from 'antd'
import type { TableColumnsType, TableProps } from 'antd'
import { useCategories } from '@/admin/hooks/category.hook'
import { CategoriesResponse } from '@/admin/types/Category'
import { EditOutlined } from '@ant-design/icons'
import { useSearchParams } from 'react-router-dom'

type TableRowSelection<T> = TableProps<T>['rowSelection']

const columns: TableColumnsType<CategoriesResponse> = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.length - b.name.length,
    },
    {
        title: 'Published',
        dataIndex: 'published',
        key: 'published',
    },
    {
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: 180,
        title: 'Action',
        key: 'action',
        render: () => (
            <Button
                style={{ backgroundColor: '#374151', borderColor: '#374151', color: '#ffffff' }}
                icon={<EditOutlined />}
                // onClick={() => handleEdit(record)}
            >
                Edit
            </Button>
        ),
    },
]
export default function Category() {
    const [searchParams, setSearchParams] = useSearchParams()
    const pageNo = Number(searchParams.get('pageNo')) || 1

    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState({
        name: '',
        pageNo,
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
        setSearchParams(
            new URLSearchParams({
                pageNo: pagination.current?.toString(),
            }),
        )
    }

    return (
        <div>
            {isLoading && <p>Loading ...</p>}
            {data && (
                <Table
                    style={{ marginBottom: 15, marginTop: 25 }}
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
    )
}
