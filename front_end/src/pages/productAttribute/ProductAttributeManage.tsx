import { Button, Modal, Table, TableColumnsType } from 'antd'
import ProductAttributeSearch from '@/pages/productAttribute/ProductAttributeSearch.tsx'
import { useState } from 'react'
import useProductAttributeViewModel from './ProductAttribute.vm'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { EditOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue'

export default function ProductAttributeManage() {
    const {
        rowSelection,
        // columns,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    } = useProductAttributeViewModel()
    const [isOpenList, setIsOpenList] = useState(false)
    const [dataDetail, setDataDetail] = useState<Array<PredefinedProductAttributeValueRequest>>([])
    const getProductAttributeColumns = (): TableColumnsType<ProductAttributeResponse> => [
        {
            width: '40%',
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            // render: (_, record) => getCategoryFullName(record),
            // sorter: (a, b) => {
            //     const aName = a.categoryParent ? `${a.categoryParent.name} >> ${a.name}` : a.name
            //     const bName = b.categoryParent ? `${b.categoryParent.name} >> ${b.name}` : b.name
            //     return aName.localeCompare(bName)
            // },
        },
        {
            width: '50%',
            title: 'Description',
            dataIndex: 'description',
            key: 'description',
            // render: (published) => (published ? 'Yes' : 'No'),
        },
        // {
        //     width: '25%',
        //     title: 'Display Order',
        //     dataIndex: 'displayOrder',
        //     key: 'displayOrder',
        //     sorter: (a, b) => a.displayOrder - b.displayOrder,
        // },
        {
            width: '10%',
            align: 'center',
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <Button
                    onClick={() => setShowList(record)}
                    className='bg-[#374151] border-[#374151] text-white'
                    icon={<EditOutlined />}
                >
                    Xem chi tiết
                </Button>
            ),
        },
    ]

    const setShowList = (record: ProductAttributeResponse) => {
        setIsOpenList(true)
        setDataDetail(record?.values)
    }

    const columnsList = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Price Adjustment', dataIndex: 'priceAdjustment', key: 'priceAdjustment' },
        {
            title: 'Price Adjustment Use Percentage',
            dataIndex: 'priceAdjustmentUsePercentage',
            key: 'priceAdjustmentUsePercentage',
            render: (text: string) => (text ? 'Yes' : 'No'),
        },
        { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment' },
        { title: 'Cost', dataIndex: 'cost', key: 'cost' },
        {
            title: 'Pre-selected',
            dataIndex: 'isPreSelected',
            key: 'isPreSelected',
            render: (text: string) => (text ? 'Yes' : 'No'),
        },
        { title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder' },
    ]

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
                <div>List Predefined Values</div>
                <Table
                    rowKey='id'
                    bordered
                    // rowSelection={rowSelection}
                    columns={columnsList}
                    dataSource={dataDetail}
                    // pagination={{
                    //     current: filter.pageNo ?? 1,
                    //     pageSize: filter.pageSize ?? 6,
                    //     // total: listResponse.totalPages * (filter.pageSize ?? 6),
                    //     onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                    // }}
                />
            </Modal>
            <ProductAttributeSearch
                onSearch={handleSearch}
                selectedRowKeys={selectedRowKeys}
                handleDelete={handleDelete}
            />
            {isLoading && <p>Loading ...</p>}
            <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        rowSelection={rowSelection}
                        columns={getProductAttributeColumns()}
                        dataSource={listResponse.items}
                        pagination={{
                            current: filter.pageNo ?? 1,
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
