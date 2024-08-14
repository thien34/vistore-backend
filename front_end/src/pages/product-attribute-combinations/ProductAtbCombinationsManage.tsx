import AppActions from '@/constants/AppActions '
import { EditOutlined } from '@ant-design/icons'
import { Button, Table } from 'antd'
import ProductAtbCombinationsModal from './ProductAtbCombinationsModal'

import {
    ProductAttributeCombinationRequest,
    ProductAttributeCombinationResponse,
} from '@/model/ProductAttributeCombination'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import useProductAtbCombinationsViewModel from './ProductAtbCombinations.vm'

export default function ProductAtbCombinationsManage() {
    const columns = [
        {
            title: 'Attributes',
            dataIndex: 'attributesXml',
            key: 'attributesXml',
            render: (attributesXml: string) => {
                const attributes = JSON.parse(attributesXml).attributes
                return (
                    <div>
                        {attributes.map((attr: any) => {
                            const key = Object.keys(attr)[0]
                            const value = attr[key]
                            return (
                                <div key={key}>
                                    <strong>{key ? key.toUpperCase() + ':' : ''}</strong> {value || ''}
                                </div>
                            )
                        })}
                    </div>
                )
            },
        },
        {
            title: 'Stock quantity',
            dataIndex: 'stockQuantity',
            key: 'stockQuantity',
        },
        {
            title: 'Allow out of stock',
            dataIndex: 'allowOutOfStockOrders',
            key: 'allowOutOfStockOrders',
            render: (value: boolean) => (value ? 'Yes' : 'No'),
        },
        {
            title: 'SKU',
            dataIndex: 'sku',
            key: 'sku',
        },
        {
            title: 'Manufacturer part number',
            dataIndex: 'manufacturerPartNumber',
            key: 'manufacturerPartNumber',
        },
        {
            title: 'GTIN',
            dataIndex: 'gtin',
            key: 'gtin',
        },
        {
            title: 'Overridden price',
            dataIndex: 'overriddenPrice',
            key: 'overriddenPrice',
            render: (value: number) => `$${value.toFixed(2)}`,
        },
        {
            title: 'Notify admin for quantity below',
            dataIndex: 'minStockQuantity',
            key: 'minStockQuantity',
        },
        {
            title: 'Picture',
            dataIndex: 'pictureUrl',
            key: 'pictureUrl',
            render: (url: string) => (url ? <img src={url} alt='Product' style={{ width: 50 }} /> : ''),
        },
        {
            title: 'Edit',
            key: 'edit',
            render: (_: any, record: ProductAttributeCombinationRequest) => (
                <Button
                    onClick={() => handleEdit(record)}
                    className='bg-[#475569] text-white border-[#475569]'
                    icon={<EditOutlined />}
                >
                    {AppActions.EDIT}
                </Button>
            ),
        },
        {
            title: 'Delete',
            key: 'delete',
            render: (_: any, record: ProductAttributeCombinationRequest) => (
                <Button onClick={() => handleDelete(record.id)} className='bg-[#475569] text-white border-[#475569]'>
                    {AppActions.DELETE}
                </Button>
            ),
        },
    ]

    const { data } = useGetByIdApi<ProductAttributeCombinationResponse[]>(
        ProductAtbCombinationsConfig.resourceUrlByProductId,
        ProductAtbCombinationsConfig.resourceUrlByProductId,
        1,
    )

    const { open, setOpen, handleDelete, handleEdit, selectedRecord, handleOpenModal } =
        useProductAtbCombinationsViewModel()
    return (
        <div>
            <Table bordered dataSource={data} columns={columns} />
            <div className='bg-[#d4d2d2c0] h-24 flex  items-center  pl-5 rounded-lg'>
                <Button type='primary' onClick={() => handleOpenModal()}>
                    Add combination
                </Button>
            </div>
            <ProductAtbCombinationsModal selectedRecord={selectedRecord} isModalOpen={open} setIsModalOpen={setOpen} />
        </div>
    )
}
