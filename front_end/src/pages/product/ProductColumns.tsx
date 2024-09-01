import AppActions from '@/constants/AppActions '
import { ProductResponse } from '@/model/Product'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Image } from 'antd'
import { Link } from 'react-router-dom'

export const getProductColumns = (): TableColumnsType<ProductResponse> => [
    {
        align: 'center',
        title: 'Picture',
        dataIndex: 'imageUrl',
        key: 'imageUrl',
        render: (imageUrl: string) => <Image src={imageUrl} alt='Product' width={50} height={50} preview={false} />,
    },
    {
        align: 'center',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        align: 'center',
        title: 'SKU',
        dataIndex: 'sku',
        key: 'sku',
    },
    {
        align: 'center',
        title: 'Price',
        dataIndex: 'unitPrice',
        key: 'unitPrice',
    },
    {
        align: 'center',
        title: 'Stock quantity',
        dataIndex: 'minStockQuantity',
        key: 'minStockQuantity',
    },
    {
        align: 'center',
        title: 'Published',
        dataIndex: 'published',
        key: 'published',
        render: (text: boolean) => (text ? '✔' : '✘'),
    },
    {
        width: '10%',
        align: 'center',
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Link to={`/admin/categories/${record.id}/update`}>
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    {AppActions.EDIT}
                </Button>
            </Link>
        ),
    },
]
