import AppActions from '@/constants/AppActions'
import { ProductResponse } from '@/model/Product'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType, Image } from 'antd'
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
        title: 'Product name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'SKU',
        dataIndex: 'sku',
        key: 'sku',
    },
    {
        title: 'Price',
        dataIndex: 'unitPrice',
        key: 'unitPrice',
    },
    {
        title: 'Stock quantity',
        dataIndex: 'minStockQuantity',
        key: 'minStockQuantity',
    },
    {
        width: '10%',
        align: 'center',
        title: 'Published',
        dataIndex: 'published',
        key: 'published',
        render: (published: boolean) => (published ? '✔' : '✘'),
    },
    {
        width: '10%',
        align: 'center',
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Link to={`/admin/products/${record.id}`}>
                <Button className='default-btn-color' icon={<EditOutlined />}>
                    {AppActions.EDIT}
                </Button>
            </Link>
        ),
    },
]
