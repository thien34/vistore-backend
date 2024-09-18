import AppActions from '@/constants/AppActions'
import ManagerPath from '@/constants/ManagerPath'
import { RelatedProductResponse } from '@/model/RelatedProduct'
import { EditOutlined, EyeOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'

const getRelatedProductColumns = (
    handleEdit: (record: RelatedProductResponse) => void
) : TableColumnsType<RelatedProductResponse> => [
    {
        width: '35%',
        title: 'Product',
        dataIndex: 'nameProduct2',
        key: 'nameProduct2',
    },
    {
        width: '15%',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder
    },
    {
        width: '20%',
        title: 'View',
        key: 'view',
        render: (_, record) => (
            <Link to ={`${ManagerPath.PRODUCT_EDIT.replace(':productId',record?.product2Id.toString())}`}>
                <Button type='link'  icon={<EyeOutlined />}>
                </Button>
            </Link>
        ),
    },
    {
        width: '20%',
        title: 'Edit',
        key: 'edit',
        render: (_, record) => (
                <Button type='primary'   onClick={() => handleEdit(record)} icon={<EditOutlined />}>                  
                    {AppActions.EDIT}
                </Button>
        ),
    },
    
]
export default getRelatedProductColumns