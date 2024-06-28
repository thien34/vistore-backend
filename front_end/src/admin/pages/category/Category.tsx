import React, { useEffect, useState } from 'react'
import { Button, Table } from 'antd'
import type { TableColumnsType, TableProps } from 'antd'
import { useCategories, useDeleteCategory } from '@/admin/hooks/category.hook'
import { CategoriesResponse, CategoryFilter, CategoryParentResponse } from '@/admin/types/Category'
import { EditOutlined } from '@ant-design/icons'
import CategorySearch from './CategorySearch'
import { Link } from 'react-router-dom'

type TableRowSelection<T> = TableProps<T>['rowSelection']

function getCategoryFullName(category: CategoriesResponse | CategoryParentResponse): string {
    if (!category.categoryParent) {
        return `${category.name}`
    }
    return `${getCategoryFullName(category.categoryParent)} >> ${category.name}`
}

const columns: TableColumnsType<CategoriesResponse> = [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: (_, record) => getCategoryFullName(record),
        sorter: (a, b) => {
            const aName = a.categoryParent ? `${a.categoryParent.name} >> ${a.name}` : a.name
            const bName = b.categoryParent ? `${b.categoryParent.name} >> ${b.name}` : b.name
            return aName.localeCompare(bName)
        },
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
        render: (_, record) => (
            <Link to={`/admin/category/${record.id}/update`}>
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    Edit
                </Button>
            </Link>
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
    const { data, isLoading, error, refetch } = useCategories({ ...filter })
    const deleteCategory = useDeleteCategory()

    useEffect(() => {
        if (error) {
            console.error('Error fetching categories:', error)
        }
    }, [error])

    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
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

    const handleDelete = () => {
        deleteCategory.mutate(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch()
                setSelectedRowKeys([])
            },
        })
    }

    return (
        <>
            <CategorySearch onSearch={handleSearch} selectedRowKeys={selectedRowKeys} handleDelete={handleDelete} />
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
        </>
    )
}
