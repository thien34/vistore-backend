import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
const getProductAttributeColumns = ({ setShowList }): TableColumnsType<ProductAttributeResponse> => [
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

export default getProductAttributeColumns
