import ManagerPath from '@/constants/ManagerPath'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'

const getCustomerRoleColumns = (): TableColumnsType<CustomerRoleResponse> => [
    {
        title: 'Name',
        key: 'name',
        dataIndex: 'name',
    },
    {
        title: 'Active',
        dataIndex: 'active',
        key: 'active',
        render: (active: boolean) => (active ? '✔' : '✘'),
    },
    {
        width: '10%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <Link to={`${ManagerPath.CUSTOMER_ROLE_UPDATE.replace(':id', record.id.toString())}`}>
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    Edit
                </Button>
            </Link>
        ),
    },
]

export default getCustomerRoleColumns
