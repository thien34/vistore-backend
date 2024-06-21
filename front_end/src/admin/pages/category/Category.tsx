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
    const pageNo = searchParams.get('pageNo')

    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const { data, isLoading, error } = useCategories({
        name: '',
        pageSize: 6,
        pageNo: pageNo ? parseInt(pageNo) : 0,
        published: undefined,
    })

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

    const onChange: TableProps<CategoriesResponse>['onChange'] = (pagination) => {
        setSearchParams(
            new URLSearchParams({
                pageNo: (pagination.current! - 1)?.toString() ?? '0',
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
                    columns={columns}
                    dataSource={data.data.items}
                    onChange={onChange}
                    rowKey='id'
                    pagination={{
                        pageSize: 6,
                        total: data.data.total * 6,
                        current: data.data.page + 1,
                    }}
                />
            )}
        </div>
    )
}
