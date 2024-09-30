import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'
import { AddressResponse } from '@/model/Address.ts'
import ManagerPath from '@/constants/ManagerPath.ts'

const getAddressColumns = (): TableColumnsType<AddressResponse> => [
    {
        title: 'First Name',
        dataIndex: 'firstName',
        key: 'firstName',
    },
    {
        title: 'Last Name',
        dataIndex: 'lastName',
        key: 'lastName',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'Phone',
        dataIndex: 'phoneNumber',
        key: 'phoneNumber',
    },
    {
        title: 'Address',
        dataIndex: 'addressName',
        key: 'addressName',
    },
    {
        width: '10%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <Link
                to={`${ManagerPath.ADDRESS_UPDATE.replace(':id', record.id.toString())}?customerId=${record.customerId}`}
            >
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    Edit
                </Button>
            </Link>
        ),
    },
]

export default getAddressColumns
