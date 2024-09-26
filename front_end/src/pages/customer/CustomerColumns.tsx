import ManagerPath from '@/constants/ManagerPath'
import { CustomerResponse } from '@/model/Customer'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'

const getCustomerColumns = (): TableColumnsType<CustomerResponse> => [
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'Name',
        key: 'name',
        render: (customer: CustomerResponse) => `${customer.firstName} ${customer.lastName}`,
    },
    {
        title: 'Active',
        dataIndex: 'active',
        key: 'active',
        render: (active: boolean) => (active ? '✔' : '✘'),
    },
    {
        title: 'Customer Roles',
        dataIndex: 'customerRoles',
        key: 'customerRoles',
        render: (roles: number[]) => roles.join(', '),
    },
    {
        width: '10%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <Link to={`${ManagerPath.CUSTOMER_EDIT.replace(':id', record.id.toString())}`}>
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    Edit
                </Button>
            </Link>
        ),
    },
]

export default getCustomerColumns
