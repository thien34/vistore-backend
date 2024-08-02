import { Button, Modal, Table, TableColumnsType, Form, Spin } from 'antd'
import ProductAttributeSearch from '@/pages/productAttribute/ProductAttributeSearch.tsx'
import { useEffect } from 'react'
import useProductAttributeViewModel from './ProductAttribute.vm'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { EditOutlined, EyeOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue'
import { Link } from 'react-router-dom'

export default function ProductAttributeManage() {
    const {
        rowSelection,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
        setShowList,
        isEditing,
        setIsOpenList,
        setDataDetail,
        isOpenList,
        form,
        dataDetail,
        cancel,
    } = useProductAttributeViewModel()
    useEffect(() => {
        handleSearch({ name: '', published: true })
    }, [])

    const getProductAttributeColumns = (): TableColumnsType<ProductAttributeResponse> => [
        {
            width: '40%',
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            width: '50%',
            title: 'Description',
            dataIndex: 'description',
            key: 'description',
        },
        {
            width: '10%',
            align: 'center',
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <div className='flex gap-x-1.5'>
                    <Link to={`/admin/product-attributes/${record?.id}`}>
                        <Button onClick={() => setShowList(record)} type='primary' icon={<EditOutlined />}>
                            Update
                        </Button>
                    </Link>
                    <Button
                        onClick={() => setShowList(record)}
                        className='bg-[#374151] border-[#374151] text-white'
                        icon={<EyeOutlined />}
                    >
                        View
                    </Button>
                </div>
            ),
        },
    ]

    const columnsList = [
        { title: 'Name', dataIndex: 'name', key: 'name', editable: true },
        { title: 'Price Adjustment', dataIndex: 'priceAdjustment', key: 'priceAdjustment', editable: true },
        {
            title: 'Price Adjustment Use Percentage',
            dataIndex: 'priceAdjustmentUsePercentage',
            key: 'priceAdjustmentUsePercentage',
            render: (text: string) => (text ? 'Yes' : 'No'),
            editable: true,
        },
        { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment', editable: true },
        { title: 'Cost', dataIndex: 'cost', key: 'cost', editable: true },
        {
            title: 'Pre-selected',
            dataIndex: 'isPreSelected',
            key: 'isPreSelected',
            render: (text: string) => (text ? 'Yes' : 'No'),
            editable: true,
        },
        { title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder', editable: true },
    ]

    const mergedColumnsList = columnsList.map(
        (col: { editable: boolean; dataIndex: string | number; title: string }) => {
            if (!col.editable) {
                return col
            }
            return {
                ...col,
                onCell: (record: PredefinedProductAttributeValueRequest) => ({
                    record,
                    inputType:
                        col.dataIndex === 'priceAdjustment' ||
                        col.dataIndex === 'weightAdjustment' ||
                        col.dataIndex === 'cost' ||
                        col.dataIndex === 'displayOrder'
                            ? 'number'
                            : 'text',
                    dataIndex: col.dataIndex,
                    title: col.title,
                    editing: isEditing(record),
                }),
            }
        },
    )

    return (
        <>
            <Modal
                width={'80%'}
                footer={null}
                onCancel={() => {
                    setIsOpenList(false)
                    setDataDetail([])
                }}
                open={isOpenList}
            >
                <div className={'py-5 text-[24px] font-bold'}>List Predefined Values</div>
                <Form form={form} component={false}>
                    <Table
                        rowKey='id'
                        bordered
                        columns={mergedColumnsList}
                        dataSource={dataDetail}
                        pagination={{ onChange: cancel }}
                    />
                </Form>
            </Modal>
            <ProductAttributeSearch
                onSearch={handleSearch}
                selectedRowKeys={selectedRowKeys}
                handleDelete={handleDelete}
            />
            {isLoading && (
                <div
                    style={{
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        backgroundColor: 'rgba(0, 0, 0, 0.5)',
                        zIndex: 9999,
                    }}
                >
                    <Spin size='small'>
                        <Spin size='large' tip='Loading...'></Spin>
                    </Spin>
                </div>
            )}
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        rowSelection={rowSelection}
                        columns={getProductAttributeColumns()}
                        dataSource={listResponse.items}
                        pagination={{
                            current: (filter.pageNo ?? 1) + 1,
                            pageSize: filter.pageSize ?? 6,
                            total: listResponse.totalPages * (filter.pageSize ?? 6),
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                        }}
                    />
                )}
            </div>
        </>
    )
}
