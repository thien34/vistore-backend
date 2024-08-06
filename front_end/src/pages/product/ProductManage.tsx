import React from 'react'
import { Table, Spin, Alert } from 'antd'
import { useNavigate } from 'react-router-dom'
import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductResponse } from '@/model/Product'
import ProductConfigs from '@/pages/product/ProductConfigs'

const { Column } = Table

export default function ProductManage() {
    const navigate = useNavigate()
    const {
        data: listResponse,
        isLoading,
        error,
    } = useGetAllApi<ProductResponse>(ProductConfigs.resourceUrl, ProductConfigs.resourceKey)

    if (isLoading) {
        return <Spin tip='Loading products...' />
    }

    if (error) {
        return <Alert message='Error' description='Failed to fetch products.' type='error' showIcon />
    }

    const dataSource = listResponse?.items || []

    const handleRowClick = (record) => {
        navigate(`/admin/products/product-spec-attribute-mapping/productId/${record.id}`)
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Table
                dataSource={dataSource}
                rowKey='id'
                bordered
                pagination={{ pageSize: 6 }}
                onRow={(record) => {
                    return {
                        onClick: () => handleRowClick(record),
                    }
                }}
            >
                <Column title='ID' dataIndex='id' key='id' />
                <Column title='Name' dataIndex='name' key='name' />
            </Table>
        </div>
    )
}
