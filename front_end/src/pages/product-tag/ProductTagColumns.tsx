import { Button, TableColumnsType } from 'antd'
import { EditOutlined } from '@ant-design/icons'
import { ProductTagResponse } from '@/model/ProductTag'
import AppActions from '@/constants/AppActions'

const getProductTagColumns = (
    handleEdit: (record: ProductTagResponse) => void,
): TableColumnsType<ProductTagResponse> => [
    {
        title: 'Tag name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.length - b.name.length,
    },
    {
        title: 'Tagged products',
        dataIndex: 'productId',
        width: 190,
        key: 'productId',
        sorter: (a, b) => a.productId - b.productId,
    },
    {
        width: 180,
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Button className='default-btn-color' icon={<EditOutlined />} onClick={() => handleEdit(record)}>
                {AppActions.EDIT}
            </Button>
        ),
    },
]

export default getProductTagColumns
