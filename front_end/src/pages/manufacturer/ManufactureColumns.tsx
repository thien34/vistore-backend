import AppActions from '@/constants/AppActions'
import { ManufacturerResponse } from '@/model/Manufacturer'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'
const getManufactureColumns = (): TableColumnsType<ManufacturerResponse> => [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: (_, record) => <span>{record.name}</span>,
        sorter: (a, b) => {
            // Sort by 'name'
            return a.name.localeCompare(b.name)
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
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Link to={`/admin/manufacturers/${record.id}/update`}>
                <Button className='default-btn-color' icon={<EditOutlined />}>
                    {AppActions.EDIT}
                </Button>
            </Link>
        ),
    },
]
export default getManufactureColumns
